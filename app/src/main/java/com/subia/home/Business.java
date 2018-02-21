package com.subia.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Business extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //Card Objects
    CardView hospital,labs,chemist;
    String countryList[]={"Select Country"};
    ArrayList<String> countrylist=new ArrayList<String>();
    ArrayList<String> stateList=new ArrayList<String>();
    String countryNames="Select Country";
    String stateNames="Select State";
    int uid=0;
    int bid=0;
    ArrayAdapter<CharSequence> countryArrayAdapter,stateArrayAdapter,businessAdapter;
    //Spinner
    Spinner addBusiness,hospitalCountry,hospitalState,labCountry,labState,chemistCountry,chemistState;
    Button addHospital;
    EditText hospitalName,hospitalAddress,hospitalCity,hospitalPincode,labName,labAddress,labCity,labPincode,chemistName,chemistAddress,chemistCity,chemistPincode;
    private String state[]=null;
    private String countryName;
    private String StateName;
    private StringRequest stringRequest;
    private SharedPreferences pref;
    public static final String mypreference = "loginPref";
    public static final String UID = "userId";
    public static final String COUNTRYNAME = "countryName";
    public static final String ADDRESS = "address";
    public static final String BID = "businessId";
    public static final String DID = "doctorId";
    public static final String CITY = "city";
    public static final String PINCODE = "pincode";
    public static final String COUNTRY = "country";
    public static final String STATE = "state";
    public static final String EMAIL = "email";
    private String email="aashifa_s@yahoo.in";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        init();
        loadInitNames();
        loadArrayAdapter();
        loadCountryNames();
       // loadStateNames();
    }
    private void init() {
        //Cards
        hospital=(CardView)findViewById(R.id.hospitalCard);
        labs=(CardView)findViewById(R.id.labs);
        chemist=(CardView)findViewById(R.id.chemist);
        //Spinner
        addBusiness=(Spinner)findViewById(R.id.addBusiness);
        hospitalCountry=(Spinner)findViewById(R.id.hospitalCountry);
        hospitalState=(Spinner)findViewById(R.id.hospitalState);
        labCountry=(Spinner)findViewById(R.id.labCountry);
        labState=(Spinner)findViewById(R.id.labState);
        chemistCountry=(Spinner)findViewById(R.id.chemistCountry);
        chemistState=(Spinner)findViewById(R.id.chemistState);
        addHospital=(Button)findViewById(R.id.addHospital);
        //Edit Text Reference
        hospitalName=(EditText)findViewById(R.id.hospitalName);
        hospitalAddress=(EditText)findViewById(R.id.hospitalAddress);
        hospitalCity=(EditText)findViewById(R.id.hospitalCity);
        hospitalPincode=(EditText)findViewById(R.id.hospitalPincode);

        labName=(EditText)findViewById(R.id.labName);
        labAddress=(EditText)findViewById(R.id.labAddress);
        labCity=(EditText)findViewById(R.id.labCity);
        labPincode=(EditText)findViewById(R.id.labPincode);

        chemistName=(EditText)findViewById(R.id.chemistName);
        chemistAddress=(EditText)findViewById(R.id.chemistAddress);
        chemistCity=(EditText)findViewById(R.id.chemistCity);
        chemistPincode=(EditText)findViewById(R.id.chemistPincode);
        //Card Visibility
        hospital.setVisibility(hospital.INVISIBLE);
        labs.setVisibility(labs.INVISIBLE);
        chemist.setVisibility(chemist.INVISIBLE);
        //
        pref=getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if(pref.contains(EMAIL)&&pref.contains(UID)){
            email=pref.getString(EMAIL,null);
            uid=pref.getInt(UID,0);
            bid=pref.getInt(BID,0);
        }
    }
    private void loadInitNames() {
        //Toast.makeText(getApplicationContext(),"Load Init Names",Toast.LENGTH_LONG).show();
        countrylist.clear();
        stateList.clear();
        countrylist.add("Select Country");
        stateList.add("Select State");

    }
    private void loadArrayAdapter() {
        businessAdapter = ArrayAdapter.createFromResource(this, R.array.Business, android.R.layout.simple_spinner_item);
        businessAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addBusiness.setAdapter(businessAdapter);
        addBusiness.setOnItemSelectedListener(this);
        ////
        countryArrayAdapter = new ArrayAdapter(getApplicationContext(),
                R.layout.spinner_item,
                countrylist);
        countryArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        hospitalCountry.setAdapter(countryArrayAdapter);
        hospitalCountry.setOnItemSelectedListener(this);
        ////
        stateArrayAdapter = new ArrayAdapter(getApplicationContext(),
                R.layout.spinner_item,
                stateList);
        stateArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        hospitalState.setAdapter(stateArrayAdapter);
        hospitalState.setOnItemSelectedListener(this);
    }
    private void labAdapters(){
        countryArrayAdapter = new ArrayAdapter(getApplicationContext(),
                R.layout.spinner_item,
                countrylist);
        countryArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        labCountry.setAdapter(countryArrayAdapter);
        labCountry.setOnItemSelectedListener(this);

        ///
        stateArrayAdapter = new ArrayAdapter(getApplicationContext(),
                R.layout.spinner_item,
                stateList);
        stateArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        labState.setAdapter(stateArrayAdapter);
        labState.setOnItemSelectedListener(this);
    }
    private void chemistAdapters(){
        countryArrayAdapter = new ArrayAdapter(getApplicationContext(),
                R.layout.spinner_item,
                countrylist);
        countryArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        chemistCountry.setAdapter(countryArrayAdapter);
        chemistCountry.setOnItemSelectedListener(this);

        ///
        stateArrayAdapter = new ArrayAdapter(getApplicationContext(),
                R.layout.spinner_item,
                stateList);
        stateArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        chemistState.setAdapter(stateArrayAdapter);
        chemistState.setOnItemSelectedListener(this);
    }
    private void loadCountryNames() {
        stringRequest = new StringRequest(Request.Method.POST, Constants.CountryNamesurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject= new JSONObject(response);
                            if(jsonObject.getInt("result_code")==1) {
                                for (int i = 0; i < jsonObject.getJSONArray("data").length(); i++) {
                                    countrylist.add(jsonObject.getJSONArray("data").getString(i));
                                }
                            }else{
                                countrylist.add("No Countries Present");
                            }
                            //Toast.makeText(getApplicationContext(),"success" +i,Toast.LENGTH_LONG).show();
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
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    private void loadStateNames() {
        stringRequest = new StringRequest(Request.Method.POST, Constants.StateNamesurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject= new JSONObject(response);
                            if(jsonObject.getInt("result_code")==1) {
                                for (int i = 0; i < jsonObject.getJSONArray("data").length(); i++) {
                                    stateList.add(jsonObject.getJSONArray("data").getString(i));
                                }
                            }else{
                                stateList.add("No States Present");
                            }
                            //Toast.makeText(getApplicationContext(),"success" +i,Toast.LENGTH_LONG).show();
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
                params.put("countryName",countryName);
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position!=0) {
            switch(parent.getId()) {
                case R.id.addBusiness:
                    if(position==1){
                        hospital.setVisibility(hospital.VISIBLE);
                        labs.setVisibility(labs.INVISIBLE);
                        chemist.setVisibility(chemist.INVISIBLE);
                    }
                    else if(position==2)
                    {
                        labs.setVisibility(labs.VISIBLE);
                        hospital.setVisibility(hospital.INVISIBLE);
                        chemist.setVisibility(chemist.INVISIBLE);
                        loadInitNames();
                        labAdapters();
                        loadCountryNames();
                    }
                    else if(position==3)
                    {
                        chemist.setVisibility(chemist.VISIBLE);
                        hospital.setVisibility(hospital.INVISIBLE);
                        labs.setVisibility(labs.INVISIBLE);
                        loadInitNames();
                        chemistAdapters();
                        loadCountryNames();
                    }
                    else
                    {
                        chemist.setVisibility(chemist.INVISIBLE);
                        hospital.setVisibility(hospital.INVISIBLE);
                        labs.setVisibility(labs.INVISIBLE);
                    }
                    break;
                case R.id.hospitalCountry:
                    TextView hospitalCountryName = (TextView) view;
                    countryName=hospitalCountryName.getText().toString();
                    //Toast.makeText(getApplicationContext(), countryName, Toast.LENGTH_LONG).show();
                    stateList.clear();
                    stateList.add("Select State");
                    loadStateNames();
                    countryArrayAdapter.notifyDataSetChanged();
                    stateArrayAdapter.notifyDataSetChanged();
                    break;
                case R.id.hospitalState:
                    stateArrayAdapter.notifyDataSetChanged();
                    TextView hospitalStateName = (TextView) view;
                    stateNames=hospitalStateName.getText().toString();
                  //  Toast.makeText(getApplicationContext(), stateNames, Toast.LENGTH_LONG).show();
                    break;
                case R.id.labCountry:
                    TextView labCountryName = (TextView) view;
                    countryName=labCountryName.getText().toString();
                    //Toast.makeText(getApplicationContext(), countryName, Toast.LENGTH_LONG).show();
                    stateList.clear();
                    stateList.add("Select State");
                    loadStateNames();
                    countryArrayAdapter.notifyDataSetChanged();
                    stateArrayAdapter.notifyDataSetChanged();
                    break;
                case R.id.labState:
                    stateArrayAdapter.notifyDataSetChanged();
                    TextView labStateName = (TextView) view;
                    stateNames=labStateName.getText().toString();
                    Toast.makeText(getApplicationContext(), stateNames, Toast.LENGTH_LONG).show();
                    break;
                case R.id.chemistCountry:
                    TextView chemistCountryName = (TextView) view;
                    countryName=chemistCountryName.getText().toString();
                    //Toast.makeText(getApplicationContext(), countryName, Toast.LENGTH_LONG).show();
                    stateList.clear();
                    stateList.add("Select State");
                    loadStateNames();
                    countryArrayAdapter.notifyDataSetChanged();
                    stateArrayAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void addHospital(View view) {
        //Toast.makeText(getApplicationContext()," Hospital",Toast.LENGTH_LONG).show();
     //   Log.d("Msg", "onResponse: "+"Hospital");
        stringRequest = new StringRequest(Request.Method.POST, Constants.addHospital,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getApplicationContext()," Respose Is "+response.toString(),Toast.LENGTH_LONG).show();
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getInt("result_code")==1){
                                Toast.makeText(getApplicationContext(),jsonObject.getString("result"),Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),jsonObject.getString("error"),Toast.LENGTH_LONG).show();
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
                params.put("hospitalName",hospitalName.getText().toString());
                params.put("hospitalAddress",hospitalAddress.getText().toString());
                params.put("hospitalCountry", countryNames);
                params.put("hospitalState",stateNames);
                params.put("hospitalCity",hospitalCity.getText().toString());
                params.put("hospitalPincode", hospitalPincode.getText().toString());
               // params.put("email", email);
                params.put("uid",String.valueOf(uid));
                params.put("bid",String.valueOf(bid));
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void addLabs(View view) {
        stringRequest = new StringRequest(Request.Method.POST, Constants.addLabs,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getApplicationContext()," Respose Is "+response.toString(),Toast.LENGTH_LONG).show();
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getInt("result_code")==1){
                                Toast.makeText(getApplicationContext(),jsonObject.getString("result"),Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),jsonObject.getString("error"),Toast.LENGTH_LONG).show();
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
                params.put("labName",labName.getText().toString());
                params.put("labAddress",labAddress.getText().toString());
                params.put("labCountry", countryNames);
                params.put("labState",stateNames);
                params.put("labCity",labCity.getText().toString());
                params.put("labPincode", labPincode.getText().toString());
               // params.put("email", email);
                params.put("uid",String.valueOf(uid));
                params.put("bid",String.valueOf(bid));
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void addChemists(View view) {
        stringRequest = new StringRequest(Request.Method.POST, Constants.addChemist,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getApplicationContext()," Respose Is "+response.toString(),Toast.LENGTH_LONG).show();
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getInt("result_code")==1){
                                Toast.makeText(getApplicationContext(),jsonObject.getString("result"),Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),jsonObject.getString("error"),Toast.LENGTH_LONG).show();
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
                params.put("chemistName",chemistName.getText().toString());
                params.put("chemistAddress",chemistAddress.getText().toString());
                params.put("chemistCountry", countryNames);
                params.put("chemistState",stateNames);
                params.put("chemistCity",chemistCity.getText().toString());
                params.put("chemistPincode", chemistPincode.getText().toString());
               // params.put("email", email);
                params.put("uid",String.valueOf(uid));
                params.put("bid",String.valueOf(bid));
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}