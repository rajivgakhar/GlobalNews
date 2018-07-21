package com.example.a300269668.newsapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapter.MyAdapter;
import Model.ListItem;

public class MainActivity extends AppCompatActivity {
    final String url = "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=a119b4537d944624af08fb67a63e46ca";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        recyclerView=(RecyclerView)findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        //every item has fixed size
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        listItems=new ArrayList<>();
        progressDialog.show();
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", "abx");
        CustomRequest user_request = new CustomRequest(Request.Method.GET, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    String status = response.getString("status");
                    if(status.equals("ok")){
                        JSONArray ja = response.getJSONArray("articles");
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jobj = ja.getJSONObject(i);
                            ListItem listItem=new ListItem(jobj.getString("title"),
                                    jobj.getString("description"),jobj.getString("urlToImage"),
                                    jobj.getString("url") ,jobj.getString("publishedAt"));
                            listItems.add(listItem);
                          /*  Log.e("dateaaaaaaaaaaaaaa", String.valueOf(jobj.getString("author")));
                            Log.e("dateaaaaaaaaaaaaaa", String.valueOf(jobj.getString("title")));
                            Log.e("dateaaaaaaaaaaaaaa", String.valueOf(jobj.getString("publishedAt")));
                            Log.e("dateaaaaaaaaaaaaaa", String.valueOf(jobj.getString("description")));
                            Log.e("dateaaaaaaaaaaaaaa", String.valueOf(jobj.getString("urlToImage")));
                            Log.e("dateaaaaaaaaaaaaaa", String.valueOf(jobj.getString("url")));
                            Log.e("dateaaaaaaaaaaaaaa", String.valueOf(jobj.getJSONObject("source").getString("name")));
*/
                        }
                        adapter=new MyAdapter(MainActivity.this,listItems);
                        recyclerView.setAdapter(adapter);

                        progressDialog.dismiss();

                    }

/*
                    Log.e("dateaaaaaaaaaaaaaa", String.valueOf(response.length()));
                    Log.e("dateaaaaaaaaaaaaaa", String.valueOf(response.getString("status")));
                    Log.e("dateaaaaaaaaaaaaaa", String.valueOf(response.getString("totalResults")));
                    Log.e("dateaaaaaaaaaaaaaa", String.valueOf(response.getJSONArray("articles").length()));

*/


                   // if (success == 1) {

                       // JSONArray ja = response.getJSONArray("users");

                       // for (int i = 0; i < ja.length(); i++) {

                           // JSONObject jobj = ja.getJSONObject(i);
                            //HashMap<String, String> item = new HashMap<String, String>();
                            //txt.setText(jobj.getString("id"));
                            // storeInfoSharedPref(jobj.getString("id"), jobj.getString("name"), jobj.getString("age"), jobj.getString("city"));

                            //Toast.makeText(LoginActivity.this, jobj.getString("name"), Toast.LENGTH_SHORT).show();


                     //   } // for loop ends

                  /*  } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                "Invalid id", Toast.LENGTH_SHORT)
                                .show();
                    }*/
                } catch (Exception e) {
                    Log.e("dateaaaaaaaaaaaaaa", "FAILEDDDDDD");
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                progressDialog.dismiss();
            }
        });


        // Adding request to request queue
        MyApplication.getInstance().addToReqQueue(user_request);


    }

}
