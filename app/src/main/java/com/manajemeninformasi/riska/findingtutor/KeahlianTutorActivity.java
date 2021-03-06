package com.manajemeninformasi.riska.findingtutor;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
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

public class KeahlianTutorActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    Button back, add;
    private Database db;
    private ListView listView;
    private List<KeahlianTutorData> keahlianTutorDataList;
    private KeahlianTutorAdapter mAdapter;
    private SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keahlian_tutor);
        db = new Database(this);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);

        listView = (ListView) findViewById(R.id.lvkeahlian);


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
        swipe.setOnRefreshListener(this);

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
    public void getKeahlianByUsername(final String Id_user)
    {
        keahlianTutorDataList = new ArrayList<>();
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
                                            objectKeahlian.getString("kelas"),
                                            objectKeahlian.getString("pelajaran"));

                            keahlianTutorDataList.add(dataKeahlian);
                        }

                        mAdapter = new KeahlianTutorAdapter(KeahlianTutorActivity.this, 0, keahlianTutorDataList);
                        listView.setAdapter(mAdapter);
                    }
                    mAdapter.notifyDataSetChanged();
                    swipe.setRefreshing(false);

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
                params.put("id_user",Id_user);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onRefresh() {
        getKeahlianByUsername(db.getIduser());
    }

    @Override
    protected void onResume() {
        getKeahlianByUsername(db.getIduser());
        super.onResume();
    }
}
