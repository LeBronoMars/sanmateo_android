package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.FacebookFeedsAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.interfaces.EndlessRecyclerViewScrollListener;
import sanmateo.avinnovz.com.sanmateoprofile.models.others.FacebookFeeds;

/**
 * Created by rsbulanon on 8/29/16.
 */
public class SocialMediaActivity extends BaseActivity {

    @BindView(R.id.rvFeeds) RecyclerView rvFeeds;
    private CallbackManager callbackManager;
    private ArrayList<FacebookFeeds> feeds = new ArrayList<>();
    private String nextPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        ButterKnife.bind(this);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvFeeds.setAdapter(new FacebookFeedsAdapter(feeds));
        rvFeeds.setLayoutManager(linearLayoutManager);

        rvFeeds.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                getFeeds(nextPage);
            }
        });


        callbackManager = CallbackManager.Factory.create();
        setToolbarTitle("Social Media");

        if (AccessToken.getCurrentAccessToken() == null || AccessToken.getCurrentAccessToken().isExpired()) {
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    LogHelper.log("fb","on login success --> " + loginResult.getAccessToken());
                    getFeeds("/sanmateolgu/feed");
                }

                @Override
                public void onCancel() {
                    LogHelper.log("fb","on login cancelled");
                }

                @Override
                public void onError(FacebookException error) {
                    LogHelper.log("fb","on fb error --> " + error.getMessage());
                }
            });
            LoginManager.getInstance().logInWithPublishPermissions(this, Arrays.asList("publish_actions"));
        } else {
            getFeeds("/sanmateolgu/feed");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private void getFeeds(final String graphPath) {
        LogHelper.log("fb","graph path --> " + graphPath);
        showCustomProgress("Fetching feeds from San Mateo's official facebook page, Please wait...");
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                graphPath,
                null,
                HttpMethod.GET, response -> {
                    dismissCustomProgress();
                    try {
                        final JSONObject json = new JSONObject(response.getRawResponse());
                        LogHelper.log("fb","RAW ---> " + response.getRawResponse());
                        final JSONArray jsonArray = json.getJSONArray("data");
                        if (json.has("paging")) {
                            final JSONObject paging = json.getJSONObject("paging");
                            if (paging.has("next")) {
                                LogHelper.log("fb","NEXT PAGE --> " + paging.getString("next"));
                                nextPage = paging.getString("next");
                            }
                        }
                        for (int i = 0 ; i < jsonArray.length() ; i++) {
                            final JSONObject obj = jsonArray.getJSONObject(i);
                            if (obj.has("message")) {
                                feeds.add(new FacebookFeeds(obj.getString("message"),
                                                            obj.getString("created_time")));
                            }
                        }
                        rvFeeds.getAdapter().notifyDataSetChanged();
                    } catch (JSONException e) {
                        LogHelper.log("fb","unable to parse fb feeds ---> " +  e.getMessage());
                        e.printStackTrace();
                    }
                }
        ).executeAsync();
    }
}
