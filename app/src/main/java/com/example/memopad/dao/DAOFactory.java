package com.example.memopad.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DAOFactory extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "memoDatabase.db";

    public static final String TABLE_MEMOS = "memos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MEMO = "memo";

    public static final String QUERY_CREATE_MEMOS_TABLE =
            "CREATE TABLE " + TABLE_MEMOS +
                    " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_MEMO + " TEXT NOT NULL)";

    public static final String QUERY_DELETE_MEMOS_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_MEMOS;

    public DAOFactory(@Nullable Context context) {
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

    public MemoDAO getMemoDao() {
        return new MemoDAO(this);
    }
}