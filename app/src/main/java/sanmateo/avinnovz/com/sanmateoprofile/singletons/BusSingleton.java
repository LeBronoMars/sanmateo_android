package sanmateo.avinnovz.com.sanmateoprofile.singletons;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by rsbulanon on 6/28/16.
 */
public class BusSingleton {

    private static Bus BUS = new Bus(ThreadEnforcer.ANY);

    private BusSingleton() {}

    public static Bus getInstance() {
        return BUS;
    }
}
