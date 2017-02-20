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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.manajemeninformasi.riska.findingtutor.setting.Database;

import java.util.ArrayList;
import java.util.List;

public class CariTutorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Database db;
    private Spinner spinner;
    private EditText pelajaran, biaya, tanggal, waktu, alamat;
    private String pilihKelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_tutor);
        db = new Database(this);
        spinner = (Spinner) findViewById(R.id.spkelas);
        spinner.setOnItemSelectedListener(this);
        pelajaran = (EditText) findViewById(R.id.etpelajaran);
        biaya = (EditText) findViewById(R.id.etbiaya);
        tanggal = (EditText) findViewById(R.id.ettanggal);
        waktu = (EditText) findViewById(R.id.etwaktu);
        alamat = (EditText) findViewById(R.id.etalamat);

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
                cariTutor();
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
        pilihKelas = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        pilihKelas = "";
    }

    private void cariTutor()
    {
        String cekkelas, cekpelajaran, cekbiaya, cektanggal, cekwaktu, cekalamat;

        cekkelas = pilihKelas;
        cekpelajaran = pelajaran.getText().toString();
        cekbiaya = biaya.getText().toString();
        cektanggal = tanggal.getText().toString();
        cekwaktu = waktu.getText().toString();
        cekalamat = alamat.getText().toString();
        if(cekkelas.matches("") || cekpelajaran.matches("") || cekbiaya.matches("") ||
                cektanggal.matches("") || cekwaktu.matches("") || cekalamat.matches(""))
        {
            Toast.makeText(getApplicationContext(),"Semua data harus di isi!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Kelas = "+cekkelas+"\n Pelajaran = "+cekpelajaran+"\n Biaya = "+cekbiaya+
                    "\n Tanggal = "+cektanggal+"\n Waktu = "+cekwaktu+"\n Alamat = "+cekalamat,Toast.LENGTH_LONG).show();
        }

    }
}
