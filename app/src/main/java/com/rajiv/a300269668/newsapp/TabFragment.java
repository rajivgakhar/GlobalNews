package com.rajiv.a300269668.newsapp;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapter.MyAdapter;
import Model.ListItem;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {
 public static String  category="";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
     ProgressDialog progressDialog;
int position;
String searchText;
    public TabFragment() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public TabFragment(int pos) {
        // Required empty public constructor
        this.position=pos;
    }
    @SuppressLint("ValidFragment")
    public TabFragment(String searchText, int pos) {
        // Required empty public constructor
        this.searchText=searchText;
        this.position=pos;
    }

    public static TabFragment newInstance() {
        TabFragment fragment = new TabFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tab, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        recyclerView=(RecyclerView)view.findViewById(R.id.recycleView1);
        recyclerView.setHasFixedSize(true);
        //every item has fixed size
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));



                switch (position){
                    case 0:
                        category="business";
                        getNewsByCategory(category);

                        break;
                    case 1:
                        category="entertainment";
                        getNewsByCategory(category);
                        //CookieManager.getInstance().setCookie("abc","abcd");
                       // String cookies = CookieManager.getInstance().getCookie("abc");

                     //   Log.e("ffff", "All the cookies in a string:" + CookieManager.getInstance().hasCookies()+cookies);
                        break;
                    case 2:
                        category="general";
                        getNewsByCategory(category);
                        break;
                    case 3:
                        category="Health";
                        getNewsByCategory(category);
                        break;
                    case 4:
                        category="Science";
                        getNewsByCategory(category);
                        break;
                    case 5:
                        category="Sports";
                        getNewsByCategory(category);
                        break;
                    case 6:
                        category="Technology";
                        getNewsByCategory(category);
                        break;
                    case 10:
                        getNewsBySearch("");
                        break;

                        default:
                            category="";
                            getNewsByCategory(category);
                            break;
                }
        return  view;
    }

    public void getNewsByCategory(String category){
         String url = "https://newsapi.org/v2/top-headlines?country=ca"+
                 "&apiKey=a119b4537d944624af08fb67a63e46ca"+
                 "&category="+category;
        Log.e("urllll",url+"");
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
                            ListItem listItem=new ListItem(jobj.getString("title")
                                    ,jobj.getString("description")
                                    ,jobj.getString("urlToImage")
                                    ,jobj.getString("url")
                                    ,jobj.getString("publishedAt")
                            );
                            listItems.add(listItem);
                        }
                        adapter=new MyAdapter(getContext(),listItems);
                        recyclerView.setAdapter(adapter);

                        progressDialog.dismiss();

                    }


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

    public void getNewsBySearch(String category){
        String url = "https://newsapi.org/v2/everything?q="+searchText+
                "&apiKey=a119b4537d944624af08fb67a63e46ca";
        Log.e("urllll",url+"");
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
                            ListItem listItem=new ListItem(jobj.getString("title")
                                    ,jobj.getString("description")
                                    ,jobj.getString("urlToImage")
                                    ,jobj.getString("url")
                                    ,jobj.getString("publishedAt")
                            );
                            listItems.add(listItem);
                        }
                        adapter=new MyAdapter(getContext(),listItems);
                        recyclerView.setAdapter(adapter);

                        progressDialog.dismiss();

                    }


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
