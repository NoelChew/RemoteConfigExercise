package com.noelchew.remoteconfigsample;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Context context;

    FloatingActionButton fabAddToCart;
    Button btnAddToCart;

    boolean useFAB;

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

        useFAB = getUseFABRemoteConfigParamValue();

        if (useFAB) {
            btnAddToCart.setVisibility(View.GONE);
            fabAddToCart.setVisibility(View.VISIBLE);
        } else {
            btnAddToCart.setVisibility(View.VISIBLE);
            fabAddToCart.setVisibility(View.GONE);
        }
    }

    private boolean getUseFABRemoteConfigParamValue() {
        boolean useFAB = false;
        // TODO: get RemoteConfig param

        return useFAB;
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
        // TODO: create User Property and send Event

    }


}
