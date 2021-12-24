package com.database.diarymanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.security.PublicKey;
import java.util.Queue;


public class DatabaseManagement extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Diary3.db";
    private static final String TABLE_NAME = "user";
    private static final String TABLE_NAME_1 = "user_diary";
    private static final String TABLE_NAME_2 = "secret_page";

    private static final String THEME_TABLE = "theme_table";
    private static final String QUES_TABLE = "ques_table";

    private static final int VERSION_NUMBER = 25;


    String table_create = "create table " +TABLE_NAME+ " ( id integer primary key autoincrement, name varchar(50) not null, password varchar(20) not null, secret_word varchar(20))";
    String table_create_1 = "create table " +TABLE_NAME_1+ " ( id integer primary key autoincrement, subject varchar(255) not null, date date, description varchar(255))";
    String table_create_2 = "create table " +TABLE_NAME_2+ " ( id integer primary key autoincrement, subject varchar(255) not null, date date, description varchar(255), key_word varchar(255) not null)";
    String theme_table_create = "create table " + THEME_TABLE + "( id integer primary key autoincrement,  theme integer(255))";
    String ques_table = "create table " + QUES_TABLE + "(id integer primary key autoincrement, ques varchar(255), ans varchar(255))";

    Context context;

    public DatabaseManagement(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(table_create);
        db.execSQL(table_create_1);
        db.execSQL(table_create_2);
        db.execSQL(theme_table_create);
        db.execSQL(ques_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String drop_table = "drop table if exists "+TABLE_NAME;
        String drop_table_1 = "drop table if exists "+TABLE_NAME_1;
        String drop_table_2 = "drop table if exists "+TABLE_NAME_2;
        onCreate(db);
    }

    public long insertQuesData(QuestionModel questionModel){
        String ques = questionModel.getQues();
        String ans = questionModel.getAns();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ques", ques);
        contentValues.put("ans", ans);
        long result = sqLiteDatabase.insert(QUES_TABLE, null, contentValues);
        return result;
    }

    public long InsertUserData(UserDetail userDetail)
    {
        String name = userDetail.getName();
        String password = userDetail.getPassword();
        String secret_word = userDetail.getSecret_word();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        contentValues.put("secret_word", secret_word);
        long rowId = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return rowId;
    }

    public long InsertUserDiaryData(SaveDiary saveDiary)
    {
        String subject = saveDiary.getSubject();
        String date = saveDiary.getDate();
        String description = saveDiary.getDescription();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("subject", subject);
        contentValues.put("date", date);
        contentValues.put("description", description);
        long rowId = sqLiteDatabase.insert(TABLE_NAME_1, null, contentValues);
        return rowId;
    }

    public long InsertSecretPageData(SaveDiary saveDiary)
    {
        String subject = saveDiary.getSubject();
        String date = saveDiary.getDate();
        String description = saveDiary.getDescription();
        String keyWord = saveDiary.getKeyWord();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("subject", subject);
        contentValues.put("date", date);
        contentValues.put("description", description);
        contentValues.put("key_word", keyWord);
        long rowId = sqLiteDatabase.insert(TABLE_NAME_2, null, contentValues);
        return rowId;
    }

    public long insertThemeTableData(int theme){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("theme", theme);
        long result = sqLiteDatabase.insert(THEME_TABLE, null, cv);
        return result;
    }

    public Cursor getAllQuestion(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        String sql = "SELECT * FROM " + QUES_TABLE;
        Cursor result = sqLiteDatabase.rawQuery(sql, null);
        return result;
    }

    public long updateTheme(int theme){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("theme", theme);
        long result = sqLiteDatabase.update(THEME_TABLE, cv, "id = ?", new String[]{String.valueOf(1)});
        return result;
    }

    public Cursor getTheme(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql = "SELECT * FROM " + THEME_TABLE + " WHERE id = " + 1;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        return cursor;
    }

    public Cursor NumberOfRow()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        return cursor;
    }

    public Cursor showdata()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME_1, null);
        return cursor;
    }

    public Cursor showdata1()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME_2, null);
        return cursor;
    }



    public long deleteSubjectWise(String deleteItem, int checker) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long result = -1;
        if (checker == 1)
        {
             result = sqLiteDatabase.delete("user_diary", "date=?", new String[]{deleteItem});
        }
        else if (checker == 2)
        {
             result = sqLiteDatabase.delete("user_diary", "subject=?", new String[]{deleteItem});
        }
        return result;
    }

    public long editPageData(String sub, String date, String des, String preSubject)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("subject", sub);
        contentValues.put("date", date);
        contentValues.put("description", des);
        long result = sqLiteDatabase.update(TABLE_NAME_1,contentValues, "subject=?", new String[]{preSubject});
        return result;
    }

    public Cursor getPassword(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = " + 1;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        return cursor;
    }

    public Cursor getKeyWord(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME_2;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        return cursor;
    }

    public String getSecretKey(){
        String secretKey = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = " + 1;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToNext()){
            secretKey = cursor.getString(3);
        }
        return  secretKey;
    }

    public Cursor getAllSectetWord(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME_2;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        return cursor;
    }
}
