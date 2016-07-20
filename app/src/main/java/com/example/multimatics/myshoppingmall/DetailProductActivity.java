package com.example.multimatics.myshoppingmall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DetailProductActivity extends AppCompatActivity {

    private TextView tvName, tvPrice, tvDesc;
    private Button btnAddToCart;
    private ImageView imgDetail;
    private Spinner spnSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        tvName = (TextView)findViewById(R.id.tv_name);
        tvPrice = (TextView)findViewById(R.id.tv_price);
        btnAddToCart = (Button)findViewById(R.id.btn_add_to_cart);
        imgDetail = (ImageView)findViewById(R.id.img_detail);
        spnSize = (Spinner)findViewById(R.id.spn_size);
        tvDesc = (TextView)findViewById(R.id.tv_desc);

        getSupportActionBar().setTitle("Detail Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Product selectedProduct = getIntent().getParcelableExtra("product");
        tvName.setText(selectedProduct.getName());
        tvPrice.setText(selectedProduct.getPrice());
            Glide.with(DetailProductActivity.this).load(selectedProduct.getImageUrl()).into(imgDetail);

        String[] size = new String[]{
        "Pilih Ukuran",
                "38",
                "39", "40","41", "42", "43", "44", "45"};

            ArrayAdapter<String> sizeAdapter = new ArrayAdapter<String>(DetailProductActivity.this, android.R.layout.simple_dropdown_item_1line, android.R.id.text1, size);
        spnSize.setAdapter(sizeAdapter);

        String desc = "Not only should your apps stop relying on the hardware Menu button, but you should stop thinking about your activities using a “menu button” at all. Your activities should provide buttons for important user actions directly in the action bar (or elsewhere on screen). Those that can’t fit in the action bar end up in the action overflow.\n" +
                "\n" +
                "In the screenshot here, you can see an action button for Search and the action overflow on the right side of the action bar.\n" +
                "\n" +
                "Even if your app is built to support versions of Android older than 3.0 (in which apps traditionally use the options menu panel to display user options/actions), when it runs on Android 3.0 and beyond, there’s no Menu button. The button that appears in the system/navigation bar represents the action overflow for legacy apps, which reveals actions and user options that have.\n";
        tvDesc.setText(desc);
    }
}

