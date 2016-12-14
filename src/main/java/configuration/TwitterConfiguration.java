package configuration;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by ercan on 14.12.2016.
 */
public class TwitterConfiguration {

    private static Twitter twitter;

    public TwitterConfiguration(){

    }

    private static Twitter buildTwitter() {
        try {
            ConfigurationBuilder cfb = new ConfigurationBuilder();

            cfb.setDebugEnabled(true)
                    .setOAuthConsumerKey("0M2bfJYlUWpnMM2iM67qBzp1j")
                    .setOAuthConsumerSecret("BZhIUxlddgQJ6uBd1HjZrQFnrzJEum4BJU1kZxfHEuyvFQ7r1X")
                    .setOAuthAccessToken("2587971211-ZqKWpwR2U589KURGoV7yKlUJ7yyNZaZIU7v0lxH")
                    .setOAuthAccessTokenSecret("1WEUrc0LWiYJlluDMGLsP1HoDu2jlIs84TgkrAOplzSQd");
            TwitterFactory tf = new TwitterFactory(cfb.build());

            return tf.getInstance();
        }
        catch(Throwable ex) {
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
