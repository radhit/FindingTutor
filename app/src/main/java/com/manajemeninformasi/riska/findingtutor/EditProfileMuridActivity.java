package com.manajemeninformasi.riska.findingtutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.manajemeninformasi.riska.findingtutor.setting.Database;

public class EditProfileMuridActivity extends AppCompatActivity {
    private Database db;
    private EditText nama, alamat, usia, notelp, email;
    private Button back, submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_murid);
        db = new Database(this);
        nama = (EditText) findViewById(R.id.etnama);
        alamat = (EditText) findViewById(R.id.etalamat);
        usia = (EditText) findViewById(R.id.etusia);
        notelp = (EditText) findViewById(R.id.etnotelp);
        email = (EditText) findViewById(R.id.etemail);

        back = (Button) findViewById(R.id.btnback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit = (Button) findViewById(R.id.btnsubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ceknama,cekalamat,cekusia,cektelp,cekemail,cekusername,cekpass;
                ceknama = nama.getText().toString();
                cekalamat = alamat.getText().toString();
                cekusia = usia.getText().toString();
                cektelp = notelp.getText().toString();
                cekemail = email.getText().toString();
                if(ceknama.matches("") || cekalamat.matches("") || cekusia.matches("") ||
                        cektelp.matches("") || cekemail.matches(""))
                {
                    Toast.makeText(getApplicationContext(),"Semua data harus di isi!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    updateUser();
                    Intent intent = new Intent(getApplicationContext(), HomeMuridActivity.class);
                    startActivity(intent);
                }
            }
        });


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
    public void updateUser()
    {
        final String getNama, getAlamat, getUsia, getTelp, getEmail;
        getNama = nama.getText().toString();
        getAlamat = alamat.getText().toString();
        getUsia = usia.getText().toString();
        getTelp = notelp.getText().toString();
        getEmail = email.getText().toString();
    }
}
