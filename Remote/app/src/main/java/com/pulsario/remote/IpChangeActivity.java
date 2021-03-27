package com.pulsario.remote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IpChangeActivity extends AppCompatActivity {

    EditText editText;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_change);

        editText = findViewById(R.id.edittext);
        btn = findViewById(R.id.save_btn);

        if(Vars.sharedPreferences==null) {
            Vars.sharedPreferences=getSharedPreferences(Vars.MyPref, Context.MODE_PRIVATE);
        }

        editText.setText(Vars.sharedPreferences.getString(Vars.IP_ADDRESS, " "));

        ((Button) findViewById(R.id.reset_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = Vars.sharedPreferences.edit();
                editor.putString(Vars.IP_ADDRESS, "192.168.43.37");
                editor.apply();
                Vars.showMsg(IpChangeActivity.this, "Saved");
                gotoMain();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().isEmpty()){
                    Toast.makeText(IpChangeActivity.this, "Empty Text", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = Vars.sharedPreferences.edit();
                    editor.putString(Vars.IP_ADDRESS, editText.getText().toString());
                    editor.apply();
                    Vars.showMsg(IpChangeActivity.this, "Saved");
                    gotoMain();
                }
            }
        });
    }

    private void gotoMain() {
        Intent output = new Intent();
        setResult(RESULT_OK, output);
        finish();
    }
}