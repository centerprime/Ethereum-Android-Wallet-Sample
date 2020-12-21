package com.example.centerprimesampleethsdk;

import android.os.Bundle;
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

        /**
         * Using this balanceInEth function you can check balance of provided walletAddress.
         *
         * @params walletAddress
         *
         * @return balance
         */

        EthManager ethManager = EthManager.getInstance();
        ethManager.init("https://mainnet.infura.io/v3/a396c3461ac048a59f389c7778f06689");
        balanceBinding.checkBtn.setOnClickListener(v -> {
            String address = balanceBinding.address.getText().toString();
            if (!address.startsWith("0x")) {
                address = "0x" + address;
            }

            ethManager.balanceInEth(address, this)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(balance -> {

                        balanceBinding.balanceTxt.setText("Eth balance: " + balance.toString());
                        balanceBinding.balanceTxt.setVisibility(View.VISIBLE);
                     //   Toast.makeText(this, "Eth Balance : " + balance, Toast.LENGTH_SHORT).show();

                    }, error -> {
                        balanceBinding.balanceTxt.setVisibility(View.INVISIBLE);
                        Toast.makeText(this, "Please insert valid address.", Toast.LENGTH_SHORT).show();

                    });
        });
    }
}
