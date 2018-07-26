package Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajiv.a300269668.newsapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import Model.Feedback;

/**
 * Created by 300269668 on 7/21/2018.
 */

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {
    private Context context; //current state of the class
    private List<Feedback> listItems;//create custom ListItem class

    public FeedbackAdapter(Context context, List<Feedback> listItem) {
        this.context = context;
        listItems = listItem;

    }

    @Override
    public FeedbackAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new FeedbackAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FeedbackAdapter.ViewHolder holder, int position) {
        Feedback listItem = listItems.get(position);
        holder.title.setText(listItem.getName());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView txtTime, txtShare, txtSavedIcon;
        public ImageView newsImg;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.txtTitle);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtShare = (TextView) itemView.findViewById(R.id.txtShare);
            txtSavedIcon = (TextView) itemView.findViewById(R.id.txtSavedIcon);
            newsImg = (ImageView) itemView.findViewById(R.id.newsImage);
            txtShare.setOnClickListener(this);
            newsImg.setOnClickListener(this);
            title.setOnClickListener(this);
            txtSavedIcon.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Feedback item = listItems.get(position);
            if (view.getId() == R.id.txtShare) {

            }
        }
    }

}
