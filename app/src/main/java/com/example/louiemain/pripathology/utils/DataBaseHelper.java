package com.example.louiemain.pripathology.utils;/**
 * @description
 * @author&date Created by louiemain on 2018/3/26 22:18
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @Pragram: PriPathology
 * @Type: Class
 * @Description: 数据库相关操作类
 * @Author: louiemain
 * @Created: 2018/3/26 22:18
 **/
public class DataBaseHelper extends SQLiteOpenHelper {

    private String createExam = "CREATE TABLE if not exists exam (" +
            "  id integer primary key  autoincrement not null," +
            "  name varchar(2550) NOT NULL," +
            "  catalog varchar(255) NOT NULL," +
            "  type varchar(255) NOT NULL," +
            "  eid varchar(255) DEFAULT NULL," +
            "  commons varchar(2550) DEFAULT NULL," +
            "  anser varchar(255) DEFAULT NULL," +
            "  analysis varchar(5550) DEFAULT NULL," +
            "  rid integer DEFAULT NULL)";

    private String createRadio = "CREATE TABLE  if not exists radio (" +
            "  id integer primary key  autoincrement not null," +
            "  a varchar(2550) DEFAULT NULL," +
            "  b varchar(2550) DEFAULT NULL," +
            "  c varchar(2550) DEFAULT NULL," +
            "  d varchar(2550) DEFAULT NULL," +
            "  e varchar(2550) DEFAULT NULL)";

    private String createTopicRecord = "CREATE TABLE  if not exists topic_record (" +
            "  id integer primary key  autoincrement not null," +
            "  name varchar(2550) DEFAULT NULL," +
            "  number integer," +
            "  rightAnswer varchar(25) DEFAULT NULL," +
            "  time DATETIME DEFAULT NULL," +
            "  target integer," +
            "  selectAnswer varchar(25) DEFAULT NULL)";

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createExam);
        sqLiteDatabase.execSQL(createRadio);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        sqLiteDatabase.execSQL("drop table if exists exam");
//        sqLiteDatabase.execSQL("drop table if exists radio");
        sqLiteDatabase.execSQL("drop table if exists topic_record");
//
//        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL(createTopicRecord);
    }

    /**
     * 打开数据库时执行
     * @param db
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
