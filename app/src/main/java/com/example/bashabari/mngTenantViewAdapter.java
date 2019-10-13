package com.example.bashabari;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class mngTenantViewAdapter extends RecyclerView.Adapter<mngTenantViewAdapter.tenantViewHolder> {
    private Context mCtx;
    private List<tenantInfo> tenantList;

    public mngTenantViewAdapter(Context mCtx, List<tenantInfo> tenantList) {
        this.mCtx = mCtx;
        this.tenantList = tenantList;
    }

    @NonNull
    @Override
    public mngTenantViewAdapter.tenantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.manage_tenanat_cardview, parent, false);
        return new mngTenantViewAdapter.tenantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull mngTenantViewAdapter.tenantViewHolder holder, int position) {
        tenantInfo ten = tenantList.get(position);
        holder.nameShow.setText(ten.getName());
        holder.phoneShow.setText(ten.getPhone_no());

    }

    @Override
    public int getItemCount() { return tenantList.size(); }

    public class tenantViewHolder extends RecyclerView.ViewHolder {
        TextView phoneShow;
        CheckBox nameShow;
        public tenantViewHolder(@NonNull View itemView) {
            super(itemView);
            nameShow = itemView.findViewById(R.id.name_mng_tenants);
            phoneShow = itemView.findViewById(R.id.phone_mng_tenants);
        }
    }
}
