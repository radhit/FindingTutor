package com.manajemeninformasi.riska.findingtutor;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.manajemeninformasi.riska.findingtutor.setting.Database;

public class HomeTutorActivity extends AppCompatActivity {
    Button cari, keahlian, profile, history;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tutor);
        db = new Database(this);
        cari = (Button) findViewById(R.id.btncari);
        keahlian = (Button) findViewById(R.id.btnkeahlian);
        profile = (Button) findViewById(R.id.btnprofile);
        history = (Button) findViewById(R.id.btnhistory);
        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toIntent(KriteriaMuridActivity.class);
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
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toIntent(HistoryTutorActivity.class);
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
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Keluar");
        alertDialog.setMessage("Anda yakin ingin keluar?")
                .setCancelable(false)
                .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }
}
