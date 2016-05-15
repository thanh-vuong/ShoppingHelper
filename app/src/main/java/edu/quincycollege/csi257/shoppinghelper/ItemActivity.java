package edu.quincycollege.csi257.shoppinghelper;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.UUID;

/*
 * Course: CSI257 Android Development
 * Name: Thanh Vuong
 *
 */

/**
 * Activity hosting an item details fragment.
 */
public class ItemActivity
    extends AppCompatActivity
    implements ItemDetailsFragment.OnUpcScannedListener
{
    // Key for pack/unpack item UUID
    private static final String EXTRA_ITEM_ID = "edu.quincycollege.csi257.shoppinghelper.item_uuid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
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

        UUID id = (UUID)getIntent().getSerializableExtra(EXTRA_ITEM_ID);
        ItemDetailsFragment itemDetailsFragment = ItemDetailsFragment.newInstance(id);
        setFragment(itemDetailsFragment);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        frag.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onUpcScanned(String upc) {
        Toast.makeText(this, "scanned upc = " + upc, Toast.LENGTH_SHORT).show();
    }

    private void setFragment(android.support.v4.app.Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(R.id.fragment_container) == null)
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
    }

    /**
     * Method creates intent with UUID of item.
     */
    public static Intent createIntentWithUuid(Context context,
                                              UUID id)
    {
        Intent intent = new Intent(context,
                ItemActivity.class);
        intent.putExtra(EXTRA_ITEM_ID,
                id);
        return intent;
    }
}
