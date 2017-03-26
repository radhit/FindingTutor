package com.manajemeninformasi.riska.findingtutor;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class CountdownActivity extends AppCompatActivity {
    TextView textView;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        textView = (TextView) findViewById(R.id.textView6);

        s = getIntent().getStringExtra("durasi");
        textView.setText(s);

        CountDownTimer countDownTimer = new CountDownTimer(Integer.parseInt(textView.getText().toString())*60*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long hour = millisUntilFinished/3600000;
                long sisaHour = millisUntilFinished%3600000;

                long minute = sisaHour/60000;
                long sisaMinute = sisaHour%60000;


                long second = sisaMinute/1000;

                textView.setText(hour+":"+minute+":"+second);
            }

            @Override
            public void onFinish() {
                Toast.makeText(CountdownActivity.this, "Waktu Transaksi telah Selesai", Toast.LENGTH_SHORT).show();
                Intent tIntent = new Intent(CountdownActivity.this, ReaderActivity.class);
                startActivity(tIntent);
                finish();
            }
        };
        countDownTimer.start();
    }
}
