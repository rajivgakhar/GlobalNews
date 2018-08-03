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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.google.firebase.database.FirebaseDatabase;

public class SettingActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private Switch sLayout;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        pref = getSharedPreferences("_androidId", MODE_PRIVATE);
        setupToolbarMenu();
        initializeWidgets();
        String comp_layout=pref.getString("compactLayout","0");
        if (comp_layout.equals("true")) {
            sLayout.setChecked(true);
        }else{
            sLayout.setChecked(false);
        }
        sLayout.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                SharedPreferences.Editor editor = pref.edit();
                editor.putString("compactLayout", b + "");
                editor.apply();
                Log.e("check", b + "");
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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SettingActivity.this, HomeActivity.class));
        finish();
    }
}
