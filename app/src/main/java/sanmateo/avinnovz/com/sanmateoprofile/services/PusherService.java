package sanmateo.avinnovz.com.sanmateoprofile.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionStateChange;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.NewHomeActivity;
import sanmateo.avinnovz.com.sanmateoprofile.activities.admin.PublicAnnouncementsActivity;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.NotificationHelper;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.PrefsHelper;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Announcement;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.BusSingleton;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.CurrentUserSingleton;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.IncidentsSingleton;


/**
 * Created by rsbulanon on 6/28/16.
 */
public class PusherService extends Service {

    private CurrentUserSingleton currentUserSingleton;
    private IncidentsSingleton incidentsSingleton;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        currentUserSingleton = CurrentUserSingleton.newInstance();
        incidentsSingleton = IncidentsSingleton.getInstance();
        /** start fore ground */
        final Intent notificationIntent = new Intent(this, NewHomeActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this,0,
                notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.san_mateo_logo)
                .setContentText(getString(R.string.app_name))
                .setContentIntent(pendingIntent).build();
        startForeground(9999, notification);
        LogHelper.log("pusher","pusher service created and started in fore ground");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        final Pusher pusher = new Pusher("d37253507ae4d71dee6b",options);
        /** listen to public channel */
        final Channel publicChannel = pusher.subscribe("client");
        publicChannel.bind("san_mateo_event", (channelName, eventName, data) -> {
            /** manage, construct and display local push notification */
            try {
                final JSONObject json = new JSONObject(data);

                if (json.has("action")) {
                    final String action = json.getString("action");
                    final int id = Integer.valueOf(json.getInt("id"));

                    if (action.equals("new incident")) {
                        /** new incident notification */
                        PrefsHelper.setBoolean(PusherService.this,"refresh_incidents",true);
                        LogHelper.log("pusher","must show push notification for new incident");
                        NotificationHelper.displayNotification(id,PusherService.this,
                                json.getString("title"),json.getString("content"), null);
                    } else if (action.equals("block report")) {
                        /** new incident notification */
                        LogHelper.log("pusher","must delete incident report");
                        final int reportedBy = Integer.valueOf(json.getString("reported_by"));
                        if (currentUserSingleton.getCurrentUser().getUserId() == reportedBy) {
                            LogHelper.log("pusher","Show notification for blocked report");
                            NotificationHelper.displayNotification(id,PusherService.this,
                                    "Your report was blocked by the admin",json.getString("remarks"),null);

                            /** removed blocked incident from incidents singleton */
                            final int toRemoveIncidentId = Integer.valueOf(json.getString("id"));
                            for (int i = 0 ; i < incidentsSingleton.getIncidents().size(); i++) {
                                final Incident incident = incidentsSingleton.getIncidents().get(i);
                                if (incident.getIncidentId() == toRemoveIncidentId) {
                                    incidentsSingleton.getIncidents().remove(i);
                                }
                            }
                        } else {
                            LogHelper.log("pusher","must delete only blocked report");
                        }
                    } else if (action.equals("news created")) {
                        LogHelper.log("pusher","news created");
                        NotificationHelper.displayNotification(id,PusherService.this,
                                json.getString("title"),json.getString("reported_by"),null);
                    } else if (action.equals("announcements")) {
                        LogHelper.log("pusher","announcements created");
                        PrefsHelper.setBoolean(PusherService.this,"has_notifications",true);
                        PrefsHelper.setBoolean(PusherService.this,"refresh_announcements",true);
                        NotificationHelper.displayNotification(id,PusherService.this,
                                json.getString("title"),json.getString("message"),
                                PublicAnnouncementsActivity.class);
                    } else if (action.equals("water level")) {
                        PrefsHelper.setBoolean(PusherService.this,"has_notifications",true);
                        LogHelper.log("pusher","water level created");
                        PrefsHelper.setBoolean(PusherService.this,"refresh_water_level",true);
                        NotificationHelper.displayNotification(id,PusherService.this,
                                json.getString("title"),json.getString("message"),null);
                    }
                }
            } catch (JSONException e) {
                LogHelper.log("err","unable to manage,construct and display push notification --> " + e);
            }

            /** broadcast received push notification */
            final HashMap<String,Object> map = new HashMap<>();
            map.put("channel",channelName);
            map.put("data",data);
            BusSingleton.getInstance().post(map);
        });

        /** listen to private channel using user's email */
        LogHelper.log("pusher","subscribe to private channel --> " + currentUserSingleton.getCurrentUser().getEmail());
        final Channel privateChannel = pusher.subscribe(currentUserSingleton.getCurrentUser().getEmail());
        privateChannel.bind("san_mateo_event", (channelName, eventName, data) -> {
            try {
                final JSONObject json = new JSONObject(data);
                LogHelper.log("pusher","JSON ---> " + json.toString());
                if (json.has("action")) {
                    final String action = json.getString("action");
                    final int id = Integer.valueOf(json.getInt("id"));

                    if (action.equals("incident_approval")) {
                        LogHelper.log("pusher","new incident report needed for approval");
                        NotificationHelper.displayNotification(id,PusherService.this,
                                json.getString("title"),json.getString("content"), null);
                    }
                } else {

                }
                /** broadcast received push notification */
                final HashMap<String,Object> map = new HashMap<>();
                map.put("channel",channelName);
                map.put("data",data);
                BusSingleton.getInstance().post(map);
            } catch (JSONException e) {
                LogHelper.log("pusher","unable to listen to own channel using email --> " + e);
            }
        });

        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange connectionStateChange) {
                Log.d("pusher","connection state changed ---> " + connectionStateChange.getCurrentState().name());
            }

            @Override
            public void onError(String s, String s1, Exception e) {
                Log.d("pusher","on error ---> " + s + " s1 --> " + s1);
            }
        });
        return Service.START_STICKY;
    }
}
