package com.example.commercialapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.commercialapp.R;
import com.example.commercialapp.models.orderHistoryModels.ProductHistoryModel;

import java.util.ArrayList;
import java.util.List;

public class OrderedProductsAdapter extends RecyclerView.Adapter<OrderedProductsAdapter.ProductHolder>{
    private List<ProductHistoryModel> products = new ArrayList<>();

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ordered_product_list_item, parent, false);
        return new ProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        ProductHistoryModel currentProduct = products.get(position);
        holder.textViewName.setText(currentProduct.getAcName());
        holder.textViewPrice.setText(currentProduct.getAnPrice());
        holder.textViewDate.setText(currentProduct.getAcIdent());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<ProductHistoryModel> products) {
        this.products = products;
    }

    class ProductHolder extends RecyclerView.ViewHolder {

        TextView textViewDate;
        TextView textViewName;
        TextView textViewPrice;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);

            textViewDate = itemView.findViewById(R.id.text_view_ordered_item_quant);
            textViewName = itemView.findViewById(R.id.text_view_ordered_product_name);
            textViewPrice = itemView.findViewById(R.id.text_view_ordered_item_price);
        }
    }
}
