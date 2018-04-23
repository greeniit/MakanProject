package com.makan.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makan.R;
import com.makan.app.adapter.NotificationAdapter;
import com.makan.app.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends BaseFragment{

    private RecyclerView rvNotification;
    private List<Notification> mNotificationList;
    private NotificationAdapter mNotificationAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotificationList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        initialiseComponents(rootView);
        setRecycleView();
        prepareDealerList();
        setListeners();
        return rootView;
    }

    private void initialiseComponents(View rootView) {
        rvNotification =(RecyclerView)rootView.findViewById(R.id.rvNotification);
    }

    private void setRecycleView() {

        mNotificationAdapter = new NotificationAdapter(getActivity(), mNotificationList);
        rvNotification.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvNotification.setItemAnimator(new DefaultItemAnimator());
        rvNotification.setAdapter(mNotificationAdapter);
    }

    /**
     * Adding few properties for testing
     */
    private void prepareDealerList() {

        int[] featuredProperty = new int[]{
                R.drawable.image_1,
                R.drawable.image_2,
                R.drawable.image_3,
                R.drawable.image_4};

        for (int i=0;i<5;i++){

            mNotificationList.add(new Notification());
        }

        mNotificationAdapter.notifyDataSetChanged();
    }

    private void setListeners(){

    }

}
