package com.example.lky575.parkingenter_and_exit;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lky575 on 2017-08-23.
 */

public class PostConnector extends Thread {
    private int res_code;
    private String url_str;
    private int select;
    private int floor;
    private int zone_index;
    private String zone_name;
    private String numbering;

    public PostConnector(String url_str, int select){
        this.url_str = url_str;
        this.select = select;
    }

    public void run(){
        if(select == 3) {
            try {
                URL url = new URL(url_str);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if (conn != null) {
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(10000);
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/json");

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("floor", floor);
                        jsonObject.put("zone_name", zone_name);
                        jsonObject.put("zone_index", zone_index);
                        jsonObject.put("car_numbering", numbering);
                    } catch (JSONException e) {
                        Log.d("Test", e.getMessage());
                    }

                    Log.d("Test", jsonObject.toString());
                    writer.write(jsonObject.toString());
                    writer.close();

                    res_code = conn.getResponseCode();

                    if (res_code == conn.HTTP_OK) {
                        Log.d("Test", "OutputStream 요청 완료");
                    } else {
                        Log.d("Test", "res_code : " + res_code + "\nOutputStream 요청 실패");
                    }
                    conn.disconnect();

                } else {
                    Log.d("Test", "conn error");
                }
            } catch (IOException e) {
                Log.d("Test", e.getMessage());
            }
        }
        else{
            try {
                URL url = new URL(url_str);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if (conn != null) {
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(10000);
                    conn.setDoOutput(true);

                    res_code = conn.getResponseCode();

                    if (res_code == conn.HTTP_OK) {
                        Log.d("Test", "OutputStream 요청 완료");
                    } else {
                        Log.d("Test", "res_code : " + res_code + "\nOutputStream 요청 실패");
                    }
                    conn.disconnect();

                } else {
                    Log.d("Test", "conn error");
                }
            } catch (IOException e) {
                Log.d("Test", e.getMessage());
            }
        }
    }

    public void setPosition(String numbering, int floor, String zone_name, int zone_index){
        this.numbering = numbering;
        this.floor = floor;
        this.zone_name = zone_name;
        this.zone_index = zone_index;
    }
}
