package com.example.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.Manifest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class FragmentEditMsg extends Fragment {
    private MyViewModel viewModel;
    private MyApplication application;
    private ActivityResultLauncher<Intent> launcher;
    private ActivityResultLauncher<String[]> requestPermissionsLauncher;

    private final String[] mPermissionList = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    public FragmentEditMsg() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (MyApplication) requireActivity().getApplication();

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), o -> {
            if (o.getResultCode() == Activity.RESULT_OK) {
                        if (o.getData() != null) {
                            String realPathFromUri = RealPathFromUriUtils.getPath(getActivity(), o.getData().getData());
                            showImg(realPathFromUri);
                        } else {
                            Toast.makeText(getActivity(), "图片损坏,请重新选择", Toast.LENGTH_SHORT).show();
                        }
            }
        });

       requestPermissionsLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), o -> {
           if(Boolean.TRUE.equals(o.get(Manifest.permission.WRITE_EXTERNAL_STORAGE)) &&
                   Boolean.TRUE.equals(o.get(Manifest.permission.READ_EXTERNAL_STORAGE))){
               getImage();
           }else{
               Toast.makeText(getActivity(), "请设置必要权限", Toast.LENGTH_SHORT).show();
           }
       });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_msg, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        EditText editText = view.findViewById(R.id.edit_nickname);
        Button btnEditHeadIcon = view.findViewById(R.id.edit_head_icon);
        ImageView imageView = view.findViewById(R.id.qr_code);
        Intent intent = new Intent(requireActivity(), AddFriendsActivity.class);
        String mUri = intent.toUri(Intent.URI_INTENT_SCHEME);
        imageView.setImageBitmap(new QRCode().qrcode(mUri));

        editText.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_DONE){
                UserInfo userInfo = viewModel.data.getValue();
                if(userInfo != null) {
                    userInfo.nickname = String.valueOf(editText.getText());
//                    viewModel.data.postValue(userInfo);
                    application.service.updateUserInfo(userInfo);
                }
            }
            return false;
        });

        btnEditHeadIcon.setOnClickListener(v -> openCamera());
        return view;
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

    public void showImg(String path){
        UserInfo userInfo = viewModel.data.getValue();
        if(userInfo != null) {
            userInfo.url = path;
            application.service.updateUserInfo(userInfo);
        }
    }
}