package com.example.brenda.jccexample.gps;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.example.brenda.jccexample.activities.CentralPoint;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.Date;

public class MyLocationProvider implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
	private static final String REQUESTING_LOCATION_UPDATES_KEY = "true";
	private static final String LOCATION_KEY = null;
	private static final String LAST_UPDATED_TIME_STRING_KEY = null;
	private GoogleApiClient mGoogleApiClient;
	private Location mLastLocation;
	private Activity mActivity;
	private boolean mRequestingLocationUpdates = true;
	private LocationRequest mLocationRequest;
	private Location mCurrentLocation;
	private Object mLastUpdateTime;

	public void setActivity(Activity mActivity) {
		this.mActivity = mActivity;
	}

	/** To run {@onCreate}**/
	public void createService() {
		buildGoogleApiClient();
		connect();
		//updateValuesFromBundle(savedInstanceState);
	}

	public boolean isConnected() {
		if (mGoogleApiClient == null)
			return false;
		else
			return mGoogleApiClient.isConnected();
	}

	private void connect() {
		mGoogleApiClient.connect();
	}

	protected synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API)
				.build();
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle connectionHint) {
		if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
		if (mLastLocation != null) {
			mActivity.runOnUiThread(
					new Runnable() {
						@Override
						public void run() {
							((CentralPoint) mActivity).setLatitudeText("Latitud: " + String.valueOf(mLastLocation.getLatitude()));
							((CentralPoint) mActivity).setLongitudeText("Longitud: " + String.valueOf(mLastLocation.getLongitude()));
						}
					}
			);
		}
		if (mRequestingLocationUpdates) {
			createLocationRequest();
			startLocationUpdates();
		}
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub

	}

	protected void createLocationRequest() {
		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(1000);
		mLocationRequest.setFastestInterval(1000);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	}

	protected void startLocationUpdates() {
		if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		if(mGoogleApiClient.isConnected())
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
	}

	@Override
	public void onLocationChanged(final Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				((CentralPoint)mActivity).updateLocationData(
						location.getLatitude(),
						location.getLongitude());
			}
		});
		stopLocationUpdates();
//	    updateUI();
	}
	
	private void updateUI() {
        mActivity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        ((CentralPoint) mActivity).setLatitudeText("Lat: " + String.valueOf(mCurrentLocation.getLatitude()));
                        ((CentralPoint) mActivity).setLongitudeText("Long: " + String.valueOf(mCurrentLocation.getLongitude()));
                    }
                }
        );
    }

	public void stopLocationUpdates() {
	    LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        if(mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
        mLocationRequest.setExpirationTime(0);
	}

    /** To be run {@onResume}**/
	public void onResume() {
	    if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
	        startLocationUpdates();
	    }
	}

    /** To be run {@onSaveInstanceState}**/
	public void onSaveInstanceState(Bundle savedInstanceState) {
	    savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY,
                mRequestingLocationUpdates);
	    savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);
	    savedInstanceState.putString(LAST_UPDATED_TIME_STRING_KEY, String.valueOf(mLastUpdateTime));
	}
	
	private void updateValuesFromBundle(Bundle savedInstanceState) {
	    if (savedInstanceState != null) {
	        // Update the value of mRequestingLocationUpdates from the Bundle, and
	        // make sure that the Start Updates and Stop Updates buttons are
	        // correctly enabled or disabled.
	        if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
	            mRequestingLocationUpdates = savedInstanceState.getBoolean(
	                    REQUESTING_LOCATION_UPDATES_KEY);
	        }

	        // Update the value of mCurrentLocation from the Bundle and update the
	        // UI to show the correct latitude and longitude.
	        if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
	            // Since LOCATION_KEY was found in the Bundle, we can be sure that
	            // mCurrentLocationis not null.
	            mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
	        }

	        // Update the value of mLastUpdateTime from the Bundle and update the UI.
	        if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
	            mLastUpdateTime = savedInstanceState.getString(
	                    LAST_UPDATED_TIME_STRING_KEY);
	        }
	    }
	}
}