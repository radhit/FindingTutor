package com.manajemeninformasi.riska.findingtutor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.manajemeninformasi.riska.findingtutor.setting.Connect;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RatingActivity extends AppCompatActivity {
    private RatingBar ratingBar;
    private TextView tvnama, tvpelajaran, tvtanggal, tvbiaya;
    private EditText etkomentar;
    private Bundle bundle;
    private Button back, submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        tvnama = (TextView) findViewById(R.id.tvnamatutor);
        tvtanggal = (TextView) findViewById(R.id.tvtanggal);
        tvpelajaran = (TextView) findViewById(R.id.tvpelajaran);
        tvbiaya = (TextView) findViewById(R.id.tvbiaya);
        etkomentar = (EditText) findViewById(R.id.etkomentar);
        ratingBar = (RatingBar) findViewById(R.id.rating);
        bundle = getIntent().getBundleExtra("bundle");

        tvnama.setText(bundle.getString("nama_tutor"));
        tvtanggal.setText(bundle.getString("tanggal"));
        tvpelajaran.setText(bundle.getString("pelajaran"));
        tvbiaya.setText(bundle.getString("biaya"));
        etkomentar.setText(bundle.getString("komentar"), TextView.BufferType.EDITABLE);


        back = (Button) findViewById(R.id.btnback);
        submit = (Button) findViewById(R.id.btnsubmit);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating(bundle.getInt("id"), ratingBar.getRating(), etkomentar.getText().toString());
                //Toast.makeText(RatingActivity.this, "Id:"+bundle.getInt("id")+"Rating: "+ratingBar.getRating()+"komentar:"+etkomentar.getText().toString(), Toast.LENGTH_SHORT).show();
                Log.d("data = ",String.valueOf(ratingBar.getRating()));
                //submitRating();
            }
        });
    }

    private void rating(final int id, final float rating, final String komentar) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Connect.RATING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",String.valueOf(id));
                params.put("rating",String.valueOf(rating));
                params.put("komentar", komentar);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
