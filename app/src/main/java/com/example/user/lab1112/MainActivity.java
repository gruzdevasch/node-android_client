package com.example.user.lab1112;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public String current_user = "";
    String responseStr = "";
    private ArrayAdapter adapter;
    ArrayList<String> ar = new ArrayList<>();
    String result;
    JSONObject jobject;
    boolean flag = false;
    private ListView lvMain;
    Runnable run = new Runnable() {
        public void run() {
            adapter.notifyDataSetChanged();
            lvMain.invalidateViews();
            lvMain.refreshDrawableState();
        }
    };
    NetworkHelper networkHelper = new NetworkHelper();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setClickable(true);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                String selectedFromList = (String) lvMain.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, UserProfile.class).putExtra("chosen_user", selectedFromList);
                startActivity(intent);

            }
        });
        current_user = getIntent().getStringExtra("current_user");
        if (current_user == null){
            findViewById(R.id.chpbutton).setVisibility(View.GONE);
            findViewById(R.id.textView2).setVisibility(View.GONE);
        }
        else {
            findViewById(R.id.chpbutton).setVisibility(View.VISIBLE);
            findViewById(R.id.textView2).setVisibility(View.VISIBLE);
            TextView tv = ((TextView)findViewById(R.id.textView2));
            tv.append(current_user);
        }
        networkHelper.getlist("http://10.3.224.20:8000/users", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseStr = response.body().string();
                try {
                    JSONArray jarray = new JSONArray(responseStr);
                    for (int x=0; x < jarray.length(); x = x + 1)
                    {
                        jobject = jarray.getJSONObject(x);
                        result = jobject.getString("username");
                        ar.add(result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });


        // создаем адаптер
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, ar);

        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);
    }


    public void openLoginActivity(View view) {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    public void openSignUpActivity(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
    public void openChangePasswordActivity(View view) {
        Intent intent = new Intent(this, PasswordChangeActivity.class).putExtra("current_user", current_user);;
        startActivity(intent);
    }

    public void sortUsers(View view) {
        ar.clear();
        String url = "";
        if (!flag){
            url = "http://10.3.224.20:8000/sortedusers";
            Button p1_button = (Button)findViewById(R.id.sortButton);
            p1_button.setText("Sort it back");
        }
        else {
            url = "http://10.3.224.20:8000/users";

            Button p1_button = (Button)findViewById(R.id.sortButton);
            p1_button.setText("Sort users by name");
        }
        flag = !flag;
        networkHelper.getlist(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseStr = response.body().string();
                try {
                    JSONArray jarray = new JSONArray(responseStr);
                    for (int x=0; x < jarray.length(); x = x + 1)
                    {
                        jobject = jarray.getJSONObject(x);
                        result = jobject.getString("username");
                        ar.add(result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(run);
            }

        });
    }
}

