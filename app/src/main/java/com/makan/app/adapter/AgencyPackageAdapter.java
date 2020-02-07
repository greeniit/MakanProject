package com.makan.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makan.R;
import com.makan.app.activity.AgencyPackageActivity;
import com.makan.app.activity.AgencyRegistrationPackage;
import com.makan.app.activity.ServiceProviderRegistrationActivity;
import com.makan.app.app.WebConstant;
import com.makan.app.fragment.AgencyRegistrationFragment;
import com.makan.app.web.pojo.AgencyPackageResponse;
import com.makan.app.web.pojo.ProffesionalPackageResponse;

import java.util.ArrayList;
import java.util.List;

public class AgencyPackageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<AgencyPackageResponse.AgencyPackage> mPackageList;
    private ArrayList<AgencyPackageResponse.CostDetail> costDetailsList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_packageName,tv_selectPlan,tv_details,tv_clientdetails,tv_descrption,tv_features,tv_packagePlan,tv_sociallink,tv_postinmob,tv_featured;
        public RecyclerView rv_packagePlan,rv_packageDetails;
        public ImageView iv_packageImage,iv_clientDetails,iv_socialLinks,iv_postInMobile,iv_featured,iv_imageLimit;
        private LinearLayout ll_client,ll_socialLink,ll_postIn,ll_featured,ll_imageLimit;

        public ViewHolder(View view) {
            super(view);
            iv_packageImage = (ImageView) view.findViewById(R.id.iv_packageImage);
            iv_clientDetails = (ImageView) view.findViewById(R.id.iv_clientDetails);
            iv_socialLinks = (ImageView) view.findViewById(R.id.iv_socialLinks);
            iv_postInMobile = (ImageView) view.findViewById(R.id.iv_postInMobile);
            iv_featured = (ImageView) view.findViewById(R.id.iv_featured);
            iv_imageLimit = (ImageView) view.findViewById(R.id.iv_imageLimit);
            tv_packageName = (TextView) view.findViewById(R.id.tv_packageName);
            tv_selectPlan = (TextView) view.findViewById(R.id.tv_selectPlan);
            tv_details  = (TextView) view.findViewById(R.id.tv_details);
            tv_descrption  = (TextView) view.findViewById(R.id.tv_descrption);
            tv_features  = (TextView) view.findViewById(R.id.tv_features);
            tv_clientdetails  = (TextView) view.findViewById(R.id.tv_clientdetails);
            tv_packagePlan  = (TextView) view.findViewById(R.id.tv_packagePlan);
            tv_sociallink  = (TextView) view.findViewById(R.id.tv_sociallink);
            tv_postinmob  = (TextView) view.findViewById(R.id.tv_postinmob);
            tv_featured  = (TextView) view.findViewById(R.id.tv_featured);
            rv_packagePlan= (RecyclerView) view.findViewById(R.id.rv_packagePlan);


            ll_client= (LinearLayout) view.findViewById(R.id.ll_client);
            ll_socialLink= (LinearLayout) view.findViewById(R.id.ll_socialLink);
            ll_postIn= (LinearLayout) view.findViewById(R.id.ll_postIn);
            ll_featured= (LinearLayout) view.findViewById(R.id.ll_featured);
            ll_imageLimit= (LinearLayout) view.findViewById(R.id.ll_imageLimit);
            ll_imageLimit.setVisibility(View.GONE);

        }
    }

    public AgencyPackageAdapter(Context mContext, List<AgencyPackageResponse.AgencyPackage> mPackageList) {
        this.mContext = mContext;
        this.mPackageList = mPackageList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem_proffessional_package, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        final AgencyPackageResponse.AgencyPackage property = mPackageList.get(position);

        final ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.tv_packageName.setText(property.getPlanName());


        Glide.with(mContext).load(WebConstant.BASE_IMAGE_URL + property.getImage()).placeholder(R.drawable.placeholder_error).into(viewHolder.iv_packageImage);

        viewHolder.tv_clientdetails.setText(mContext.getResources().getString(R.string.propertylisting) + " " + "(" + property.getNoOfPpty() + ")");
        viewHolder.tv_sociallink.setText(mContext.getResources().getString(R.string.featuredlisting) + " " + "(" + property.getFeaturedListing() + ")");
        viewHolder.tv_postinmob.setText(mContext.getResources().getString(R.string.professionalservice) + " " + "(" + property.getAdevrtisment() + ")");
        viewHolder.tv_featured.setText(mContext.getResources().getString(R.string.emails));




        if (property.getPostReqMail().equals("no")) {
            Glide.with(mContext).load(R.drawable.error).into(viewHolder.iv_featured);
        }

        viewHolder.tv_descrption.setText(property.getDescription());

        viewHolder.tv_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.tv_details.setVisibility(View.GONE);
                viewHolder.tv_features.setVisibility(View.VISIBLE);

                viewHolder.tv_descrption.setVisibility(View.VISIBLE);

                viewHolder.ll_client.setVisibility(View.GONE);
                viewHolder.ll_featured.setVisibility(View.GONE);
                viewHolder.ll_imageLimit.setVisibility(View.GONE);
                viewHolder.ll_postIn.setVisibility(View.GONE);
                viewHolder.ll_socialLink.setVisibility(View.GONE);


            }
        });

        viewHolder.tv_features.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.tv_details.setVisibility(View.VISIBLE);
                viewHolder.tv_features.setVisibility(View.GONE);

                viewHolder.tv_descrption.setVisibility(View.GONE);

                viewHolder.ll_client.setVisibility(View.VISIBLE);
                viewHolder.ll_featured.setVisibility(View.VISIBLE);
                viewHolder.ll_imageLimit.setVisibility(View.VISIBLE);
                viewHolder.ll_postIn.setVisibility(View.VISIBLE);
                viewHolder.ll_socialLink.setVisibility(View.GONE);

            }
        });

        costDetailsList = new ArrayList<>();
        if (property.getCostDetails() != null && property.getCostDetails().size() > 0) {
            costDetailsList.addAll(property.getCostDetails());
            viewHolder.rv_packagePlan.setVisibility(View.VISIBLE);
            viewHolder.tv_packagePlan.setVisibility(View.GONE);
        } else{
            viewHolder.rv_packagePlan.setVisibility(View.GONE);
            viewHolder.tv_packagePlan.setVisibility(View.VISIBLE);
    }



        final AgencyCostDetailsAdapter agencyCostDetailsAdapter = new AgencyCostDetailsAdapter(mContext, costDetailsList);
        viewHolder.rv_packagePlan.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        viewHolder.rv_packagePlan.setItemAnimator(new DefaultItemAnimator());
        viewHolder.rv_packagePlan.setAdapter(agencyCostDetailsAdapter);





        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (agencyCostDetailsAdapter.getSelected() != null || property.getCostDetails().size() == 0 ){
                    Intent intent = new Intent(mContext, AgencyRegistrationPackage.class);
                    mContext.startActivity(intent);
                    if (property.getCostDetails().size()==0)
                    {
                        Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(mContext, agencyCostDetailsAdapter.getSelected().getPlanCostId(), Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.pleaseselectanyplan), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setRecycleview(List<ProffesionalPackageResponse.CostDetail> costDetails) {


    }

    @Override
    public int getItemCount() {
        return mPackageList.size();
    }

    public void addItems(List<ProffesionalPackageResponse.ProfessionalPackage> propertyList){

        propertyList.addAll(propertyList);

    }

}
