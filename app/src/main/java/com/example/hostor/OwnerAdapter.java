package com.example.hostor;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class OwnerAdapter extends RecyclerView.Adapter<OwnerViewHolder> {

    private Context context;
    private List<DataClass> dataList;

    public OwnerAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public OwnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.owner_recycler_item, parent, false);
        return new OwnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getImageURL()).into(holder.recImage);
        holder.recLocation.setText(dataList.get(position).getLocation());
        holder.recRoomType.setText(dataList.get(position).getRoomType());
        holder.recInternet.setText(dataList.get(position).getInternet());
        holder.recPark.setText(dataList.get(position).getPark());
        holder.recFood.setText(dataList.get(position).getFood());
        holder.recResidential.setText(dataList.get(position).getResidential());
        holder.recPrice.setText(dataList.get(position).getPrice());

        holder.OwnerrecCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OwnerDetailActivity.class);
                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getImageURL());
                intent.putExtra("Location", dataList.get(holder.getAdapterPosition()).getLocation());
                intent.putExtra("RoomType", dataList.get(holder.getAdapterPosition()).getRoomType());
                intent.putExtra("Internet", dataList.get(holder.getAdapterPosition()).getInternet());
                intent.putExtra("Park", dataList.get(holder.getAdapterPosition()).getPark());
                intent.putExtra("Food", dataList.get(holder.getAdapterPosition()).getFood());
                intent.putExtra("Residential", dataList.get(holder.getAdapterPosition()).getResidential());
                intent.putExtra("Price", dataList.get(holder.getAdapterPosition()).getPrice());

                intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(ArrayList<DataClass> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }
}

class OwnerViewHolder extends RecyclerView.ViewHolder{

    ImageView recImage;
    TextView recLocation,recRoomType,recInternet,recPark,recFood, recResidential, recPrice;
    CardView OwnerrecCard;

    public OwnerViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage = itemView.findViewById(R.id.recyclerImage);
        OwnerrecCard = itemView.findViewById(R.id.OwnerRecCard);
        recLocation = itemView.findViewById(R.id.recyclerLocation);
        recRoomType = itemView.findViewById(R.id.recyclerRoomType);
        recInternet = itemView.findViewById(R.id.recyclerInternet);
        recPark = itemView.findViewById(R.id.recyclerPark);
        recFood = itemView.findViewById(R.id.recyclerFood);
        recResidential = itemView.findViewById(R.id.recyclerResidential);
        recPrice = itemView.findViewById(R.id.recyclerPrice);
    }
}