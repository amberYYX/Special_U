package com.example.specialyou;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.specialyou.Data.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter {

    private ArrayList<Product> mProducts;

    private onItemClickListener mListener;

    // for click
    public interface onItemClickListener{
        void onItemClick(int position);
    }


    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mName;
        public TextView mPrice;

//        public ImageButton mDetails;

        public MyViewHolder(final View itemView,
                                 final onItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.card_image);
            mName = itemView.findViewById(R.id.card_name);
            mPrice = itemView.findViewById(R.id.card_price);

//            mDetails = itemView.findViewById(R.id.card_details);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }

    }

    public Adapter(ArrayList<Product> products){
        mProducts = products;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        MyViewHolder evh = new MyViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product currentProduct = mProducts.get(position);

        String link = currentProduct.getLink();
        Picasso.get().load(link).into( ((MyViewHolder)holder).mImageView);
        ((MyViewHolder)holder).mName.setText(currentProduct.getName());
        ((MyViewHolder)holder).mPrice.setText("€"+currentProduct.getPrice());

    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }
}
