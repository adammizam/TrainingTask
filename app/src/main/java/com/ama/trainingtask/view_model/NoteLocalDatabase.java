package com.ama.trainingtask.view_model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteLocalDatabase extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "note_table";

    private static final String COLUMN_NOTE_ID = "note_id_table";
    private static final String COLUMN_NOTE_USER_ID = "note_userid_table";
    private static final String COLUMN_NOTE_TITLE = "note_title_table";
    private static final String COLUMN_NOTE_TEXT = "note_password_table";


    public NoteLocalDatabase(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NOTE_ID + " TEXT, "
                + COLUMN_NOTE_USER_ID + " TEXT, "
                + COLUMN_NOTE_TITLE + " TEXT, "
                + COLUMN_NOTE_TEXT +" TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String noteId, String userId, String noteTitle, String noteText) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NOTE_ID, noteId);
        contentValues.put(COLUMN_NOTE_USER_ID, userId);
        contentValues.put(COLUMN_NOTE_TITLE, noteTitle);
        contentValues.put(COLUMN_NOTE_TEXT, noteText);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        return result != -1;
    }

    public Cursor getAllDataBasedUser(String userId){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NOTE_USER_ID + " = '" +
                userId + "' ORDER BY " + COLUMN_NOTE_TITLE + " ASC";
        return db.rawQuery(query, null);
    }

    public Cursor getNoteBasedUserIdNoteId(String userId, String noteId){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NOTE_USER_ID + " = '" +
                userId + "' AND " + COLUMN_NOTE_ID + " ='" + noteId + "'";
        return db.rawQuery(query, null);
    }

    public void updateNoteText(String noteId, String userId, String noteText){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_NOTE_TEXT +
                " = '" + noteText + "' WHERE " + COLUMN_NOTE_ID + " = '" + noteId + "'" +
                " AND " + COLUMN_NOTE_USER_ID + " = '" + userId + "'";
        db.execSQL(query);
    }

    public void deleteNote(String noteId, String userId){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COLUMN_NOTE_ID + " = '" + noteId + "'" +
                " AND " + COLUMN_NOTE_USER_ID + " = '" + userId + "'";

        db.execSQL(query);
    }
}
