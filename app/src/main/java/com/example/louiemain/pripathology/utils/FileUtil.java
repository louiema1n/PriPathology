package com.example.louiemain.pripathology.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Program: PriPathology
 * @Type: Class
 * @Description: 文件操作相关工具类
 * @Author: louiemain
 * @Created: 2018-04-09 15:47
 **/
public class FileUtil {

    private static final String SD_FOLDER = Environment.getExternalStorageDirectory() + "/PriPathology/downloadApk/";

    public static void appInnerDownLoder(final Context context, final String url, final String appName) {
        // 进度对话框
        final ProgressDialog progressDialog = new ProgressDialog(context);
        // 设置progressDialog
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("下载应用中");
//        progressDialog.setMessage("正在下载安装包，请稍后");
        progressDialog.show();

        // 开启新线程下载文件
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    File file = downloadFile(url, appName, progressDialog);

                    // 安装文件
                    installApk(context, file);
                    progressDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            }
        }.start();
    }

    /**
     * 安装apk
     * @param context
     * @param file
     */
    private static void installApk(Context context, File file) {
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 防止打不开应用
        context.startActivity(intent);
    }

    /**
     * 下载文件
     * @param path
     * @param appName
     * @param progressDialog
     * @return
     */
    private static File downloadFile(String path, String appName, ProgressDialog progressDialog) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            // 当前sd卡挂载且可用
            try {
                // 开始下载
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setReadTimeout(3000);
                conn.setRequestMethod("GET");
                // 获取文件大小
                progressDialog.setMax(conn.getContentLength());
                // 获取输入流
                InputStream inputStream = conn.getInputStream();
                // 创建目标文件
                File dist = new File(SD_FOLDER + appName);
                if (!dist.getParentFile().exists()) {
                    // 创建
                    dist.getParentFile().mkdirs();
                }
                // 获取文件输出流
                FileOutputStream fos = new FileOutputStream(dist);
                // 获取缓冲输入流
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                byte[] bytes = new byte[1024];
                int len, total = 0;
                while ((len = bis.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                    total += len;
                    // 设置当前下载量
                    progressDialog.setProgress(total);
                }
                bis.close();
                fos.close();
                inputStream.close();
                return dist;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                throw new IOException("未发现可用SD卡");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
