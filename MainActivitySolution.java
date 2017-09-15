package com.noelchew.remoteconfigsample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Context context;

    private FloatingActionButton fabAddToCart;
    private Button btnAddToCart;

    boolean useFAB;

    private FirebaseRemoteConfig mFirebaseRemoteConfig; // uncomment this after adding Firebase SDK dependency
    private FirebaseAnalytics mFirebaseAnalytics; // uncomment this after adding Firebase SDK dependency
    private static final String USER_FAB_KEY = "useFAB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        fabAddToCart = (FloatingActionButton) findViewById(R.id.fab);
        btnAddToCart = (Button) findViewById(R.id.button_add_to_cart);

        getSupportActionBar().setTitle("RemoteConfig Sample");
        getSupportActionBar().setSubtitle("A/B Testing");

        fabAddToCart.setOnClickListener(addToCartOnClickListener);
        btnAddToCart.setOnClickListener(addToCartOnClickListener);

        getUseFABRemoteConfigParamValue();
    }

    private void getUseFABRemoteConfigParamValue() {
        // Get Remote Config instance.
        // [START get_remote_config_instance]
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        // [END get_remote_config_instance]

        // Create a Remote Config Setting to enable developer mode, which you can use to increase
        // the number of fetches available per hour during development.
        // [START enable_dev_mode]
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        // [END enable_dev_mode]

        // Set default Remote Config parameter values. An app uses the in-app default values, and
        // when you need to adjust those defaults, you set an updated value for only the values you
        // want to change in the Firebase console.
        // [START set_default_values]
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        // [END set_default_values]

        long cacheExpiration = 3600; // 1 hour in seconds.
        // If your app is using developer mode, cacheExpiration is set to 0, so each fetch will
        // retrieve values from the service.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }

        // [START fetch_config_with_callback]
        // cacheExpirationSeconds is set to cacheExpiration here, indicating the next fetch request
        // will use fetch data from the Remote Config service, rather than cached parameter values,
        // if cached parameter values are more than cacheExpiration seconds old.
        // See Best Practices in the README for more information.
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Fetch Succeeded",
                                    Toast.LENGTH_SHORT).show();

                            // After config data is successfully fetched, it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.activateFetched();
                        } else {
                            Toast.makeText(MainActivity.this, "Fetch Failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START get_config_values]
                        useFAB = mFirebaseRemoteConfig.getBoolean(USER_FAB_KEY);
                        // [END get_config_values]
                        displayAddToCartUI();
                    }
                });
        // [END fetch_config_with_callback]

    }

    private void displayAddToCartUI() {
        if (useFAB) {
            btnAddToCart.setVisibility(View.GONE);
            fabAddToCart.setVisibility(View.VISIBLE);
        } else {
            btnAddToCart.setVisibility(View.VISIBLE);
            fabAddToCart.setVisibility(View.GONE);
        }

        if (mFirebaseAnalytics == null) {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        }
        mFirebaseAnalytics.setUserProperty("usingFAB", String.valueOf(useFAB));
    }

    private View.OnClickListener addToCartOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showAddToCartSuccess(useFAB);
            reportAddToCartEventToFirebaseAnalytics(useFAB);
        }
    };

    private void showAddToCartSuccess(boolean usingFAB) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add to Cart");
        if (usingFAB) {
            builder.setMessage("Added to cart using FAB");
        } else {
            builder.setMessage("Added to cart using Button");
        }
        builder.setPositiveButton("OK", null)
                .setCancelable(false)
                .show();
    }

    private void reportAddToCartEventToFirebaseAnalytics(boolean usingFAB) {
        // [START shared_app_measurement]
        // Obtain the FirebaseAnalytics instance.
        if (mFirebaseAnalytics == null) {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        }
        // [END shared_app_measurement]

        // [START custom_event]
        Bundle params = new Bundle();
        params.putBoolean("usingFAB", usingFAB);
        mFirebaseAnalytics.logEvent("clicked_add_to_cart", params);
        // [END custom_event]
    }


}
