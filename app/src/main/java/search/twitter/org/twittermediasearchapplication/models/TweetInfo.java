package search.twitter.org.twittermediasearchapplication.models;

import java.io.Serializable;

public class TweetInfo implements Serializable {

    private String imgUrl;
    private String imgDescription;
    private String imgProfileUrl;
    private String userName;
    private String screenName;
    private String profileUrl;
    private String tweetUrl;

    public TweetInfo(String imgUrl, String imgDescription, String imgProfileUrl, String userName, String screenName ,String profileUrl, String tweetUrl) {
        this.imgUrl = imgUrl;
        this.imgDescription = imgDescription;
        this.imgProfileUrl = imgProfileUrl;
        this.userName = userName;
        this.screenName = screenName;
        this.profileUrl = profileUrl;
        this.tweetUrl = tweetUrl;
    }

    public String getImgDescription() {
        return imgDescription;
    }

    public void setImgDescription(String imgDescription) {
        this.imgDescription = imgDescription;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgProfileUrl() {
        return imgProfileUrl;
    }

    public void setImgProfileUrl(String imgProfileUrl) {
        this.imgProfileUrl = imgProfileUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getTweetUrl() {
        return tweetUrl;
    }

    public void setTweetUrl(String tweetUrl) {
        this.tweetUrl = tweetUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
}
