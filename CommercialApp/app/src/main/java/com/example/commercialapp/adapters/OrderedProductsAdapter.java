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
        holder.textViewAcName.setText(currentProduct.getAcName());
        holder.textViewAnQty.setText(currentProduct.getAnQty());
        holder.textViewAcIdent.setText(currentProduct.getAcIdent());
        holder.textViewAnPrice.setText(currentProduct.getAnPrice());
        holder.textViewAnVat.setText(currentProduct.getAnVat());
        //holder.textViewAcIdent.setText(currentProduct.getAcIdent());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<ProductHistoryModel> products) {
        this.products = products;
    }

    class ProductHolder extends RecyclerView.ViewHolder {

        TextView textViewAcName;
        TextView textViewAnQty;
        TextView textViewAcIdent;
        TextView textViewAnPrice;
        TextView textViewAnVat;
        TextView textViewTotal;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);

            textViewAcName = itemView.findViewById(R.id.text_view_ordered_product_acName);
            textViewAnQty = itemView.findViewById(R.id.text_view_ordered_item_anQty);
            textViewAcIdent = itemView.findViewById(R.id.text_view_ordered_item_acIdent);
            textViewAnPrice = itemView.findViewById(R.id.text_view_ordered_item_anPrice);
            textViewAnVat = itemView.findViewById(R.id.text_view_ordered_item_anVat);
            textViewTotal = itemView.findViewById(R.id.text_view_ordered_item_total);
        }
    }
}
