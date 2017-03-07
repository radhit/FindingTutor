package com.manajemeninformasi.riska.findingtutor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.manajemeninformasi.riska.findingtutor.setting.Connect;
import com.manajemeninformasi.riska.findingtutor.setting.Database;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TambahKeahlianTutorActivity extends AppCompatActivity {
    private Database db;
    private Spinner kelas,hari;
    private String pilihKelas, pilihHari;
    private EditText pelajaran;
    private TimePicker waktu;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_keahlian_tutor);
        db = new Database(this);
        pelajaran = (EditText) findViewById(R.id.etpelajaran);
        waktu = (TimePicker) findViewById(R.id.tpwaktu);
        progressDialog =  new ProgressDialog(this);

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
                String cekkelas, cekpelajaran, cekketersediaan, cekwaktu;
                cekkelas = pilihKelas;
                cekketersediaan = pilihHari;
                cekpelajaran = pelajaran.getText().toString();
                cekwaktu = waktu.getCurrentHour()+":"+waktu.getCurrentMinute();
                if(cekkelas.matches("") || cekketersediaan.matches("") || cekpelajaran.matches("") || cekwaktu.matches(""))
                {
                    Toast.makeText(getApplicationContext(),"Semua data harus di isi!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    tambahKeahlian();
                }

            }
        });

        kelas = (Spinner) findViewById(R.id.spkelas);
        ArrayAdapter<CharSequence> kelasAdapter = ArrayAdapter.createFromResource(this, R.array.kelas, android.R.layout.simple_spinner_item);
        kelasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kelas.setAdapter(kelasAdapter);
        kelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pilihKelas = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                pilihKelas = "";
            }
        });

        hari = (Spinner) findViewById(R.id.sphari);
        ArrayAdapter<CharSequence> hariAdapter = ArrayAdapter.createFromResource(this, R.array.hari, android.R.layout.simple_spinner_item);
        hariAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hari.setAdapter(hariAdapter);
        hari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pilihHari = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                pilihHari = "";
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


    private void tambahKeahlian()
    {
        final String stringUsername, stringKelas, stringPelajaran, stringWaktu, stringHari;
        stringUsername = db.getUser();
        stringKelas = pilihKelas;
        stringHari = pilihHari;
        stringPelajaran = pelajaran.getText().toString();
        stringWaktu = waktu.getCurrentHour().toString()+":"+waktu.getCurrentMinute().toString();
        Log.d("jamnya atas",stringWaktu);
        Log.d("menit",waktu.getCurrentMinute().toString());
        progressDialog.setMessage("Tambah Keahlian...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Connect.TAMBAHKEAHLIAN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",stringUsername);
                params.put("kelas",stringKelas);
                params.put("pelajaran",stringPelajaran);
                params.put("keterbatasanhari",stringHari);
                params.put("jam",stringWaktu);
                Log.d("jamnya",stringWaktu);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
