package com.manajemeninformasi.riska.findingtutor;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.manajemeninformasi.riska.findingtutor.setting.Database;

import java.io.IOException;
import java.util.List;

public class DetilMuridActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView nama, kelas, pelajaran, alamat, tanggal, hari, jam, biaya, jarak;
    private Bundle bundle;
    private Database db;
    private String alamatTutordb;
    private Geocoder geocoder;

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

        nama = (TextView) findViewById(R.id.tvnama);
        kelas = (TextView) findViewById(R.id.tvkelas);
        pelajaran = (TextView) findViewById(R.id.tvpelajaran);
        alamat = (TextView) findViewById(R.id.tvalamat);
        tanggal = (TextView) findViewById(R.id.tvtanggal);
        hari = (TextView) findViewById(R.id.tvhari);
        jam = (TextView) findViewById(R.id.tvjam);
        biaya = (TextView) findViewById(R.id.tvbiaya);
        jarak = (TextView) findViewById(R.id.tvjarak);

        nama.setText(bundle.getString("nama"));
        kelas.setText(bundle.getString("kelas"));
        pelajaran.setText(bundle.getString("pelajaran"));
        alamat.setText(bundle.getString("alamat"));
        tanggal.setText(bundle.getString("tanggal"));
        hari.setText(bundle.getString("hari"));
        jam.setText(bundle.getString("jam"));
        biaya.setText(bundle.getString("biaya"));

    }

    public void jarak(double latMurid, double longMurid, double latTutor, double longTutor)
    {
        Location murid = new Location("murid");
        Location tutor = new Location("tutor");
        murid.setLatitude(latMurid);
        murid.setLongitude(longMurid);
        tutor.setLatitude(latTutor);
        tutor.setLongitude(longTutor);

        float getJarak = tutor.distanceTo(murid)/1000;
        jarak.setText(String.valueOf(getJarak)+" Km");
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
            LatLng latlongMurid = new LatLng(latMurid, longMurid);

            List<Address> listTutor = geocoder.getFromLocationName(alamatTutordb,1);
            Address alamatTutor = listTutor.get(0);
            Double latTutor = alamatTutor.getLatitude();
            Double longTutor = alamatTutor.getLongitude();


            mMap.addMarker(new MarkerOptions().position(latlongMurid).title(alamat.getText().toString()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlongMurid));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            jarak(latMurid, longMurid, latTutor, longTutor);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
