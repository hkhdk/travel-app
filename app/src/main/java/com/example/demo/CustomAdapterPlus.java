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

public class CustomAdapterPlus extends RecyclerView.Adapter<CustomAdapterPlus.ViewHolder>{
    private List<CheckInPoint> dataSet = new ArrayList<>();
    public interface Callback{
        void itemClick(View view, int position);
    }

    private final CustomAdapterPlus.Callback callback;

    private final FragmentActivity activity;

    @SuppressLint("NotifyDataSetChanged")
    public void itemChanged(List<CheckInPoint> list)
    {
        this.dataSet = list;
        notifyDataSetChanged();
    }

    public CustomAdapterPlus(CustomAdapterPlus.Callback callback, FragmentActivity activity){
        this.callback = callback;
        this.activity = activity;
    }

    public List<CheckInPoint> getDataSet()
    {
        return this.dataSet;
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

            /*
//            DisplayMetrics dm = new DisplayMetrics();
//            ((Activity) this.view.getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
//            int width = dm.widthPixels;
//            ViewGroup.LayoutParams params = this.view.getLayoutParams();
//            params.width = width / 2;
//            params.height =  (int) (200 + Math.random() * 400) ;
//            this.view.setLayoutParams(params);
            */
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
                .inflate(R.layout.check_in_point_item, viewGroup, false);

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
