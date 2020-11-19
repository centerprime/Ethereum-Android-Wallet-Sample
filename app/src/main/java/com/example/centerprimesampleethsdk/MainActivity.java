package com.example.centerprimesampleethsdk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.centerprimesampleethsdk.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.createBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CreateWalletActivity.class));
        });

        binding.importBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ImportWalletFromKeyStoreActivity.class));
        });

        binding.checkBalance.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CheckBalanceActivity.class));
        });

        binding.exportKeystore.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ExportKeyStoreActivity.class));
        });

        binding.importByPrivatekey.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ImportByPrivateKeyActivity.class));
        });

        binding.exportPrivateKey.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ExportPrivateKeyActivity.class));
        });

        binding.sendEth.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SendEthActivity.class));
        });
        binding.checkERCTokenkBalance.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CheckERCTokenBalanceActivity.class));
        });
        binding.sendERCToken.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SendERCTokenActivity.class));
        });


    }
}