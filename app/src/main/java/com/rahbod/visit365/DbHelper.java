package com.rahbod.visit365;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "visit", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS token(id integer primary key autoincrement,access_token VARCHAR(512),refresh_token VARCHAR(512), expire_in VARCHAR(20));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Integer deleteSound(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("sounds",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }
}