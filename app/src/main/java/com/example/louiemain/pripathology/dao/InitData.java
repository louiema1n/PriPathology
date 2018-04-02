package com.example.louiemain.pripathology.dao;/**
 * @description
 * @author&date Created by louiemain on 2018/3/31 10:25
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.louiemain.pripathology.utils.DataBaseHelper;
import com.example.louiemain.pripathology.utils.SharedPreferencesUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @Pragram: PriPathology
 * @Type: Class
 * @Description: 初始化远程数据
 * @Author: louiemain
 * @Created: 2018/3/31 10:25
 **/
public class InitData {

    private Context context;

    private static final String TABLE_EXAM = "exam";
    private static final String TABLE_RADIO = "radio";
    private DataBaseHelper helper;
    private SQLiteDatabase database;

    public InitData(Context context) {
        this.context = context;
    }

    /**
     * @param result
     * @return void
     * @description 插入到数据库
     * @author louiemain
     * @date Created on 2018/4/1 10:21
     */
    public String insetTopic(String result) {
        // 获取数据库操作对象
        helper = new DataBaseHelper(context, "topic", null, new SharedPreferencesUtil(context).getDatabaseVer());

        //获得SQLiteDatabase对象，读写模式
        database = helper.getWritableDatabase();

        database.beginTransaction();
        //ContentValues类似HashMap，区别是ContentValues只能存简单数据类型，不能存对象
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ContentValues values = new ContentValues();
                values.put("name", jsonObject.optString("name"));
                values.put("catalog", jsonObject.optString("catalog"));
                values.put("type", jsonObject.optString("type"));
                values.put("eid", jsonObject.optString("eid"));
                values.put("commons", jsonObject.optString("commons"));
                values.put("anser", jsonObject.optString("anser"));
                values.put("analysis", jsonObject.optString("analysis"));
                values.put("rid", jsonObject.optInt("rid"));
                //执行插入操作
                database.insert(TABLE_EXAM, null, values);
                values = new ContentValues();
                String radio = jsonObject.optString("radio");
                JSONObject radioObj = new JSONObject(radio);
                values.put("a", radioObj.optString("a"));
                values.put("b", radioObj.optString("b"));
                values.put("c", radioObj.optString("c"));
                values.put("d", radioObj.optString("d"));
                values.put("e", radioObj.optString("e"));
                database.insert(TABLE_RADIO, null, values);
            }
            // 设置事务成功
            database.setTransactionSuccessful();
            return "插入数据成功";
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } finally {
            // 结束事务
            database.endTransaction();
            database.close();
        }
    }
}
