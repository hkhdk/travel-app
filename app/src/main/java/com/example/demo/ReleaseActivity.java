package com.example.demo;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ReleaseActivity extends AppCompatActivity {
    private MyApplication application;
    private ActivityResultLauncher<Intent> launcher;
    private ActivityResultLauncher<String[]> requestPermissionsLauncher;
    private String realPathFromUri = null;
    private ImageView btnUploadPicture;

    private final String[] mPermissionList = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        application = (MyApplication) getApplication();

        EditText titleText = findViewById(R.id.title);
        EditText contentText = findViewById(R.id.content);
        EditText addressText = findViewById(R.id.address);
        btnUploadPicture = findViewById(R.id.picture);
        Button btn_release = findViewById(R.id.release);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), o -> {
            if (o.getResultCode() == Activity.RESULT_OK) {
                if (o.getData() != null) {
                    realPathFromUri = RealPathFromUriUtils.getPath(this, o.getData().getData());
                    if(realPathFromUri != null) {
                        Toast.makeText(this, realPathFromUri, Toast.LENGTH_LONG).show();
                        btnUploadPicture.setImageBitmap(BitmapFactory.decodeFile(realPathFromUri));
                    }
                } else {
                    Toast.makeText(this, "图片损坏,请重新选择", Toast.LENGTH_SHORT).show();
                }
            }
        });

        requestPermissionsLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), o -> {
            if(Boolean.TRUE.equals(o.get(Manifest.permission.WRITE_EXTERNAL_STORAGE)) &&
                    Boolean.TRUE.equals(o.get(Manifest.permission.READ_EXTERNAL_STORAGE))){
                getImage();
            }else{
                Toast.makeText(this, "请设置必要权限", Toast.LENGTH_SHORT).show();
            }
        });


        btnUploadPicture.setOnClickListener(v -> openCamera());

        btn_release.setOnClickListener(v -> {
            if(realPathFromUri == null){
                Toast.makeText(this, "请先选择要上传的图片!", Toast.LENGTH_SHORT).show();
            }else{
                application.networkService.postCheckInPoint(realPathFromUri, new CheckInPoint(realPathFromUri,
                   String.valueOf(titleText.getText()),
                   String.valueOf(contentText.getText()),
                   String.valueOf(addressText.getText())), new NetworkService.PostCheckInPointsResult() {
                            @Override
                            public void postSuccess() {
                                Toast.makeText(ReleaseActivity.this, "发布成功!", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void postFail() {
                                Toast.makeText(ReleaseActivity.this, "发布失败!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
    private void getImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        launcher.launch(intent);
    }

    public void openCamera() {
        requestPermissionsLauncher.launch(mPermissionList);
    }
}