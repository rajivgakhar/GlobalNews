package Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.rajiv.a300269668.newsapp.R;
import com.rajiv.a300269668.newsapp.ViewNewsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import Model.ListItem;
import Model.SavedNews;


public class SavedNewsAdapter extends RecyclerView.Adapter<SavedNewsAdapter.ViewHolder> {

    private Activity context; //current state of the class
    private List<ListItem> listItems;//create custom ListItem class
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId = "";
    List<String> savedItemKeys = new ArrayList<>();


    public SavedNewsAdapter(Activity con, List<ListItem> listItem) {
        this.context = con;
        listItems = listItem;

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("savedNews");
        SharedPreferences pref = con.getSharedPreferences("_androidId", context.MODE_PRIVATE);
        String android_id = pref.getString("android_id", "0");
        mFirebaseDatabase = mFirebaseDatabase.child(android_id);

    }

    @Override
    public SavedNewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_list_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SavedNewsAdapter.ViewHolder holder, int position) {
        ListItem listItem = listItems.get(position);
        holder.title.setText(listItem.getTitle());
        holder.txtTime.setText(getTimeInFormat(listItem.getPublishedAt()));
            Picasso.get()
                    .load(listItem.getImage())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.no_image)
                    .into(holder.newsImg);

        addSavedNewsChangeListener(holder.ivSavedIcon, listItem.getPublishedAt());
        holder.ivSavedIcon.setTag(R.drawable.bookmark_set);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView txtTime, txtShare;
        public ImageView newsImg;
        ImageView ivSavedIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.txtTitle);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtShare = (TextView) itemView.findViewById(R.id.txtShare);
            ivSavedIcon = (ImageView) itemView.findViewById(R.id.txtSavedIcon);
            newsImg = (ImageView) itemView.findViewById(R.id.newsImage);
            txtShare.setOnClickListener(this);
            newsImg.setOnClickListener(this);
            title.setOnClickListener(this);
            ivSavedIcon.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            ListItem item = listItems.get(position);
            if (view.getId() == R.id.txtShare) {
                shareTextUrl(item.getUrl());
            } else if (view.getId() == R.id.txtSavedIcon) {
                ImageView iv = (ImageView) view;
                switch ((Integer) iv.getTag()) {
                    case R.drawable.bookmark:
                        iv.setBackgroundResource(R.drawable.bookmark_set);
                        iv.setTag(R.drawable.bookmark_set);
                        saveNews(item);
                        break;
                    case R.drawable.bookmark_set:
                        iv.setBackgroundResource(R.drawable.bookmark);
                        iv.setTag(R.drawable.bookmark);
                        deleteNews(item.getPublishedAt());
                        Snackbar.make(view, "Articles unsaved!", Toast.LENGTH_SHORT).show();
                        break;
                }
                listItems.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, listItems.size());
            } else {
                Intent browserIntent = new Intent(context.getApplicationContext(), ViewNewsActivity.class);
                browserIntent.putExtra("url", item.getUrl() + "");
                context.startActivity(browserIntent);
            }
        }
    }

    private void deleteNews(String publishedAt) {
        publishedAt = publishedAt.replace("Z", " ");
        mFirebaseDatabase.child(publishedAt).setValue(null);

    }

    public String getTimeInFormat(String time) {
        String mytime = "";
        mytime = time.replace("T", " ");
        mytime = mytime.replace("Z", "");
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        Date myDate = null;
        try {
            myDate = dateFormat.parse(mytime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long newsTimeInLocalFormat = (myDate.getTime() - (7 * 60 * 60 * 1000));
        long milisec = new Date().getTime() - (newsTimeInLocalFormat);
        long sec = milisec / 1000;
        String finalDate = "";
        if (sec < 60)
            finalDate = sec + " seconds ago";
        else if ((sec / 60) < 60)
            finalDate = (sec / 60) + " minutes ago";
        else if ((sec / 3600) < 24)
            finalDate = (sec / 3600) + " hours ago";
        else
            finalDate = timeFormat.format(newsTimeInLocalFormat);

        return finalDate;
    }

    // Method to share either text or URL.
    private void shareTextUrl(String url) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Share News");
        share.putExtra(Intent.EXTRA_TEXT, url + "");

        context.startActivity(Intent.createChooser(share, "Share News!"));
    }

    private void saveNews(ListItem item) {
        userId = item.getPublishedAt();
        userId = userId.replace("Z", " ");
        SavedNews news = new SavedNews(item);

        mFirebaseDatabase.child(userId).setValue(news);

    }

    private void addSavedNewsChangeListener(final ImageView ivSavedIcon, String publishedAt) {
        publishedAt = publishedAt.replace("Z", " ");

        final String finalPublishedAt = publishedAt;
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (savedItemKeys.contains(dataSnapshot.getKey()))
                    savedItemKeys.remove(savedItemKeys.indexOf(dataSnapshot.getKey()));


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mFirebaseDatabase.addChildEventListener(childEventListener);

    }
}
