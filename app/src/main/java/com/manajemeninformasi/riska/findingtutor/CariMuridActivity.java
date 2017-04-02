package com.manajemeninformasi.riska.findingtutor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.manajemeninformasi.riska.findingtutor.adapter.CariMuridAdapter;
import com.manajemeninformasi.riska.findingtutor.adapter.KeahlianTutorAdapter;
import com.manajemeninformasi.riska.findingtutor.model.CariMuridData;
import com.manajemeninformasi.riska.findingtutor.model.KeahlianTutorData;
import com.manajemeninformasi.riska.findingtutor.setting.Connect;
import com.manajemeninformasi.riska.findingtutor.setting.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CariMuridActivity extends AppCompatActivity {
    private Database db;
    private ListView listView;
    private List<CariMuridData> cariMuridDataList;
    private CariMuridAdapter mAdapter;
    private Bundle bundle;
    private String username;
    private Float getJarak;
    private Geocoder geocoder;
    private Double latTutor, longTutor, latMurid, longMurid;
    private String alamatTutordb, alamatMurid;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_murid);
        db = new Database(this);
        db = new Database(this);
        alamatTutordb = db.getAlamatuser();
        username = db.getUsername();
        bundle = getIntent().getBundleExtra("bundle");

        progressDialog = new ProgressDialog(this);

        listView = (ListView) findViewById(R.id.lvcarimurid);
        cariMuridDataList = new ArrayList<>();
        getMurid();
        mAdapter = new CariMuridAdapter(this, 0, cariMuridDataList);
        listView.setAdapter(mAdapter);


        Button back = (Button) findViewById(R.id.btnback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
    public void getMurid()
    {
        progressDialog.setMessage("Please Wait ...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Connect.GETMURID_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.length()>0)
                    {
                        JSONArray arrayMurid = jsonObject.getJSONArray("result");
                        for (int i=0; i<arrayMurid.length();i++)
                        {
                            JSONObject objectMurid = arrayMurid.getJSONObject(i);
                            alamatMurid = objectMurid.getString("alamat");
                            Log.d("ataas",alamatMurid);
                            getDistance(objectMurid, alamatMurid);
                        }
                    }


                    progressDialog.dismiss();
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
                params.put("username",username);
                params.put("kriteria",bundle.getString("kriteria"));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void getDistance(final JSONObject objectJarak, String alamat)
    {
        geocoder = new Geocoder(getBaseContext());
        try {
            List<Address> listMurid = geocoder.getFromLocationName(alamat, 1);
            Log.d("askdjas", listMurid.toString());
            Address alamatMurid = listMurid.get(0);
            latMurid = alamatMurid.getLatitude();
            longMurid = alamatMurid.getLongitude();

            List<Address> listTutor = geocoder.getFromLocationName(alamatTutordb, 1);
            Log.d("tutor", listTutor.toString());
            Address alamatTutor = listTutor.get(0);
            latTutor = alamatTutor.getLatitude();
            longTutor = alamatTutor.getLongitude();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=" +latTutor+ "," +longTutor+
                "&destination="+latMurid+"," +longMurid+
                "&key=AIzaSyCwH6FT975GOvqRVaf_-rmp429uGgFXhR0", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.length()>0)
                    {
                        JSONArray arrayDistanceMap = jsonObject.getJSONArray("routes");
                        JSONObject objectDistanceMap = arrayDistanceMap.getJSONObject(0);
                        JSONArray jarak = objectDistanceMap.getJSONArray("legs");
                        JSONObject objectDistance = jarak.getJSONObject(0);
                        JSONObject jarakFinal = objectDistance.getJSONObject("distance");
                        getJarak = Float.valueOf(jarakFinal.getString("value"));
                        Log.d("jarak",getJarak.toString());
                        CariMuridData dataMurid = new
                                CariMuridData(objectJarak.getInt("id"),
                                objectJarak.getString("username"),
                                objectJarak.getString("name"),
                                objectJarak.getString("kelas"),
                                objectJarak.getString("pelajaran"),
                                objectJarak.getString("alamat"),
                                objectJarak.getString("tanggal"),
                                objectJarak.getString("hari"),
                                objectJarak.getString("jam"),
                                objectJarak.getString("biaya"),
                                getJarak,
                                objectJarak.getInt("durasi"));
                        cariMuridDataList.add(dataMurid);
                        Log.d("durasi", String.valueOf(objectJarak.getInt("durasi")));
                    }
                    if (bundle.getString("kriteria").matches("jarak")) {
                        mAdapter.sort(new Comparator<CariMuridData>() {
                            @Override
                            public int compare(CariMuridData arg1, CariMuridData arg0) {
                                return arg1.getJarak_pencarian().compareTo(arg0.getJarak_pencarian());
                            }
                        });
                    }
                    mAdapter.notifyDataSetChanged();
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
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

