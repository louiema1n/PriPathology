package com.example.louiemain.pripathology.dao;/**
 * @description
 * @author&date Created by louiemain on 2018/3/29 20:35
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import com.example.louiemain.pripathology.utils.DataBaseHelper;
import com.example.louiemain.pripathology.utils.SharedPreferencesUtil;

/**
 * @Pragram: PriPathology
 * @Type: Class
 * @Description: 答题记录数据库相关操作类
 * @Author: louiemain
 * @Created: 2018/3/29 20:35
 **/
public class TopicRecordDao {

    private static final String TABLE_TOPIC_RECORD = "topic_record";
    private SQLiteDatabase database;
    private DataBaseHelper helper;

    private Context context;
    private Integer databaseVer;

    public TopicRecordDao(Context context) {
        this.context = context;
        databaseVer = new SharedPreferencesUtil(context).getDatabaseVer();
    }

    /**
     * 获取查询结果
     *
     * @param target
     * @return
     */
    public Cursor getCursorByTarget(int target) {
        intiDataBaseHelper();
        // 得到数据库操作对象-读取模式
        database = helper.getReadableDatabase();
        try {
            Cursor cursor = database.query(TABLE_TOPIC_RECORD,
                    new String[]{"id", "name", "number", "rightAnswer", "time", "selectAnswer", "target"},
                    "target = ?",
                    new String[]{String.valueOf(target)},
                    null,
                    null,
                    "time desc");
            return cursor;
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            Toast.makeText(context, "未查询到数据，请先同步远程数据库。", Toast.LENGTH_SHORT).show();
        }
        database.close();
        return null;
    }

    private void intiDataBaseHelper() {
        helper = new DataBaseHelper(context, "topic", null, databaseVer);
    }

    public int getMaxSelectedId() {
        intiDataBaseHelper();
        // 得到数据库操作对象-读取模式
        database = helper.getReadableDatabase();
        try {
            Cursor cursor = database.rawQuery("select max(id) from " + TABLE_TOPIC_RECORD + " where target = ?",
                    new String[]{String.valueOf(0)});
            cursor.moveToFirst();
            if (cursor != null) {
                int id = cursor.getInt(cursor.getColumnIndex("max(id)"));
                return id;
            }
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            Toast.makeText(context, "未查询到数据，请先同步远程数据库。", Toast.LENGTH_SHORT).show();
        }
        database.close();
        return 0;
    }

    public void saveTopicRecord(ContentValues values) {
        intiDataBaseHelper();
        database = helper.getWritableDatabase();
        database.insert(TABLE_TOPIC_RECORD, null, values);
        database.close();
    }
}
