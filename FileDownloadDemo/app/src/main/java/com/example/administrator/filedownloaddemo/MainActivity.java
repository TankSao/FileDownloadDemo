package com.example.administrator.filedownloaddemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.filedownloaddemo.utils.DensityUtil;
import com.example.administrator.filedownloaddemo.utils.DialogCircle;
import com.example.administrator.filedownloaddemo.utils.DownloadListener;
import com.example.administrator.filedownloaddemo.utils.DownloadUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.file_path)
    TextView filePath;
    private String fileUrl = "https://rjwpublic.oss-cn-qingdao.aliyuncs.com/user/avatar/2bZ74wEpEkPzkSBsYeWzhHe7asHCkpxw.jpg";//文件下载路径
    private static boolean isDowning = false;//是否正在下载
    TextView qd;
    TextView qx;
    TextView tv;
    DialogCircle newDialog;
    int width, height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        requestAllPower();
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        filePath.setText("下载路径:"+ Environment.getExternalStorageDirectory() + "/DownloadFile");
    }
    public void requestAllPower() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.INTERNET}, 1);
            }
        }
    }
    private void showPop(final String url) {
        View view = View.inflate(this, R.layout.pop_download, null);
        tv = view.findViewById(R.id.tv);
        qd = view.findViewById(R.id.qd);
        qx = view.findViewById(R.id.qx);
        newDialog = new DialogCircle(this, DensityUtil.dip2px(this, width / 4), DensityUtil.dip2px(this, height / 8), view,
                R.style.dialog);
        newDialog.setCancelable(false);
        tv.setText("确认下载文件吗?");
        qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadFile(url);
                newDialog.dismiss();
            }
        });
        newDialog.show();
        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newDialog.dismiss();
            }
        });
    }
    private void downloadFile(String url) {
        DownloadUtils downloadUtils = new DownloadUtils();
        downloadUtils.downloadFile(url, new DownloadListener() {
            @Override
            public void onStart() {
                isDowning = true;
                Toast.makeText(MainActivity.this,"正在下载，请稍后",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(final int currentLength) {
            }

            @Override
            public void onFinish(String localPath) {
                isDowning = false;
                Toast.makeText(MainActivity.this,"下载完成",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(final String errorInfo) {
                isDowning = false;
                Toast.makeText(MainActivity.this,"下载失败，请重试。。。",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @OnClick({R.id.download})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.download:
                if(isDowning){
                    Toast.makeText(MainActivity.this,"有文件正在下载。。。",Toast.LENGTH_SHORT).show();
                }else {
                    showPop(fileUrl);//下载文件弹框
                }
                break;
        }
    }
}
