package com.example.user.lab1112;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SignUp extends Activity {
    NetworkHelper networkHelper = new NetworkHelper();

    EditText nameInput;
    EditText realNameInput;
    EditText passwordInput;
    EditText confirmPasswordInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void signup(View view) {
        nameInput = (EditText) findViewById(R.id.nameInput);

        realNameInput = (EditText) findViewById(R.id.realNameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        confirmPasswordInput = (EditText) findViewById(R.id.confirmPasswordInput);
        if (!passwordInput.getText().toString().equals(confirmPasswordInput.getText().toString())) {
            Toast.makeText(getApplicationContext(), "The passwords do not match", Toast.LENGTH_LONG).show();
        } else {
            String user = String.valueOf(nameInput.getText());
            String pass = String.valueOf(passwordInput.getText());
            String realName = String.valueOf(realNameInput.getText());
            networkHelper.post("http://10.3.224.20:8000/signup", user, pass, realName, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseStr = response.body().string();

                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                    startActivity(intent);
                }

            });
        }
    }
}
