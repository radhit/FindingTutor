package com.manajemeninformasi.riska.findingtutor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.manajemeninformasi.riska.findingtutor.setting.Connect;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GeneratorActivity extends AppCompatActivity {
    Button gen_btn;
    ImageView image;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);

        gen_btn = (Button) findViewById(R.id.btnEnd);
        image = (ImageView) findViewById(R.id.image);
        s = getIntent().getStringExtra("QR_CODES");

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(s, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            image.setImageBitmap(bitmap);
        }
        catch (WriterException e){
            e.printStackTrace();
        }
        waktu(s);
    }
    private void waktu(final String tes) {
        CountDownTimer countDownTimer = new CountDownTimer(120 * 60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long hour = millisUntilFinished / 3600000;
                long sisaHour = millisUntilFinished % 3600000;

                long minute = sisaHour / 60000;
                long sisaMinute = sisaHour % 60000;


                long second = sisaMinute / 1000;
                //textView.setText(hour+":"+minute+":"+second);
                tombol(tes);
            }

            @Override
            public void onFinish() {
                getData(tes,2);

            }
        }; countDownTimer.start();
    }

    private void tombol(final String status) {
        //getData(status);
        gen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(status,1);
            }
        });
    }

    private void getData(final String s, final int temp) {
        //for (int i = 0; i < 60; i++) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Connect.TIMESERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String current_data) {
                try {
                    final JSONObject jsonObject = new JSONObject(current_data);
                    //String min = jsonObject.getString("min");
                    final String status = jsonObject.getString("status");

                    if (status.equals("0") && temp == 2) {
                        Toast.makeText(GeneratorActivity.this, "QR Codes gagal di scan dalam kurun waktu yang telah ditentukan!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(GeneratorActivity.this, " Transaksi dibatalkan!", Toast.LENGTH_SHORT).show();
                        Intent aIntent = new Intent(GeneratorActivity.this, MainActivity.class);
                        startActivity(aIntent);
                        finish();
                    }
                    else if(temp == 1) {
                        if (status.equals("2")) {
                            Toast.makeText(GeneratorActivity.this, "Transaksi telah Selesai Terima Kasih Telah Menggunakan Aplikasi Ini", Toast.LENGTH_SHORT).show();
                            Intent tIntent = new Intent(GeneratorActivity.this, MainActivity.class);
                            startActivity(tIntent);
                            finish();
                        } else {
                            Toast.makeText(GeneratorActivity.this, "Transaksi masih sedang berjalan", Toast.LENGTH_SHORT).show();
                        }
                    }
                    //waktu(s);
                    //tombol(s);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //    progresDialog.hide();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("qr_codes", s);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
