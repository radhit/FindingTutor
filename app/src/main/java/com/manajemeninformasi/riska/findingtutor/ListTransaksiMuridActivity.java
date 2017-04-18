package com.manajemeninformasi.riska.findingtutor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.manajemeninformasi.riska.findingtutor.adapter.ListTransaksiMuridAdapter;
import com.manajemeninformasi.riska.findingtutor.model.ListTransaksiMuridData;
import com.manajemeninformasi.riska.findingtutor.setting.Connect;
import com.manajemeninformasi.riska.findingtutor.setting.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListTransaksiMuridActivity extends AppCompatActivity {
    private ListView listView;
    private Database database;
    private String username;
    private List<ListTransaksiMuridData> listTransaksiMuridDatas;
    private ListTransaksiMuridAdapter mAdapter;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_transaksi_murid);
        listView = (ListView) findViewById(R.id.lvtransaksimurid);
        database = new Database(this);
        username = database.getUsername();
        back = (Button) findViewById(R.id.btnback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        getTransaksi(username);
        super.onResume();
    }

    private void getTransaksi(final String username) {
        listTransaksiMuridDatas = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Connect.LISTTRANSAKSIMURID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("coba", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.length() > 0)
                    {
                        JSONObject arrayTransaksi = jsonObject.getJSONObject("result");
                        Log.d("result", arrayTransaksi.toString());
                        String message = arrayTransaksi.getString("message");
                        Log.d("lala", message);
                        if (message.equals("Tidak ada transaksi sedang berjalan"))
                        {
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                        }
                        JSONArray arrayListTransaksi = arrayTransaksi.getJSONArray("list");

                        for (int i=0; i< arrayListTransaksi.length();i++)
                        {
                            JSONObject objectTransaksi = arrayListTransaksi.getJSONObject(i);
                            ListTransaksiMuridData dataTransaksi = new ListTransaksiMuridData(
                                    objectTransaksi.getInt("id_transaksi"),
                                    objectTransaksi.getInt("id_pencariantutor"),
                                    objectTransaksi.getString("pelajaran"),
                                    objectTransaksi.getString("nama_tutor"),
                                    objectTransaksi.getString("username_murid"));
                            listTransaksiMuridDatas.add(dataTransaksi);
                        }
                        mAdapter = new ListTransaksiMuridAdapter(ListTransaksiMuridActivity.this, 0, listTransaksiMuridDatas);
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
}
