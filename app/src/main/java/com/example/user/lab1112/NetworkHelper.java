package com.example.user.lab1112;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class NetworkHelper {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    Call post(String url, String user, String pass, String realName,  Callback callback) {
        RequestBody body = new FormBody.Builder()
                .add("username", user)
                .add("password", pass)
                .add("realname", realName)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }
    Call login(String url, String user, String pass,  Callback callback) {
        RequestBody body = new FormBody.Builder()
                .add("username", user)
                .add("password", pass)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }
    Call put(String url, String user, String oldpass, String newpass,  Callback callback) {
        RequestBody body = new FormBody.Builder()
                .add("username", user)
                .add("oldpassword", oldpass)
                .add("newpassword", newpass)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }
    Call getlist(String url, Callback callback)  {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }
    Call getuser(String url, String user, Callback callback)  {
        RequestBody body = new FormBody.Builder()
                .add("username", user)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }
    Call deleteuser(String url, Callback callback)  {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }
}
