package com.manajemeninformasi.riska.findingtutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class KriteriaMuridActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private String pilihKriteria;
    private Button back, submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kriteria_murid);

        spinner = (Spinner) findViewById(R.id.spkriteria);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> kelasAdapter = ArrayAdapter.createFromResource(this, R.array.kriteria, android.R.layout.simple_spinner_item);
        kelasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(kelasAdapter);

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
                cariMurid();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        pilihKriteria = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void cariMurid()
    {
        final String kriteria;
        if (pilihKriteria.toString().equals("Berdasarkan Jarak Terdekat"))
        {
            kriteria = "jarak";
            Intent myIntent =  new Intent(getBaseContext(),CariMuridActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("kriteria", kriteria);
            myIntent.putExtra("bundle",bundle);
            startActivityForResult(myIntent,0);
        }
        else if(pilihKriteria.toString().equals("Berdasarkan Pelajaran/Keahlian"))
        {
            kriteria = "pelajaran";
            Intent myIntent =  new Intent(getBaseContext(),CariMuridActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("kriteria", kriteria);
            myIntent.putExtra("bundle",bundle);
            startActivityForResult(myIntent,0);
        }

        else if(pilihKriteria.toString().equals("Berdasarkan Ketersediaan Hari"))
        {
            kriteria = "hari";
            Intent myIntent =  new Intent(getBaseContext(),CariMuridActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("kriteria", kriteria);
            myIntent.putExtra("bundle",bundle);
            startActivityForResult(myIntent,0);
        }
        else if(pilihKriteria.toString().equals("Berdasarkan Jenis Kelamin"))
        {
            kriteria = "jenis kelamin";
            Intent myIntent =  new Intent(getBaseContext(),CariMuridActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("kriteria", kriteria);
            myIntent.putExtra("bundle",bundle);
            startActivityForResult(myIntent,0);
        }
        else if(pilihKriteria.toString().equals("Berdasarkan Kelas"))
        {
            kriteria = "kelas";
            Intent myIntent =  new Intent(getBaseContext(),CariMuridActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("kriteria", kriteria);
            myIntent.putExtra("bundle",bundle);
            startActivityForResult(myIntent,0);
        }
        else if(pilihKriteria.toString().equals("Berdasarkan Usia"))
        {
            kriteria = "usia";
            Intent myIntent =  new Intent(getBaseContext(),CariMuridActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("kriteria", kriteria);
            myIntent.putExtra("bundle",bundle);
            startActivityForResult(myIntent,0);
        }
        else if(pilihKriteria.toString().equals("Semua Murid"))
        {
            kriteria = "all";
            Intent myIntent =  new Intent(getBaseContext(),CariMuridActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("kriteria", kriteria);
            myIntent.putExtra("bundle",bundle);
            startActivityForResult(myIntent,0);
        }
    }
}
