package com.manajemeninformasi.riska.findingtutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.manajemeninformasi.riska.findingtutor.setting.Database;

public class HomeMuridActivity extends AppCompatActivity {
    Button cari, profile;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_murid);
        db = new Database(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        db.delete();
        startActivity(new Intent(this, MainActivity.class));
        finish();
        return super.onOptionsItemSelected(item);
    }
}
