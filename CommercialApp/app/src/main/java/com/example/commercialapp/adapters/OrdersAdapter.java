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
        holder.textViewAcKey.setText(currentOrder.getAcKey());
        holder.textViewDeliverPlace.setText(currentOrder.getAcReceiver());
        holder.textViewHistoryDate.setText(currentOrder.getAdDate());
        holder.textViewHistoryPrice.setText(currentOrder.getAnForPay());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setOrders(List<OrderHistoryModel> orders) {
        this.orders = orders;
    }

    class OrderHolder extends RecyclerView.ViewHolder {

        TextView textViewAcKey;
        TextView textViewHistoryDate;
        TextView textViewDeliverPlace;
        TextView textViewHistoryPrice;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);

            textViewAcKey = itemView.findViewById(R.id.text_view_history_acKey);
            textViewHistoryDate = itemView.findViewById(R.id.text_view_history_date);
            textViewDeliverPlace = itemView.findViewById(R.id.text_view_history_deliver_place);
            textViewHistoryPrice = itemView.findViewById(R.id.text_view_history_cena);

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
