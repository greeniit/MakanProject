package com.makan.app.draweritems;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makan.R;

import java.util.ArrayList;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    ArrayList<DrawerModelClass> dataList;
    Context context;
    DrawerClickListener drawerClickListener;

    public DrawerAdapter(ArrayList<DrawerModelClass> dataList, Context context, DrawerClickListener drawerClickListener) {
        this.dataList = dataList;
        this.context = context;
        this.drawerClickListener = drawerClickListener;
    }

    @NonNull
    @Override
    public DrawerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listItem= layoutInflater.inflate(R.layout.rowitem_layout_navigation, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DrawerAdapter.ViewHolder viewHolder, int i) {

        DrawerModelClass drawerModelClass = dataList.get(i);
        viewHolder.tv_menuName.setText(drawerModelClass.name);
        Drawable res = context.getResources().getDrawable(drawerModelClass.image);
        viewHolder.im_menuImage.setImageDrawable(res);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_menuName;
        private ImageView im_menuImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_menuName = (TextView)itemView.findViewById(R.id.tv_title);
            im_menuImage = (ImageView)itemView.findViewById(R.id.im_icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (drawerClickListener!=null){
                        DrawerModelClass drawerModelClass = dataList.get(getAdapterPosition());
                        drawerClickListener.recycleItemClick(drawerModelClass.getName());
                    }
                }
            });

        }
    }
}
