package Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Date;
import java.util.List;

import Model.ListItem;
import Model.SavedNews;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context; //current state of the class
    private List<ListItem> listItems;//create custom ListItem class
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId = "";

    public MyAdapter(Context context, List<ListItem> listItem) {
        this.context = context;
        listItems = listItem;

        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("savedNews");
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        ListItem listItem = listItems.get(position);
        holder.title.setText(listItem.getTitle());

        //  holder.txtTime.setText(listItem.getPublishedAt());
        holder.txtTime.setText(getTimeInFormat(listItem.getPublishedAt()));
        //  if(!(listItem.getImage().equals("null")))
        Picasso.get()
                .load(listItem.getImage())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                //.resize(800, 600)
                .into(holder.newsImg);
        //  else
        //     holder.newsImg.setVisibility(View.GONE);
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
            ListItem item = listItems.get(position);
            if (view.getId() == R.id.txtShare) {
                shareTextUrl(item.getUrl());
            } else if (view.getId() == R.id.txtSavedIcon) {
                view.setBackgroundResource(R.drawable.bookmark_set);
                saveNews(item.getTitle());
            } else {
                Intent browserIntent = new Intent(context.getApplicationContext(), ViewNewsActivity.class);
                browserIntent.putExtra("url", item.getUrl() + "");
                context.startActivity(browserIntent);
            }
        }
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

    private void saveNews(String title) {
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }
        SavedNews news = new SavedNews("testUserId", title);
        mFirebaseDatabase.child(userId).setValue(news);
        addUserChangeListener();
    }

    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SavedNews news = dataSnapshot.getValue(SavedNews.class);
            /* User user = null;
             for (DataSnapshot child : dataSnapshot.getChildren()) {
                 user = child.getValue(User.class);
             }
*/

                // Check for null
                if (news == null) {
                    Log.e("tagg", "User data is null!");
                    return;
                }
                Log.e("tagg", "User data is changed!" + news.userId + ", " + news.title);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("taggee", "Failed to read user", error.toException());
            }
        });
    }
}
