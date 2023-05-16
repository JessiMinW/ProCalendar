package com.example.procalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.procalendar.Calendar_Alarm.WorkAlarm;

import java.util.ArrayList;

public class MyDataBase extends SQLiteOpenHelper
{
    // thiet lap database
    private static final Integer DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PRO_CALENDAR";
    // thiet lap table, columns
    private static final String TABLE_NAME = "CALENDAR_ALARM";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_DATE = "DATE";
    private static final String COLUMN_BODY = "BODY";

    public MyDataBase(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // tao table
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.i("SQLite", "creating...");
        String script = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_DATE + " TEXT, " + COLUMN_BODY + " TEXT)";
        db.execSQL(script);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public WorkAlarm getData(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_ID,
                        COLUMN_DATE, COLUMN_BODY }, COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        WorkAlarm node = new WorkAlarm(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        db.close();
        return node;
    }

    // chen them nhac nho
    public void addAlarm(WorkAlarm workAlarm)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, workAlarm.getID());
        contentValues.put(COLUMN_DATE, workAlarm.getDate());
        contentValues.put(COLUMN_BODY, workAlarm.getNote());
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public void deleteOne(WorkAlarm myAlarm)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[] { String.valueOf(myAlarm.getID()) });
        db.close();
    }

    public int getNotesCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();
        db.close();

        // return count
        return count;
    }
}
