package com.example.centerprimesampleethsdk;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.centerprime.ethereum_client_sdk.EthManager;
import com.example.centerprimesampleethsdk.databinding.ActivityErc20TokenBalanceBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CheckERCTokenBalanceActivity extends AppCompatActivity {
    ActivityErc20TokenBalanceBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_erc20_token_balance);

        EthManager ethManager = EthManager.getInstance();
        ethManager.init("https://mainnet.infura.io/v3/a396c3461ac048a59f389c7778f06689");
        //ethManager.init("https://ropsten.infura.io/v3/a396c3461ac048a59f389c7778f06689");
        binding.checkBtn.setOnClickListener(v -> {

            String walletAddress = binding.address.getText().toString().trim();
            String password = binding.walletPassword.getText().toString().trim();
            String erc20TokenContractAddress = "0x913903bD683914288FDaa812cC2f51F243cCC731";
            ethManager.getTokenBalance(walletAddress, password, erc20TokenContractAddress, this)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(balance -> {

                        binding.balanceTxt.setText("Token Balance :" + balance.toString());
                        Toast.makeText(this, "Token Balance : " + balance, Toast.LENGTH_SHORT).show();

                    }, error -> {
                        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
