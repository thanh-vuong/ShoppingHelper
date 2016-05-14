package edu.quincycollege.csi257.shoppinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ShoppingAdapter mShoppingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
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


        mRecyclerView = (RecyclerView)findViewById(R.id.shopping_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateUI() {
        ShoppingList closet = ShoppingList.get(this);
        List<Item> clothingList = closet.getShoppingList();
        if (mShoppingAdapter == null) {
            mShoppingAdapter = new ShoppingAdapter(clothingList);
            mRecyclerView.setAdapter(mShoppingAdapter);
        }
        else {
            mShoppingAdapter.notifyDataSetChanged();
        }
    }

    private class ItemHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Item mShoppingItem;
        private TextView mItemBrand;
        private TextView mItemProduct;
        private TextView mItemUnit;
        private TextView mItemPackage;
        private TextView mItemPrice;

        public ItemHolder(View itemView) {
            super(itemView);
            //mItemName = (TextView)itemView;
            mItemBrand = (TextView)itemView.findViewById(R.id.list_item_brand);
            mItemProduct = (TextView)itemView.findViewById(R.id.list_item_product);
            mItemUnit = (TextView)itemView.findViewById(R.id.list_item_unit);
            mItemPackage = (TextView)itemView.findViewById(R.id.list_item_package);
            mItemPrice = (TextView)itemView.findViewById(R.id.list_item_price);

            itemView.setOnClickListener(this);
        }

        public void bindClothingItem(Item item) {
            mShoppingItem = item;
            mItemBrand.setText(item.getBrand());
            mItemProduct.setText(item.getProduct());
            mItemUnit.setText(Integer.toString(item.getPriceUnit()));
            mItemPackage.setText(Double.toString(item.getPackageSize()) + " " + item.getPackageSizeUnit());
            mItemPrice.setText("$" + Double.toString(item.getPrice()));
        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(getActivity(), mClothingItem.getName(), Toast.LENGTH_SHORT).show();
            //Intent intent = ClothingPagerActivity.newIntent(getActivity(), mClothingItem.getId());
            //startActivity(intent);

            startActivity(new Intent(ShoppingListActivity.this, ItemActivity.class));
        }
    }

    private class ShoppingAdapter extends RecyclerView.Adapter<ItemHolder> {
        private List<Item> mShoppingList;

        public ShoppingAdapter(List<Item> clothingList) {
            mShoppingList = clothingList;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater =  LayoutInflater.from(parent.getContext());
            //View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            // for custom recycle adapter layout
            View view = layoutInflater.inflate(R.layout.viewholder_item, parent, false);
            return new ItemHolder(view);
        }

        @Override
        public int getItemCount() {
            return mShoppingList.size();
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            Item item = mShoppingList.get(position);
            //holder.mItemName.setText(item.getName());
            holder.bindClothingItem(item);
        }
    }

}