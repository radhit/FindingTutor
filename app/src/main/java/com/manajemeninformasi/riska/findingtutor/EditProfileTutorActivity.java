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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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

public class EditProfileTutorActivity extends AppCompatActivity {
    private Database db;
    private Button back, submit;
    private EditText etnama, ettelp, etemail;
    private CheckBox senin,selasa,rabu,kamis,jumat,sabtu,minggu;
    private String nama, alamat, telp, email, username;
    private ProgressDialog progressDialog;
    private PlaceAutocompleteFragment acAlamat;
    private StringBuilder selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_tutor);
        db = new Database(this);
        username = db.getUsername();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        progressDialog = new ProgressDialog(this);

        etnama = (EditText) findViewById(R.id.etnama);
        ettelp = (EditText) findViewById(R.id.etnotelp);
        etemail = (EditText) findViewById(R.id.etemail);

        senin = (CheckBox) findViewById(R.id.cbsenin);
        selasa = (CheckBox) findViewById(R.id.cbselasa);
        rabu = (CheckBox) findViewById(R.id.cbrabu);
        kamis = (CheckBox) findViewById(R.id.cbkamis);
        jumat = (CheckBox) findViewById(R.id.cbjumat);
        sabtu = (CheckBox) findViewById(R.id.cbsabtu);
        minggu = (CheckBox) findViewById(R.id.cbminggu);

        etnama.setText(bundle.getString("nama"));
        ettelp.setText(bundle.getString("telp"));
        etemail.setText(bundle.getString("email"));

        acAlamat = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.acalamat);
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
                if (etnama.getText().toString().equals("") || alamat.equals("") ||
                        ettelp.getText().toString().equals("") || etemail.getText().toString().equals(""))
                {
                    Toast.makeText(EditProfileTutorActivity.this, "Data harus Lengkap", Toast.LENGTH_SHORT).show();
                }
                else
                editProfile();
            }
        });
    }

    private void editProfile() {

        int count = 0;

        nama = etnama.getText().toString();
        telp = ettelp.getText().toString();
        email = etemail.getText().toString();

        selectedDay = new StringBuilder();
        if (senin.isChecked()) {
            selectedDay.append("Senin,");
            count = 0;
        }
        if (selasa.isChecked()) {
            selectedDay.append("Selasa,");
            count = 0;
        }
        if (rabu.isChecked()) {
            selectedDay.append("Rabu,");
            count = 0;
        }
        if (kamis.isChecked()) {
            selectedDay.append("Kamis,");
            count = 0;
        }
        if (jumat.isChecked()) {
            selectedDay.append("Jumat,");
            count = 0;
        }
        if (sabtu.isChecked()) {
            selectedDay.append("Sabtu,");
            count = 0;
        }
        if (minggu.isChecked()) {
            selectedDay.append("Minggu,");
            count = 0;
        }
        if (selectedDay.toString().equals("")) {
            Toast.makeText(this, "Minimal 1 hari", Toast.LENGTH_SHORT).show();
            count = 1;
        }
        if (count == 0) {

            progressDialog.setMessage("Ubah Profil...");
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Connect.EDITPROFILE_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                db.updateAlamat(username,alamat);
                                //Log.d("alamat",alamat);
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
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("nama", nama);
                    params.put("alamat", alamat);
                    params.put("telp", telp);
                    params.put("email", email);
                    params.put("ketersediaanhari", selectedDay.toString());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
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

}
