package com.example.vidyadaan;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {

    private List<requestModel> requestdetails;

    public Adapter(List<requestModel> requestdetails){this.requestdetails=requestdetails;}




    @NonNull
    @Override
    public Adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_donor_needs,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.viewHolder holder, int position) {

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

            request_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = request_id.getText().toString();
                    Intent intent37 = new Intent(view.getContext(),view_request.class);
                    intent37.putExtra("request",id);
                    view.getContext().startActivity(intent37);
                }
            });
            request_desc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = request_id.getText().toString();
                    Intent intent37 = new Intent(view.getContext(),view_request.class);
                    intent37.putExtra("request",id);
                    view.getContext().startActivity(intent37);
                }
            });
        }

        public void setData(String ngo_request_id, String ngo_request_desc) {
            request_id.setText(ngo_request_id);
            request_desc.setText(ngo_request_desc);
        }
    }
}
