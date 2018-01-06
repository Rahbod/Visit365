package com.rahbod.visit365;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.rahbod.visit365.models.ListTrans;
import com.rahbod.visit365.Adapters.RecyclerAdapterTransaction;
import com.rahbod.visit365.helper.AccessTokenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TransactionActivity extends AppCompatActivity {

    TextView countTrans, sumTrans;
    RecyclerView recTrans;
    RecyclerAdapterTransaction adapter;
    ArrayList<ListTrans> data;
    DrawerLayout drTrans;
    private static final int time =1500;
    private static long BackPressed;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_activity);
        setContentView(R.layout.navigationdraw_trans);

        countTrans = (TextView) findViewById(R.id.countTrans);
        sumTrans = (TextView) findViewById(R.id.sumTrans);

        drTrans = (DrawerLayout) findViewById(R.id.drawer_trans);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationViewTrans);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.credit_card_NavigationView:
                        drTrans.closeDrawer(Gravity.LEFT);
                        finish();
                        break;

                    case R.id.help_NavigationView:
                        Intent goHelp = new Intent(TransactionActivity.this, HelpActivity.class);
                        startActivity(goHelp);
                        finish();
                        break;

                    case R.id.user_NavigationView:
                        Intent goProfile = new Intent(TransactionActivity.this, ProfileUserActivity.class);
                        startActivity(goProfile);
                        finish();
                        break;

                    case R.id.abut_NavigationView:
                        Intent goAbout = new Intent(TransactionActivity.this, AboutActivity.class);
                        startActivity(goAbout);
                        finish();
                        break;

                    case R.id.home_NavigationView:
                        Intent goHome = new Intent(TransactionActivity.this, Index.class);
                        startActivity(goHome);
                        finish();
                        break;
                }

                return true;
            }
        });



        AppController.getInstance().sendAuthRequest("api/transactions", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("status")) ;
                    String count = response.getString("count");
                    String sum = response.getString("sum");

                    data = new ArrayList<>();
                    JSONArray trans = response.getJSONArray("transactions");

                    for (int i = 0; i < trans.length(); i++) {
                        JSONObject obj = trans.getJSONObject(i);
                        String Amount = obj.getString("amount");
                        String Date = obj.getString("date");
                        String Gateway = obj.getString("gateway");
                        String Code = obj.getString("code");

                        PersianDate pdate = new PersianDate(Long.parseLong(Date) * 1000);
                        PersianDateFormat pdformater = new PersianDateFormat("j  F  13y");
                        String strData = pdformater.format(pdate);

                        data.add(new ListTrans(Amount, strData, Gateway, Code));





                        adapter = new RecyclerAdapterTransaction(data, TransactionActivity.this);
                        recTrans = (RecyclerView) findViewById(R.id.recTrans);
                        recTrans.setLayoutManager(new LinearLayoutManager(TransactionActivity.this));
                        recTrans.setAdapter(adapter);
                    }




                    countTrans.setText(count + "  تراکنش");
                    sumTrans.setText(sum + "  تومان");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void openNvTrans(View view) {
        drTrans.openDrawer(Gravity.LEFT);
        drTrans.findViewById(R.id.btnExitProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccessTokenHelper.logout(getApplicationContext());
                restart();
            }
        });
    }
    public void restart() {
        Intent intent = new Intent(this, Login.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);

        AlarmManager mgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);
        finish();
        System.exit(2);
    }

    @Override
    public void onBackPressed() {
        if (time + BackPressed>System.currentTimeMillis()){
            super.onBackPressed();
        }
        else
            Toast.makeText(this, "لطفا کلید برگشت را مجددا فشار دهید.", Toast.LENGTH_SHORT).show();

        BackPressed = System.currentTimeMillis();
    }
}
