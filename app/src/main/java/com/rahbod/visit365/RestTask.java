package com.rahbod.visit365;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class RestTask extends AsyncTask<Void, Void, String> {
    private static final String BASE_URL = "http://visit365.ir/";
    private static final String CLIENT_ID = "UiRgEQt91vL60-8pixMTkxplOB-jAplL24rdfgDFgS6RTSf6eBGuFZ8ckmLXFT0nBRrz_6C5rGbmmY2f";
    private static final String CLIENT_SECRET = "huHxZTH6EZ_3Y8b71wcFetW34aFGc2P1x2H_yHVh9-uxB5lbcF12ds81mM8SB5IDWGZGpasd211";

    private String url;
    private JSONObject params;
    private String tokenType;

    RestTask(String url, JSONObject params, String tokenType) {
        this.url = url;
        this.params = params;
        this.tokenType = tokenType;

    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(Void... urls) {
        try {
            URL url = new URL(BASE_URL + this.url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            if (tokenType.equals("Basic")) {
                String token = Base64.encodeToString(
                        (CLIENT_ID + ":" + CLIENT_SECRET).getBytes(),
                        Base64.NO_WRAP);
                urlConnection.setRequestProperty("Authorization", tokenType + " " + token);
            } else if (tokenType.equals("Bearer")) {
                return null;
                // get access token first
                // urlConnection.setRequestProperty("Authorization", tokenType + " " + access_token);
            }
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);
            urlConnection.setInstanceFollowRedirects(false);
            urlConnection.setUseCaches(false);

            if (params != null) {
                OutputStream os = urlConnection.getOutputStream();
                os.write(params.toString().getBytes("UTF-8"));
                os.close();
            }

            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                String response = stringBuilder.toString();
                Log.v("API-Response", response);
                return response;
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }

    }

    @Override
    protected void onPostExecute(String s) {
    }
}