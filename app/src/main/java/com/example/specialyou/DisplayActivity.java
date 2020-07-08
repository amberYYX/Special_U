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

import com.example.specialyou.Data.Product;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    private ArrayList<Product> mProducts;
    private RecyclerView mRecycleView;
    private Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager2;
    private com.google.android.material.floatingactionbutton.FloatingActionButton floatingButton;

    private ArrayList<Product> mPickedItems;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference databaseReference1;
    private DatabaseReference databaseReference2;
    private DatabaseReference databaseReference3;
    private DatabaseReference databaseReference4;
    private DatabaseReference databaseReference5;

    private DatabaseReference dbReference;

    private String userId;



    public static final String Tag = "DisplayActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        floatingButton = findViewById(R.id.floatingActionButton);

        creatProductsList();
        buildRecyclerView();


        getProductsListFromDatabase();

        mPickedItems = new ArrayList<>();

        Intent intent = getIntent();
        userId = intent.getStringExtra("userID");


        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity.this,CartActivity.class);
//                intent.putExtra("test",mProducts.get(0));
//                old way
                intent.putExtra("shoppingList",mPickedItems);

                intent.putExtra("userID",userId);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 66){
            if(resultCode == 666){
                String idReceive = data.getStringExtra("pickedID");
                final String id = idReceive;
                Log.d("get call back",id);

                dbReference = FirebaseDatabase.getInstance().getReference().child("products").child(id);
                dbReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String link = dataSnapshot.child("image").getValue().toString();
                        final String name = dataSnapshot.child("name").getValue().toString();
                        String stock = dataSnapshot.child("stock").getValue().toString();
                        String price = dataSnapshot.child("price").getValue().toString();

                        // add item to firebase
                        FirebaseDatabase database =  FirebaseDatabase.getInstance();
//                        DatabaseReference mRef =  database.getReference().child("Users").child(userId).child("childshoppinglist").child(name);
                        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("shoppinglist");
                        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (snapshot.hasChild(id)) {
                                    DatabaseReference mRef =  FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("shoppinglist").child(id);
                                    int currentNumber = Integer.parseInt(snapshot.child(id).getValue().toString());
                                    currentNumber = currentNumber+1;
                                    mRef.setValue(currentNumber);
                                    // run some code
                                }else{
                                    DatabaseReference mRef =  FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("shoppinglist").child(id);
                                    mRef.setValue(1);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

//                        mRef.setValue(1);


                        Product pickedOne = new Product(id,link,name,stock,price);
                        mPickedItems.add(pickedOne);
                        Log.d(Tag,"picked one added to new array list.");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }if (resultCode == 77){

            }

        }
    }



    private void creatProductsList() {
        mProducts = new ArrayList<>();
    }

    private void buildRecyclerView() {
        mRecycleView = findViewById(R.id.id_recyclerView);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager2 = new GridLayoutManager(this, 2);
        mAdapter = new Adapter(mProducts);
        mRecycleView.setLayoutManager(mLayoutManager2);
        mRecycleView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new Adapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = new Intent(DisplayActivity.this, SingleProductActivity.class);
                intent.putExtra("checkFurther",mProducts.get(position));
                startActivityForResult(intent,66);
//                startActivity(intent);

            }
        });
    }



    private void getProductsListFromDatabase() {
        Log.d(Tag,"start to get product info.");

        //database, item 5
        final String id5 = "earing5";
        databaseReference5 = FirebaseDatabase.getInstance().getReference().child("products").child(id5);
        databaseReference5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String link = dataSnapshot.child("image").getValue().toString();
                String name = dataSnapshot.child("name").getValue().toString();
                String stock = dataSnapshot.child("stock").getValue().toString();
                String price = dataSnapshot.child("price").getValue().toString();

//                int position = mProducts.size();
                addNewProducts(3,id5,link,name,stock,price);
                mAdapter.notifyDataSetChanged();
                Log.d(Tag,"add earing5 finished.");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //database, item 1
        final String id4 = "earing4";
        databaseReference4 = FirebaseDatabase.getInstance().getReference().child("products").child(id4);
        databaseReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String link = dataSnapshot.child("image").getValue().toString();
                String name = dataSnapshot.child("name").getValue().toString();
                String stock = dataSnapshot.child("stock").getValue().toString();
                String price = dataSnapshot.child("price").getValue().toString();

//                int position = mProducts.size();
                addNewProducts(4,id4,link,name,stock,price);
                mAdapter.notifyDataSetChanged();
                Log.d(Tag,"add earing3 finished.");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //database, item 1
        final String id3 = "earing3";
        databaseReference3 = FirebaseDatabase.getInstance().getReference().child("products").child(id3);
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String link = dataSnapshot.child("image").getValue().toString();
                String name = dataSnapshot.child("name").getValue().toString();
                String stock = dataSnapshot.child("stock").getValue().toString();
                String price = dataSnapshot.child("price").getValue().toString();

//                int position = mProducts.size();
                addNewProducts(0,id3,link,name,stock,price);
                mAdapter.notifyDataSetChanged();
                Log.d(Tag,"add earing3 finished.");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //database, item 2
        final String id2 = "earing2";
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("products").child(id2);
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String link = dataSnapshot.child("image").getValue().toString();
                String name = dataSnapshot.child("name").getValue().toString();
                String stock = dataSnapshot.child("stock").getValue().toString();
                String price = dataSnapshot.child("price").getValue().toString();

//                int position = mProducts.size();
                addNewProducts(1,id2,link,name,stock,price);
                mAdapter.notifyDataSetChanged();
                Log.d(Tag,"add earing2 finished.");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //database, item 1
        final String id1 = "earing1";
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("products").child(id1);
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String link = dataSnapshot.child("image").getValue().toString();
                String name = dataSnapshot.child("name").getValue().toString();
                String stock = dataSnapshot.child("stock").getValue().toString();
                String price = dataSnapshot.child("price").getValue().toString();

//                int position = mProducts.size();
                addNewProducts(2,id1,link,name,stock,price);
                mAdapter.notifyDataSetChanged();
                Log.d(Tag,"add earing1 finished.");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }

    public void addNewProducts(int mPosition,
                               String mId,
                               String mLink,
                               String mName,
                               String mStock,
                               String mPrice){
//        mProducts.add(new Product(mLink,mName,Integer.valueOf(mStock)));

        mProducts.add(new Product(mId,mLink,mName,mStock,mPrice));
        mAdapter.notifyDataSetChanged();
    }



}
