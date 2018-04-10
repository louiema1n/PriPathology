package com.example.louiemain.pripathology.utils;/**
 * @description
 * @author&date Created by louiemain on 2018/4/10 22:46
 */

import android.content.Context;
import android.os.Looper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * @Pragram: PriPathology
 * @Type: Class
 * @Description: 网络线路检测工具类
 * @Author: louiemain
 * @Created: 2018/4/10 22:46
 **/
public class URLCheckUtil {

    private static int count = 0;
    private static String urlPath = "http://192.168.110.94:8085";
    /**
     * @description 保存能够正确连接的URL线路
     * @author louiemain
     * @date Created on 2018/4/10 22:49
     * @param context
     * @return void
     */
    public static void saveLinkSuccessURL(final Context context) {
        new Thread() {
            @Override
            public void run() {
                super.run();

                // 解决 Can't create handler inside thread that has not called Looper.prepare()
                Looper.prepare();

                while (count < 2) {
                    count++;
                    HttpURLConnection connection = null;
                    URL url = null;
                    try {
                        url = new URL(urlPath);
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(3000);
                        connection.setReadTimeout(3000);

                        if (connection.getResponseCode() == 200) {
                            // 连接成功
                            new SharedPreferencesUtil(context).writeLinkSuccessURL(urlPath);
                        }
                    } catch (SocketTimeoutException e) {
                        // 超时处理
                        if (count == 1) {
                            urlPath = "http://192.168.1.103:8085";
                            saveLinkSuccessURL(context);
                        }
                        e.printStackTrace();
                    } catch (UnknownHostException e) {
                        // 异常主机处理
                        if (count == 1) {
                            urlPath = "http://192.168.1.103:8085";
                            saveLinkSuccessURL(context);
                        }
                        e.printStackTrace();
                    } catch (IOException e) {
                        if (count == 1) {
                            urlPath = "http://192.168.1.103:8085";
                            saveLinkSuccessURL(context);
                        }
                        e.printStackTrace();
                    } finally {
                        // 关闭连接
                        connection.disconnect();
                    }

                }
            }
        }.start();
    }

}
