package search.twitter.org.twittermediasearchapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.etsy.android.grid.util.DynamicHeightImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import search.twitter.org.twittermediasearchapplication.R;
import search.twitter.org.twittermediasearchapplication.activities.TweetDetailsActivity;
import search.twitter.org.twittermediasearchapplication.models.TweetInfo;

public class TweetItemAdapter extends ArrayAdapter<TweetInfo> {

    private Context context;
    private List<TweetInfo> mediaArrayList;
    private int screenWidth;
    private DynamicHeightImageView imageView;

    public TweetItemAdapter(Context context, int resource, List<TweetInfo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.mediaArrayList = objects;
    }


    @Override
    public View getView(final int position, View convertView,
                        final ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater
                .inflate(R.layout.row_media, parent, false);

        imageView = (DynamicHeightImageView) view.findViewById(R.id.imgView);
        final TweetInfo tweetInfo =  mediaArrayList.get(position);

        Picasso.with(context)
                .load(tweetInfo.getImgUrl())
                .into((imageView));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsTweet = new Intent(context,TweetDetailsActivity.class);
                detailsTweet.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                detailsTweet.putExtra("tweetData", tweetInfo);
                context.startActivity(detailsTweet);
            }
        });
        return view;
    }
}
