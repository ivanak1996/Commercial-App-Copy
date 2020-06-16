package com.example.commercialapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.commercialapp.R;
import com.example.commercialapp.roomDatabase.deliveryPlaces.DeliveryPlace;
import com.example.commercialapp.roomDatabase.deliveryPlaces.DeliveryPlaceViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrderExtraFragment extends Fragment {

    DeliveryPlaceViewModel deliveryPlaceViewModel;
    List<DeliveryPlace> deliveryPlaces = new ArrayList<>();
    LinearLayout deliveryPlacesLayout;
    Spinner deliveryPlacesSpinner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_extra, container, false);
        deliveryPlacesLayout = view.findViewById(R.id.layout_delivery_place);
        deliveryPlacesLayout.setVisibility(View.GONE);

        deliveryPlacesSpinner = view.findViewById(R.id.spinner_delivery_place);
        deliveryPlacesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        deliveryPlaceViewModel = ViewModelProviders.of(this).get(DeliveryPlaceViewModel.class);
        deliveryPlaceViewModel.getAllDeliveryPlaces().observe(OrderExtraFragment.this.getActivity(), new Observer<List<DeliveryPlace>>() {
            @Override
            public void onChanged(List<DeliveryPlace> deliveryPlaces) {
                OrderExtraFragment.this.deliveryPlaces = deliveryPlaces;
                if (deliveryPlaces.size() > 1) {
                    deliveryPlacesLayout.setVisibility(View.VISIBLE);
                    ArrayAdapter<DeliveryPlace> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, deliveryPlaces);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    deliveryPlacesSpinner.setAdapter(spinnerArrayAdapter);
                }
            }
        });

        return view;
    }
}
