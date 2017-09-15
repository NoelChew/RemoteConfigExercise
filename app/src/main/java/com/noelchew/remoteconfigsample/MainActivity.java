package com.noelchew.remoteconfigsample;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Context context;

    private FloatingActionButton fabAddToCart;
    private Button btnAddToCart;

    boolean useFAB;

//    private FirebaseRemoteConfig mFirebaseRemoteConfig; // uncomment this after adding Firebase SDK dependency
//    private FirebaseAnalytics mFirebaseAnalytics; // uncomment this after adding Firebase SDK dependency
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
        // TODO: Part 1 - get Remote Config param value
//        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
//        ...

    }

    private void displayAddToCartUI() {
        if (useFAB) {
            btnAddToCart.setVisibility(View.GONE);
            fabAddToCart.setVisibility(View.VISIBLE);
        } else {
            btnAddToCart.setVisibility(View.VISIBLE);
            fabAddToCart.setVisibility(View.GONE);
        }

// TODO: uncomment these codes for Part 2
//        if (mFirebaseAnalytics == null) {
//            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//        }
//        mFirebaseAnalytics.setUserProperty("usingFAB", String.valueOf(useFAB));
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
        // TODO: Part 2 - send event to Firebase Analytics

    }


}
