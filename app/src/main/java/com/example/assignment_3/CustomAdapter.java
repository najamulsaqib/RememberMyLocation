package com.example.assignment_3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> loc, desc;
    private ArrayList<byte[]> img;
    public Bitmap bitmap;

    public CustomAdapter(Context context, ArrayList loc, ArrayList desc, ArrayList img){
        this.context = context;
        this.loc = loc;
        this.desc = desc;
        this.img = img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.location.setText(String.valueOf(loc.get(position)));
        holder.description.setText(String.valueOf(desc.get(position)));

        bitmap = BitmapFactory.decodeByteArray(img.get(position), 0, img.get(position).length);
        holder.image.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return loc.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView location, description;
        ImageView image;
        CardView cardView;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            location = itemView.findViewById(R.id.location);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.image);

        }
    }
}
