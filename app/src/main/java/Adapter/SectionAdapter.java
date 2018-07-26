package Adapter;


import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rajiv.a300269668.newsapp.R;

import java.util.List;

import Model.ListItem;


public class SectionAdapter extends BaseAdapter {
    private Activity context; //current state of the class
    private List<ListItem> listItems;//create custom ListItem class
    private Integer[] mThumbIds;
    private String[] mHeadings;

    public SectionAdapter(Activity context, Integer[] mThumbId, String[] mHeading) {
        this.context = context;
        this.mThumbIds = mThumbId;
        this.mHeadings = mHeading;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=context.getLayoutInflater();
        if(view==null){
            //pick layout from layout
            view=inflater.inflate(R.layout.layout_sections,null);
        }
        TextView txt=(TextView)view.findViewById(R.id.txtHeadings);
        ImageView iv=(ImageView)view.findViewById(R.id.ivSections);
        iv.setImageResource(mThumbIds[position]);
        //populate text
        txt.setText(mHeadings[position]);
        return view;
    }

}
