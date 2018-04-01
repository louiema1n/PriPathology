package com.example.louiemain.pripathology.utils;/**
 * @description
 * @author&date Created by louiemain on 2018/4/1 12:04
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import com.example.louiemain.pripathology.R;
import com.example.louiemain.pripathology.dao.InitData;
import com.example.louiemain.pripathology.dao.TopicRecordDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

/**
 * @Pragram: PriPathology
 * @Type: Class
 * @Description: 网络操作相关工具类
 * @Author: louiemain
 * @Created: 2018/4/1 12:04
 **/
public class HttpUtil {

    private static final int UPDATE_SUCCESS = 1;
    private static final int UPDATE_FAILURE = 0;
    private static final int SOCKET_TIMEOUT = 3;
    private static final int LINK_NETWORK_FAIL = 4;
    private static final int EMPTY_DATA = 5;
    private static final int DOWNLOAD_DATA_HALF = 50;
    private static final int DOWNLOAD_DATA_DONE = 100;
    private static final int INSERT_FAILURE = 6;
    private static final int INSERT_SUCCESS = 7;

    private HttpURLConnection conn = null;
    private URL url = null;
    private String result = "";
    // 插入数据库响应内容
    private String responeContent;

    private ProgressDialog progressDialog;
    private Context context;

    private SharedPreferencesUtil sharedPreferencesUtil;

    public HttpUtil(Context context) {
        this.context = context;
        sharedPreferencesUtil = new SharedPreferencesUtil(context);
    }

    /**
     * 上传答题记录
     * @param uploadData
     */
    public void uploadTopicRecord(String uploadData) {

        final String up = uploadData;
        if (up == null || up.equals("")) {
            handler.sendEmptyMessage(EMPTY_DATA);
        } else {

        // 获取进度条
        progressDialog = getProgressDialog(100, context.getString(R.string.upload_topic_record));
        progressDialog.show();

        new Thread() {
            @Override
            public void run() {
                super.run();

                // 解决 Can't create handler inside thread that has not called Looper.prepare()
                Looper.prepare();

                try {
//                        url = new URL("http://192.168.110.94/blcj/get/" + i);
                    String encodedUp = URLEncoder.encode(up, "UTF-8");
                    url = new URL("http://192.168.1.103:8085/tr/add?json=" + encodedUp);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    // 设置文件类型
                    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
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

                        if (result != null) {
                            handler.sendEmptyMessage(UPDATE_SUCCESS);
                        } else {
                            handler.sendEmptyMessage(UPDATE_FAILURE);
                        }
                    }
                } catch (SocketTimeoutException e) {
                    // 超时处理
                    handler.sendEmptyMessage(SOCKET_TIMEOUT);
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    // 异常主机处理
                    handler.sendEmptyMessage(LINK_NETWORK_FAIL);
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    // 关闭连接
                    conn.disconnect();
                }

            }

        }.start();
        }
    }

    /**
     * 下载答题记录
     */
    public void downloadTopicRecord() {
        progressDialog = getProgressDialog(100, context.getString(R.string.download_topic_record));
        progressDialog.setMessage(context.getString(R.string.download_data));
        progressDialog.show();
        new Thread() {
            @Override
            public void run() {
                super.run();

                // 解决 Can't create handler inside thread that has not called Looper.prepare()
                Looper.prepare();

                // 1.download data from server
                HttpURLConnection conn = null;
                URL url = null;
                String result = "";

                try {
//                        url = new URL("http://192.168.110.94/blcj/get/" + i);
                    url = new URL("http://192.168.1.103:8085/tr/all");
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(3000);
                    conn.setReadTimeout(3000);

                    handler.sendEmptyMessage(DOWNLOAD_DATA_HALF);

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

                        handler.sendEmptyMessage(DOWNLOAD_DATA_DONE);

                        // 2.insert into local database
                        responeContent = new TopicRecordDao(context).downloadData(result);
                        if (responeContent == null) {
                            handler.sendEmptyMessage(INSERT_FAILURE);
                        } else {
                            handler.sendEmptyMessage(INSERT_SUCCESS);
                        }
                    }
                } catch (SocketTimeoutException e) {
                    // 超时处理
                    handler.sendEmptyMessage(SOCKET_TIMEOUT);
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    // 异常主机处理
                    handler.sendEmptyMessage(LINK_NETWORK_FAIL);
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    // 关闭连接
                    conn.disconnect();
                }

            }

        }.start();
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
                    progressDialog.setProgress(100);
                    progressDialog.dismiss();
                    Toast.makeText(context, "成功上传" + result + "条答题记录。", Toast.LENGTH_SHORT).show();
                    sharedPreferencesUtil.writeUploadedMaxId(new TopicRecordDao(context).getMaxSelectedId(1));
                    break;
                case UPDATE_FAILURE:
                    Toast.makeText(context, "数据更新失败，线程被终止。请退出程序后重试。", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    break;
                case SOCKET_TIMEOUT:
                    Toast.makeText(context, "服务器连接超时，请稍后重试。", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    break;
                case EMPTY_DATA:
                    Toast.makeText(context, "未发现需要上传的答题记录。", Toast.LENGTH_SHORT).show();
                    break;
                case DOWNLOAD_DATA_HALF:
                    progressDialog.setProgress(25);
                    progressDialog.setSecondaryProgress(50);
                    break;
                case DOWNLOAD_DATA_DONE:
                    progressDialog.setProgress(50);
                    progressDialog.setMessage(context.getString(R.string.insert_data));
                    progressDialog.setSecondaryProgress(100);
                    break;
                case INSERT_FAILURE:
                    Toast.makeText(context, "插入数据失败！", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    break;
                case INSERT_SUCCESS:
                    Toast.makeText(context, responeContent, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
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
            }
        });

        // 条形进度条
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        return progressDialog;
    }
}
