package com.rahbod.visit365;

import android.content.Context;
import android.database.Cursor;

public class AccessTokenHelper {
    public static boolean isExpired(Context context) {
        Long now = System.currentTimeMillis() / 1000;

        DbHelper dbHelper = new DbHelper(context);
        Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT * FROM token", null);
        c.moveToFirst();
        String exp = c.getString(c.getColumnIndex("expire_in"));
        Integer expire = Integer.parseInt(exp);

        if (now > (expire - 30))
            return true;
        return false;
    }

    public static String getAccessToken(Context context)
    {
        DbHelper dbHelper = new DbHelper(context);
        Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT * FROM token", null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex("access_token"));
    }

    public static String getRefreshToken(Context context)
    {
        DbHelper dbHelper = new DbHelper(context);
        Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT * FROM token", null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex("refresh_token"));
    }

    public static String getExpireTime(Context context)
    {
        DbHelper dbHelper = new DbHelper(context);
        Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT * FROM token", null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex("expire_in"));
    }
}
