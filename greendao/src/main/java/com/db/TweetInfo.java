package com.db;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class TweetInfo {


    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "search.twitter.org.twittermediasearchapplication.db");

        Entity notification = schema.addEntity("TweetInformation");
        notification.addStringProperty("imgUrl");
        notification.addStringProperty("imgDesc");
        notification.addStringProperty("imgProfileUrl");
        notification.addStringProperty("userName");
        notification.addStringProperty("screenName");
        notification.addStringProperty("profileUrl");
        notification.addStringProperty("tweetUrl");
        DaoGenerator dg = new DaoGenerator();
        dg.generateAll(schema, "./app/src/main/java");


    }
}
