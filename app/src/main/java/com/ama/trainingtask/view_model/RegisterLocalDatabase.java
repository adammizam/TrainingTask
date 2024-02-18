package com.ama.trainingtask.view_model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RegisterLocalDatabase extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "register_table";

    private static final String COLUMN_REGISTER_ID = "register_id_table";
    private static final String COLUMN_REGISTER_NAME = "register_name_table";
    private static final String COLUMN_REGISTER_EMAIL = "register_email_table";
    private static final String COLUMN_REGISTER_PASSWORD = "register_password_table";


    public RegisterLocalDatabase(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_REGISTER_ID + " TEXT, "
                + COLUMN_REGISTER_NAME + " TEXT, "
                + COLUMN_REGISTER_EMAIL + " TEXT, "
                + COLUMN_REGISTER_PASSWORD +" TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String registerID, String registerEmail, String registerName, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_REGISTER_ID, registerID);
        contentValues.put(COLUMN_REGISTER_NAME, registerName);
        contentValues.put(COLUMN_REGISTER_EMAIL, registerEmail);
        contentValues.put(COLUMN_REGISTER_PASSWORD, password);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        return result != -1;
    }

    public Cursor getDataUser(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_REGISTER_EMAIL + " = '" +
                email + "'";
        return db.rawQuery(query, null);
    }
}
