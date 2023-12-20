package com.example.demo;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class FragmentRight extends Fragment {
    private CustomAdapterPlus customAdapterPlus;
    private MyApplication application;

    public FragmentRight() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (MyApplication) requireActivity().getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_right, container, false);
        SwipeRefreshLayout refreshLayout = view.findViewById(R.id.refresh_of_check_in_point);

        RecyclerView recyclerView = view.findViewById(R.id.check_in_point);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );
        recyclerView.setHasFixedSize(true);

        CustomAdapterPlus.Callback callback = (view1, position) -> view1.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), IntroduceActivity.class);
            intent.putExtra("checkInPoint", customAdapterPlus.getDataSet().get(position));
            startActivity(intent);
        });

        customAdapterPlus = new CustomAdapterPlus(callback, getActivity());
        refreshLayout.setOnRefreshListener(() -> application.networkService.getCheckInPointList(
                new NetworkService.NetworkCallBack() {
                    @Override
                    public void callBackOf() {
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void dataChangedOfAttractions(List<Attractions> list) {

                    }

                    @Override
                    public void dataChangedOfCheckInPoints(List<CheckInPoint> list) {
                        customAdapterPlus.itemChanged(list);
                    }
                }
        ));

        recyclerView.setAdapter(customAdapterPlus);
        application.networkService.getCheckInPointList(null);
        return view;
    }
}