package com.rajiv.a300269668.newsapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Adapter.FeedbackAdapter;
import Adapter.MyAdapter;
import Model.Feedback;
import Model.ListItem;
import Model.SavedNews;

public class FeedbackActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    ActionMode actionMode;
    private EditText etName, etEmail, etComment;
    private TextInputLayout inputName, inputEmail, inputComment;
    private Button btnSend;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Feedback> listItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setupToolbarMenu();
        initializeWidgets();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=etName.getText().toString().trim();
                String email=etEmail.getText().toString().trim();
                String comment=etComment.getText().toString().trim();
                if(name.isEmpty()){
                    inputName.setError("Please enter name.");
                    return;
                }else
                    inputName.setErrorEnabled(false);
                if(email.isEmpty()){
                    inputEmail.setError("Please enter email.");
                    return;
                }else
                    inputEmail.setErrorEnabled(false);
                if(!isEmailValid(email)){
                    inputEmail.setError("Please enter correct email.");
                    return;
                }else
                    inputEmail.setErrorEnabled(false);
                if(comment.isEmpty()){
                    inputComment.setError("Please enter comment.");
                    return;
                }
                else
                    inputComment.setErrorEnabled(false);
                saveFeedback(name,email,comment);
            }
        });
    }
    private void setupToolbarMenu(){
        mToolbar=(Toolbar)findViewById(R.id.toolbar);
        mToolbar.setTitle("Feedback");
        mToolbar.setNavigationIcon(R.drawable.back_arrow);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FeedbackActivity.this,HomeActivity.class));
                finish();
            }
        });
        //mToolbar.inflateMenu(R.menu.menu_main);
    }
    private void initializeWidgets() {
        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etComment = (EditText) findViewById(R.id.etMessage);

        inputName = (TextInputLayout) findViewById(R.id.inputName);
        inputEmail = (TextInputLayout) findViewById(R.id.inputEmail);
        inputComment = (TextInputLayout) findViewById(R.id.inputComment);

        btnSend=(Button)findViewById(R.id.btnSendFeed);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("feedback");
        recyclerView=(RecyclerView)findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems=new ArrayList<>();
        adapter=new FeedbackAdapter(getApplicationContext(),listItems);
        recyclerView.setAdapter(adapter);
    }
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
    private void saveFeedback(String name,String email,String comment) {
            String userId = mFirebaseDatabase.push().getKey();
        Feedback feedback = new Feedback(name,email,comment);
        mFirebaseDatabase.child(userId).setValue(feedback);
        Toast.makeText(FeedbackActivity.this,"Feedback sent!",Toast.LENGTH_SHORT).show();
        etName.setText("");
        etEmail.setText("");
        etComment.setText("");

    }
}
