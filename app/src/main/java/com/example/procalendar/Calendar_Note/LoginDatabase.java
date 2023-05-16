package com.example.procalendar.Calendar_Note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.procalendar.Calendar_Alarm.WorkAlarm;

import java.util.ArrayList;

public class LoginDatabase extends SQLiteOpenHelper
{
    // thiet lap database
    private static final Integer DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LOGIN";
    // thiet lap table, columns
    private static final String TABLE_NAME = "LOGIN_DATABASE";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_PASSWORD = "PASSWORD";
    private static final String COLUMN_QUESTION1 = "QUESTION1";
    private static final String COLUMN_ANSWER1 = "ANSWER1";
    private static final String COLUMN_QUESTION2 = "QUESTION2";
    private static final String COLUMN_ANSWER2 = "ANSWER2";
    private static final String COLUMN_QUESTION3 = "QUESTION3";
    private static final String COLUMN_ANSWER3 = "ANSWER3";

    public LoginDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // tao table
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.i("SQLite", "creating...");
        String script = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " +  COLUMN_PASSWORD + " TEXT, " + COLUMN_QUESTION1 + " TEXT, "
        + COLUMN_ANSWER1 + " TEXT, " + COLUMN_QUESTION2 + " TEXT, "
                + COLUMN_ANSWER2 + " TEXT, " + COLUMN_QUESTION3 + " TEXT, "
            + COLUMN_ANSWER3 + " TEXT )";
        db.execSQL(script);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public MyAccount getData(int _ID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_ID, COLUMN_PASSWORD, COLUMN_QUESTION1
                , COLUMN_ANSWER1, COLUMN_QUESTION2, COLUMN_ANSWER2, COLUMN_QUESTION3, COLUMN_ANSWER3}, COLUMN_ID + "=?",
                new String[] { String.valueOf(_ID) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.getCount() > 0)
        {
            MyAccount node = new MyAccount(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
            cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), cursor.getString(7));
            db.close();
            return node;
        }
        else return new MyAccount(-1, "", "", "", "", "", "", "");
    }

    // chen them nhac nho
    public void addAccout(MyAccount myAccount)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, myAccount.getID());
        contentValues.put(COLUMN_PASSWORD, myAccount.getPassWord());
        contentValues.put(COLUMN_QUESTION1, myAccount.getQuestion1());
        contentValues.put(COLUMN_QUESTION2, myAccount.getQuestion2());
        contentValues.put(COLUMN_QUESTION3, myAccount.getQuestion3());
        contentValues.put(COLUMN_ANSWER1, myAccount.getAnswer1());
        contentValues.put(COLUMN_ANSWER2, myAccount.getAnswer2());
        contentValues.put(COLUMN_ANSWER3, myAccount.getAnswer3());
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public void updatePassword(int _ID, String newPassword)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PASSWORD, newPassword);
        db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[] {String.valueOf(_ID)});
        db.close();
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public void deleteOne(MyAccount myAccount)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[] { String.valueOf(myAccount.getID()) });
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

    public boolean isExist(SQLiteDatabase _db, MyAccount myAccount)
    {
        String mQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_QUESTION1 + " = " + myAccount.getQuestion1() +
                " AND " + COLUMN_ANSWER1 + " = " + myAccount.getAnswer1() +
                " AND " + COLUMN_QUESTION2 + " = " + myAccount.getQuestion2() + " AND " + COLUMN_ANSWER2 + " = " + myAccount.getAnswer2() +
                " AND " + COLUMN_QUESTION3 + " = " + myAccount.getQuestion3() + " AND " + COLUMN_ANSWER3 + " = " + myAccount.getAnswer3();
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
