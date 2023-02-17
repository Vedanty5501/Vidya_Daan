package com.example.vidyadaan;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
import java.util.List;

public class Adapter_new extends RecyclerView.Adapter<Adapter_new.viewHolder>{
    private List<requestModel> requestdetails;

    public Adapter_new(List<requestModel> requestdetails){this.requestdetails=requestdetails;}

    @NonNull
    @Override
    public Adapter_new.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_donor_needs,parent,false);
        return new Adapter_new.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_new.viewHolder holder, int position) {

        String ngo_request_id = requestdetails.get(position).getRequestid();
        String ngo_request_desc = requestdetails.get(position).getRequestdesc();

        holder.setData(ngo_request_id,ngo_request_desc);

    }

    @Override
    public int getItemCount() {
        return requestdetails.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        private TextView request_id,request_desc;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            request_id = itemView.findViewById(R.id.requestid);
            request_desc = itemView.findViewById(R.id.requestdesc);
        }

        public void setData(String ngo_request_id, String ngo_request_desc) {
            request_id.setText(ngo_request_id);
            request_desc.setText(ngo_request_desc);
        }
    }

}
