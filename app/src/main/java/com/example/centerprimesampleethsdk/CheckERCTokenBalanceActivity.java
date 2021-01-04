package com.example.centerprimesampleethsdk;

import android.os.Bundle;
import android.text.TextUtils;
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

        /**
         * Using this getTokenBalance function you can check balance of provided walletAddress with smart contract.
         *
         * @param walletAddress - which user want to check it's balance
         * @param password - password of provided password
         * @param contractAddress - contract address of token
         *
         * @return balance
         */

        EthManager ethManager = EthManager.getInstance();
        /**
         * @param infura - Initialize infura
         */
        ethManager.init("https://mainnet.infura.io/v3/a396c3461ac048a59f389c7778f06689");
        binding.checkBtn.setOnClickListener(v -> {

            if (!TextUtils.isEmpty(binding.address.getText().toString())
                    && !TextUtils.isEmpty(binding.walletPassword.getText().toString())
                    && !TextUtils.isEmpty(binding.contractAddress.getText().toString())) {

                String walletAddress = binding.address.getText().toString().trim();
                String password = binding.walletPassword.getText().toString().trim();
                String erc20TokenContractAddress = binding.contractAddress.getText().toString().trim();
                ethManager.getTokenBalance(walletAddress, password, erc20TokenContractAddress, this)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(balance -> {

                            binding.balanceTxt.setText("Token Balance :" + balance.toString());

                        }, error -> {
                            Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(this, "Fill fields!", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
