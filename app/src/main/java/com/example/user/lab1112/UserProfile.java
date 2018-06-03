package com.example.user.lab1112;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserProfile extends AppCompatActivity {

    NetworkHelper networkHelper = new NetworkHelper();
    String current_user;
    String responseStr = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        current_user = getIntent().getStringExtra("chosen_user");

        networkHelper.getuser("http://10.3.224.20:8000/getuser", current_user, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseStr = response.body().string();

                try {
                    JSONObject jobject = new JSONObject(responseStr);
                    String id = jobject.getString("_id");
                    String username = jobject.getString("username");
                    String pass = jobject.getString("password");

                    String realName = jobject.getString("realName");

                    TextView idView = (TextView) findViewById(R.id.textView3);
                    TextView usernameView = (TextView) findViewById(R.id.textView4);
                    TextView passView = (TextView) findViewById(R.id.textView5);

                    TextView realNameView = (TextView) findViewById(R.id.textView6);
                    idView.append(id);
                    usernameView.append(username);
                    passView.append(pass);
                    realNameView.append(realName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void deleteUser(View view) {

        TextView idView = (TextView) findViewById(R.id.textView3);
        String id = idView.getText().toString();
        id = id.replace("id: ", "");
        networkHelper.deleteuser("http://10.3.224.20:8000/users/"+id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseStr = response.body().string();
                Intent intent = new Intent(UserProfile.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
