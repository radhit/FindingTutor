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
import com.manajemeninformasi.riska.findingtutor.setting.Connect;
import com.manajemeninformasi.riska.findingtutor.setting.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileMuridActivity extends AppCompatActivity {
    Button back, edit;
    private Database db;
    private String username, jenis;
    private String nama,alamat,notelp,email;
    private TextView tvnama, tvalamat, tvtelp, tvemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_murid);
        db = new Database(this);
        username = db.getUsername();
        jenis = db.getJenis();

        tvnama = (TextView) findViewById(R.id.tvnama);
        tvalamat = (TextView) findViewById(R.id.tvalamat);
        tvtelp = (TextView) findViewById(R.id.tvnotelp);
        tvemail = (TextView) findViewById(R.id.tvemail);

        getProfileMurid(username);
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
                Intent myIntent =  new Intent(getBaseContext(),EditProfileMuridActivity.class);
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
    public void getProfileMurid(final String username)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Connect.PROFILE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("coba", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray arrayKeahlian = jsonObject.getJSONArray("result");
                    JSONObject objectProfile = arrayKeahlian.getJSONObject(0);
                    nama = objectProfile.getString("nama");
                    alamat = objectProfile.getString("alamat");
                    notelp= objectProfile.getString("telp");
                    email= objectProfile.getString("email");
                    setView(nama,alamat,notelp,email);

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

    private void setView(String nama, String alamat, String notelp, String email)
    {
        tvnama.setText(nama);
        tvalamat.setText(alamat);
        tvtelp.setText(notelp);
        tvemail.setText(email);
    }
    @Override
    protected void onPostResume() {
        getProfileMurid(username);
        super.onPostResume();
    }
}

