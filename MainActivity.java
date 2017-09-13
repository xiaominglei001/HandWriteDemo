package com.example.xiao.myapplicationokdownload;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadUtil.get().download("", "download", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                //Utils.showToast(MainActivity.this, "下载完成");
            }

            @Override
            public void onDownloading(int progress) {
               // progressBar.setProgress(progress);
            }

            @Override
            public void onDownloadFailed() {
                //Utils.showToast(MainActivity.this, "下载失败");
            }
        });

    }
}
