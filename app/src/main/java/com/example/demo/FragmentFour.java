package com.example.demo;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;


public class FragmentFour extends Fragment {
    private MyViewModel viewModel;

    public FragmentFour() {
       super(R.layout.fragment_four);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_four, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        Button btnEdit = view.findViewById(R.id.edit_msg);
        Button btnMyLevel = view.findViewById(R.id.my_level);
        TextView textView = view.findViewById(R.id.nickname);
        CircleImageView imageView = view.findViewById(R.id.head_icon);


        viewModel.data.observe(requireActivity(), userInfo -> {
            textView.setText(Objects.requireNonNull(viewModel.data.getValue()).nickname);
            if(viewModel.data.getValue().url != null){
                imageView.setImageBitmap(BitmapFactory.decodeFile(viewModel.data.getValue().url));
            }
        });
        btnEdit.setOnClickListener(v ->{
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container, FragmentEditMsg.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });

        btnMyLevel.setOnClickListener(v ->{

        });
        return view;
    }
}