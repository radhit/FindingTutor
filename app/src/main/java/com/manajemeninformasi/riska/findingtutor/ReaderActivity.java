package com.manajemeninformasi.riska.findingtutor;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.manajemeninformasi.riska.findingtutor.setting.Connect;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReaderActivity extends AppCompatActivity {
    private Button scan_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        final Activity activity = this;
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                finish();
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            else {
                //Toast.makeText(this, result.getContents(),Toast.LENGTH_LONG).show();
                next(result.getContents());
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void next(final String contents) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Connect.GETDATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("status").equals("1")) {
                        Intent aIntent = new Intent(ReaderActivity.this, CountdownActivity.class);
                        aIntent.putExtra("durasi", jsonObject.getString("durasi"));
                        startActivity(aIntent);
                        finish();
                    } else if(jsonObject.getString("status").equals("2")){
                        Toast.makeText(ReaderActivity.this, "Transaksi telah Selesai Terima Kasih Telah Menggunakan Aplikasi Ini", Toast.LENGTH_SHORT).show();
                        tambahHistory(contents);
//                        Intent gxIntent = new Intent(ReaderActivity.this, MainActivity.class);
//                        startActivity(gxIntent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("qr_codes", contents);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void tambahHistory(final String qr_codes) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Connect.ADDHISTORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(ReaderActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //    progresDialog.hide();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("qr_codes", qr_codes);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ReaderActivity.this);
        requestQueue.add(stringRequest);
    }

}
