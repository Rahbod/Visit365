package com.rahbod.visit365;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.rahbod.visit365.models.DrList;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Step1Activity extends AppCompatActivity {
    JSONObject params ;
    AdapterDrList adapterDrList;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<DrList> DrList ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step1activity);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String a = bundle.getString("Id");
            int id =Integer.parseInt(a);
            try {
                params.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AppController.getInstance().sendRequest("http://visit365.ir/api/getDoctors", params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("TAG", "*********"+response);
                    adapterDrList = new AdapterDrList(getApplicationContext(),DrList);
                    recyclerView.setAdapter(adapterDrList);
                }
            });
        }
    }
}
