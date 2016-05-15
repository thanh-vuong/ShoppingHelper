package edu.quincycollege.csi257.shoppinghelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.UUID;

/*
 * Course: CSI257 Android Development
 * Name: Thanh Vuong
 *
 */

/**
 * Activity for searching and adding item to shopping list
 *
 *
 */

public class ItemSearchActivity extends AppCompatActivity {
    // Key for pack/unpack item UUID
    private static final String EXTRA_ITEM_ID = "edu.quincycollege.csi257.shoppinghelper.item_uuid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /**
     * Method creates intent with UUID of item.
     */
    public static Intent createIntentWithUuid(Context context,
                                              UUID id)
    {
        Intent intent = new Intent(context,
                                   ItemSearchActivity.class);
        intent.putExtra(EXTRA_ITEM_ID,
                        id);
        return intent;
    }
}
