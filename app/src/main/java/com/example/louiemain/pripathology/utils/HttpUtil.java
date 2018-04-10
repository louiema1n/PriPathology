package com.example.louiemain.pripathology.utils;/**
 * @description
 * @author&date Created by louiemain on 2018/4/1 12:04
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.louiemain.pripathology.R;
import com.example.louiemain.pripathology.dao.InitData;
import com.example.louiemain.pripathology.dao.TopicRecordDao;
import org.json.JSONException;
import org.json.JSONObject;

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

    private static final int DOWN_TOPIC_SUCCESS = 1;
    private static final int DOWN_TOPIC_FAILURE = 2;
    private static final int DOWN_TR_FAILURE = 3;
    private static final int DOWN_TR_SUCCESS = 4;
    private static final int UPLOAD_TR_SUCCESS = 5;
    private static final int UPLOAD_TR_FAILURE = 6;
    private static final int SOCKET_TIMEOUT = 7;
    private static final int LINK_NETWORK_FAIL = 8;
    private static final int EMPTY_DATA = 9;
    private static final int DOWNLOAD_DATA_HALF = 50;
    private static final int DOWNLOAD_DATA_DONE = 100;
    private static final int SHOW_PROGRESS_DIALOG = 10;
    private static final int UPDATE_APK = 111;

    private HttpURLConnection conn = null;
    private URL url = null;
    private String result = "";

    // 插入数据库响应内容
    private String responseContent;

    private ProgressDialog progressDialog;
    private Context context;

    private SharedPreferencesUtil sharedPreferencesUtil;

    private String httpResource = "";
    private String Uri;

    private ImageView iv_has_new_version;

    public HttpUtil(Context context) {
        this.context = context;
        sharedPreferencesUtil = new SharedPreferencesUtil(context);
        if (NetworkUtil.isWifi(context)) {
            // Wifi环境
            if (NetworkUtil.getWifiSSID(context).equals("\"louiemain\"")) {
                this.Uri = "http://192.168.110.94:8085";
            } else {
                this.Uri = "http://192.168.1.103:8085";
            }
        } else {
            this.Uri = null;
        }
    }

    /**
     * 上传答题记录
     *
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
                        String encodedUp = URLEncoder.encode(up, "UTF-8");
                        url = new URL(Uri + "/tr/add?json=" + encodedUp);
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
                                handler.sendEmptyMessage(UPLOAD_TR_SUCCESS);
                            } else {
                                handler.sendEmptyMessage(UPLOAD_TR_FAILURE);
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
        getHttpResource(Uri + "/tr/all", "topicRecord");
    }

    /**
     * @param
     * @Description: 自动检测版本更新
     * @Author: louiemain
     * @Date: 2018-04-09 14:45
     * @return: void
     */
    public void checkNewVersion(ImageView iv_has_new_version) {
        this.iv_has_new_version = iv_has_new_version;
        getHttpResourceNoProgressDialog(Uri + "/upd/last", "laUPDATE_APKApk");
    }

    /**
     * 按钮点击检测更新
     */
    public void checkNewVersion() {
        getHttpResourceNoProgressDialog(Uri + "/upd/last", "laUPDATE_APKApk");
    }

    /**
     * 获取HttpResource
     *
     * @param urlPath
     * @return
     */
    public void getHttpResource(String urlPath, String target) {
        if (Uri != null) {
            handler.sendEmptyMessage(SHOW_PROGRESS_DIALOG);
            final String up = urlPath;
            final String tg = target;
            new Thread() {
                @Override
                public void run() {
                    super.run();

                    // 解决 Can't create handler inside thread that has not called Looper.prepare()
                    Looper.prepare();

                    // 1.download data from server
                    HttpURLConnection conn = null;
                    URL url = null;

                    try {
                        url = new URL(up);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(3000);
                        conn.setReadTimeout(3000);

                        handler.sendEmptyMessage(DOWNLOAD_DATA_HALF);
                        String str = "";
                        if (conn.getResponseCode() == 200) {
                            // 连接成功
                            InputStream is = conn.getInputStream();
                            BufferedReader br = new BufferedReader(new InputStreamReader(is));
                            while ((str = br.readLine()) != null) {
                                // 还有数据
                                httpResource += str;
                            }
                            br.close();
                            is.close();
                            handler.sendEmptyMessage(DOWNLOAD_DATA_DONE);
                            if (httpResource != null || httpResource != "") {
                                switch (tg) {
                                    case "laUPDATE_APKApk":
                                        Toast.makeText(context, httpResource, Toast.LENGTH_SHORT).show();
                                        break;
                                    case "topic":
                                        // 2.insert into local database
                                        String s = new InitData(context).insetTopic(httpResource);
                                        if (s == null) {
                                            handler.sendEmptyMessage(DOWN_TOPIC_FAILURE);
                                        } else {
                                            handler.sendEmptyMessage(DOWN_TOPIC_SUCCESS);
                                        }
                                        break;
                                    case "topicRecord":
                                        // 2.insert into local database
                                        responseContent = new TopicRecordDao(context).insertTopicRecord(httpResource);
                                        if (responseContent == null) {
                                            handler.sendEmptyMessage(DOWN_TR_FAILURE);
                                        } else {
                                            handler.sendEmptyMessage(DOWN_TR_SUCCESS);
                                        }
                                        break;
                                }
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
        } else {
            Toast.makeText(context, "当前为非WiFi环境", Toast.LENGTH_SHORT).show();
        }
    }

    public void getHttpResourceNoProgressDialog(String urlPath, String target) {
        if (Uri != null) {
            final String up = urlPath;
            final String tg = target;
            new Thread() {
                @Override
                public void run() {
                    super.run();

                    // 解决 Can't create handler inside thread that has not called Looper.prepare()
                    Looper.prepare();

                    // 1.download data from server
                    HttpURLConnection conn = null;
                    URL url = null;

                    try {
                        url = new URL(up);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(3000);
                        conn.setReadTimeout(3000);

                        String str = "";
                        if (conn.getResponseCode() == 200) {
                            // 连接成功
                            InputStream is = conn.getInputStream();
                            BufferedReader br = new BufferedReader(new InputStreamReader(is));
                            while ((str = br.readLine()) != null) {
                                // 还有数据
                                httpResource += str;
                            }
                            br.close();
                            is.close();
                            if (httpResource != null || httpResource != "") {
                                switch (tg) {
                                    case "laUPDATE_APKApk":
                                        handler.sendEmptyMessage(UPDATE_APK);
                                        break;
                                }
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
        } else {
            Toast.makeText(context, "当前为非WiFi环境", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param
     * @return void
     * @description 从服务器更新数据到本地数据库
     * @author louiemain
     * @date Created on 2018/3/20 20:10
     */
    public void downloadTopic() {
        getHttpResource(Uri + "/exam/all", "topic");
    }

    /**
     * @Description: 更新管理
     * @Author: louiemain
     * @Date: 2018-04-09 14:56
     * @return: void
     */
    public void updateManager() {
        if (httpResource != null) {

            try {
                // 获取jsonObject
                final JSONObject jsonObject = new JSONObject(httpResource);
                int serverVersion = jsonObject.optInt("serverVersion");
                if (serverVersion > Integer.parseInt(APKUtil.getVersionCode(context))) {
                    // 当前版本较低，需要更新。
                    if (iv_has_new_version != null) {
                        iv_has_new_version.setVisibility(View.VISIBLE);
                    } else {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                                .setCancelable(false)
                                .setTitle("更新")
                                .setMessage("检测到新版本应用，是否立即更新？")
                                .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // 更新

                                        FileUtil.appInnerDownLoder(context,
                                                Uri + jsonObject.optString("updateurl"),
                                                jsonObject.optString("appname"));
                                    }
                                });
                        if (!jsonObject.getBoolean("lastForce")) {
                            // 强制更新
                            alertDialog.setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // 无需处理
                                }
                            });
                        }
                        alertDialog.create().show();
                    }
                } else {
                    if (iv_has_new_version != null) {

                    } else {
                        Toast.makeText(context, "当前已是最新版本", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DOWN_TOPIC_SUCCESS:
                    progressDialog.setProgress(100);
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(context, "成功同步" + new SharedPreferencesUtil(context).getSyncDataState() + "条题目数据。", Toast.LENGTH_SHORT).show();
                    break;
                case DOWN_TOPIC_FAILURE:
                    Toast.makeText(context, "下载题目数据失败，请重试。", Toast.LENGTH_SHORT).show();
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    break;
                case DOWN_TR_FAILURE:
                    Toast.makeText(context, "更新答题记录失败！", Toast.LENGTH_SHORT).show();
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    break;
                case DOWN_TR_SUCCESS:
                    Toast.makeText(context, responseContent, Toast.LENGTH_SHORT).show();
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    sharedPreferencesUtil.writeUploadedMaxId(new TopicRecordDao(context).getMaxSelectedId(1));
                    break;
                case UPLOAD_TR_SUCCESS:
                    progressDialog.setProgress(100);
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(context, "成功上传" + result + "条答题记录。", Toast.LENGTH_SHORT).show();
                    sharedPreferencesUtil.writeUploadedMaxId(new TopicRecordDao(context).getMaxSelectedId(1));
                    break;
                case UPLOAD_TR_FAILURE:
                    Toast.makeText(context, "上传数据失败！", Toast.LENGTH_SHORT).show();
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    break;
                case LINK_NETWORK_FAIL:
                    Toast.makeText(context, "连接服务器失败，请稍后重试。", Toast.LENGTH_SHORT).show();
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    break;
                case SOCKET_TIMEOUT:
                    Toast.makeText(context, "服务器连接超时，请稍后重试。", Toast.LENGTH_SHORT).show();
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
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
                case SHOW_PROGRESS_DIALOG:
                    progressDialog = getProgressDialog(100, context.getString(R.string.sync_database));
                    progressDialog.setMessage(context.getString(R.string.download_data));
                    progressDialog.show();
                    break;
                case UPDATE_APK:
                    updateManager();
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
