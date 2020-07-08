package com.example.specialyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.specialyou.Data.Product;
import com.example.specialyou.cartAdapter.CartAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ArrayList<Product> mPickedProducts;
    private RecyclerView mRecycleView;
    private CartAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private TextView totalPrice;
    private Button backBt;
    private Button checkOut;

    public int aggregation;

    public CartActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //backBt = findViewById(R.id.cartBackToDisplay);
        checkOut = findViewById(R.id.check_out);
        totalPrice = findViewById(R.id.total_price);

        mPickedProducts = new ArrayList<>();

        aggregation = 0;

        buildRecyclerView();
        creatMyList();

        totalPrice.setText("€"+aggregation);

//        backBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(CartActivity.this,DisplayActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this,GoogleMapActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    private void creatMyList() {

        // get data from display picked items
        ArrayList<Product> shoppingList = (ArrayList<Product>) getIntent().getSerializableExtra("shoppingList");
        for(int i = 0; i< shoppingList.size();i++){
            mPickedProducts.add(shoppingList.get(i));
            aggregation = aggregation+Integer.parseInt(shoppingList.get(i).price);
        }
//        mPickedProducts.add(test);

//        Intent intent = getIntent();
//        String userId = intent.getStringExtra("userID");
//        final List<String> mShoppingList = null;
//
//
//        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("shoppinglist");
//        dbReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    String key = postSnapshot.getKey();
//                    mShoppingList.add(key);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        for (int i = 0; i< mShoppingList.size();i++){
//            final String id = mShoppingList.get(i);
//            DatabaseReference Reference = FirebaseDatabase.getInstance().getReference().child("products").child(id);
//            Reference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    String link = dataSnapshot.child("image").getValue().toString();
//                    String name = dataSnapshot.child("name").getValue().toString();
//                    String price = dataSnapshot.child("price").getValue().toString();
//
//                    mPickedProducts.add(new Product(id,link, name, "0", price));
//                    mAdapter.notifyDataSetChanged();
////            aggregation = aggregation+Integer.parseInt(shoppingList.get(i).price);
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
    }



    private void buildRecyclerView() {
        mRecycleView = findViewById(R.id.id_cartRecyclerView);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CartAdapter(mPickedProducts);
        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setAdapter(mAdapter);
    }
}
