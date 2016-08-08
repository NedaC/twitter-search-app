package search.twitter.org.twittermediasearchapplication.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import search.twitter.org.twittermediasearchapplication.R;
import search.twitter.org.twittermediasearchapplication.db.DaoMaster;
import search.twitter.org.twittermediasearchapplication.db.DaoSession;
import search.twitter.org.twittermediasearchapplication.db.TweetInformation;
import search.twitter.org.twittermediasearchapplication.db.TweetInformationDao;
import search.twitter.org.twittermediasearchapplication.models.TweetInfo;

public class TweetDetailsActivity extends AppCompatActivity {
    private TextView nameTextView, screenNameTextView, descTextView, tweetLinkTextView;
    private ImageView bigImgView;
    private CircleImageView profileImgView;
    private LinearLayout detailsLayout;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private DaoMaster.DevOpenHelper openHelper;
    private TweetInformationDao tweetInformationDao;
    private TweetInformation tweetInformation;
    private static final String DATABASE_NAME = "TWEET_INFORMATION";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);

        TweetInfo info = (TweetInfo) getIntent().getExtras().get("tweetData");
        openHelper = new DaoMaster.DevOpenHelper(this, DATABASE_NAME, null);
        db = openHelper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        tweetInformationDao = daoSession.getTweetInformationDao();

        bigImgView = (ImageView) findViewById(R.id.bigImgView);
        profileImgView = (CircleImageView) findViewById(R.id.profileImgView);
        nameTextView = (TextView) findViewById(R.id.userTextView);
        screenNameTextView = (TextView) findViewById(R.id.screenNameTextView);
        descTextView = (TextView) findViewById(R.id.descTextView);
        tweetLinkTextView = (TextView) findViewById(R.id.tweetLinkTextView);
        detailsLayout = (LinearLayout) findViewById(R.id.detailsLayout);

        detailsLayout.bringToFront();
        tweetLinkTextView.setText(getResources().getString(R.string.tweet_link));
        tweetLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/?lang=en"));
                startActivity(browserIntent);
            }
        });
        descTextView.setText(info.getImgDescription());
        nameTextView.setText(info.getUserName());
        screenNameTextView.setText(info.getScreenName());

        Picasso.with(getApplicationContext())
                .load(info.getImgUrl())
                .fit().centerCrop()
                .into((bigImgView));

        Picasso.with(getApplicationContext())
                .load(info.getImgProfileUrl())
                .into((profileImgView));

    }
}
