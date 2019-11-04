package com.need.unknown.component.helper;

import android.service.autofill.UserData;
import android.util.Log;

import com.freshchat.consumer.sdk.Freshchat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.need.unknown.R;
import com.need.unknown.component.model.DataUser;
import com.need.unknown.component.model.ResponseNetworkError;
import com.need.unknown.presenter.impl.BasePresenterImpl;
import com.need.unknown.view.BaseView;

import java.util.List;

/**
 * Created by khadafi on 9/4/2016.
 */
public class FirebaseInstanceService extends FirebaseMessagingService implements BaseView {
    private static final String TAG = "MyFirebaseIIDService";

    SessionManager sessionManager;
    BasePresenterImpl<BaseView> baseViewBasePresenter;
    String newToken;

    @Override
    public void onCreate() {
        baseViewBasePresenter = new BasePresenterImpl<BaseView>() {
        };
        sessionManager = new SessionManager(this);
        super.onCreate();
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        this.newToken = s;

        Log.d(TAG, "Refreshed token: " + newToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(refreshedToken);

        try {
            Log.d("Token: ", newToken);
//            Toast.makeText(this, refreshedToken + "", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataUser userData = sessionManager.getUserData();
        //Log.d(this.getClass().getSimpleName(),"Inside Instance on onCreate");
        String savedToken = sessionManager.getFirebaseInstanceId();
        String defaultToken = getApplication().getString(R.string.pref_firebase_instance_id_default_key);

        if (newToken != null && !savedToken.equalsIgnoreCase(defaultToken)) {
            //currentToken is null when app is first installed and token is not available
            //also skip if token is already saved in preferences...
            sessionManager.setFirebaseInstanceId(newToken);
            baseViewBasePresenter.getProfile(this, false);
        }
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */


    // [START refresh_token]
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
        //registerDevice(token);
    }

    @Override
    public void onProgress() {

    }

    @Override
    public <T> void onSuccess(T object) {
        if (object instanceof DataUser) {
            sessionManager.setFirebaseInstanceId(newToken);
            sessionManager.updateUserData((DataUser) object);
            Freshchat.getInstance(this).setPushRegistrationToken(sessionManager.getFirebaseInstanceId());
        }
    }

    @Override
    public void onFailure(ResponseNetworkError networError) {

    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public void updateNetwork(long totalBytesRead, long contentLength, boolean b) {

    }

    @Override
    public void presenterReady(DataUser userData) {

    }

//   private void registerDevice(String token) {
//	  final User user = User.getUser();
//
//	  try {
//		 JSONObject requestJson = new JSONObject();
//		 RequestParams params = new RequestParams();
//		 params.put("email", user.email);
//		 params.put("token", token);
////            params.put("device_id", Helper.getDeviceId(getApplication()));
//
//		 BisatopupApi.get("/user/register-device", params, getApplicationContext(), new TextHttpResponseHandler() {
//
//			@Override
//			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//
//			}
//
//			@Override
//			public void onGoogleSuccess(int statusCode, Header[] headers, final String responseString) {
//
//			   try {
//				  Log.d(TAG, responseString);
//
//			   } catch (Exception e) {
//				  e.printStackTrace();
//
//
//			   }
//
//			}
//		 });
//
//	  } catch (Exception e) {
//		 e.printStackTrace();
//	  }
//
//   }
}