package com.example.commercialapp.fragments.orderHistoryFragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.commercialapp.JsonParser;
import com.example.commercialapp.ProductListActivity;
import com.example.commercialapp.R;
import com.example.commercialapp.adapters.OrdersAdapter;
import com.example.commercialapp.asyncResponses.GetOrdersAsyncResponse;
import com.example.commercialapp.models.orderHistoryModels.OrderHistoryModel;
import com.example.commercialapp.roomDatabase.user.User;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryFragment extends Fragment implements GetOrdersAsyncResponse {

    private User user;
    private OrdersAdapter ordersAdapter;
    private RecyclerView ordersHistoryRecyclerView;
    private TextView noDataInRecyclerView;

    public OrderHistoryFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.history_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.history_menu_title);
        TextView titleTextView = (TextView) menuItem.getActionView();
        titleTextView.setPadding(0, 0, 50, 0);
        titleTextView.setText(user.getAcName2());

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);

        user = ((ProductListActivity) getActivity()).getUser();
        new GetOrderHistoryFromApiAsyncTask(this, user.getEmail(), user.getPassword()).execute();

        // display for empty basket
        noDataInRecyclerView = view.findViewById(R.id.empty_order_history);

        ordersHistoryRecyclerView = view.findViewById(R.id.recycler_view_order_history);
        ordersHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ordersHistoryRecyclerView.setHasFixedSize(true);

        ordersAdapter = new OrdersAdapter();
        ordersAdapter.setOnItemClickListener(new OrdersAdapter.OrdersAdapterItemClickListener() {
            @Override
            public void onClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("acKey", ordersAdapter.getOrders().get(position).getAcKey());
                Navigation.findNavController(OrderHistoryFragment.this.getActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.orderHistoryDetailsFragment, bundle);
            }
        });
        ordersHistoryRecyclerView.setAdapter(ordersAdapter);
        return view;
    }

    @Override
    public void finish(List<OrderHistoryModel> orders) {
        ordersAdapter.setOrders(orders);
        ordersAdapter.notifyDataSetChanged();
    }

    private static class GetOrderHistoryFromApiAsyncTask extends AsyncTask<Void, Void, List<OrderHistoryModel>> {

        private GetOrdersAsyncResponse delegate;
        private String username;
        private String password;

        public GetOrderHistoryFromApiAsyncTask(GetOrdersAsyncResponse delegate, String username, String password) {
            this.delegate = delegate;
            this.username = username;
            this.password = password;
        }

        @Override
        protected List<OrderHistoryModel> doInBackground(Void... voids) {
            return JsonParser.getOrderHistoryFromApi(username, password);
        }

        @Override
        protected void onPostExecute(List<OrderHistoryModel> serverModels) {
            delegate.finish((serverModels != null) ? serverModels : new ArrayList<OrderHistoryModel>());
        }
    }
}
