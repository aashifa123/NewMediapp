package com.subia.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    private EditText firstName,lastName,email,password,confirmPassword,phoneNum;
    private StringRequest stringRequest;
    AwesomeValidation awesomeValidation;
    private SharedPreferences pref;
    public static final String mypreference = "loginPref";
    public static final String UID = "userId";
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    public static final String EMAIL = "email";
    public static final String STATUS = "statusCode";
    private static int user_type=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        spinner = (Spinner)findViewById(R.id.spinner4);
        init();

        adapter = ArrayAdapter.createFromResource(this, R.array.user_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                       switch(position){
                           case 1:
                               user_type=1;
                               //Toast.makeText(getApplicationContext(),"Patient",Toast.LENGTH_LONG).show();
                               break;
                           case 2:
                               user_type=2;
                               //Toast.makeText(getApplicationContext(),"Doctor",Toast.LENGTH_LONG).show();
                               break;
                           case 3:
                               user_type=3;
                               //Toast.makeText(getApplicationContext(),"Business",Toast.LENGTH_LONG).show();
                               break;
                       }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void init(){
        firstName=(EditText)findViewById(R.id.firstName);
        lastName=(EditText)findViewById(R.id.lastName);
        email=(EditText)findViewById(R.id.email);
        phoneNum=(EditText)findViewById(R.id.phoneNum);
        password=(EditText)findViewById(R.id.password);
        confirmPassword=(EditText)findViewById(R.id.confirmPassword);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        pref=getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        validations();
    }
    private void validations(){
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(SignUp.this,R.id.firstName,"[a-zA-Z\\s]+",R.string.fnameerr);
        awesomeValidation.addValidation(SignUp.this,R.id.lastName,"[a-zA-Z\\s]+",R.string.lnameerr);
        awesomeValidation.addValidation(SignUp.this,R.id.email, android.util.Patterns.EMAIL_ADDRESS,R.string.emailerr);
        awesomeValidation.addValidation(SignUp.this,R.id.phoneNum, RegexTemplate.TELEPHONE,R.string.phnerr);
        awesomeValidation.addValidation(SignUp.this, R.id.password, regexPassword, R.string.pwderr );
        awesomeValidation.addValidation(SignUp.this, R.id.confirmPassword, R.id.password, R.string.cnfpwerr);
    }
    public void signUp(View view) {
          if(awesomeValidation.validate()){
              String s;
              addUser();
          }
          else{
              Toast.makeText(getApplicationContext(),"Please Fill The Required Fields",Toast.LENGTH_LONG).show();
          }
    }

    private void addUser() {
        stringRequest = new StringRequest(Request.Method.POST, Constants.signupUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getApplicationContext()," Respose Is "+response.toString(),Toast.LENGTH_LONG).show();
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
                                if(user_type==1){
                                    Intent intent = new Intent(SignUp.this,MainActivity.class);
                                    startActivity(intent);
                                }
                                 else if(user_type==2){
                                    Toast.makeText(getApplicationContext(),"Yet To Implement",Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Intent intent = new Intent(SignUp.this,Business.class);
                                    startActivity(intent);
                                }
                                //Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
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
                //here we  send parametrs to API
                Map<String,String> params = new HashMap<String, String>();
                params.put("firstName",firstName.getText().toString());
                params.put("lastName",lastName.getText().toString());
                params.put("email", email.getText().toString());
                params.put("password",password.getText().toString());
                params.put("phoneNum",phoneNum.getText().toString());
                params.put("user_type", String.valueOf(user_type));
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
