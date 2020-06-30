package com.example.commercialapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.commercialapp.ProductListActivity;
import com.example.commercialapp.R;
import com.example.commercialapp.roomDatabase.deliveryPlaces.DeliveryPlace;
import com.example.commercialapp.roomDatabase.deliveryPlaces.DeliveryPlaceViewModel;
import com.example.commercialapp.roomDatabase.user.User;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrderExtraFragment extends Fragment {

    LinearLayout deliveryPlacesSingleChoiceLayout;
    LinearLayout deliveryPlacesLayout;

    DeliveryPlaceViewModel deliveryPlaceViewModel;
    List<DeliveryPlace> deliveryPlaces = new ArrayList<>();
    Spinner deliveryPlacesSpinner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_order_extra, container, false);
        deliveryPlacesLayout = view.findViewById(R.id.layout_delivery_place);
        deliveryPlacesLayout.setVisibility(View.GONE);

        deliveryPlacesSingleChoiceLayout = view.findViewById(R.id.layout_delivery_place_single_choice);
        deliveryPlacesSingleChoiceLayout.setVisibility(View.GONE);

        deliveryPlacesSpinner = view.findViewById(R.id.spinner_delivery_place);
        deliveryPlacesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectDeliveryPlaceFromSpinner();
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
                    deliveryPlacesSingleChoiceLayout.setVisibility(View.GONE);
                    ArrayAdapter<DeliveryPlace> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, deliveryPlaces);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    deliveryPlacesSpinner.setAdapter(spinnerArrayAdapter);
                    selectDeliveryPlaceFromSpinner();
                } else {
                    deliveryPlacesSingleChoiceLayout.setVisibility(View.VISIBLE);
                    deliveryPlacesLayout.setVisibility(View.GONE);
                    TextView textViewDeliveryPlace = view.findViewById(R.id.text_view_delivery_place);
                    String deliveryPlace = "";
                    if (deliveryPlaces.size() == 1) {
                        DeliveryPlace place = deliveryPlaces.get(0);
                        deliveryPlace += place.getAcName2();
                        selectDeliveryPlaceString(place.getAcSubject());
                    } else {
                        User user = ((ProductListActivity) getActivity()).getUser();
                        deliveryPlace += user.getAcSubject();
                        selectDeliveryPlaceString(user.getId());
                    }

                    textViewDeliveryPlace.setText(deliveryPlace);
                }
            }
        });

        TextView deliveryDateTextView = view.findViewById(R.id.text_view_delivery_date);
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0); // same for minutes and seconds
        String currDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(today.getTime());
        deliveryDateTextView.setText(currDate);

        return view;
    }

    private void selectDeliveryPlaceString(String deliveryPlace) {
        ((ProductListActivity) OrderExtraFragment.this.getActivity()).setPickedDeliveryPlace(deliveryPlace);
    }

    private void selectDeliveryPlaceFromSpinner() {
        DeliveryPlace selectedPlace = (DeliveryPlace) deliveryPlacesSpinner.getSelectedItem();
        if (selectedPlace != null) {
            ((ProductListActivity) OrderExtraFragment.this.getActivity()).setPickedDeliveryPlace(selectedPlace.getAnQId());
        }
    }
}
