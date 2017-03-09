package com.manajemeninformasi.riska.findingtutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.manajemeninformasi.riska.findingtutor.setting.Database;

public class MainActivity extends AppCompatActivity {
    Button signin, signup;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new Database(this);
        if (db.cekLogin().equals("Pentutor"))
        {
            toIntent(KetersediaanHariTutorActivity.class);
            Log.d("cek", "Tutor");
            finish();
        }
        else if(db.cekLogin().equals("Murid"))
        {
            toIntent(HomeMuridActivity.class);
            Log.d("Cek", "Murid");
            finish();
        }
        else if(db.cekLogin().equals(""))
        {
            Log.d("Cek", "Home");
            setContentView(R.layout.activity_main);
            signin = (Button) findViewById(R.id.btnlogin);
            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toIntent(SignInActivity.class);
                }
            });
            signup = (Button) findViewById(R.id.btnsignup);
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toIntent(SignUpActivity.class);
                }
            });
        }
    }
    public void toIntent(Class x)
    {
        Intent myintent = new Intent(getBaseContext(),x);
        startActivityForResult(myintent,0);
    }
}
