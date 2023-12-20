package com.example.demo;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;


import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.net.URISyntaxException;


public class FragmentThree extends Fragment {
    private ActivityResultLauncher launcher;
    public FragmentThree() {
        super(R.layout.fragment_three);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        launcher = registerForActivityResult(new ScanContract(), result -> {
            if(result.getContents() != null) {
                try {
                    Toast.makeText(requireActivity(), result.getContents(), Toast.LENGTH_LONG).show();
                    Intent intent = Intent.parseUri(result.getContents(), Intent.URI_INTENT_SCHEME);
                    requireActivity().startActivity(intent);
                } catch (URISyntaxException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        ImageButton button = view.findViewById(R.id.add_friend);
        button.setOnClickListener(v -> launcher.launch(new ScanOptions()));
        return view;
    }
}