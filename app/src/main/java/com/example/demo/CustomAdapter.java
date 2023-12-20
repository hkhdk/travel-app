package com.example.demo;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<Attractions> dataSet = new ArrayList<>(); // 只关心数据内容

    public interface Callback{
        void itemClick(View view, int position);
    }

    private final Callback callback;

    private final FragmentActivity activity;

    @SuppressLint("NotifyDataSetChanged")
    public void itemChanged(List<Attractions> list)
    {
        this.dataSet = list;
        notifyDataSetChanged();
    }

    public CustomAdapter(Callback callback, FragmentActivity activity){
        this.callback = callback;
        this.activity = activity;
    }

    public Attractions getDataSet(int position)
    {
        return this.dataSet.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final ImageView imageView;
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);

            this.view = view;
            textView = view.findViewById(R.id.introduce_txt);
            imageView = view.findViewById(R.id.introduce_img);
        }

        private TextView getTextView() {
            return textView;
        }
        private ImageView getImageView(){
            return imageView;
        }
        private View getView() {return view;}

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.introduce_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.getTextView().setText(dataSet.get(position).getTitle());
        Glide.with(activity).load(dataSet.get(position).getPictureUrl()).into(viewHolder.getImageView());
        if(callback != null){
            callback.itemClick(viewHolder.getView(), position);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}

