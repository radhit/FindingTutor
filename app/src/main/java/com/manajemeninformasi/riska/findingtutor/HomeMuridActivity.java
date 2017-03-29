package com.manajemeninformasi.riska.findingtutor;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class HomeMuridActivity extends AppCompatActivity {
    Button cari, profile, transaksi, history;
    private Database db;
    private String id, username, jeniskelamin, usia, usernamedb;
    private Intent myIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_murid);
        db = new Database(this);
        usernamedb = db.getUsername();
        usia = "";
        jeniskelamin = "";

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        cari = (Button) findViewById(R.id.btncari);
        transaksi = (Button) findViewById(R.id.btntransaksi);
        profile = (Button) findViewById(R.id.btnprofile);
        history = (Button) findViewById(R.id.btnhistory);
        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    alertDialog.setTitle("Kriteria Pentutor");
                    alertDialog.setMessage("Anda ingin menggunakan kriteria pentutor sebelumnya? \nPengguna baru tekan Tidak")
                            .setCancelable(false)
                            .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getKriteria();
                                }
                            })
                            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteKriteria();
                                }
                            });
                    alertDialog.show();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toIntent(ProfileMuridActivity.class);
            }
        });
        transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toIntent(TransaksiMuridActivity.class);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toIntent(HistoryMuridActivity.class);
            }
        });
    }

    public void getKriteria()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Connect.GETKRITERIATUTOR_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("coba", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray arrayKriteria = jsonObject.getJSONArray("result");
                    JSONObject objectKriteria = arrayKriteria.getJSONObject(0);
                    id = objectKriteria.getString("id");
                    username = objectKriteria.getString("username");
                    jeniskelamin = objectKriteria.getString("jeniskelamin");
                    usia = objectKriteria.getString("usia");

                    myIntent =  new Intent(getBaseContext(),CariTutorActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("jeniskelamin", jeniskelamin);
                    bundle.putString("usia", usia);
                    myIntent.putExtra("bundle",bundle);
                    startActivityForResult(myIntent,0);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",usernamedb);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void deleteKriteria()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Connect.DELETEKRITERIA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",usernamedb);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        myIntent =  new Intent(getBaseContext(),CariTutorActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("jeniskelamin", jeniskelamin);
        bundle.putString("usia", usia);
        myIntent.putExtra("bundle",bundle);
        startActivityForResult(myIntent,0);
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
