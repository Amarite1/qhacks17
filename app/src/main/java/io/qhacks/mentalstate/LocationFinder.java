package io.qhacks.mentalstate;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class LocationFinder implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for logging
    private static final String TAG = "LF";
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;

    private Context context;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationReadyListener lrl;
    private Activity activity;

    public interface LocationReadyListener{
        void onLocation(Location location);
        void onPermissionNotGranted();
        void onConnectionError();
    }

    private LocationFinder(Context context, LocationReadyListener listener, Activity activity){
        this.context = context;
        lrl = listener;
        this.activity = activity;

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    public static LocationFinder init(Context context){
        if(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION)){
            return new LocationFinder(context, null, null);
        }else{
            return null;
        }
    }

    public static LocationFinder init(Context context, LocationReadyListener listener){
        if(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION)){
            return new LocationFinder(context, listener, null);
        }else{
            return null;
        }
    }

    public static LocationFinder init(Activity activity, LocationReadyListener listener){
        if(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION)){
            return new LocationFinder(activity, listener, activity);
        }else{
            return null;
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (result.hasResolution() && activity != null) {
            try {
                mResolvingError = true;
                result.startResolutionForResult(activity, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                mGoogleApiClient.connect();
            }
        } else {
            Log.w(TAG, "Connection Failed");
            if(lrl != null){
                lrl.onConnectionError();
            }
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (lrl != null) {
                lrl.onLocation(mLastLocation);
            }
        }catch(SecurityException e){
            e.printStackTrace();
            if (lrl != null) {
                lrl.onPermissionNotGranted();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        //todo: figure this out
    }

    public Location getLocation(){
        return mLastLocation;
    }
}