package com.manajemeninformasi.riska.findingtutor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class KetersediaanHariTutorActivity extends AppCompatActivity {
    private Button submit, home;
    private CheckBox senin,selasa,rabu,kamis,jumat,sabtu,minggu;
    private Database db;
    private String username;
    private ProgressDialog progressDialog;
    private StringBuilder selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ketersediaan_hari_tutor);
        db = new Database(this);
        username = db.getUsername();
        progressDialog = new ProgressDialog(this);
        senin = (CheckBox) findViewById(R.id.cbsenin);
        selasa = (CheckBox) findViewById(R.id.cbselasa);
        rabu = (CheckBox) findViewById(R.id.cbrabu);
        kamis = (CheckBox) findViewById(R.id.cbkamis);
        jumat = (CheckBox) findViewById(R.id.cbjumat);
        sabtu = (CheckBox) findViewById(R.id.cbsabtu);
        minggu = (CheckBox) findViewById(R.id.cbminggu);
        submit = (Button) findViewById(R.id.btnsubmit);
        home  = (Button) findViewById(R.id.btnhome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toIntent(HomeTutorActivity.class);
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitHari();
            }
        });
    }
    public void toIntent(Class x)
    {
        Intent myintent = new Intent(getBaseContext(),x);
        startActivityForResult(myintent,0);
    }
    public void submitHari()
    {
        selectedDay = new StringBuilder();
        if (senin.isChecked()){
            selectedDay.append("Senin, ");
        }
        if (selasa.isChecked()){
            selectedDay.append("Selasa, ");
        }
        if (rabu.isChecked()){
            selectedDay.append("Rabu, ");
        }
        if (kamis.isChecked()){
            selectedDay.append("Kamis, ");
        }
        if (jumat.isChecked()){
            selectedDay.append("Jumat, ");
        }
        if (sabtu.isChecked()){
            selectedDay.append("Sabtu, ");
        }
        if (minggu.isChecked()){
            selectedDay.append("Minggu");
        }
        //Toast.makeText(this, selectedDay.toString(), Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Connect.TAMBAHKETERSEDIAANHARI_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                            toIntent(HomeTutorActivity.class);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                params.put("hari",selectedDay.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
