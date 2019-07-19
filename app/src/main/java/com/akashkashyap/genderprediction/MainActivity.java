package com.akashkashyap.genderprediction;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView result;
    private EditText name;
    private Button bt;
    private String url = "https://api.genderize.io/?name=";
    StringRequest stringRequest;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = findViewById(R.id.button);
        result = findViewById(R.id.textView3);
        name = findViewById(R.id.editText);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = name.getText().toString();
                final String tag = "Gender";
                url = url + n;
                requestQueue = Volley.newRequestQueue(MainActivity.this);
                stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject gen = new JSONObject(response);
                            String gender = gen.getString("gender");
                            if (gender != "null"){
                                result.setText("You are: "+gender);
                                requestQueue.stop();
                            }
                            else{
                                result.setText(gen.getString("name"));
                                requestQueue.stop();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        result.setText("Something Went Wrong");
                    }
                });
                stringRequest.setTag(tag);
                requestQueue.add(stringRequest);
            }


        });


    }
}
