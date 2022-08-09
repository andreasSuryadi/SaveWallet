package com.example.tabunganku;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tabunganku.api.ApiEndPoint;
import com.example.tabunganku.api.ApiService;
import com.example.tabunganku.request.ChangePasswordRequest;
import com.example.tabunganku.request.WalletRequest;
import com.example.tabunganku.response.ChangePasswordResponse;
import com.example.tabunganku.response.WalletResponse;
import com.example.tabunganku.session.SessionManager;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * Tanggal Pengerjaan       : 7 Agustus 2022
 * NIM                      : 10119918
 * Nama                     : Andreas Suryadi
 * Kelas                    : IF-10K
 *
 */

public class AddTransactionActivity extends AppCompatActivity {
    private EditText moneyText, descriptionText;
    private Button btnAddTransaction;
    private ProgressBar loadingPb;
    public RadioGroup typeRadioGroup;
    public RadioButton typeRadioButton;
    private static final int successCode=200;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        Objects.requireNonNull(getSupportActionBar()).hide();

        moneyText = findViewById(R.id.money_text);
        descriptionText = findViewById(R.id.description_text);
        btnAddTransaction = findViewById(R.id.btn_add_transaction);
        typeRadioGroup = findViewById(R.id.transaction_type);
        loadingPb = findViewById(R.id.idLoadingPB);

        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (moneyText.getText().toString().isEmpty()) {
                    Toast.makeText(AddTransactionActivity.this, "Field money is empty", Toast.LENGTH_SHORT).show();
                    return;
                } else if (descriptionText.getText().toString().isEmpty()) {
                    Toast.makeText(AddTransactionActivity.this, "Field description is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                int selectedId = typeRadioGroup.getCheckedRadioButtonId();
                typeRadioButton = findViewById(selectedId);

                Log.d("radio", typeRadioButton.getText().toString());

                postData(typeRadioButton.getText().toString(), moneyText.getText().toString(), descriptionText.getText().toString());
            }
        });
    }

    private void postData(String type, String money, String description) {
        loadingPb.setVisibility(View.VISIBLE);

        ApiService api = ApiEndPoint.getClient().create(ApiService.class);

        WalletRequest walletRequest = new WalletRequest(type, money, description);
        sessionManager = new SessionManager(this);
        String token = "Bearer " + sessionManager.fetchAuthToken();

        Call<WalletResponse> call = api.saveWallet(token, walletRequest);
        call.enqueue(new Callback<WalletResponse>() {
            @Override
            public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {
                int statusCode = response.code();
                loadingPb.setVisibility(View.GONE);

                if (statusCode == successCode) {
                    moneyText.setText("");
                    descriptionText.setText("");

                    new Handler().postDelayed(() -> {
                        Intent intent = new Intent(AddTransactionActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }, 100);
                } else {
                    Toast.makeText(AddTransactionActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WalletResponse> call, Throwable t) {
                Toast.makeText(AddTransactionActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
