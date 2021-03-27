package com.pulsario.remote;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button stop;
    Button left;
    Button right;
    Button move;
    Button back;

    Button siren;

    String serverIP = "";
    String sip = "192.168.43.37";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stop = findViewById(R.id.stop);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        back = findViewById(R.id.back);
        move = findViewById(R.id.move);

        siren = findViewById(R.id.siren);

        if(Vars.sharedPreferences==null) {
            Vars.sharedPreferences=getSharedPreferences(Vars.MyPref, Context.MODE_PRIVATE);
        }

        serverIP = Vars.sharedPreferences.getString(Vars.IP_ADDRESS, "");

        if (!serverIP.equals("")){
            sip = serverIP;
            ((TextView) findViewById(R.id.show_ip)).setText(sip);
        } else {
            ((TextView) findViewById(R.id.show_ip)).setText(sip);
        }

        ((ImageView) findViewById(R.id.open_ip)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IpChangeActivity.class);
                startActivityForResult(intent, Vars.REQUEST_CHANGE_IP);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskEsp taskEsp = new TaskEsp(serverIP, "stop");
                taskEsp.execute();
            }
        });
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskEsp taskEsp = new TaskEsp(serverIP, "forward");
                taskEsp.execute();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskEsp taskEsp = new TaskEsp(serverIP, "backward");
                taskEsp.execute();
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskEsp taskEsp = new TaskEsp(serverIP, "right");
                taskEsp.execute();
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskEsp taskEsp = new TaskEsp(serverIP, "left");
                taskEsp.execute();
            }
        });
        siren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskEsp taskEsp = new TaskEsp(serverIP, "horn");
                taskEsp.execute();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Vars.REQUEST_CHANGE_IP){
            recreate();
        }
    }

    private class TaskEsp extends AsyncTask<Void, Void, String> {

        String server;
        String action;

        TaskEsp(String server, String action){
            this.server = server;
            this.action = action;
        }

        @Override
        protected String doInBackground(Void... params) {

            final String p = "http://"+sip+"/"+action;
            StringBuilder str = new StringBuilder(p);
            Log.e("Send : ", p);

            String serverResponse = "";
            URL url = null;
            try {
                 url = new URL(p);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                String response = in.toString();
                Toast.makeText(MainActivity.this, urlConnection.getResponseCode()+" "+response, Toast.LENGTH_SHORT).show();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Send : ","ERRor 1");
            }

            return serverResponse;
        }

        @Override
        protected void onPostExecute(String s) {

        }
    }
}