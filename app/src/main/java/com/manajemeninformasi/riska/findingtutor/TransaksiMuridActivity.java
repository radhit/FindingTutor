package com.manajemeninformasi.riska.findingtutor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TransaksiMuridActivity extends AppCompatActivity {
    private Button scan, back, cancel;
    private Database db;
    private String namaTutor, alamatTutor, usiaTutor, telpTutor, pelajaran, durasi, qrcode, biaya;
    private TextView tvnama, tvalamat, tvusia, tvtelp, tvpelajaran, tvdurasi, tvbiaya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_murid);
        tvnama = (TextView) findViewById(R.id.tvnama);
        tvalamat = (TextView) findViewById(R.id.tvalamat);
        tvusia = (TextView) findViewById(R.id.tvusia);
        tvtelp = (TextView) findViewById(R.id.tvnotelp);
        tvpelajaran = (TextView) findViewById(R.id.tvpelajaran);
        tvdurasi = (TextView) findViewById(R.id.tvdurasi);
        tvbiaya = (TextView) findViewById(R.id.tvbiaya);
        scan = (Button) findViewById(R.id.btnscan);
        back = (Button) findViewById(R.id.btnback);
        cancel = (Button) findViewById(R.id.btncancel);
        db = new Database(this);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toIntent(ReaderActivity.class);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        transaksi(db.getUsername());

    }

    private void transaksi(final String username) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Connect.TRANSAKSIMURID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("respon :", jsonObject.toString());
                            String message = jsonObject.getString("message");
                            Log.d("lala", message);
                            if (message.equals("Tidak ada transaksi sedang berjalan"))
                            {
                                namaTutor = "-";
                                alamatTutor = "-";
                                usiaTutor = "-";
                                telpTutor = "-";
                                pelajaran = "-";
                                durasi = "-";
                                biaya = "-";

                                setView(namaTutor, alamatTutor, usiaTutor, telpTutor, pelajaran, durasi, biaya);
                                Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                            }
                            else {
                                qrcode = jsonObject.getString("data_transaksi");

                                JSONArray arraytransaksi = jsonObject.getJSONArray("transaksi");
                                JSONObject objecttransaksi = arraytransaksi.getJSONObject(0);
                                durasi = String.valueOf(objecttransaksi.getInt("durasi_pencarian"));
                                pelajaran = objecttransaksi.getString("pelajaran_pencarian");
                                biaya = objecttransaksi.getString("biayatutor_pencarian");

                                JSONArray arraydatatutor = jsonObject.getJSONArray("data_tutor");
                                JSONObject objectdata = arraydatatutor.getJSONObject(0);
                                namaTutor = objectdata.getString("nama_user");
                                alamatTutor = objectdata.getString("alamat_user");
                                usiaTutor = objectdata.getString("usia_user");
                                telpTutor = objectdata.getString("telp_user");


                                setView(namaTutor, alamatTutor, usiaTutor, telpTutor, pelajaran, durasi, biaya);
                                waktu(qrcode);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                        Log.d("cek",error.getMessage());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void waktu(final String qrcode) {
        final CountDownTimer countDownTimer = new CountDownTimer(120 * 60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long hour = millisUntilFinished / 3600000;
                long sisaHour = millisUntilFinished % 3600000;

                long minute = sisaHour / 60000;
                long sisaMinute = sisaHour % 60000;

                long second = sisaMinute / 1000;

                if(second%5 == 0) {
                    Log.d("sini","lala");
                    ambilData(qrcode, 3);
                }
            }

            @Override
            public void onFinish() {
                ambilData(qrcode, 2);
            }
        }; countDownTimer.start();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder altdial = new AlertDialog.Builder(TransaksiMuridActivity.this);
                altdial.setMessage("Apakah Anda Yakin?").setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Connect.CANCELTRANSAKSI, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            final JSONObject jsonObject = new JSONObject(response);
                                            Toast.makeText(TransaksiMuridActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("qr_codes", qrcode
                                        );
                                        return params;
                                    }
                                };
                                RequestQueue requestQueue = Volley.newRequestQueue(TransaksiMuridActivity.this);
                                requestQueue.add(stringRequest);

                                countDownTimer.cancel();
                                Intent genIntent = new Intent(TransaksiMuridActivity.this, HomeMuridActivity.class);
                                startActivity(genIntent);
                                finish();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alert = altdial.create();
                alert.show();
            }
        });
    }

    private void ambilData(final String qrcode, final int temp){
        Log.d("qrcode", qrcode);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Connect.TIMESERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String current_data) {
                try {
                    final JSONObject jsonObject = new JSONObject(current_data);
                    final String status = jsonObject.getString("status");
                    Log.d("status", jsonObject.getString("status"));

                    if (status.equals("0") && temp==2) {
                        Toast.makeText(TransaksiMuridActivity.this, "Pentutor tidak datang dalam kurun waktu yang telah ditentukan!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(TransaksiMuridActivity.this, " Transaksi dibatalkan!", Toast.LENGTH_SHORT).show();
                        Intent aIntent = new Intent(TransaksiMuridActivity.this, HomeMuridActivity.class);
                        startActivity(aIntent);
                        finish();
                    }

                    else if(status.equals("cancel") && temp==3)
                    {
                        AlertDialog.Builder altd = new AlertDialog.Builder(TransaksiMuridActivity.this);
                        altd.setMessage("Apakah Memberatkan Anda?").setCancelable(false)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        pembatalan(qrcode);
                                        Toast.makeText(TransaksiMuridActivity.this, "Pesanan anda sedang di proses ulang", Toast.LENGTH_SHORT).show();
                                    }
                                }) .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                pembatalan(qrcode);
                                Toast.makeText(TransaksiMuridActivity.this, "Pesanan anda sedang di proses ulang", Toast.LENGTH_SHORT).show();
                            }
                        });
                        AlertDialog alert = altd.create();
                        alert.setTitle("Transaksi Dibatalkan Oleh Pentutor");
                        alert.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("qr_codes", qrcode);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void pembatalan(final String qrcode) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Connect.DELETETRANSAKSI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(TransaksiMuridActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("qr_codes", qrcode);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(TransaksiMuridActivity.this);
        requestQueue.add(stringRequest);

        Intent aIntent = new Intent(TransaksiMuridActivity.this, HomeMuridActivity.class);
        startActivity(aIntent);
        finish();
    }

    private void setView(String namaTutor, String alamatTutor, String usiaTutor, String telpTutor, String pelajaran, String durasi, String biaya) {
        tvnama.setText(namaTutor);
        tvalamat.setText(alamatTutor);
        tvusia.setText(usiaTutor);
        tvtelp.setText(telpTutor);
        tvpelajaran.setText(pelajaran);
        tvdurasi.setText(durasi);
        tvbiaya.setText(biaya);
//        Log.d("biaya", biaya);
    }

    public void toIntent(Class x)
    {
        Intent myintent = new Intent(getBaseContext(),x);
        startActivityForResult(myintent,0);
    }
}