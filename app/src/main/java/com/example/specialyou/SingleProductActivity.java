package com.example.specialyou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.specialyou.Data.Product;
import com.example.specialyou.Data.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SingleProductActivity extends AppCompatActivity {

    private ImageView mImage;
    private TextView mName;
    private TextView mPrice;
    private Button addToCartBt;
    private Button backBt;

    private String purchaseState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);

        mImage = findViewById(R.id.single_image);
        mName = findViewById(R.id.single_name);
        mPrice = findViewById(R.id.single_price);
        addToCartBt = findViewById(R.id.single_addToCart);
        backBt = findViewById(R.id.single_back);

        purchaseState = "not";

        final Product furtherCheck = (Product)getIntent().getSerializableExtra("checkFurther");

        String productImageLink = furtherCheck.getLink();
        String productName = furtherCheck.getName();
        String productPrice = furtherCheck.getPrice();

        Picasso.get().load(productImageLink).into(mImage);

        mName.setText(productName);
        mPrice.setText("€"+productPrice);

//        final Product forBuy = furtherCheck;
//        Log.d("check state", forBuy.getLink());

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(purchaseState.equals("yes")){
                    Intent intent = new Intent(SingleProductActivity.this, DisplayActivity.class);
                    String pickedID = furtherCheck.getId();
                    Log.d("picked ID",pickedID);
                    intent.putExtra("pickedID",pickedID);
                    intent.putExtra("test","test");

                    setResult(666,intent);
                    finish();
//                    startActivity(intent);
                }if(purchaseState.equals("not")){
                    Intent intent = new Intent(SingleProductActivity.this, DisplayActivity.class);
                    setResult(77,intent);
                    finish();
//                    startActivity(intent);
                }
            }
        });

        addToCartBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                purchaseState = "yes";
                Toast.makeText(SingleProductActivity.this, "This item has been added to your cart.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
