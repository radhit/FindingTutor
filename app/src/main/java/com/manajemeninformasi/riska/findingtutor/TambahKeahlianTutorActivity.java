package com.manajemeninformasi.riska.findingtutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.manajemeninformasi.riska.findingtutor.setting.Database;

public class TambahKeahlianTutorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Database db;
    private Spinner spinner;
    private String pilih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_keahlian_tutor);
        db = new Database(this);
        spinner = (Spinner) findViewById(R.id.spkelas);
        spinner.setOnItemSelectedListener(this);

        Button back = (Button) findViewById(R.id.btnback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button submit = (Button) findViewById(R.id.btnsubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahKeahlian();
            }
        });
        ArrayAdapter<CharSequence> kelasAdapter = ArrayAdapter.createFromResource(this, R.array.kelas, android.R.layout.simple_spinner_item);
        kelasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(kelasAdapter);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        pilih = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        pilih = "";
    }

    private void tambahKeahlian()
    {
        String cekkelas, cekpelajaran, cekbiaya, cekketersediaan;
    }
}
