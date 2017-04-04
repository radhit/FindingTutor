package com.manajemeninformasi.riska.findingtutor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.manajemeninformasi.riska.findingtutor.adapter.HistoryMuridAdapter;
import com.manajemeninformasi.riska.findingtutor.adapter.KeahlianTutorAdapter;
import com.manajemeninformasi.riska.findingtutor.model.HistoryMuridData;
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

public class HistoryMuridActivity extends AppCompatActivity {
    private ListView listView;
    private Database database;
    private String username;
    private List<HistoryMuridData> historyMuridDatas;
    private HistoryMuridAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_murid);
        listView = (ListView) findViewById(R.id.lvhistorymurid);
        database = new Database(this);
        username = database.getUsername();
    }

    private void getHistory(final String username) {
        historyMuridDatas = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Connect.HISTORYMURID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("coba", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.length() > 0)
                    {
                        JSONArray arrayHistory = jsonObject.getJSONArray("result");
                        for (int i=0; i< arrayHistory.length();i++)
                        {
                            JSONObject objectHistory = arrayHistory.getJSONObject(i);
                            HistoryMuridData dataMurid = new HistoryMuridData(
                                    objectHistory.getInt("id_history"),
                                    objectHistory.getInt("id_pencariantutor"),
                                    objectHistory.getString("nama_tutor"),
                                    objectHistory.getString("telp_tutor"),
                                    objectHistory.getString("pelajaran"),
                                    objectHistory.getString("tanggal"),
                                    objectHistory.getString("biaya"),
                                    objectHistory.getString("rating"),
                                    objectHistory.getString("komentar"));
                            historyMuridDatas.add(dataMurid);
                        }
                        mAdapter = new HistoryMuridAdapter(HistoryMuridActivity.this, 0, historyMuridDatas);
                        listView.setAdapter(mAdapter);
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
                params.put("username",username);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        getHistory(username);
        super.onResume();
    }

}
