package sanmateo.avinnovz.com.sanmateoprofile.singletons;

import java.util.ArrayList;

import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.News;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class NewsSingleton {

    private ArrayList<News> newsToday = new ArrayList<>();
    private ArrayList<News> newsPrevious = new ArrayList<>();

    private static NewsSingleton NEWS = new NewsSingleton();

    private NewsSingleton() {}

    public static NewsSingleton getInstance() {
        return NEWS;
    }

    public ArrayList<News> getNewsToday() {
        return newsToday;
    }

    public void setNewsToday(ArrayList<News> news) {
        this.newsToday.clear();
        this.newsToday.addAll(news);
    }

    public ArrayList<News> getNewsPrevious() {
        return newsPrevious;
    }

    public void setNewsPrevious(ArrayList<News> news) {
        this.newsPrevious.clear();
        this.newsPrevious.addAll(news);
    }
}
