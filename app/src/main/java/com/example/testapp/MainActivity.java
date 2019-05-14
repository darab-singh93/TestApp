package com.example.testapp;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    LinearLayout layout;
    SeekBar seek1, seek2, seek3;
    TextView txt1, txt2, txt3;
    Button btnSend;
    String r = "", g = "", b = "";
    private ProgressDialog pDialog;
    private static final String TAG = "Darab";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seek1 = (SeekBar) findViewById(R.id.seek1);
        seek2 = (SeekBar) findViewById(R.id.seek2);
        seek3 = (SeekBar) findViewById(R.id.seek3);
        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        txt3 = (TextView) findViewById(R.id.txt3);
        btnSend = (Button) findViewById(R.id.btnSend);
        layout = (LinearLayout) findViewById(R.id.layout);


        // For API Level-21 and above We can chane Seekbar Color by using these lines

        seek1.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        seek1.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

        seek2.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        seek2.getThumb().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);

        seek3.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        seek3.getThumb().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

        seek1.setOnSeekBarChangeListener(this);
        seek2.setOnSeekBarChangeListener(this);
        seek3.setOnSeekBarChangeListener(this);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (r.isEmpty() || g.isEmpty() || b.isEmpty()) {
                    r = "0";
                    g = "0";
                    b = "0";
                }
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                final String value = r + "," + g + "," + b;

                String url = "http://www.thecolorapi.com/id?rgb=" + value;

// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (pDialog.isShowing())
                                    pDialog.dismiss();
                                //System.out.println("Response is: " + response.toString());
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    String s = obj.getJSONObject("hex").getString("value");
                                    //System.out.println("MyColor is: " + s);
                                    layout.setBackgroundColor(Color.parseColor(s));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error!");
                    }
                });


// Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seek1:
                r = String.valueOf(progress);
                txt1.setText(String.valueOf(progress));
                break;
            case R.id.seek2:
                g = String.valueOf(progress);
                txt2.setText(String.valueOf(progress));
                break;
            case R.id.seek3:
                b = String.valueOf(progress);
                txt3.setText(String.valueOf(progress));
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
