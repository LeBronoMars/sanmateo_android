package sanmateo.avinnovz.com.sanmateoprofile.singletons;

import java.util.ArrayList;

import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class IncidentsSingleton {

    private ArrayList<Incident> incidents = new ArrayList<>();

    private static IncidentsSingleton INCIDENTS = new IncidentsSingleton();

    private IncidentsSingleton() {}

    public static IncidentsSingleton getInstance() {
        return INCIDENTS;
    }

    public ArrayList<Incident> getIncidents() {
        return incidents;
    }

    public void setIncidents(ArrayList<Incident> incidents) {
        this.incidents.clear();
        this.incidents.addAll(incidents);
    }
}
