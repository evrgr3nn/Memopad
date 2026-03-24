package com.example.memopad.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.memopad.Memo;
import java.util.ArrayList;
import java.util.List;

public class MemoDAO {
    private final DAOFactory daoFactory;

    MemoDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public void create(Memo m) {
        ContentValues values = new ContentValues();
        values.put(DAOFactory.COLUMN_MEMO, m.getText());

        SQLiteDatabase db = daoFactory.getWritableDatabase();
        db.insert(DAOFactory.TABLE_MEMOS, null, values);
        db.close();
    }

    public void delete(Integer id) {
        SQLiteDatabase db = daoFactory.getWritableDatabase();
        db.delete(DAOFactory.TABLE_MEMOS,
                DAOFactory.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public Memo find(int id) {
        SQLiteDatabase db = daoFactory.getWritableDatabase();
        Cursor cursor = db.query(DAOFactory.TABLE_MEMOS,
                null,
                DAOFactory.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);

        Memo memo = null;
        if (cursor.moveToFirst()) {
            memo = new Memo(cursor.getInt(0), cursor.getString(1));
        }
        cursor.close();
        db.close();
        return memo;
    }

    public List<Memo> list() {
        List<Memo> memos = new ArrayList<>();
        SQLiteDatabase db = daoFactory.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DAOFactory.TABLE_MEMOS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String text = cursor.getString(1);
                memos.add(new Memo(id, text));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return memos;
    }
}