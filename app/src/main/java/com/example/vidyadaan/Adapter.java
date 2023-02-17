package com.example.vidyadaan;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
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
                    try {
                        String name = request_id.getText().toString();
                        Double.parseDouble(name);
                        try {
                            String filename = "Userdetail.txt";
                            FileInputStream fin = view.getContext().openFileInput(filename);
                            int a;
                            StringBuilder temp = new StringBuilder();
                            while ((a = fin.read()) != -1) {
                                temp.append((char) a);
                            }
                            String user = temp.toString();
                            if (user.charAt(0) == 'D') {
                                String id = request_id.getText().toString();
                                Intent intent37 = new Intent(view.getContext(), view_request.class);
                                intent37.putExtra("request", id);
                                view.getContext().startActivity(intent37);
                                fin.close();
                            } else {
                                String id = request_id.getText().toString();
                                Intent intent37 = new Intent(view.getContext(), ngo_request_status.class);
                                intent37.putExtra("request", id);
                                view.getContext().startActivity(intent37);
                                fin.close();
                            }
                        } catch (Exception e) {
                            String id = request_id.getText().toString();
                            Intent intent37 = new Intent(view.getContext(), Choose.class);
                            intent37.putExtra("request", id);
                            view.getContext().startActivity(intent37);

                        }
                    }
                    catch (NumberFormatException e){
                    }
                }
            });
            request_desc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        String name = request_id.getText().toString();
                        Double.parseDouble(name);
                        try
                        {
                            String filename = "Userdetail.txt";
                            FileInputStream fin = view.getContext().openFileInput(filename);
                            int a;
                            StringBuilder temp = new StringBuilder();
                            while ((a = fin.read()) != -1)
                            {
                                temp.append((char)a);
                            }
                            String user = temp.toString();
                            if(user.charAt(0)=='D'){
                                String id = request_id.getText().toString();
                                Intent intent37 = new Intent(view.getContext(),view_request.class);
                                intent37.putExtra("request",id);
                                view.getContext().startActivity(intent37);
                                fin.close();
                            }
                            else{
                                String id = request_id.getText().toString();
                                Intent intent37 = new Intent(view.getContext(),ngo_request_status.class);
                                intent37.putExtra("request",id);
                                view.getContext().startActivity(intent37);
                                fin.close();
                            }
                        }
                        catch (Exception e){
                            String id = request_id.getText().toString();
                            Intent intent37 = new Intent(view.getContext(),Choose.class);
                            intent37.putExtra("request",id);
                            view.getContext().startActivity(intent37);

                        }
                    }
                    catch (NumberFormatException e){

                    }

                }
            });
        }

        public void setData(String ngo_request_id, String ngo_request_desc) {
            request_id.setText(ngo_request_id);
            request_desc.setText(ngo_request_desc);
        }
    }
}
