package com.manajemeninformasi.riska.findingtutor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.manajemeninformasi.riska.findingtutor.setting.Connect;
import com.manajemeninformasi.riska.findingtutor.setting.Database;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfileMuridActivity extends AppCompatActivity {
    private Database db;
    private EditText etnama, etnotelp, etemail;
    private String nama, alamat, notelp, email;
    private Button back, submit;
    private ProgressDialog progressDialog;
    private PlaceAutocompleteFragment acAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_murid);
        db = new Database(this);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        progressDialog = new ProgressDialog(this);

        etnama = (EditText) findViewById(R.id.etnama);
        etnotelp = (EditText) findViewById(R.id.etnotelp);
        etemail = (EditText) findViewById(R.id.etemail);

        etnama.setText(bundle.getString("nama"));
        etnotelp.setText(bundle.getString("telp"));
        etemail.setText(bundle.getString("email"));

        acAlamat = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.acalamat);
        acAlamat.setText(bundle.getString("alamat"));
        acAlamat.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                alamat = place.getName().toString();
            }

            @Override
            public void onError(Status status) {
                Log.d("error: ",status.toString());
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
                if(etnama.getText().toString().matches("") || alamat.matches("") || etemail.getText().toString().matches("") ||
                        etnotelp.getText().toString().matches(""))
                {
                    Toast.makeText(getApplicationContext(),"Data harus lengkap!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    updateUser();
                    Intent intent = new Intent(getApplicationContext(), HomeMuridActivity.class);
                    startActivity(intent);
                }
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
    public void updateUser()
    {
        final String username = db.getUsername();
        nama = etnama.getText().toString();
        email = etemail.getText().toString();
        notelp = etnotelp.getText().toString();

        progressDialog.setMessage("Ubah Profil...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Connect.EDITPROFILE_URL,
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
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                params.put("nama",nama);
                params.put("alamat",alamat);
                params.put("telp",notelp);
                params.put("email",email);
                params.put("ketersediaanhari","NULL");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
