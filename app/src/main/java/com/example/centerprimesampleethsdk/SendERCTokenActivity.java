package com.example.centerprimesampleethsdk;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.centerprime.ethereum_client_sdk.EthManager;
import com.example.centerprimesampleethsdk.databinding.ActivitySendErc20TokenBinding;

import java.math.BigDecimal;
import java.math.BigInteger;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SendERCTokenActivity extends AppCompatActivity {
    ActivitySendErc20TokenBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_send_erc20_token);

        EthManager ethManager = EthManager.getInstance();
        /**
         * @param infura - Initialize infura
         */
        ethManager.init("https://mainnet.infura.io/v3/a396c3461ac048a59f389c7778f06689");
        binding.sendERCToken.setOnClickListener(v -> {

            if (!TextUtils.isEmpty(binding.address.getText().toString().trim()) && !TextUtils.isEmpty(binding.ethAmount.getText().toString().trim())
                    && !TextUtils.isEmpty(binding.gasLimit.getText().toString().trim())
                    && !TextUtils.isEmpty(binding.receiverAddress.getText().toString().trim())
                    && !TextUtils.isEmpty(binding.password.getText().toString().trim())
                    && !TextUtils.isEmpty(binding.contractAddress.getText().toString().trim()) ) {

                String walletAddress = binding.address.getText().toString();
                String password = binding.password.getText().toString();
                BigInteger gasPrice = new BigInteger("30000000000");
                BigInteger gasLimit = new BigInteger(binding.gasLimit.getText().toString());
                BigDecimal tokenAmount = new BigDecimal(binding.ethAmount.getText().toString().trim());
                String receiverAddress = binding.receiverAddress.getText().toString().trim();
                String erc20TokenContractAddress = binding.contractAddress.getText().toString().trim();

                /**
                 * Using this sendToken function you can send ethereum from walletAddress to another walletAddress.
                 *
                 * @param walletAddress - must be provided sender's wallet address
                 * @param password - User must enter password of wallet address
                 * @param gasPrice - gas price: 30000000000
                 * @param gasLimit - gas limit atleast 21000 or more
                 * @param tokenAmount - amount of token
                 * @param etherAmount - amount of ethereum which user want to send
                 * @param receiverWalletAddress - wallet address which is user want to send token
                 * @param Context - activity context
                 *
                 * @return if sending token completes successfully the function returns transactionHash or returns error name
                 */

                ethManager.sendToken(walletAddress, password, gasPrice, gasLimit, tokenAmount, receiverAddress, erc20TokenContractAddress, this)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(tx -> {
                            /**
                             * if function successfully completes result can be caught in this block
                             */
                            Toast.makeText(this, "TX : " + tx, Toast.LENGTH_SHORT).show();

                        }, error -> {
                            /**
                             * if function fails error can be caught in this block
                             */
                            Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            System.out.println("Error: " + error.getMessage());
                        });
            }
        });

    }
}
