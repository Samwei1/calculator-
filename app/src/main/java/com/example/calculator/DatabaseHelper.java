package com.example.calculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/***************************************************************************************
 *    Reference:
 *    Title: Save data into SQLite database [Beginner Android Studio Example]
 *    Author: Mitch
 *    Date: 2017
 *    Code version:2.0
 *    Availability: https://www.youtube.com/watch?v=aQAIMY-HzL8&t=311s
 *
 ***************************************************************************************/
public class DatabaseHelper extends SQLiteOpenHelper {
    private  static final String TAG = "DatabaseHelper";
    private  static final String TABLE_NAME = "functions_table";
    private  static final String COL1 = "ID";
    private  static final String COL2 = "content";
    public DatabaseHelper(Context context){
        super(context,TABLE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String creatTable = "CREATE TABLE "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, "+ COL2 +" TEXT)";
        db.execSQL(creatTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }
    public boolean addData(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,item);
        Log.d(TAG,"addData: Adding "+ item + " to "+ TABLE_NAME);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result ==-1){
            return false;
        }else {
            return true;
        }

    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }
    public Cursor getItemId(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+ COL1 + " FROM "+ TABLE_NAME+" WHERE "+COL2 +" = '"+name+"'";
        Cursor data =  db.rawQuery(query,null);
        return data;
    }
    public void deleteFunction(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+ TABLE_NAME +" WHERE " + COL1+" = '"+id+"'"+" AND "+COL2 +" = '"+name+"'";
        db.execSQL(query);
    }
}
