package com.subia.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Business extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    CardView card,labs,chemist;
    Spinner spinner,s1,s2,s3,s4;
    String state[]=null;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        card = (CardView) findViewById(R.id.cardView);
        spinner = (Spinner)findViewById(R.id.spinner);
        s1 = (Spinner) findViewById(R.id.spinner2);
        s2 = (Spinner) findViewById(R.id.spinner3);
        labs=(CardView)findViewById(R.id.labs);
        chemist=(CardView) findViewById(R.id.chemist);
        s3=(Spinner)findViewById(R.id.state);
        s4=(Spinner)findViewById(R.id.state1);


        s1.setOnItemSelectedListener(this);

        card.setVisibility(card.INVISIBLE);
        labs.setVisibility(labs.INVISIBLE);
        chemist.setVisibility(chemist.INVISIBLE);
        adapter = ArrayAdapter.createFromResource(this, R.array.Business, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                    card.setVisibility(card.VISIBLE);
                    labs.setVisibility(labs.INVISIBLE);
                    chemist.setVisibility(chemist.INVISIBLE);
                }
                else if(i==2)
                {
                    labs.setVisibility(labs.VISIBLE);
                    card.setVisibility(card.INVISIBLE);
                    chemist.setVisibility(chemist.INVISIBLE);
                }
                else if(i==3)
                {
                    chemist.setVisibility(chemist.VISIBLE);
                    card.setVisibility(card.INVISIBLE);
                    labs.setVisibility(labs.INVISIBLE);
                }
                else
                {
                    chemist.setVisibility(chemist.INVISIBLE);
                    card.setVisibility(card.INVISIBLE);
                    labs.setVisibility(labs.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(i==0)
        {
            state = new String[]{"Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chattisgarh","Goa","Gujarat","Haryana","Himachal Pradesh","Uttar Pradesh"};
        }
        if(i==1)
        {
            state = new String[]{"Faisalabad","Gujranwala","Islamabad","Karachi","Lahore","Multan","Rawalpindi"};
        }
        if(i==2)
        {
            state = new String[]{"Anhui","Fuzian","Henan","Hubei","Shandong","Sichuan","Tibet","Zhejiang","Yunnan"};
        }
        if(i==3)
        {
            state = new String[]{"Barisal","Chittagong","Comilla","Dhaka","Gazipur","Khulna","Mymensingh","Narayanganj","Rajshahi","Rangpur","Sylhet"};
        }
        if(i==4)
        {
            state = new String[]{"Ampara","Anuradhapura","Badulla","Batticaloa","Colombo","Galle","Hambantota","Jaffna","Kandy"};
        }
        ArrayAdapter<String> adt=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,state);
        s2.setAdapter(adt);
        s3.setAdapter(adt);
        s4.setAdapter(adt);
    }

    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
