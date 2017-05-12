package com.example.kvana.retrofitexample.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.kvana.retrofitexample.R;
import com.example.kvana.retrofitexample.adapter.ContactsAdapter;
import com.example.kvana.retrofitexample.model.Contact;
import com.example.kvana.retrofitexample.network.RetrofitHandler;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView rv_details;
    private ContactsAdapter contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_details = (RecyclerView) findViewById(R.id.rv_details);

        RetrofitHandler.getInstance().getContactList().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.raw());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        JSONArray jsonArray = jsonObject.optJSONArray("contacts");
                        List<Contact> users = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            users.add(Contact.fromJson(jsonArray.optJSONObject(i).toString()));
                        }
                        buildList(users);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e(TAG, "onResponse: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void buildList(List<Contact> contacts) {
        if (rv_details.getAdapter() == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rv_details.setLayoutManager(linearLayoutManager);
            contactsAdapter = new ContactsAdapter(this, contacts);
            rv_details.setAdapter(contactsAdapter);
        } else {
            contactsAdapter.notifyData(contacts);
        }
    }
}
