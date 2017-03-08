package com.manajemeninformasi.riska.findingtutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.manajemeninformasi.riska.findingtutor.adapter.CariMuridAdapter;
import com.manajemeninformasi.riska.findingtutor.adapter.KeahlianTutorAdapter;
import com.manajemeninformasi.riska.findingtutor.model.CariMuridData;
import com.manajemeninformasi.riska.findingtutor.model.KeahlianTutorData;
import com.manajemeninformasi.riska.findingtutor.setting.Connect;
import com.manajemeninformasi.riska.findingtutor.setting.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CariMuridActivity extends AppCompatActivity {
    private Database db;
    private ListView listView;
    private List<CariMuridData> cariMuridDataList;
    private CariMuridAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_murid);
        db = new Database(this);

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
                            CariMuridData dataMurid = new
                                    CariMuridData(objectMurid.getInt("id"),
                                    objectMurid.getString("username"),
                                    objectMurid.getString("name"),
                                    objectMurid.getString("kelas"),
                                    objectMurid.getString("pelajaran"),
                                    objectMurid.getString("alamat"),
                                    objectMurid.getString("tanggal"),
                                    objectMurid.getString("hari"),
                                    objectMurid.getString("jam"),
                                    objectMurid.getString("biaya"));
                            cariMuridDataList.add(dataMurid);
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
            });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
