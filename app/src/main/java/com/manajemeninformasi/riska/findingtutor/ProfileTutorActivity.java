package com.manajemeninformasi.riska.findingtutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.manajemeninformasi.riska.findingtutor.model.KeahlianTutorData;
import com.manajemeninformasi.riska.findingtutor.setting.Connect;
import com.manajemeninformasi.riska.findingtutor.setting.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileTutorActivity extends AppCompatActivity {
    Button back, edit;
    private Database db;
    private String username, jenis;
    private String nama,alamat,notelp,email,hari="";
    private TextView tvnama, tvalamat, tvtelp, tvemail, tvhari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tutor);
        db = new Database(this);
        username = db.getUsername();
        jenis = db.getJenis();
        getProfileTutor(username);

        tvnama = (TextView) findViewById(R.id.tvnama);
        tvalamat = (TextView) findViewById(R.id.tvalamat);
        tvtelp = (TextView) findViewById(R.id.tvnotelp);
        tvemail = (TextView) findViewById(R.id.tvemail);
        tvhari = (TextView) findViewById(R.id.tvhari);

        back = (Button) findViewById(R.id.btnback);
        edit = (Button) findViewById(R.id.btnedit);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent =  new Intent(getBaseContext(),EditProfileTutorActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nama", nama);
                bundle.putString("alamat", alamat);
                bundle.putString("telp", notelp);
                bundle.putString("email", email);
                myIntent.putExtra("bundle",bundle);
                startActivityForResult(myIntent,0);
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
    public void getProfileTutor(final String username)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Connect.PROFILE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject arrayKeahlian = jsonObject.getJSONObject("result");
                    JSONArray arrayUser = arrayKeahlian.getJSONArray("user");
                    JSONObject objectProfile = arrayUser.getJSONObject(0);
                    nama = objectProfile.getString("nama_user");
                    alamat = objectProfile.getString("alamat_user");
                    notelp= objectProfile.getString("telp_user");
                    email= objectProfile.getString("email_user");

                    JSONArray arrayHari = arrayKeahlian.getJSONArray("hari");
                    hari="";
                    for (int i=0; i< arrayHari.length();i++)
                    {
                        JSONObject objectHari = arrayHari.getJSONObject(i);
                        hari = hari+" "+objectHari.getString("hari_tutor");
                    }
                    setView(nama,alamat,notelp,email, hari);

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
                params.put("username",username);
                params.put("jenis",jenis);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setView(String nama, String alamat, String notelp, String email, String hari)
    {
        tvnama.setText(nama);
        tvalamat.setText(alamat);
        tvtelp.setText(notelp);
        tvemail.setText(email);
        tvhari.setText(hari);
    }
    @Override
    protected void onResume() {
        getProfileTutor(username);
        super.onResume();
    }
}
