package edu.quincycollege.csi257.shoppinghelper;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class ItemActivity
    extends AppCompatActivity
    implements ItemDetailsFragment.OnUpcScannedListener
{
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

        FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = ItemDetailsFragment.newInstance("nothing", "nothing");
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
//        setFragment(mItemDetailsFragment);

    }

    @Override
    public void onUpcScanned(int upc) {
        Toast.makeText(this, "scanned upc = " + upc, Toast.LENGTH_SHORT).show();
    }

//    public void setFragment(android.support.v4.app.Fragment fragment) {
//        FragmentManager fm = getSupportFragmentManager();
//        if (fm.findFragmentById(R.id.fragment_item_details_container) == null)
//            fm.beginTransaction().add(R.id.fragment_item_details_container, fragment).commit();
//    }
}
