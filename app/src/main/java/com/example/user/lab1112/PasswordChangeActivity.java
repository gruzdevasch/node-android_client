package com.example.user.lab1112;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PasswordChangeActivity extends AppCompatActivity {
    NetworkHelper networkHelper = new NetworkHelper();

    EditText oldPasswordInput;
    EditText newPasswordInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
    }

    public void changePass(View view) {
        oldPasswordInput = (EditText) findViewById(R.id.oldPasswordInput);
        newPasswordInput = (EditText) findViewById(R.id.newPasswordInput);
        String current_user = getIntent().getStringExtra("current_user");

        String oldpass = String.valueOf(oldPasswordInput.getText());
        String newpass = String.valueOf(newPasswordInput.getText());
        networkHelper.put("http://10.3.224.20:8000/changepass", current_user, oldpass, newpass, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
            }

        });
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
