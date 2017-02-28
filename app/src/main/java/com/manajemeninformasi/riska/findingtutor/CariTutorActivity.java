package com.manajemeninformasi.riska.findingtutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.manajemeninformasi.riska.findingtutor.setting.Database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CariTutorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Database db;
    private Spinner spinner;
    private EditText pelajaran, biaya, alamat;
    private String pilihKelas;
    private DatePicker tanggal;
    private Calendar calendar;
    private TimePicker waktu;
    private Integer day, year, month, selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_tutor);
        db = new Database(this);
        spinner = (Spinner) findViewById(R.id.spkelas);
        spinner.setOnItemSelectedListener(this);

        pelajaran = (EditText) findViewById(R.id.etpelajaran);
        biaya = (EditText) findViewById(R.id.etbiaya);
        alamat = (EditText) findViewById(R.id.etalamat);

        tanggal = (DatePicker) findViewById(R.id.dptanggal);
        day = tanggal.getDayOfMonth();
        month = tanggal.getMonth();
        year = tanggal.getYear();
        calendar = Calendar.getInstance();
        tanggal.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int years, int monthOfYear, int dayOfMonth) {
                day = dayOfMonth;
                month = monthOfYear;
                year = years;
                calendar.set(year, month, day);
                selectedDay = calendar.get(Calendar.DAY_OF_WEEK);
            }
        });

        waktu = (TimePicker) findViewById(R.id.tpwaktu);

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
        String cekkelas, cekpelajaran, cekbiaya, cektanggal, cekwaktu, cekalamat, hari;
        cekkelas = pilihKelas;
        cekpelajaran = pelajaran.getText().toString();
        cekbiaya = biaya.getText().toString();
        cektanggal = selectedDay.toString();
        cekwaktu = waktu.getCurrentHour()+" : "+waktu.getCurrentMinute();
        cekalamat = alamat.getText().toString();
        if(cekkelas.matches("") || cekpelajaran.matches("") || cekbiaya.matches("") || cekwaktu.matches("") || cekalamat.matches("") || cektanggal.matches("") )
        {
            Toast.makeText(getApplicationContext(),"Semua data harus di isi!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (cektanggal.equals("1"))
            {
                hari = "Minggu";
                Toast.makeText(getApplicationContext(),"Kelas = "+cekkelas+"\n Pelajaran = "+cekpelajaran+"\n Biaya = "+cekbiaya+
                        "\n Tanggal = "+cektanggal+"\n Hari = "+hari+"\n Waktu = "+cekwaktu+"\n Alamat = "+cekalamat,Toast.LENGTH_LONG).show();
            }
            if (cektanggal.equals("2"))
            {
                hari = "Senin";
                Toast.makeText(getApplicationContext(),"Kelas = "+cekkelas+"\n Pelajaran = "+cekpelajaran+"\n Biaya = "+cekbiaya+
                        "\n Tanggal = "+cektanggal+"\n Hari = "+hari+"\n Waktu = "+cekwaktu+"\n Alamat = "+cekalamat,Toast.LENGTH_LONG).show();
            }
            if (cektanggal.equals("3"))
            {
                hari = "Selasa";
                Toast.makeText(getApplicationContext(),"Kelas = "+cekkelas+"\n Pelajaran = "+cekpelajaran+"\n Biaya = "+cekbiaya+
                        "\n Tanggal = "+cektanggal+"\n Hari = "+hari+"\n Waktu = "+cekwaktu+"\n Alamat = "+cekalamat,Toast.LENGTH_LONG).show();
            }
            if (cektanggal.equals("4"))
            {
                hari = "Rabu";
                Toast.makeText(getApplicationContext(),"Kelas = "+cekkelas+"\n Pelajaran = "+cekpelajaran+"\n Biaya = "+cekbiaya+
                        "\n Tanggal = "+cektanggal+"\n Hari = "+hari+"\n Waktu = "+cekwaktu+"\n Alamat = "+cekalamat,Toast.LENGTH_LONG).show();
            }
            if (cektanggal.equals("5"))
            {
                hari = "Kamis";
                Toast.makeText(getApplicationContext(),"Kelas = "+cekkelas+"\n Pelajaran = "+cekpelajaran+"\n Biaya = "+cekbiaya+
                        "\n Tanggal = "+cektanggal+"\n Hari = "+hari+"\n Waktu = "+cekwaktu+"\n Alamat = "+cekalamat,Toast.LENGTH_LONG).show();
            }
            if (cektanggal.equals("6"))
            {
                hari = "Jumat";
                Toast.makeText(getApplicationContext(),"Kelas = "+cekkelas+"\n Pelajaran = "+cekpelajaran+"\n Biaya = "+cekbiaya+
                        "\n Tanggal = "+cektanggal+"\n Hari = "+hari+"\n Waktu = "+cekwaktu+"\n Alamat = "+cekalamat,Toast.LENGTH_LONG).show();
            }
            if (cektanggal.equals("7"))
            {
                hari = "Sabtu";
                Toast.makeText(getApplicationContext(),"Kelas = "+cekkelas+"\n Pelajaran = "+cekpelajaran+"\n Biaya = "+cekbiaya+
                        "\n Tanggal = "+cektanggal+"\n Hari = "+hari+"\n Waktu = "+cekwaktu+"\n Alamat = "+cekalamat,Toast.LENGTH_LONG).show();
            }
        }
    }
}