package com.azhar.couplecat.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.azhar.couplecat.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BarcodeTicketActivity extends AppCompatActivity {
    @BindView(R.id.imageView)
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_ticket);
        ButterKnife.bind(this);
        initQRCode();
    }
    private void initQRCode() {
//        String name = "Name : "+firstName.getText().toString() +" "+lastName.getText().toString();
//        String email = "Email: "+Email.getText().toString();
//        String phonenumber = "Phone: "+PhoneNumber.getText().toString();
//        String address = "Address: "+Address.getText().toString();

        StringBuilder textToSend = new StringBuilder();
        textToSend.append("http://localhost/anabul/"+getIntent().getStringExtra("id"));
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(textToSend.toString(), BarcodeFormat.QR_CODE, 600, 600);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);
            imageView.setVisibility(View.VISIBLE);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
