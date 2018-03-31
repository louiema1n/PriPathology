package com.example.louiemain.pripathology.pager;/**
 * @description
 * @author&date Created by louiemain on 2018/3/26 21:15
 */

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.louiemain.pripathology.R;
import com.example.louiemain.pripathology.activity.TopicRecordActivity;
import com.example.louiemain.pripathology.base.BasePager;
import com.example.louiemain.pripathology.dao.InitData;
import com.example.louiemain.pripathology.utils.DataBaseHelper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @Pragram: PriPathology
 * @Type: Class
 * @Description: 练习页面
 * @Author: louiemain
 * @Created: 2018/3/26 21:15
 **/
public class MinePager extends BasePager {

    private static final int COUNT_DOWN = 4;
    private CardView cv_download_data;
    private CardView cv_upload_data;

    private static final int LINK_NETWORK_FAIL = 0;
    private static final int UPDATE_SUCCESS = 1;
    private static final int SOCKET_TIMEOUT = 2;
    private static final int UPDATE_FAILURE = 3;

    private ProgressDialog progressDialog;

    // 是否中断线程
    private boolean ifInterrupt = false;
    private CardView cv_order_record;
    private CardView cv_random_record;
    private TextView tv_count_down;

    public MinePager(Context context) {
        super(context);
        new Thread() {
            @Override
            public void run() {
                super.run();
                handler.sendEmptyMessage(COUNT_DOWN);
            }
        }.start();
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.layout_mine, null);

        cv_download_data = (CardView) view.findViewById(R.id.cv_download_data);
        cv_upload_data = (CardView) view.findViewById(R.id.cv_upload_data);
        cv_order_record = (CardView) view.findViewById(R.id.cv_order_record);
        cv_random_record = (CardView) view.findViewById(R.id.cv_random_record);

        cv_download_data.setOnClickListener(new MyOnClickListener());
        cv_upload_data.setOnClickListener(new MyOnClickListener());
        cv_order_record.setOnClickListener(new MyOnClickListener());
        cv_random_record.setOnClickListener(new MyOnClickListener());

        tv_count_down = (TextView) view.findViewById(R.id.tv_count_down);

        return view;
    }

    /**
     * 计算倒计时
     */
    private String calcCountDown() {
        // 考试时间为5.26
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        // 当前时间戳
        long currentTime = calendar.getTimeInMillis();
        calendar.set(Calendar.MONTH, 4);
        calendar.set(Calendar.DATE, 26);
        calendar.set(Calendar.HOUR, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        // 考试时间戳
        long examTime = calendar.getTimeInMillis();
        // 倒计时
        long rang = examTime - currentTime;
        long nd = 1000 * 24 * 60 * 60;  // 一天
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;

        long day = rang / nd;
        long hour = rang % nd / nh;
        long min = rang % nd % nh / nm;
        return day + "天" + hour + "小时" + min + "分";
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, TopicRecordActivity.class);
            switch (view.getId()) {
                case R.id.cv_download_data:
                    initDataBase();
                    break;
                case R.id.cv_upload_data:

                    break;
                case R.id.cv_order_record:
                    intent.putExtra("tag", "order");
                    context.startActivity(intent);
                    break;
                case R.id.cv_random_record:
                    intent.putExtra("tag", "random");
                    context.startActivity(intent);
                    break;
            }
        }
    }

    /**
     * @param
     * @return void
     * @description 从服务器更新数据到本地数据库
     * @author louiemain
     * @date Created on 2018/3/20 20:10
     */
    private void initDataBase() {
        progressDialog = getProgressDialog(2140, context.getString(R.string.sync_database));
        progressDialog.show();
        new Thread() {
            @Override
            public void run() {
                super.run();

                // 解决 Can't create handler inside thread that has not called Looper.prepare()
                Looper.prepare();

                HttpURLConnection conn = null;
                URL url = null;
                int i = 1;
                for (; i <= 2140; i++) {
                    if (ifInterrupt) {
                        // 终止线程
                        break;
                    }
                    try {
                        String result = "";
//                        url = new URL("http://192.168.110.94/blcj/get/" + i);
                        url = new URL("http://192.168.1.102/blcj/get/" + i);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(3000);
                        conn.setReadTimeout(3000);

                        if (conn.getResponseCode() == 200) {
                            // 连接成功
                            InputStream is = conn.getInputStream();
                            BufferedReader br = new BufferedReader(new InputStreamReader(is));
                            String str = null;
                            while ((str = br.readLine()) != null) {
                                // 还有数据
                                result += str;
                            }
                            br.close();
                            is.close();
                            new InitData(context).insert(result);

                            // 更新进度条
                            progressDialog.setProgress(i);
                        }
                    } catch (SocketTimeoutException e) {
                        // 超时处理
                        handler.sendEmptyMessage(SOCKET_TIMEOUT);
                        e.printStackTrace();
                        break;
                    } catch (UnknownHostException e) {
                        // 异常主机处理
                        handler.sendEmptyMessage(LINK_NETWORK_FAIL);
                        e.printStackTrace();
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        // 关闭连接
                        conn.disconnect();
                    }
                }
                if (i == 2141) {
                    handler.sendEmptyMessage(UPDATE_SUCCESS);
                } else {
                    handler.sendEmptyMessage(UPDATE_FAILURE);
                }
            }
        }.start();
        // 关闭数据库
//        database.close();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LINK_NETWORK_FAIL:
                    Toast.makeText(context, "连接服务器失败，请稍后重试。", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    break;
                case UPDATE_SUCCESS:
                    Toast.makeText(context, "数据更新成功。", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    break;
                case UPDATE_FAILURE:
                    Toast.makeText(context, "数据更新失败，线程被终止。请退出程序后重试。", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    break;
                case SOCKET_TIMEOUT:
                    Toast.makeText(context, "服务器连接超时，请稍后重试。", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    break;
                case COUNT_DOWN:
                    tv_count_down.setText("距离考试还有" + calcCountDown());
                    removeMessages(COUNT_DOWN);
                    sendEmptyMessageDelayed(COUNT_DOWN, 1000 * 60);
                    break;
            }
        }
    };

    /**
     * @param max
     * @param title
     * @return android.app.ProgressDialog
     * @description 创建一个进度条
     * @author louiemain
     * @date Created on 2018/3/20 20:22
     */
    private ProgressDialog getProgressDialog(int max, String title) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMax(max);
        progressDialog.setCancelable(false);     // 设置点击back键取消
        progressDialog.setCanceledOnTouchOutside(false); // 设置是否点击dialog外其他区域取消进度条
        progressDialog.setTitle(title);
        progressDialog.incrementProgressBy(0);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 取消进度条
                dialog.dismiss();
                ifInterrupt = true;
            }
        });

        // 条形进度条
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        return progressDialog;
    }
}
