package com.example.procalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.procalendar.Calendar_Alarm.WorkAlarm;

public class NoteDatabase extends SQLiteOpenHelper
{
    // thiet lap database
    private static final Integer DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NOTE";
    // thiet lap table, columns
    private static final String TABLE_NAME = "WRITE_NOTE";
    private static final String COLUMN_DATE = "DATE";
    private static final String COLUMN_BODY = "BODY";

    public NoteDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // tao table
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.i("SQLite", "creating...");
        String script = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_DATE + " TEXT, " + COLUMN_BODY + " TEXT)";
        db.execSQL(script);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public WorkAlarm getData(String _date)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_DATE, COLUMN_BODY }, COLUMN_DATE + "=?",
                new String[] { _date }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.getCount() > 0)
        {
            WorkAlarm node = new WorkAlarm(cursor.getString(0), cursor.getString(1));
            db.close();
            return node;
        }
        else return new WorkAlarm("", "");
    }

    // chen them nhac nho
    public void addAlarm(WorkAlarm workAlarm)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
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
        db.delete(TABLE_NAME, COLUMN_DATE + " = ?",
                new String[] { myAlarm.getDate() });
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

    public boolean isExist(SQLiteDatabase _db, String _date)
    {
        String mQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_DATE + " = " + _date;
        Cursor mCursor = _db.rawQuery(mQuery, null);
        if (mCursor.getCount() <= 0)
        {
            mCursor.close();
            _db.close();
            return  false;
        }
        mCursor.close();
        _db.close();
        return true;
    }
}
