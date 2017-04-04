package com.manajemeninformasi.riska.findingtutor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.manajemeninformasi.riska.findingtutor.setting.Connect;
import com.manajemeninformasi.riska.findingtutor.setting.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetilMuridActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView nama, kelas, pelajaran, alamat, tanggal, hari, jam, biaya, jarak, durasi;
    private Bundle bundle;
    private Database db;
    private String alamatTutordb, status, qrcode;
    private Geocoder geocoder;
    private Button accept;
    private Context context;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil_murid);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        db = new Database(this);
        alamatTutordb = db.getAlamatuser();
        Log.d("alamat tutor : ", alamatTutordb);
        bundle = getIntent().getBundleExtra("bundle");
        progressDialog = new ProgressDialog(this);

        nama = (TextView) findViewById(R.id.tvnama);
        kelas = (TextView) findViewById(R.id.tvkelas);
        pelajaran = (TextView) findViewById(R.id.tvpelajaran);
        alamat = (TextView) findViewById(R.id.tvalamat);
        tanggal = (TextView) findViewById(R.id.tvtanggal);
        hari = (TextView) findViewById(R.id.tvhari);
        jam = (TextView) findViewById(R.id.tvjam);
        biaya = (TextView) findViewById(R.id.tvbiaya);
        jarak = (TextView) findViewById(R.id.tvjarak);
        durasi = (TextView) findViewById(R.id.tvdurasi);

        accept = (Button) findViewById(R.id.btnaccept);

        nama.setText(bundle.getString("nama"));
        kelas.setText(bundle.getString("kelas"));
        pelajaran.setText(bundle.getString("pelajaran"));
        alamat.setText(bundle.getString("alamat"));
        tanggal.setText(bundle.getString("tanggal"));
        hari.setText(bundle.getString("hari"));
        jam.setText(bundle.getString("jam"));
        biaya.setText(bundle.getString("biaya"));
        jarak.setText(String.valueOf(bundle.getFloat("jarak"))+" Km");
        durasi.setText(String.valueOf(bundle.getInt("durasi")));
//        db.updateFlag("free");
        //Log.d("flag", db.selectFlag());

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(db.selectFlag().equals("kosong"))
                {
                    transaksi();

                } else if (db.selectFlag().equals("punish"))
                    waktu();
            }
        });

    }

    private void waktu() {
        Toast.makeText(this, "Terkena Punish", Toast.LENGTH_SHORT).show();
        final CountDownTimer countDownTimer = new CountDownTimer(10 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long hour = millisUntilFinished / 3600000;
                long sisaHour = millisUntilFinished % 3600000;

                long minute = sisaHour / 60000;
                long sisaMinute = sisaHour % 60000;


                long second = sisaMinute / 1000;
            }

            @Override
            public void onFinish() {
                db.updateFlag(null);
                Log.d("flag sekarang ", db.selectFlag());
                //getData(tes,2);

            }
        }; countDownTimer.start();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        markerMurid();
    }
    public void markerMurid()
    {
        Log.d("alamat : ", alamat.getText().toString());
        geocoder = new Geocoder(getBaseContext());
        try {
            List<Address> listMurid = geocoder.getFromLocationName(alamat.getText().toString(),1);
            Address alamatMurid = listMurid.get(0);
            Double latMurid = alamatMurid.getLatitude();
            Double longMurid = alamatMurid.getLongitude();
            LatLng latlongMurid= new LatLng(latMurid, longMurid);

            List<Address> listTutor = geocoder.getFromLocationName(alamatTutordb,1);
            Address alamatTutor = listTutor.get(0);
            Double latTutor = alamatTutor.getLatitude();
            Double longTutor = alamatTutor.getLongitude();
            LatLng latlongTutor = new LatLng(latTutor, longTutor);

            getLine(latMurid,longMurid,latTutor,longTutor);


            mMap.addMarker(new MarkerOptions().position(latlongTutor).title(alamatTutordb));
            mMap.addMarker(new MarkerOptions().position(latlongMurid).title(alamat.getText().toString()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlongTutor));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void getLine(Double latMurid, Double longMurid, Double latTutor, Double longTutor)
    {
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
                        JSONArray arrayDirectionMap = jsonObject.getJSONArray("routes");
                        JSONObject objectDirectionMap = arrayDirectionMap.getJSONObject(0);
                        JSONObject polyline = objectDirectionMap.getJSONObject("overview_polyline");
                        String encodedString = polyline.getString("points");
                        List<LatLng> list = decodePoly(encodedString);
                        Polyline line = mMap.addPolyline(new PolylineOptions()
                                .addAll(list)
                                .width(15)
                                .color(Color.RED)
                                .geodesic(true)
                        );
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
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng( (((double) lat / 1E5)),
                    (((double) lng / 1E5) ));
            poly.add(p);
        }

        return poly;
    }
    public void transaksi()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Connect.TRANSAKSI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    Intent genIntent = new Intent(DetilMuridActivity.this, GeneratorActivity.class);
                    genIntent.putExtra("QR_CODES", String.valueOf(bundle.getInt("id"))+"-"+db.getUsername());
                    startActivity(genIntent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_pencarian", String.valueOf(bundle.getInt("id")));
                params.put("username_tutor", db.getUsername());
                params.put("username_murid", bundle.getString("username"));
                params.put("durasi", String.valueOf(bundle.getInt("durasi")));
                params.put("qr_codes", String.valueOf(bundle.getInt("id"))+"-"+db.getUsername());
                params.put("biaya_final", bundle.getString("biaya"));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
