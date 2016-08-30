package com.andremion.floatingnavigationview.sample;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Login_layout extends Activity implements View.OnTouchListener{
    Button btn_login;
    EditText user_field,pass_field;
    TextView register_now;
    String username="",password="";
    AlertDialog.Builder builder;
    String login_url = "http://192.168.8.101/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        builder = new AlertDialog.Builder(Login_layout.this);

        btn_login = (Button)findViewById(R.id.login_btn);
        user_field = (EditText)findViewById(R.id.et_username);
        pass_field = (EditText)findViewById(R.id.et_password);

        register_now = (TextView)findViewById(R.id.tv_text);
        register_now.setPaintFlags(register_now.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btn_login.setOnTouchListener(this);
        register_now.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //test if a button has been touch
        username= user_field.getText().toString();
        password = pass_field.getText().toString();
       switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_UP:
                //test if what button has been touch
                if (view.getId() == R.id.login_btn) {
                    if(username.equals("")||password.equals(""))
                    {
                        builder.setTitle("Something went wrong.....");
                        displayAlert("Enter a valid username and password");
                    }
                    else
                    {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONArray jsonArray = new JSONArray(response);
                                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                                            String code = jsonObject.getString("code");
                                            if (code.equals("login_failed"))
                                            {
                                                builder.setTitle("login_error");
                                                displayAlert(jsonObject.getString("message"));
                                            }
                                            else
                                            {
                                                Intent intent = new Intent(Login_layout.this,MainActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putString("fname",jsonObject.getString("fname"));
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Login_layout.this,"ERROR",Toast.LENGTH_SHORT).show();
                                error.printStackTrace();
                            }
                        })
                        {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String>params = new HashMap<String,String>();
                                params.put("uname",username);
                                params.put("pword",password);
                                return params;
                            }
                        };
                        MySingleton.getInstance(Login_layout.this).addToRequestque(stringRequest);
                    }
                }else if(view.getId() == R.id.tv_text) {
                    startActivity(new Intent(Login_layout.this,Registration.class));
                }
        }
        return true;
    }
    private void displayAlert(final String message) {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                user_field.setText("");
                pass_field.setText("");

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}