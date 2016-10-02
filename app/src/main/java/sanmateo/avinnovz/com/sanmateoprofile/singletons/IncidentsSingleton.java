package sanmateo.avinnovz.com.sanmateoprofile.singletons;

import java.util.ArrayList;

import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class IncidentsSingleton {

    private ArrayList<Incident> active = new ArrayList<>();
    private ArrayList<Incident> forApprovals = new ArrayList<>();
    private ArrayList<Incident> forReviews = new ArrayList<>();

    private static IncidentsSingleton INCIDENTS = new IncidentsSingleton();

    private IncidentsSingleton() {}

    public static IncidentsSingleton getInstance() {
        return INCIDENTS;
    }

    public ArrayList<Incident> getIncidents(final String status) {
        if (status.equals("active")) {
            return active;
        } else if (status.equals("for approval")) {
            return forApprovals;
        } else {
            return forReviews;
        }
    }

    public void clearAll() {
        active.clear();
        forApprovals.clear();
        forReviews.clear();
    }
}
