package com.example.lky575.parkingenter_and_exit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    EditText numbering, floor, zone_name, zone_index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numbering = (EditText) findViewById(R.id.numbering);
        floor = (EditText) findViewById(R.id.floor);
        zone_name = (EditText) findViewById(R.id.zone_name);
        zone_index = (EditText) findViewById(R.id.zone_index);
    }

    public void onEnterButtonClicked(View v){
        String encoded_url = null;
        try{
            encoded_url = URLEncoder.encode(numbering.getText().toString(), "utf-8");
        } catch(IOException e){}

        PostConnector conn = new PostConnector("http://13.124.74.249:3000/cars/" + encoded_url + "/enter", 1);
        conn.start();
        try{
            conn.join();
        } catch(InterruptedException e){}
    }

    public void onExitButtonClicked(View v){
        String encoded_url = null;
        try{
            encoded_url = URLEncoder.encode(numbering.getText().toString(), "utf-8");
        } catch(IOException e){}

        PostConnector conn = new PostConnector("http://13.124.74.249:3000/cars/" + encoded_url + "/exit", 2);
        conn.start();
        try{
            conn.join();
        } catch(InterruptedException e){}
    }

    public void onParkingButtonClicked(View v){
        PostConnector conn = new PostConnector("http://13.124.74.249:3000/places/update_state", 3);
        String numbering_value = numbering.getText().toString();
        int floor_value = Integer.parseInt(floor.getText().toString());
        String zone_name_value = zone_name.getText().toString();
        int zone_index_value = Integer.parseInt(zone_index.getText().toString());
        conn.setPosition(numbering_value, floor_value, zone_name_value, zone_index_value);
        conn.start();
        try{
            conn.join();
        } catch(InterruptedException e){}

    }
}
