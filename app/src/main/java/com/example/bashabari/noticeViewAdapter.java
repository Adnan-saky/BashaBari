package com.example.bashabari;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class noticeViewAdapter extends RecyclerView.Adapter<noticeViewAdapter.noticeViewHolder> {
    private Context mCtx;
    private List<noticeInfo> noticeList;

    public noticeViewAdapter(Context mCtx, List<noticeInfo> noticeList) {
        this.mCtx = mCtx;
        this.noticeList = noticeList;
    }

    @NonNull
    @Override
    public noticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.notices_cardview, parent, false);
        return new noticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull noticeViewHolder holder, int position) {
        noticeInfo not = noticeList.get(position);
        holder.nameShow.setText(not.getName());
        holder.dateShow.setText(not.getDate());
        holder.textShow.setText(not.getText());
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    public class noticeViewHolder extends RecyclerView.ViewHolder {
        TextView nameShow, dateShow, textShow;
        public noticeViewHolder(@NonNull View itemView) {
            super(itemView);

            nameShow = itemView.findViewById(R.id.name_rv_notices);
            dateShow = itemView.findViewById(R.id.date_rv_notices);
            textShow = itemView.findViewById(R.id.text_rv_notices);
        }
    }
}
