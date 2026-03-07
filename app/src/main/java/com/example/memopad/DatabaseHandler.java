package com.example.memopad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "memoDatabase.db";
    private static final String TABLE_MEMOS = "memos";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MEMO = "memo";

    public static final String QUERY_CREATE_MEMOS_TABLE =
            "CREATE TABLE " + TABLE_MEMOS +
                    " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_MEMO + " TEXT NOT NULL)";

    public static final String QUERY_DELETE_MEMOS_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_MEMOS;

    public static final String QUERY_GET_ALL_MEMOS =
            "SELECT * FROM " + TABLE_MEMOS;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_MEMOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(QUERY_DELETE_MEMOS_TABLE);
        onCreate(db);
    }

    public void addMemo(String memoText) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MEMO, memoText);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_MEMOS, null, values);
        db.close();
    }

    public void deleteMemo(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEMOS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Memo> getAllMemos() {
        List<Memo> memos = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(QUERY_GET_ALL_MEMOS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String memo = cursor.getString(1);
                memos.add(new Memo(id, memo));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return memos;
    }

}