package hu.cinemacity.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author Kircsi
 */
public class CinemaUpdateObserver extends TimerTask {

    public static final String PROJECTION_DATES_UPDATED = "project_dates_updated";

    private final ActionListener listener;
    private final URL queryURL;
    private Date latestProjectionDateSoFar;

    public CinemaUpdateObserver(ActionListener listener, int cinemaID, String movieID) throws MalformedURLException {
        this.listener = listener;
        queryURL = QueryURLFactory.getQueryURL(cinemaID, movieID);
        latestProjectionDateSoFar = null;
    }

    @Override
    public void run() {
        try {
            InputStreamReader jsonInput = new InputStreamReader(queryURL.openStream(), Charset.forName("UTF-8"));
            JSONTokener jsonTokener = new JSONTokener(jsonInput);
            JSONObject root = new JSONObject(jsonTokener);

            Optional<Date> latestProjectionDate = getLatestProjectionDate(root);
            if (latestProjectionDate.isPresent()) {
                if (null == latestProjectionDateSoFar) {
                    latestProjectionDateSoFar = latestProjectionDate.get();
                } else if (latestProjectionDate.get().after(latestProjectionDateSoFar)) {
                    ActionEvent action = new ActionEvent(this, 0, PROJECTION_DATES_UPDATED);
                    listener.actionPerformed(action);
                }
            }
        } catch (IOException ignore) {
            //ignore the exception for now...
        }
    }

    private Optional<Date> getLatestProjectionDate(JSONObject root) {
        JSONArray sites = root.getJSONArray("sites");
        assert sites.length() == 1;
        JSONObject site = sites.getJSONObject(0);
        JSONArray movies = site.getJSONArray("fe");
        assert movies.length() == 1;
        JSONObject movie = movies.getJSONObject(0);
        JSONArray projections = movie.getJSONArray("pr");

        return getProjectionDates(projections)
                .stream()
                .max(Comparator.naturalOrder());
    }

    private List<Date> getProjectionDates(JSONArray projectionDatesJSON) {
        List<Date> projectionDates = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        for (int i = 0; i < projectionDatesJSON.length(); ++i) {
            JSONObject projection = projectionDatesJSON.getJSONObject(i);
            String dateAsString = projection.getString("dt").split(" ")[0];
            try {
                Date date = dateFormat.parse(dateAsString);
                projectionDates.add(date);
            } catch (ParseException ex) {
            }
        }
        return projectionDates;
    }

}
