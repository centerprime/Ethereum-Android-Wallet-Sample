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
import com.example.centerprimesampleethsdk.databinding.ActivityImportWalletKeystoreBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ImportWalletFromKeyStoreActivity extends AppCompatActivity {

    ActivityImportWalletKeystoreBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_import_wallet_keystore);

        EthManager ethManager = EthManager.getInstance();
        /**
         * @param infura - Initialize infura
         */
        ethManager.init("https://mainnet.infura.io/v3/a396c3461ac048a59f389c7778f06689");

        binding.importBtn.setOnClickListener(v -> {
            /**
             * Using this importFromKeyStore function user can import his wallet from keystore.
             *
             * @param keystore - keystore JSON file
             * @param password - password of provided keystore
             * @param Context - activity context
             *
             * @return walletAddress
             */
            String password = binding.password.getText().toString();
            String keystore = binding.keystoreT.getText().toString();
            ethManager.importFromKeystore(keystore, password, this)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(walletAddress -> {
                        /**
                         * if function successfully completes result can be caught in this block
                         */
                        binding.walletAddress.setText(walletAddress);
                        binding.copy.setVisibility(View.VISIBLE);

                    }, error -> {
                        /**
                         * if function fails error can be caught in this block
                         */
                        Toast.makeText(this, "Please insert valid password and keystore.", Toast.LENGTH_SHORT).show();
                    });
        });

        binding.copy.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", binding.walletAddress.getText().toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show();
        });
    }
}
