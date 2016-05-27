package hu.cinemacity.logic;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 *
 * @author Kircsi
 */
public class QueryURLFactory {

    private static final String PROPERTY_FILE_NAME = "queryURL.properties";
    private static final String PROPERTY_KEY = "queryURL";
    private static final String RAW_QUERY_URL;

    static {
        Properties properties = new Properties();
        try {
            properties.load(QueryURLFactory.class.getResourceAsStream(PROPERTY_FILE_NAME));
        } catch (IOException ignore) {
            //shouldn't happen
        }
        RAW_QUERY_URL = properties.getProperty(PROPERTY_KEY);
    }

    private QueryURLFactory() {
    }

    public static URL getQueryURL(int cinemaID, String movieID) throws MalformedURLException {
        String urlText = String.format(RAW_QUERY_URL, cinemaID, movieID);
        return new URL(urlText);
    }

}
