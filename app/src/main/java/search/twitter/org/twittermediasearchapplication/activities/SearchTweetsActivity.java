package search.twitter.org.twittermediasearchapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.services.SearchService;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import search.twitter.org.twittermediasearchapplication.R;
import search.twitter.org.twittermediasearchapplication.adapters.TweetItemAdapter;
import search.twitter.org.twittermediasearchapplication.db.DaoMaster;
import search.twitter.org.twittermediasearchapplication.db.DaoSession;
import search.twitter.org.twittermediasearchapplication.db.TweetInformation;
import search.twitter.org.twittermediasearchapplication.db.TweetInformationDao;
import search.twitter.org.twittermediasearchapplication.models.TweetInfo;

public class SearchTweetsActivity extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "dUifr0h0nw6qvq1Zw3y4L3bk9";
    private static final String TWITTER_SECRET = "es5vSm5GCmFRzRGLNWR2qxZgx0CD8ZnOvU0MRT4MQDZdKHNMs9";
    private static final String DATABASE_NAME = "TWEET_INFORMATION";
    private String defaultQuery = "manhattan&filter:media";
    private TwitterAuthClient client;
    private StaggeredGridView gridView;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private DaoMaster.DevOpenHelper openHelper;
    private TweetInformationDao tweetInformationDao;
    private TweetInformation tweetInformation;
    private android.support.v7.widget.SearchView searchView;
    private SearchService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_search_tweets);

        openHelper = new DaoMaster.DevOpenHelper(this, DATABASE_NAME, null);
        db = openHelper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        tweetInformationDao = daoSession.getTweetInformationDao();

        searchView = (android.support.v7.widget.SearchView) findViewById(R.id.searchView);
        gridView = (StaggeredGridView) findViewById(R.id.grid_view);

        authenticate();
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getMedia(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    public void authenticate() {
        TwitterCore.getInstance().logInGuest(new com.twitter.sdk.android.core.Callback<AppSession>() {
            @Override
            public void success(Result<AppSession> result) {
                AppSession guestAppSession = result.data;
                service = TwitterCore.getInstance().getApiClient(guestAppSession).getSearchService();
                getMedia(defaultQuery);

            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void getMedia(String query) {
        service.tweets(query, null, null, null, null, 50, null, null, null, true, new com.twitter.sdk.android.core.Callback<Search>() {
            @Override
            public void success(Result<Search> result) {
                List<TweetInfo> tweets = new ArrayList<>();

                for (int i = 0; i < result.data.tweets.size(); i++) {

                    tweets.add(new TweetInfo(result.data.tweets.get(i).user.profileBannerUrl,
                            result.data.tweets.get(i).text,
                            result.data.tweets.get(i).user.profileImageUrl,
                            result.data.tweets.get(i).user.name,
                            result.data.tweets.get(i).user.screenName, "", ""));
                    tweetInformation = new TweetInformation(result.data.tweets.get(i).user.profileBannerUrl,
                            result.data.tweets.get(i).text,
                            result.data.tweets.get(i).user.profileImageUrl,
                            result.data.tweets.get(i).user.name,
                            result.data.tweets.get(i).user.screenName, "", "");
                    tweetInformationDao.insert(tweetInformation);
                }

                TweetItemAdapter tweetItemAdapter = new TweetItemAdapter(getApplicationContext(), R.layout.row_media, tweets);
                gridView.setAdapter(tweetItemAdapter);

            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        // Pass the activity result to the login button.
        client.onActivityResult(requestCode, responseCode, intent);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //your code
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //your code

        }
    }
}
