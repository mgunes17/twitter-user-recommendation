package configuration;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ercan on 14.12.2016.
 */
public class TwitterConfiguration {

    private static Twitter twitter;

    public TwitterConfiguration(){

    }

    private static Twitter buildTwitter() {

        try {
            Properties prop = new Properties();
            String propFileName = "twitter.properties";

            InputStream inputStream = new TwitterConfiguration().getClass().getClassLoader().getResourceAsStream(propFileName);
            prop.load(inputStream);

            ConfigurationBuilder cfb = new ConfigurationBuilder();

            cfb.setDebugEnabled(true)
                    .setOAuthConsumerKey(prop.getProperty("consumerKey"))
                    .setOAuthConsumerSecret(prop.getProperty("consumerSecret"))
                    .setOAuthAccessToken(prop.getProperty("accessToken"))
                    .setOAuthAccessTokenSecret(prop.getProperty("accessTokenSecret"));
            TwitterFactory tf = new TwitterFactory(cfb.build());

            return tf.getInstance();
        } catch(Throwable ex) {
            System.err.println("Could not create:" + ex.getMessage());
            ex.getCause().printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Twitter getInstance() {
        if(twitter == null)
            twitter = buildTwitter();

        return twitter;
    }

}
