package com.example.centerprimesampleethsdk;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.centerprime.ethereum_client_sdk.EthManager;
import com.example.centerprimesampleethsdk.databinding.ActivityCheckBalanceBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CheckBalanceActivity extends AppCompatActivity {
    ActivityCheckBalanceBinding balanceBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        balanceBinding = DataBindingUtil.setContentView(this, R.layout.activity_check_balance);

        EthManager ethManager = EthManager.getInstance();
        /**
         * @param infura - Initialize infura
         */
        ethManager.init("https://mainnet.infura.io/v3/a396c3461ac048a59f389c7778f06689");
        balanceBinding.checkBtn.setOnClickListener(v -> {
            String address = balanceBinding.address.getText().toString();
            if (!address.startsWith("0x")) {
                address = "0x" + address;
            }

            if (!TextUtils.isEmpty(balanceBinding.address.getText().toString())) {

                /**
                 * Using this balanceInEth function you can check balance of provided walletAddress.
                 *
                 * @param walletAddress - which user want to check it's balance
                 *
                 * @return if the function completes successfully returns balance of provided wallet address or returns error name
                 */
                ethManager.balanceInEth(address, this)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(balance -> {
                            /**
                             * if function successfully completes result can be caught in this block
                             */
                            balanceBinding.balanceTxt.setText("Eth balance: " + balance.toString());
                            balanceBinding.balanceTxt.setVisibility(View.VISIBLE);

                        }, error -> {
                            /**
                             * if function fails error can be caught in this block
                             */
                            balanceBinding.balanceTxt.setVisibility(View.INVISIBLE);
                            Toast.makeText(this, "Please insert valid address.", Toast.LENGTH_SHORT).show();

                        });
            } else {
                Toast.makeText(this, "Please provide wallet address", Toast.LENGTH_SHORT).show();
            }


        });
    }
}
