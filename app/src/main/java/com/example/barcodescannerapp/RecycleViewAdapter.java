package com.example.barcodescannerapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;

class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {
    List<ScanData> scanDataList;
    Context context;

    public RecycleViewAdapter(List<ScanData> scanDataList, Context context) {
        this.scanDataList = scanDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_data,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.tv_id.setText(String.valueOf(scanDataList.get(position).getId()));
        holder.tv_scanDate.setText(scanDataList.get(position).getDateOfScan());
        holder.tv_scanData.setText(scanDataList.get(position).getData());
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanData scanData = scanDataList.get(holder.getAdapterPosition());
                DatabaseHelper databaseHelper = new DatabaseHelper(holder.btn_delete.getContext());
                databaseHelper.deleteOne(scanData);
                scanDataList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return scanDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id;
        TextView tv_scanDate;
        TextView tv_scanData;
        Button btn_delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_scanDate = itemView.findViewById(R.id.tv_scanDate);
            tv_scanData = itemView.findViewById(R.id.tv_scanData);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
