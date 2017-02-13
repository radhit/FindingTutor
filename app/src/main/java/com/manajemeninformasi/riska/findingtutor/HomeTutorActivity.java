package com.manajemeninformasi.riska.findingtutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeTutorActivity extends AppCompatActivity {
    Button cari, keahlian, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tutor);
        cari = (Button) findViewById(R.id.btncari);
        keahlian = (Button) findViewById(R.id.btnkeahlian);
        profile = (Button) findViewById(R.id.btnprofile);
        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toIntent(CariMuridActivity.class);
            }
        });
        keahlian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toIntent(KeahlianTutorActivity.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toIntent(ProfileTutorActivity.class);
            }
        });
    }
    public void toIntent(Class x)
    {
        Intent myintent = new Intent(getBaseContext(),x);
        startActivityForResult(myintent,0);
    }
}
