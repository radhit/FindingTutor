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
import com.manajemeninformasi.riska.findingtutor.volley.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private EditText nama,alamat,usia,telp,email,username,pass;
    private RadioGroup jenis;
    private Button submit, back, login;
    private ProgressDialog progressDialog;

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
                registerUser();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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
        RadioButton pengguna = (RadioButton) jenis.findViewById(jenis.getCheckedRadioButtonId());
        final String snama, salamat, susia, stelp, semail, susername, spass, sjenispengguna;
        snama = nama.getText().toString();
        salamat = alamat.getText().toString();
        susia = usia.getText().toString();
        stelp = telp.getText().toString();
        semail = email.getText().toString();
        susername = username.getText().toString();
        spass = pass.getText().toString();
        sjenispengguna = pengguna.getText().toString();
        progressDialog.setMessage("Register ...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.REGISTER_URL,
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
                params.put("nama",snama);
                params.put("alamat",salamat);
                params.put("usia",susia);
                params.put("telp",stelp);
                params.put("email",semail);
                params.put("jenis",sjenispengguna);
                params.put("username",susername);
                params.put("password",spass);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
