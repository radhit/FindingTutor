package com.manajemeninformasi.riska.findingtutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeMuridActivity extends AppCompatActivity {
    Button cari, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_murid);
        cari = (Button) findViewById(R.id.btncari);
        profile = (Button) findViewById(R.id.btnprofile);
        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toIntent(CariTutorActivity.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toIntent(ProfileMuridActivity.class);
            }
        });
    }
    public void toIntent(Class x)
    {
        Intent myintent = new Intent(getBaseContext(),x);
        startActivityForResult(myintent,0);
    }
}
