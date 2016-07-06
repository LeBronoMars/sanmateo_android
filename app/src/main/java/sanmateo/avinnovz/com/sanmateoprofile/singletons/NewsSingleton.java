package sanmateo.avinnovz.com.sanmateoprofile.singletons;

import java.util.ArrayList;

import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.News;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class NewsSingleton {

    private ArrayList<News> news = new ArrayList<>();

    private static NewsSingleton NEWS = new NewsSingleton();

    private NewsSingleton() {}

    public static NewsSingleton getInstance() {
        return NEWS;
    }

    public ArrayList<News> getIncidents() {
        return news;
    }

    public void setIncidents(ArrayList<News> news) {
        this.news.clear();
        this.news.addAll(news);
    }
}
