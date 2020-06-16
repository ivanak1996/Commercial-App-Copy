package com.example.commercialapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.commercialapp.R;
import com.example.commercialapp.models.orderHistoryModels.OrderHistoryModel;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderHolder> {

    private List<OrderHistoryModel> orders = new ArrayList<>();
    private OrdersAdapterItemClickListener listener;

    public List<OrderHistoryModel> getOrders() {
        return orders;
    }

    public interface OrdersAdapterItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OrdersAdapterItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_item, parent, false);
        return new OrderHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        OrderHistoryModel currentOrder = orders.get(position);
        holder.textViewName.setText(currentOrder.getBuyer());
        holder.textViewPrice.setText(currentOrder.getPrice());
        holder.textViewDate.setText(currentOrder.getDate());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setOrders(List<OrderHistoryModel> orders) {
        this.orders = orders;
    }

    class OrderHolder extends RecyclerView.ViewHolder {

        TextView textViewDate;
        TextView textViewName;
        TextView textViewPrice;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);

            textViewDate = itemView.findViewById(R.id.text_view_order_date);
            textViewName = itemView.findViewById(R.id.text_view_order_name);
            textViewPrice = itemView.findViewById(R.id.text_view_order_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onClick(position);
                        }
                    }
                }
            });
        }
    }
}
