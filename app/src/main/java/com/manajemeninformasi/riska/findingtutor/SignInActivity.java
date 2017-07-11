package com.manajemeninformasi.riska.findingtutor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SignInActivity extends AppCompatActivity {
    EditText username, password;
    Button back, submit, register;
    private String susername, spassword;
    private ProgressDialog progressDialog;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        db = new Database(getApplicationContext());
        username = (EditText) findViewById(R.id.etusername);
        password = (EditText) findViewById(R.id.etpass);
        progressDialog = new ProgressDialog(this);
        register = (Button) findViewById(R.id.btnregister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
        back = (Button) findViewById(R.id.btnback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submit = (Button) findViewById(R.id.btnsubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cekUsername = username.getText().toString();
                String cekPass = password.getText().toString();
                if(cekUsername.matches("") || cekPass.matches(""))
                {
                    Toast.makeText(getApplicationContext(),"Username atau Password belum dimasukkan", Toast.LENGTH_SHORT).show();
                }
                else
                userLogin();
            }
        });
    }
    public void userLogin()
    {
        susername = username.getText().toString();
        spassword = password.getText().toString();
        progressDialog.setMessage("Please Wait ...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Connect.LOGIN_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("jenis").equals("Pentutor"))
                        {
                            db.add(jsonObject.getString("id"),susername,jsonObject.getString("name"),jsonObject.getString("alamat")
                                    ,jsonObject.getString("jeniskelamin")
                                    ,jsonObject.getString("jenis")
                                    ,jsonObject.getString("usia"));
                            toIntent(KetersediaanHariTutorActivity.class);
                            Log.d("ini tutor",jsonObject.getString("name"));
                            finish();
                        }
                        else if (jsonObject.getString("jenis").equals("Murid"))
                        {
                            db.add(jsonObject.getString("id"),susername,jsonObject.getString("name"),jsonObject.getString("alamat")
                                    ,jsonObject.getString("jeniskelamin")
                                    ,jsonObject.getString("jenis")
                                    ,jsonObject.getString("usia"));
                            toIntent(HomeMuridActivity.class);
                            Log.d("ini murid",jsonObject.getString("name"));
                            finish();
                        }
                        else if (jsonObject.getString("jenis").equals("Admin"))
                        {
                            db.add(jsonObject.getString("id"),susername,jsonObject.getString("name"),jsonObject.getString("alamat")
                                    ,jsonObject.getString("jeniskelamin")
                                    ,jsonObject.getString("jenis")
                                    ,jsonObject.getString("usia"));
                            toIntent(AdminActivity.class);
                            finish();
                        }
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
                    error.printStackTrace();
                    Log.d("error: ", error.toString());
                }
            })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("username",susername);
                params.put("password",spassword);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void toIntent(Class x)
    {
        Intent myintent = new Intent(getBaseContext(),x);
        startActivityForResult(myintent,0);
    }

}
