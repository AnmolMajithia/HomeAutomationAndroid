package com.anmol.roomcontrol;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Objects;

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

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        f = prefs.getBoolean("fan", false);
        ml = prefs.getBoolean("main light", false);
        sl = prefs.getBoolean("secondary light", false);
        tl = prefs.getBoolean("table lamp", false);

        settingsButton = findViewById(R.id.settings);
        mainLight = findViewById(R.id.mainLight);
        secondaryLight = findViewById(R.id.secondaryLight);
        fan = findViewById(R.id.fan);
        tableLamp = findViewById(R.id.tableLamp);

        setColors();

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

    protected void setColors() {
        if (ml) {
            mainLight.setBackgroundColor(Color.parseColor("#009688")); //Pastel green
            mainLight.setImageResource(R.drawable.ic_tubeon);
        }
        else {
            mainLight.setBackgroundColor(Color.parseColor("#F44336")); // Pastel red
            mainLight.setImageResource(R.drawable.ic_tubeoff);
        }
        if (f) {
            fan.setBackgroundColor(Color.parseColor("#009688")); //Pastel green
            fan.setImageResource(R.drawable.ic_fanon);
        }
        else {
            fan.setBackgroundColor(Color.parseColor("#F44336")); // Pastel red
            fan.setImageResource(R.drawable.ic_fanoff);
        }
        if (tl) {
            tableLamp.setBackgroundColor(Color.parseColor("#009688")); //Pastel green
            tableLamp.setImageResource(R.drawable.ic_lampon);
        }
        else {
            tableLamp.setBackgroundColor(Color.parseColor("#F44336")); // Pastel red
            tableLamp.setImageResource(R.drawable.ic_lampoff);
        }
        if (sl) {
            secondaryLight.setBackgroundColor(Color.parseColor("#009688")); //Pastel green
            secondaryLight.setImageResource(R.drawable.ic_bulbon);
        }
        else {
            secondaryLight.setBackgroundColor(Color.parseColor("#F44336")); // Pastel red
            secondaryLight.setImageResource(R.drawable.ic_bulboff);
        }

    }

    protected void connectToNodeMCU(int val) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String ip = sharedPreferences.getString("ipAddr", "192.168.0.169");
        int port = Integer.parseInt(Objects.requireNonNull(sharedPreferences.getString("port", "69")));
        try {
            Socket socket = new Socket(ip, port);
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
        prefs.edit().putBoolean("main light", ml).apply();
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

    protected void fanToggle() {
        f = !f;
        prefs.edit().putBoolean("fan", f).apply();
        if (f) {
            fan.setBackgroundColor(Color.parseColor("#009688")); //Pastel green
            fan.setImageResource(R.drawable.ic_fanon);
            connectToNodeMCU(3);
        }
        else {
            fan.setBackgroundColor(Color.parseColor("#F44336")); // Pastel red
            fan.setImageResource(R.drawable.ic_fanoff);
            connectToNodeMCU(4);
        }
    }

    protected void tableLampToggle() {
        tl = !tl;
        prefs.edit().putBoolean("table lamp", tl).apply();
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

    protected void secondaryLightToggle() {
        sl = !sl;
        prefs.edit().putBoolean("secondary light", sl).apply();
        if (sl) {
            secondaryLight.setBackgroundColor(Color.parseColor("#009688")); //Pastel green
            secondaryLight.setImageResource(R.drawable.ic_bulbon);
            connectToNodeMCU(7);
        }
        else {
            secondaryLight.setBackgroundColor(Color.parseColor("#F44336")); // Pastel red
            secondaryLight.setImageResource(R.drawable.ic_bulboff);
            connectToNodeMCU(8);
        }
    }

    protected void openSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}