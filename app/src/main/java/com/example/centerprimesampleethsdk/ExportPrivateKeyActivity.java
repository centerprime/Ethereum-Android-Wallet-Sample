package com.example.centerprimesampleethsdk;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.centerprime.ethereum_client_sdk.EthManager;
import com.example.centerprimesampleethsdk.databinding.ActivityExportPrivateKeyBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ExportPrivateKeyActivity extends AppCompatActivity {
    ActivityExportPrivateKeyBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_export_private_key);

        EthManager ethManager = EthManager.getInstance();
        /**
         * @param infura - Initialize infura
         */
        ethManager.init("https://mainnet.infura.io/v3/a396c3461ac048a59f389c7778f06689");


        binding.button.setOnClickListener(v -> {

            String walletAddress = binding.address.getText().toString();
            if (walletAddress.startsWith("0x")) {
                walletAddress = walletAddress.substring(2);
            }
            /**
             * Using this exportPrivateKey function user can export walletAddresses privateKey.
             *
             * @params walletAddress, password, Context
             *
             * @return privateKey
             */
            String password = binding.password.getText().toString();
            ethManager.exportPrivateKey(walletAddress, password, this)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(privateKey -> {
                        /**
                         * if function successfully completes result can be caught in this block
                         */
                        binding.privateKey.setText(privateKey);
                        binding.copy.setVisibility(View.VISIBLE);

                    }, error -> {
                        /**
                         * if function fails error can be caught in this block
                         */
                        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        binding.copy.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", binding.privateKey.getText().toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Copied!", Toast.LENGTH_SHORT).show();
        });
    }
}
