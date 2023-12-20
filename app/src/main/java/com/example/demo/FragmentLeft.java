package com.example.demo;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class FragmentLeft extends Fragment {
    private MyViewModel viewModel;

    public FragmentLeft() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_left, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        TextView textView = view.findViewById(R.id.main_page_nickname);
        ImageView imageView = view.findViewById(R.id.main_page_head_icon);

        viewModel.data.observe(requireActivity(), userInfo -> {
            textView.setText(String.valueOf(Objects.requireNonNull(viewModel.data.getValue()).nickname));
            if(viewModel.data.getValue().url != null){
                imageView.setImageBitmap(BitmapFactory.decodeFile(viewModel.data.getValue().url));
            }
        });
        return view;
    }
}