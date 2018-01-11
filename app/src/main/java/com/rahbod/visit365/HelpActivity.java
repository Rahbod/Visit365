package com.rahbod.visit365;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.rahbod.visit365.helper.AccessTokenHelper;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        final TextView txtWebView = (TextView) findViewById(R.id.webViewHelp);

        try {
            JSONObject params = new JSONObject();
            params.put("name", "help");

            AppController.getInstance().sendRequest("api/page", params, new Response.Listener<JSONObject>() {
                @SuppressLint("NewApi")
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("status")){
                            String strHtml = response.getString("text");
                            if (Build.VERSION.SDK_INT >= 24)
                                txtWebView.setText(Html.fromHtml(strHtml, Html.FROM_HTML_MODE_COMPACT));
                            else
                                txtWebView.setText(Html.fromHtml(strHtml));
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
        super.onBackPressed();
        finish();
    }

    public void goToIndex_Help(View view) {
        finish();
    }
}
