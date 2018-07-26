package com.rajiv.a300269668.newsapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import Adapter.SectionAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class SectionFragment extends Fragment {


    public SectionFragment() {
        // Required empty public constructor
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.section_business,
            R.drawable.section_sport,
            R.drawable.section_health,
            R.drawable.section_tech,
            R.drawable.section_entertainment,
            R.drawable.section_science
    };
    // references to our text headings
    private String[] mHeadings = {
           "Business","Sports","Health","Technology","Entertainment","Science"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_section, container, false);

        GridView gridview = (GridView) view.findViewById(R.id.gvSections);
        gridview.setAdapter(new SectionAdapter(getActivity(),mThumbIds,mHeadings));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

            }
        });
        return view;
    }

}
