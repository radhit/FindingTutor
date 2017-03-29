package com.manajemeninformasi.riska.findingtutor;

import android.content.Intent;
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
    private Button scan, back;
    private Database db;
    private String namaTutor, alamatTutor, usiaTutor, telpTutor, pelajaran, durasi;
    private TextView tvnama, tvalamat, tvusia, tvtelp, tvpelajaran, tvdurasi;

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
        scan = (Button) findViewById(R.id.btnscan);
        back = (Button) findViewById(R.id.btnback);
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
                //            JSONObject object = jsonObject.getJSONArray("result").getJSONObject(0);
                            //JSONObject object = jsonObject.getJSONObject("result");
                            String message = jsonObject.getString("message");
                            //JSONArray arrayMessage = object.getJSONArray("message");
                            Log.d("lala", message);
                            if (message.equals("Tidak ada transaksi sedang berjalan"))
                            {
                                namaTutor = "-";
                                alamatTutor = "-";
                                usiaTutor = "-";
                                telpTutor = "-";
                                pelajaran = "-";
                                durasi = "-";
                                setView(namaTutor, alamatTutor, usiaTutor, telpTutor, pelajaran, durasi);
                                Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                            }
                            else {
                                JSONArray arraytransaksi = jsonObject.getJSONArray("transaksi");
                                JSONObject objecttransaksi = arraytransaksi.getJSONObject(0);
                                durasi = String.valueOf(objecttransaksi.getInt("durasi_pencarian"));
                                pelajaran = objecttransaksi.getString("pelajaran_pencarian");

                                JSONArray arraydatatutor = jsonObject.getJSONArray("data_tutor");
                                JSONObject objectdata = arraydatatutor.getJSONObject(0);
                                namaTutor = objectdata.getString("nama_user");
                                alamatTutor = objectdata.getString("alamat_user");
                                usiaTutor = objectdata.getString("usia_user");
                                telpTutor = objectdata.getString("telp_user");


                                setView(namaTutor, alamatTutor, usiaTutor, telpTutor, pelajaran, durasi);
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

    private void setView(String namaTutor, String alamatTutor, String usiaTutor, String telpTutor, String pelajaran, String durasi) {
        tvnama.setText(namaTutor);
        tvalamat.setText(alamatTutor);
        tvusia.setText(usiaTutor);
        tvtelp.setText(telpTutor);
        tvpelajaran.setText(pelajaran);
        tvdurasi.setText(durasi);
    }

    public void toIntent(Class x)
    {
        Intent myintent = new Intent(getBaseContext(),x);
        startActivityForResult(myintent,0);
    }
}
