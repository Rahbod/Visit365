package com.rahbod.visit365.Fragment;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.android.volley.Response;
import com.rahbod.visit365.AppController;
import com.rahbod.visit365.Login;
import com.rahbod.visit365.R;
import com.rahbod.visit365.helper.AccessTokenHelper;
import com.rahbod.visit365.helper.SessionManager;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class UserInfoDialogFragment extends DialogFragment {
    HashMap<String, String> userInfo;
    EditText etNationalCode;
    EditText etFirstName;
    EditText etLastName;
    Button btnSave;
    Button btnLogout;
    private dialogDoneListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_profile, container);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.radius);
        userInfo = SessionManager.getUserInfo(getContext());

        etNationalCode = (EditText) view.findViewById(R.id.etNationalCode);
        etNationalCode.setText(userInfo.get("nationalCode"));

        etFirstName = (EditText) view.findViewById(R.id.etFirstName);
        etFirstName.setText(userInfo.get("firstName"));

        etLastName = (EditText) view.findViewById(R.id.etLastName);
        etLastName.setText(userInfo.get("lastName"));

        btnLogout = (Button) view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccessTokenHelper.logout(getContext());
                restart();
            }
        });

        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSave.setText("در حال ثبت...");
                btnSave.setEnabled(false);
                try {
                    JSONObject params = new JSONObject();
                    JSONObject userParams = new JSONObject();
                    userParams.put("first_name", etFirstName.getText().toString());
                    userParams.put("last_name", etLastName.getText().toString());
                    userParams.put("national_code", etNationalCode.getText().toString());

                    params.put("profile", userParams);
                    AppController.getInstance().sendAuthRequest("api/editProfile", params, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String message = response.getString("message");
                                if (response.getBoolean("status")){
                                    SessionManager sessionManager = new SessionManager(getContext());
                                    JSONObject user = response.getJSONObject("user");
                                    if(sessionManager.updateUserInfo(user.getString("firstName"), user.getString("lastName"), user.getString("mobile"), user.getString("email"), user.getString("phone"), user.getString("address"), user.getString("zipCode"), user.getString("nationalCode"), user.getString("avatar"))) {
                                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                        mListener.onDone(true);
                                        getDialog().dismiss();
                                    }else
                                        Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                                }else
                                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                btnSave.setText("ثبت");
                                btnSave.setEnabled(true);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        
        return view;
    }
    @Override
    public void onStart()
    {
        super.onStart();

        if (getDialog() == null)
            return;

        getDialog().getWindow().setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

    }

    public void restart() {
        Intent intent = new Intent(getContext(), Login.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);

        AlarmManager mgr = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);
        getActivity().finish();
        System.exit(2);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (dialogDoneListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement dialogDoneistener");
        }
    }

    public interface dialogDoneListener{
        void onDone(boolean state);
    }
}
