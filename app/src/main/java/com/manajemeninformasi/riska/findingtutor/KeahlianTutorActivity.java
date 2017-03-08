package com.manajemeninformasi.riska.findingtutor;

import android.content.Context;
import android.content.Intent;
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
import com.manajemeninformasi.riska.findingtutor.adapter.KeahlianTutorAdapter;
import com.manajemeninformasi.riska.findingtutor.model.KeahlianTutorData;
import com.manajemeninformasi.riska.findingtutor.setting.Connect;
import com.manajemeninformasi.riska.findingtutor.setting.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeahlianTutorActivity extends AppCompatActivity {
    Button back, add;
    private Database db;
    private String username;
    private ListView listView;
    private List<KeahlianTutorData> keahlianTutorDataList;
    private KeahlianTutorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keahlian_tutor);
        db = new Database(this);

        username = db.getUsername();
        listView = (ListView) findViewById(R.id.lvkeahlian);
        keahlianTutorDataList = new ArrayList<>();
        getKeahlianByUsername(username);
        mAdapter = new KeahlianTutorAdapter(this, 0, keahlianTutorDataList);
        listView.setAdapter(mAdapter);

        back = (Button) findViewById(R.id.btnback);
        add = (Button) findViewById(R.id.btnadd);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toIntent(TambahKeahlianTutorActivity.class);
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
    public void toIntent(Class x)
    {
        Intent myintent = new Intent(getBaseContext(),x);
        startActivityForResult(myintent,0);
    }
    public void getKeahlianByUsername(final String Username)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Connect.GETDATAKEAHLIAN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("coba", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.length() > 0)
                    {
                        JSONArray arrayKeahlian = jsonObject.getJSONArray("result");
                        for (int i=0; i< arrayKeahlian.length();i++)
                        {
                            JSONObject objectKeahlian = arrayKeahlian.getJSONObject(i);
                            KeahlianTutorData dataKeahlian =
                                    new KeahlianTutorData(objectKeahlian.getInt("id"),
                                            objectKeahlian.getString("username"),
                                            objectKeahlian.getString("kelas"),
                                            objectKeahlian.getString("pelajaran"),
                                            objectKeahlian.getString("keterbatasanhari"));
                            Log.d("coba2",objectKeahlian.getString("username"));

                            keahlianTutorDataList.add(dataKeahlian);
                        }
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
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                }
            }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",Username);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
