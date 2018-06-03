package com.example.user.lab1112;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class login extends Activity {
    NetworkHelper networkHelper = new NetworkHelper();
    String responseStr="";
    boolean check = false;
    EditText nameInput;
    EditText passwordInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        nameInput = (EditText) findViewById(R.id.nameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
    public void login(View view) {
        nameInput = (EditText) findViewById(R.id.nameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        String user = String.valueOf(nameInput.getText());
        String pass = String.valueOf(passwordInput.getText());

        networkHelper.login("http://10.3.224.20:8000/login", user, pass, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getApplicationContext(), "Something is wrong. Try again!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseStr = response.body().string();
                if (!responseStr.isEmpty()) {
                    check = true;
                    String result = "";
                    try {
                        JSONObject jobject = new JSONObject(responseStr);
                        result = jobject.getString("username");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(login.this, MainActivity.class).putExtra("current_user", result);
                    startActivity(intent);
                }
                else
                {
                    login.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(login.this, "Something gone wrong. Try again!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

        });
    }


}