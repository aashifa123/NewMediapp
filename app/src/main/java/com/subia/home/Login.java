package com.subia.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    AwesomeValidation awesomeValidation;
    private EditText email,password;
    private StringRequest stringRequest;
    private SharedPreferences pref;
    public static final String mypreference = "loginPref";
    public static final String UID = "userId";
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    public static final String EMAIL = "email";
    public static final String STATUS = "statusCode";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref=getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        loggedUser();
    }

    private void loggedUser() {
        if(pref.contains(STATUS)){
            Intent intent = new Intent(Login.this,MainActivity.class);
            startActivity(intent);
        }
        else{
            init();
        }
    }
    public void init(){
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        validations();
    }
    public void signUp(View view){
        Intent i = new Intent(Login.this, SignUp.class);
        startActivity(i);
        //Toast.makeText(getApplicationContext(),"SignUp",Toast.LENGTH_LONG).show();
    }
    private void validations(){
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";

        awesomeValidation.addValidation(Login.this,R.id.email, android.util.Patterns.EMAIL_ADDRESS,R.string.emailerr);
        awesomeValidation.addValidation(Login.this, R.id.password, regexPassword, R.string.pwderr );

    }
    public void login(View view){
        stringRequest = new StringRequest(Request.Method.POST, Constants.loginUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                        try{
                            JSONObject array = new JSONObject(response);
                            if(array.getInt("status_code")==1){
                                SharedPreferences.Editor editor=pref.edit();
                                editor.putString(EMAIL,array.getString("email"));
                                editor.putString(FIRSTNAME,array.getString("firstName"));
                                editor.putString(LASTNAME,array.getString("lastName"));
                                //editor.putInt(UID,array.getInt("uid"));
                                editor.putInt(STATUS,array.getInt("status_code"));
                                editor.commit();
                               Intent intent = new Intent(Login.this,MainActivity.class);
                              startActivity(intent);
                               // Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error "+error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("email", email.getText().toString());
                params.put("password",password.getText().toString());
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}

