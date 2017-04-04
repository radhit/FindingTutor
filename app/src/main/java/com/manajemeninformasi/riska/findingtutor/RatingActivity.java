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
                Toast.makeText(RatingActivity.this, "Id:"+bundle.getString("id")+"Rating: "+ratingBar.getRating()+"komentar:"+etkomentar.getText().toString(), Toast.LENGTH_SHORT).show();
                Log.d("data = ",String.valueOf(ratingBar.getRating()));
                //submitRating();
            }
        });
    }

    private void submitRating() {

    }
}
