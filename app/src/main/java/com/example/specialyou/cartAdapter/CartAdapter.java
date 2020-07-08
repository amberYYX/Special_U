package com.example.specialyou.cartAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.specialyou.Adapter;
import com.example.specialyou.CartActivity;
import com.example.specialyou.Data.Product;
import com.example.specialyou.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter{

    private ArrayList<Product> mProducts;


    private Adapter.onItemClickListener mListener;

    // for click
    public interface onItemClickListener{
        void onItemClick(int position);
    }


    public void setOnItemClickListener(Adapter.onItemClickListener listener){
        mListener = listener;
    }

    public class MyCartViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mName;
        public TextView mNumber;
        public TextView mPrice;

        private Spinner chooseNumber;




        public MyCartViewHolder(final View itemView,
                            final Adapter.onItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.cart_image);
            mName = itemView.findViewById(R.id.cart_name);
            mNumber = itemView.findViewById(R.id.cart_number);
            mPrice = itemView.findViewById(R.id.cart_totalPrice);

            chooseNumber = itemView.findViewById(R.id.cart_spinner);



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

            chooseNumber.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
            {
                //选择item的选择点击监听事件
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3)
                {
//                    if(arg3 == 0){
//                        aggregation = aggregation + 1*Integer.parseInt(mShoppingList.get(0).price);
//                        totalPrice.setText("€"+aggregation);
//                    }
//                    if(arg3 == 1){
//                        aggregation = aggregation + 2*Integer.parseInt(mShoppingList.get(0).price);
//                        totalPrice.setText("€"+aggregation);
//                    }
//                    if(arg3 == 2){
//                        aggregation = aggregation + 3*Integer.parseInt(mShoppingList.get(0).price);
//                        totalPrice.setText("€"+aggregation);
//                    }

                }

                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });




        }

    }

    public CartAdapter(ArrayList<Product> products){
        mProducts = products;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        CartAdapter.MyCartViewHolder evh = new CartAdapter.MyCartViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final Product currentProduct = mProducts.get(position);

        String link = currentProduct.getLink();
        Picasso.get().load(link).into( ((CartAdapter.MyCartViewHolder)holder).mImageView);
        ((CartAdapter.MyCartViewHolder)holder).mName.setText(currentProduct.getName());
        ((CartAdapter.MyCartViewHolder)holder).mNumber.setText("1");
        ((CartAdapter.MyCartViewHolder)holder).mPrice.setText("€"+currentProduct.getPrice());


    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }
}
