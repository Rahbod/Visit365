package com.rahbod.visit365;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        final TextView txtWebView = (TextView) findViewById(R.id.webViewHelp);

        try {
            JSONObject params = new JSONObject();
            params.put("name", "about");

            AppController.getInstance().sendRequest("api/page", params, new Response.Listener<JSONObject>() {
                @SuppressLint("NewApi")
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("status")){
                            String strHtml = response.getString("text");

                            txtWebView.setText(Html.fromHtml(strHtml, Html.FROM_HTML_MODE_COMPACT));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Index.class);
        startActivity(intent);
        finish();
    }
    public void goToIndex_About(View view) {
        Intent intent = new Intent(this, Index.class);
        startActivity(intent);
        finish();
    }
}
