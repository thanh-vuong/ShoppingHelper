package edu.quincycollege.csi257.shoppinghelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnUpcScannedListener} interface
 * to handle interaction events.
 * Use the {@link ItemDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String[] mPackageSizeUnits;
    String[] mPriceUnits;

    // References to View objects in layout
    private Spinner mPackageSizeUnitSpinner;
    private Spinner mPriceUnitSpinner;
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
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemDetailsFragment newInstance(String param1, String param2) {
        ItemDetailsFragment fragment = new ItemDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mPackageSizeUnits = getResources().getStringArray(R.array.package_size_units);
        mPriceUnits = getResources().getStringArray(R.array.price_units);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_details, container, false);

        mPackageSizeUnitSpinner = (Spinner)view.findViewById(R.id.item_package_size_unit);
        ArrayAdapter<String> packageUnitsAdapter =
                new ArrayAdapter<String>(getActivity(),
                                         android.R.layout.simple_spinner_item,
                                         mPackageSizeUnits);
        packageUnitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPackageSizeUnitSpinner.setAdapter(packageUnitsAdapter);
        mPackageSizeUnitSpinner.setOnItemSelectedListener(new OnPackageUnitSelectedListener());

        mPriceUnitSpinner = (Spinner)view.findViewById(R.id.item_total_price_unit);
        ArrayAdapter<String> priceUnitsAdapter =
                new ArrayAdapter<String>(getActivity(),
                                         android.R.layout.simple_spinner_item,
                                         mPriceUnits);
        priceUnitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPriceUnitSpinner.setAdapter(priceUnitsAdapter);
        mPriceUnitSpinner.setOnItemSelectedListener(new OnPriceUnitSelectedListener());

        mUpcText = (TextView)view.findViewById(R.id.item_upc);

        mScanButton = (Button)view.findViewById(R.id.button_upc_scan);
        mScanButton.setOnClickListener(new UpcScanOnClickListener());

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onUpcScanned(uri);
//        }
//    }

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
        mUpcText.setText(getUpcScanResult(requestCode,
                                          resultCode,
                                          data));
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

    private class OnPackageUnitSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getActivity(), "package size unit = " + mPackageSizeUnits[position], Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class OnPriceUnitSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getActivity(), "price unit = " + mPriceUnits[position], Toast.LENGTH_SHORT).show();
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

//        ToneGenerator beeper = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
//        beeper.startTone(ToneGenerator.TONE_CDMA_PIP, 150);

        return scanResult != null ?
               scanResult.getContents() :
               "";
    }
}
