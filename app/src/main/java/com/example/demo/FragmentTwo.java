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


public class FragmentTwo extends Fragment {
    private CustomAdapter customAdapter;
    private MyApplication application;
    public FragmentTwo() {
       super(R.layout.fragment_two);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (MyApplication) requireActivity().getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_two, container, false);
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.refresh);

        RecyclerView recyclerView = view.findViewById(R.id.recommend);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );
        recyclerView.setHasFixedSize(true);

        CustomAdapter.Callback callback = (view1, position) -> view1.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), IntroduceActivity.class);
                intent.putExtra("attractions", customAdapter.getDataSet(position));
        });

        customAdapter = new CustomAdapter(callback, getActivity());
        swipeRefreshLayout.setOnRefreshListener(() ->
                application.networkService.getAttractionsList(new NetworkService.NetworkCallBack() {
            @Override
            public void callBackOf() {
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void dataChangedOfAttractions(List<Attractions> list) {
                customAdapter.itemChanged(list);
            }

            @Override
            public void dataChangedOfCheckInPoints(List<CheckInPoint> list) {
            }}));

        recyclerView.setAdapter(customAdapter);
        application.networkService.getAttractionsList(new NetworkService.NetworkCallBack() {
            @Override
            public void callBackOf() {
            }

            @Override
            public void dataChangedOfAttractions(List<Attractions> list) {
                customAdapter.itemChanged(list);
            }

            @Override
            public void dataChangedOfCheckInPoints(List<CheckInPoint> list) {
            }
        });
        return view;
    }
}