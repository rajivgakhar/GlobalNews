package com.rajiv.a300269668.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Model.Region;


public class SettingActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private Switch sLayout;
    private Spinner sregion;
    SharedPreferences pref;
private int check = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        pref = getSharedPreferences("_androidId", MODE_PRIVATE);
        setupToolbarMenu();
        initializeWidgets();
        String comp_layout = pref.getString("compactLayout", "0");
        if (comp_layout.equals("true")) {
            sLayout.setChecked(true);
        } else {
            sLayout.setChecked(false);
        }
        sLayout.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                SharedPreferences.Editor editor = pref.edit();
                editor.putString("compactLayout", b + "");
                editor.apply();
            }
        });


        Region region=new Region();
        sregion.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_view, region.getRegions()));

        sregion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(++check > 1) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("selectedRegion", String.valueOf(adapterView.getItemAtPosition(i)));
                    editor.apply();
                    String selectedR = pref.getString("selectedRegion", "0");

                    if (!selectedR.equals("0")) {
                        TextView txt = (TextView) view.findViewById(R.id.txtSpinnerSelected);
                        txt.setText(selectedR);
                    }
                }else{
                    String selectedR = pref.getString("selectedRegion", "0");
                    if (!selectedR.equals("0")) {
                        TextView txt = (TextView) view.findViewById(R.id.txtSpinnerSelected);
                        txt.setText(selectedR);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void setupToolbarMenu() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Settings");
        mToolbar.setNavigationIcon(R.drawable.back_arrow);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, HomeActivity.class));
                finish();
            }
        });
    }

    private void initializeWidgets() {
        sLayout = (Switch) findViewById(R.id.layoutSwitch);
        sregion = (Spinner) findViewById(R.id.spinnerRegion);
        sregion.setPrompt("Select Region");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SettingActivity.this, HomeActivity.class));
        finish();
    }
}
