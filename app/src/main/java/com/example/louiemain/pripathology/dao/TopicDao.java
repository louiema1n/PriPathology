package com.example.louiemain.pripathology.dao;/**
 * @description
 * @author&date Created by louiemain on 2018/3/28 18:33
 */

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
import com.example.louiemain.pripathology.domain.Topic;
import com.example.louiemain.pripathology.utils.DataBaseHelper;

/**
 * @Pragram: PriPathology
 * @Type: Class
 * @Description: Topic相关数据库操作
 * @Author: louiemain
 * @Created: 2018/3/28 18:33
 **/
public class TopicDao {
    private static final String TABLE_EXAM = "exam";
    private static final String TABLE_RADIO = "radio";

    private Context context;

    // 获取数据库操作对象
    private DataBaseHelper helper;
    private SQLiteDatabase database;

    private Topic topic;

    public TopicDao(Context context) {
        this.context = context;
    }

    /**
     * 根据id生成题目
     *
     * @param id
     */
    public Topic generatePractice(String id) {
        helper = new DataBaseHelper(context, "topic", null, 3);

        // 得到数据库操作对象-读取模式
        database = helper.getReadableDatabase();
        topic = new Topic();
        try {
            Cursor cursorExam = database.query(TABLE_EXAM,
                    new String[]{"id", "name", "catalog", "type", "eid", "commons", "anser", "analysis", "rid"},
                    "id = ?",
                    new String[]{id},
                    null,
                    null,
                    null);

            // cursor置顶
            cursorExam.moveToFirst();
            if (cursorExam != null) {
                // 设置exam
                String name = cursorExam.getString(cursorExam.getColumnIndex("name"));
                name = name.replaceFirst("\\d+.", "");

                // 去掉前面的数字
                String commons = cursorExam.getString(cursorExam.getColumnIndex("commons"));    // commons
                commons = commons.replaceFirst("\\d+.", "").replace("<br>", "\n");

                topic.setNumber(id);
                topic.setName(name);
                topic.setAnser(cursorExam.getString(cursorExam.getColumnIndex("anser")));
                topic.setAnalysis(cursorExam.getString(cursorExam.getColumnIndex("analysis")));
                topic.setCommons(commons);
            } else {
                Log.i("msg", "未查询到数据Exam");
            }

            Cursor cursorRadio = database.query(TABLE_RADIO,
                    new String[]{"id", "a", "b", "c", "d", "e"},
                    "id = ?",
                    new String[]{id},
                    null,
                    null,
                    null);
            cursorRadio.moveToFirst();

            if (cursorRadio != null) {
                // 设置Radio
                topic.setA(cursorRadio.getString(cursorRadio.getColumnIndex("a")));
                topic.setB(cursorRadio.getString(cursorRadio.getColumnIndex("b")));
                topic.setC(cursorRadio.getString(cursorRadio.getColumnIndex("c")));
                topic.setD(cursorRadio.getString(cursorRadio.getColumnIndex("d")));
                topic.setE(cursorRadio.getString(cursorRadio.getColumnIndex("e")));
            } else {
                Log.i("msg", "未查询到数据Radio");
            }
            cursorExam.close();
            cursorRadio.close();
            return topic;
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            Toast.makeText(context, "未查询到数据，请先同步远程数据库。", Toast.LENGTH_SHORT).show();
        } finally {
            database.close();
        }
        return null;
    }
}
