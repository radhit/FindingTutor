package com.manajemeninformasi.riska.findingtutor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.manajemeninformasi.riska.findingtutor.setting.Connect;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private EditText nama,alamat,usia,telp,email,username,pass;
    private RadioGroup jenis, kelamin;
    private RadioButton pengguna, jeniskelamin;
    private Button submit, back, login;
    private ProgressDialog progressDialog;
    private String getNama,getAlamat, getUsia, getTelp , getEmail ,getUsername , getPass, getJeniskelamin, getJenisuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        nama = (EditText) findViewById(R.id.etnama);
        alamat = (EditText) findViewById(R.id.etalamat);
        usia = (EditText) findViewById(R.id.etusia);
        telp = (EditText) findViewById(R.id.etnotelp);
        email = (EditText) findViewById(R.id.etemail);
        username = (EditText) findViewById(R.id.etusername);
        pass = (EditText) findViewById(R.id.etpassword);

        submit = (Button) findViewById(R.id.btnsubmit);
        login = (Button) findViewById(R.id.btnlogin);

        jenis = (RadioGroup) findViewById(R.id.rgjenis);


        kelamin = (RadioGroup) findViewById(R.id.rgkelamin);


        progressDialog = new ProgressDialog(this);

        back = (Button) findViewById(R.id.btnback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pengguna = (RadioButton) jenis.findViewById(jenis.getCheckedRadioButtonId());
                jeniskelamin = (RadioButton) kelamin.findViewById(kelamin.getCheckedRadioButtonId());
                getNama = nama.getText().toString();
                getAlamat = alamat.getText().toString();
                getJenisuser = pengguna.getText().toString();
                getJeniskelamin = jeniskelamin.getText().toString();
                getUsia = usia.getText().toString();
                getTelp = telp.getText().toString();
                getEmail = email.getText().toString();
                getUsername = username.getText().toString();
                getPass = pass.getText().toString();
                if(getNama.matches("") || getAlamat.matches("") || getJeniskelamin.matches("") || getUsia.matches("") ||
                        getTelp.matches("") || getEmail.matches("") || getJenisuser.matches("") || getUsername.matches("") || getPass.matches(""))
                {
                    Toast.makeText(getApplicationContext(),"Semua data harus di isi!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    registerUser();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void registerUser()
    {
        getNama = nama.getText().toString();
        getAlamat = alamat.getText().toString();
        getJeniskelamin = jeniskelamin.getText().toString();
        getUsia = usia.getText().toString();
        getTelp = telp.getText().toString();
        getEmail = email.getText().toString();
        getJenisuser = pengguna.getText().toString();
        getUsername = username.getText().toString();
        getPass = pass.getText().toString();
        progressDialog.setMessage("Register ...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Connect.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                        Log.d("cek",error.getMessage());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama",getNama);
                params.put("alamat",getAlamat);
                params.put("usia",getUsia);
                params.put("jeniskelamin",getJeniskelamin);
                params.put("telp",getTelp);
                params.put("email",getEmail);
                params.put("jenis",getJenisuser);
                params.put("username",getUsername);
                params.put("password",getPass);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
