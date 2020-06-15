package com.anmol.roomcontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    ImageButton settingsButton;
    ImageButton mainLight;
    ImageButton secondaryLight;
    ImageButton fan;
    ImageButton tableLamp;

    boolean ml;
    boolean sl;
    boolean f;
    boolean tl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ml = false;
        sl = false;
        f = false;
        tl = false;

        settingsButton = (ImageButton) findViewById(R.id.settings);
        mainLight = (ImageButton) findViewById(R.id.mainLight);
        secondaryLight = (ImageButton) findViewById(R.id.secondaryLight);
        fan = (ImageButton) findViewById(R.id.fan);
        tableLamp = (ImageButton) findViewById(R.id.tableLamp);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettingsActivity();
            }
        });

        mainLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLightToggle();
            }
        });

        secondaryLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secondaryLightToggle();
            }
        });

        tableLamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableLampToggle();
            }
        });

        fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fanToggle();
            }
        });
    }

    protected void connectToNodeMCU(int val) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String ip = sharedPreferences.getString("ipAddr", "");
        //Log.d("gateeeem", "yay - " + ip);
        try {
            Socket socket = new Socket(ip, 6969);
            OutputStream outStream = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outStream);
            out.write(val);
            out.close();
            outStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void mainLightToggle() {
        ml = !ml;
        if (ml) {
            mainLight.setBackgroundColor(Color.parseColor("#009688")); //Pastel green
            mainLight.setImageResource(R.drawable.ic_tubeon);
            connectToNodeMCU(1);
        }
        else {
            mainLight.setBackgroundColor(Color.parseColor("#F44336")); // Pastel red
            mainLight.setImageResource(R.drawable.ic_tubeoff);
            connectToNodeMCU(2);
        }
    }

    protected void secondaryLightToggle() {
        sl = !sl;
        if (sl) {
            secondaryLight.setBackgroundColor(Color.parseColor("#009688")); //Pastel green
            secondaryLight.setImageResource(R.drawable.ic_bulbon);
            connectToNodeMCU(3);
        }
        else {
            secondaryLight.setBackgroundColor(Color.parseColor("#F44336")); // Pastel red
            secondaryLight.setImageResource(R.drawable.ic_bulboff);
            connectToNodeMCU(4);
        }
    }

    protected void tableLampToggle() {
        tl = !tl;
        if (tl) {
            tableLamp.setBackgroundColor(Color.parseColor("#009688")); //Pastel green
            tableLamp.setImageResource(R.drawable.ic_lampon);
            connectToNodeMCU(5);
        }
        else {
            tableLamp.setBackgroundColor(Color.parseColor("#F44336")); // Pastel red
            tableLamp.setImageResource(R.drawable.ic_lampoff);
            connectToNodeMCU(6);
        }
    }

    protected void fanToggle() {
        f = !f;
        if (f) {
            fan.setBackgroundColor(Color.parseColor("#009688")); //Pastel green
            fan.setImageResource(R.drawable.ic_fanon);
            connectToNodeMCU(7);
        }
        else {
            fan.setBackgroundColor(Color.parseColor("#F44336")); // Pastel red
            fan.setImageResource(R.drawable.ic_fanoff);
            connectToNodeMCU(8);
        }
    }

    protected void openSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}