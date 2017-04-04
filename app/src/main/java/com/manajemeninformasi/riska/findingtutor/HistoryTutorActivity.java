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
import com.manajemeninformasi.riska.findingtutor.adapter.HistoryTutorAdapter;
import com.manajemeninformasi.riska.findingtutor.model.HistoryMuridData;
import com.manajemeninformasi.riska.findingtutor.model.HistoryTutorData;
import com.manajemeninformasi.riska.findingtutor.setting.Connect;
import com.manajemeninformasi.riska.findingtutor.setting.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryTutorActivity extends AppCompatActivity {
    private ListView listView;
    private Database database;
    private String username;
    private List<HistoryTutorData> historyTutorDataList;
    private HistoryTutorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_tutor);
        listView = (ListView) findViewById(R.id.lvhistorymurid);
        database = new Database(this);
        username = database.getUsername();

        historyTutorDataList = new ArrayList<>();
        getHistory(username);
        mAdapter = new HistoryTutorAdapter(this, 0, historyTutorDataList);
        listView.setAdapter(mAdapter);
    }

    private void getHistory(final String username) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Connect.HISTORYTUTOR, new Response.Listener<String>() {
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
                            HistoryTutorData dataTutor = new HistoryTutorData(
                                    objectHistory.getInt("id_history"),
                                    objectHistory.getString("tanggal"),
                                    objectHistory.getString("rating"),
                                    objectHistory.getString("komentar"));
                            historyTutorDataList.add(dataTutor);
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
                params.put("username",username);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
