package com.manajemeninformasi.riska.findingtutor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.manajemeninformasi.riska.findingtutor.fungsi_keanggotaan.TingkatKesulitan;
import com.manajemeninformasi.riska.findingtutor.fungsi_keanggotaan.Waktu;
import com.manajemeninformasi.riska.findingtutor.proses_fuzzy.Estimasi_Fuzzy;
import com.manajemeninformasi.riska.findingtutor.setting.Connect;
import com.manajemeninformasi.riska.findingtutor.setting.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CariTutorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Database db;
    private Spinner spinnerPelajaran, spinnerKelas, spinnerDurasi;
    private EditText usia, skill;
    private RadioGroup jeniskelamin;
    private RadioButton jkTutor;
    private String pilihKelas, flag, status, pilihDurasi, pilihPelajaran;
    private String getKelas, getPelajaran, toGetDay, getWaktu, getAlamat, getUsia, getJeniskelamin, getHari, kriteriaJenis, getDurasi, getSkill ;
    private DatePicker tanggal;
    private Calendar calendar;
    private TimePicker waktu;
    private PlaceAutocompleteFragment acAlamat;
    private Integer day, year, month, selectedDay;
    private ProgressDialog progressDialog;
    private Bundle bundle;
    private Context context = this;
    private ArrayList<String> lesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_tutor);
        db = new Database(this);
        bundle = getIntent().getBundleExtra("bundle");
        status = db.selectFlag();
        lesson = new ArrayList<String>();
        skill = (EditText) findViewById(R.id.etskill);

        //pelajaran = (EditText) findViewById(R.id.etpelajaran);
        usia = (EditText) findViewById(R.id.etusia);
        usia.setText(bundle.getString("usia"));
        kriteriaJenis = bundle.getString("jeniskelamin");

        if (kriteriaJenis.equals("Man"))
        {
            RadioButton rblaki = (RadioButton) findViewById(R.id.rblakilaki);
            rblaki.setChecked(true);
        }
        else if (kriteriaJenis.equals("Woman"))
        {
            RadioButton rbperempuan = (RadioButton) findViewById(R.id.rbperempuan);
            rbperempuan.setChecked(true);
        }

        tanggal = (DatePicker) findViewById(R.id.dptanggal);
        tanggal.setMinDate(Calendar.getInstance().getTimeInMillis() - 1000);
        tanggal.setMaxDate(Calendar.getInstance().getTimeInMillis() + 2*7*24*60*60*1000);
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

        jeniskelamin = (RadioGroup) findViewById(R.id.rgkelamin);

        waktu = (TimePicker) findViewById(R.id.tpwaktu);

        spinnerKelas = (Spinner) findViewById(R.id.spkelas);
        spinnerKelas.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> kelasAdapter = ArrayAdapter.createFromResource(this, R.array.kelas, android.R.layout.simple_spinner_item);
        kelasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKelas.setAdapter(kelasAdapter);

        spinnerDurasi = (Spinner) findViewById(R.id.spdurasi);
        spinnerDurasi.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> durasiAdapter = ArrayAdapter.createFromResource(this, R.array.durasi, android.R.layout.simple_spinner_item);
        durasiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDurasi.setAdapter(durasiAdapter);

        spinnerPelajaran = (Spinner) findViewById(R.id.spinnerpelajaran);
        spinnerPelajaran.setOnItemSelectedListener(this);
        getDataPelajaran();

        acAlamat = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.acalamat);
        acAlamat.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                getAlamat = place.getName().toString();
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(CariTutorActivity.this, "Alamat tidak ditemukan, error: "+status, Toast.LENGTH_SHORT).show();
                Log.d("error: ",status.toString());
            }
        });

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
                if (status.equals("kosong"))
                {
                    flag = "0";
                }
                else if (status.equals("punish"))
                {
                    flag = "20000";
                    Toast.makeText(context, "Anda terkena tambahan biaya sebesar : "+flag, Toast.LENGTH_SHORT).show();
                }
                getKelas = pilihKelas;
                //getPelajaran = pelajaran.getText().toString();
                getSkill = skill.getText().toString();
                getDurasi = pilihDurasi;
                toGetDay = selectedDay.toString();
                getWaktu = waktu.getCurrentHour() + ":" + waktu.getCurrentMinute();
                getUsia = usia.getText().toString();
                if (getKelas.matches("") || getAlamat.matches("")
                        || toGetDay.matches("") || getWaktu.matches("") || getUsia.matches("") || getDurasi.matches("")) {
                    Toast.makeText(CariTutorActivity.this, "Semua data harus di isi lengkap!", Toast.LENGTH_SHORT).show();
                } else {
                    cariTutor();
                }
            }
        });
    }

    private void getDataPelajaran() {
        StringRequest stringRequest = new StringRequest(Connect.PELAJARAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray arrayPelajaran = jsonObject.getJSONArray("result");
                    getListPelajaran(arrayPelajaran);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getListPelajaran(JSONArray arrayPelajaran) {
        for (int i = 0;i<arrayPelajaran.length();i++)
        {
            try {
                JSONObject json = arrayPelajaran.getJSONObject(i);
                lesson.add(json.getString("nama_pelajaran"));
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        spinnerPelajaran.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lesson));
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

        Spinner spin = (Spinner)parent;
        Spinner spin2 = (Spinner)parent;
        Spinner spin3 = (Spinner)parent;
        if(spin.getId() == R.id.spkelas)
        {
            pilihKelas = parent.getItemAtPosition(position).toString();
        }
        if(spin2.getId() == R.id.spdurasi)
        {
            pilihDurasi = parent.getItemAtPosition(position).toString();
        }
        if(spin3.getId() == R.id.spinnerpelajaran)
        {
            pilihPelajaran = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Spinner spin = (Spinner)parent;
        Spinner spin2 = (Spinner)parent;
        if(spin.getId() == R.id.spkelas)
        {
            pilihKelas = "SD - Kelas 1";
        }
        if(spin2.getId() == R.id.spdurasi)
        {
            pilihDurasi = "30 Menit";
        }
    }

    private void cariTutor()
    {

        int selectedId = jeniskelamin.getCheckedRadioButtonId();
        jkTutor = (RadioButton) findViewById(selectedId);
        final String getNameuser, jam, menit;
        getNameuser = db.getNameuser();
        final String getTanggal;
        getKelas = pilihKelas;
        getPelajaran = pilihPelajaran;
        month = month+1;
        getTanggal = day+"/"+month+"/"+year;
        toGetDay = selectedDay.toString();
        jam = String.format("%02d",waktu.getCurrentHour());
        menit = String.format("%02d",waktu.getCurrentMinute());
        getWaktu = jam+":"+menit;
        getJeniskelamin = jkTutor.getText().toString();
        getUsia = usia.getText().toString();
        getDurasi = pilihDurasi;
        getSkill = skill.getText().toString();
        Log.d("jenis kelamin",getSkill);

        if (toGetDay.equals("1"))
        {
            getHari = "Sunday";
        }
        else if(toGetDay.equals("2"))
        {
            getHari = "Monday";
        }
        else if(toGetDay.equals("3"))
        {
            getHari = "Tuesday";
        }
        else if(toGetDay.equals("4"))
        {
            getHari = "Wednesday";
        }
        else if(toGetDay.equals("5"))
        {
            getHari = "Thusday";
        }
        else if(toGetDay.equals("6"))
        {
            getHari = "Friday";
        }
        else if(toGetDay.equals("7"))
        {
            getHari = "Saturday";
        }

        progressDialog.setMessage("Pencarian Tutor...");
        progressDialog.show();
        TingkatKesulitan tingkatKesulitan = new TingkatKesulitan();
        int kesulitan = 0;
        double hargaawal = 0;

        if (getKelas.equals("SD - Kelas 1"))
        {
            kesulitan = 1;
        }
        else if (getKelas.equals("SD - Kelas 2"))
        {
            kesulitan = 2;
        }
        else if (getKelas.equals("SD - Kelas 3"))
        {
            kesulitan = 3;
        }
        else if (getKelas.equals("SD - Kelas 4"))
        {
            kesulitan = 4;
        }
        else if (getKelas.equals("SD - Kelas 5"))
        {
            kesulitan = 5;
        }
        else if (getKelas.equals("SD - Kelas 6"))
        {
            kesulitan = 6;
        }
        else if (getKelas.equals("SMP - Kelas 7"))
        {
            kesulitan = 7;
        }
        else if (getKelas.equals("SMP - Kelas 8"))
        {
            kesulitan = 8;
        }
        else if (getKelas.equals("SMP - Kelas 9"))
        {
            kesulitan = 9;
        }
        else if (getKelas.equals("SMA - Kelas 10"))
        {
            kesulitan = 10;
        }
        else if (getKelas.equals("SMA - Kelas 11"))
        {
            kesulitan = 11;
        }
        else if (getKelas.equals("SMA - Kelas 12"))
        {
            kesulitan = 12;
        }
        else if (getKelas.equals("UMUM"))
        {
            kesulitan = 13;
        }
        tingkatKesulitan.setTingkatKesulitan(kesulitan);

        Log.d("gettanggal", "onResponse: "+getTanggal);
        int tanggal = Integer.parseInt(getTanggal.split("/")[0]);
        int bulan = Integer.parseInt(getTanggal.split("/")[1]);
        int tahun = Integer.parseInt(getTanggal.split("/")[2]);

        final Calendar today = Calendar.getInstance();
        Log.d("tanggal sekarang", today.toString());

        Calendar tanggalLes = Calendar.getInstance();
        tanggalLes.set(Calendar.DAY_OF_MONTH,tanggal);
        tanggalLes.set(Calendar.MONTH,bulan-1);
        tanggalLes.set(Calendar.YEAR,tahun);

        long diff = tanggalLes.getTimeInMillis()-today.getTimeInMillis();
        final long diffDay = diff/(24*60*60*1000);
        Waktu waktu = new Waktu();
        waktu.setWaktu(diffDay);
        Log.d("tanggal",String.valueOf(diffDay));

        Estimasi_Fuzzy fuzzy = new Estimasi_Fuzzy();
        fuzzy.hitungX(tingkatKesulitan, waktu);
        fuzzy.hitungY();

        hargaawal = fuzzy.defuzzyfikasi();
        Double hargafix = hargaawal+Double.parseDouble(flag);
        String durasi = spinnerDurasi.getSelectedItem().toString();
        Integer jumlah = Integer.parseInt(durasi.split(" ")[0]);
        Integer hargaakhir = hargafix.intValue()*jumlah/90;
        hargaakhir = hargaakhir - (hargaakhir%1000) +1000;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle("Konfirmasi");

        alertDialogBuilder
                .setMessage("Estimasi Biaya Transaksi Anda sebesar : " + hargaakhir)
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Connect.PENCARIANTUTOR_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        progressDialog.dismiss();
                                        try {
//                                            Log.d("respon :", response.toString());
                                            JSONObject jsonObject = new JSONObject(response);
                                            if (db.selectFlag().equals("punish"))
                                                db.updateFlag(null);
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
                                params.put("id_user",db.getIduser());
                                params.put("name",getNameuser);
                                params.put("kelas",getKelas);
                                params.put("pelajaran",getPelajaran);
                                params.put("skill", getSkill);
                                params.put("alamat",getAlamat);
                                params.put("tanggal", getTanggal);
                                params.put("hari",getHari);
                                params.put("jam",getWaktu);
                                params.put("durasi",getDurasi);
                                params.put("jeniskelamin",getJeniskelamin);
                                params.put("usia",getUsia);
                                params.put("biaya", flag);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(CariTutorActivity.this);
                        requestQueue.add(stringRequest);
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                        Toast.makeText(CariTutorActivity.this, "Pencarian Tutor Batal",
                                Toast.LENGTH_LONG).show();
                        finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}