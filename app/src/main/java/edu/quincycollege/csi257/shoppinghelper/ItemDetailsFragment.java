package edu.quincycollege.csi257.shoppinghelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/*
 * Course: CSI257 Android Development
 * Name: Thanh Vuong
 *
 */

/**
 * {@link Fragment} for display and edit shopping item detail.
 * Activities that contain this fragment must implement the
 * {@link OnUpcScannedListener} interface
 * to handle interaction events.
 * Use the {@link ItemDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemDetailsFragment extends Fragment {
    private static final String ARG_ITEM_ID = "edu.quincycollege.csi257.shoppinghelper.item_uuid";

    ShoppingList mShoppingList;
    Item mItem;

    ArrayList<String> mPackageSizeUnits;
    ArrayList<String> mPriceUnits;

    // References to View objects in layout
    private EditText mBrand;
    private EditText mName;
    private EditText mPackageSize;
    private Spinner mPackageSizeUnitSpinner;
    private EditText mPrice;
    private Spinner mPriceUnitSpinner;
    private TextView mUnitPrice;
    private EditText mStore;
    private TextView mUpcText;
    private Button mScanButton;

    private OnUpcScannedListener mListener;

//    public ItemDetailsFragment() {
//        // Required empty public constructor
//    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param itemId Item's UUID.
     *
     * @return A new instance of fragment ItemDetailsFragment.
     */
    public static ItemDetailsFragment newInstance(UUID itemId) {
        ItemDetailsFragment fragment = new ItemDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEM_ID, itemId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mShoppingList = ShoppingList.get(getActivity());
            UUID id = (UUID)getArguments().getSerializable(ARG_ITEM_ID);
            mItem = mShoppingList.getItem(id);
        }

        // Populate unit arrays
        mPackageSizeUnits = new ArrayList<>(Arrays.asList(
                getResources().getStringArray(R.array.package_size_units)));
        mPriceUnits = new ArrayList<>(Arrays.asList(
                getResources().getStringArray(R.array.price_units)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_details, container, false);

        mBrand = (EditText)view.findViewById(R.id.item_brand);
        mBrand.setText(mItem.getBrand());
        mBrand.addTextChangedListener(new OnTextEditListener(mBrand));

        mName = (EditText)view.findViewById(R.id.item_name);
        mName.setText(mItem.getName());
        mName.addTextChangedListener(new OnTextEditListener(mName));

        mPackageSize = (EditText)view.findViewById(R.id.item_package_size);
        mPackageSize.setText(String.valueOf(mItem.getPackageSize()));
        mPackageSize.addTextChangedListener(new OnTextEditListener(mPackageSize));

        mPackageSizeUnitSpinner = (Spinner)view.findViewById(R.id.item_package_size_unit);
        ArrayAdapter<String> packageUnitsAdapter =
                new ArrayAdapter<>(getActivity(),
                                   android.R.layout.simple_spinner_item,
                                   mPackageSizeUnits);
        packageUnitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPackageSizeUnitSpinner.setAdapter(packageUnitsAdapter);
        mPackageSizeUnitSpinner.setOnItemSelectedListener(new OnPackageUnitSelectedListener());
        mPackageSizeUnitSpinner.setSelection(mPackageSizeUnits.indexOf(mItem.getPackageSizeUnit()));

        mPrice = (EditText)view.findViewById(R.id.item_total_price);
        mPrice.setText(String.valueOf(mItem.getPrice()));
        mPrice.addTextChangedListener(new OnTextEditListener(mPrice));

        mPriceUnitSpinner = (Spinner)view.findViewById(R.id.item_total_price_unit);
        ArrayAdapter<String> priceUnitsAdapter =
                new ArrayAdapter<>(getActivity(),
                                   android.R.layout.simple_spinner_item,
                                   mPriceUnits);
        priceUnitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPriceUnitSpinner.setAdapter(priceUnitsAdapter);
        mPriceUnitSpinner.setOnItemSelectedListener(new OnPriceUnitSelectedListener());
        mPriceUnitSpinner.setSelection(mPriceUnits.indexOf(mItem.getPriceUnit()));

        mUnitPrice = (TextView)view.findViewById(R.id.item_unit_price);
        double unitPrice = mItem.getPrice() / mItem.getPackageSize();
        mUnitPrice.setText(String.valueOf(unitPrice));

        mStore = (EditText)view.findViewById(R.id.item_store);
        mStore.setText(mItem.getStore());
        mStore.addTextChangedListener(new OnTextEditListener(mStore));

        mUpcText = (TextView)view.findViewById(R.id.item_upc);
        mUpcText.setText(mItem.getUpc());

        mScanButton = (Button)view.findViewById(R.id.button_upc_scan);
        mScanButton.setOnClickListener(new UpcScanOnClickListener());

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUpcScannedListener) {
            mListener = (OnUpcScannedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnUpcScannedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Update item UPC
        String upc = getUpcScanResult(requestCode,
                                      resultCode,
                                      data);
        mUpcText.setText(upc);
        mItem.setUpc(upc);
        mShoppingList.updateItem(mItem);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnUpcScannedListener {
        // TODO: Update argument type and name
        void onUpcScanned(String upc);
    }

    private class OnTextEditListener implements TextWatcher {
        private View mView;

        public OnTextEditListener(View view) {
            mView = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            switch (mView.getId()) {
                case R.id.item_brand:
                    mItem.setBrand(s.toString());
                    break;
                case R.id.item_name:
                    mItem.setName(s.toString());
                    break;
                case R.id.item_package_size:
                    try {
                        mItem.setPackageSize(Double.valueOf(s.toString()));
                    } catch (NumberFormatException nfe) {
                        Toast.makeText(getActivity(), "Invalid value", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.item_total_price:
                    try {
                        mItem.setPrice(Double.valueOf(s.toString()));
                    } catch (NumberFormatException nfe) {
                        Toast.makeText(getActivity(), "Invalid value", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.item_store:
                    mItem.setStore(s.toString());
                    break;
            }
            mShoppingList.updateItem(mItem);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private class OnPackageUnitSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mItem.setPackageSizeUnit(mPackageSizeUnits.get(position));
            mShoppingList.updateItem(mItem);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class OnPriceUnitSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mItem.setPriceUnit(mPriceUnits.get(position));
            mShoppingList.updateItem(mItem);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class UpcScanOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // Start scanner for all known barcodes and use any camera.
            IntentIntegrator scanIntegrator = new IntentIntegrator(ItemDetailsFragment.this.getActivity());
            scanIntegrator.initiateScan();
        }
    }

    private String getUpcScanResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode,
                                                                       resultCode,
                                                                       data);

//        int volume = 100;
//        int duration = 150;
//        ToneGenerator beeper = new ToneGenerator(AudioManager.STREAM_MUSIC, volume);
//        beeper.startTone(ToneGenerator.TONE_CDMA_PIP, duration);

        return scanResult != null ?
               scanResult.getContents() :
               "";
    }


}
