package com.example.commercialapp.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.commercialapp.R;
import com.example.commercialapp.roomDatabase.products.Product;
import com.example.commercialapp.roomDatabase.products.ProductViewModel;
import com.example.commercialapp.utils.ProductKeyboard;


import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private Product selectedProduct = null;

    public void saveProductState(ProductViewModel productViewModel, long orderId) {
        if (selectedProduct != null)
            productViewModel.insert(selectedProduct, orderId);
    }

    public void chooseProduct(int position, ProductViewModel productViewModel, long orderId) {
        Product chosenProduct = products.get(position);

        if (selectedProduct != null) {//if we need to commit changes in product count
            saveProductState(productViewModel, orderId);
            if (selectedProduct != chosenProduct) {
                selectedProduct = chosenProduct;
            } else {
                selectedProduct = null;
            }
        } else {// if no other product has been in focus before click
            selectedProduct = chosenProduct;
        }

        notifyDataSetChanged();
    }

    public interface ProductAdapterItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(ProductAdapterItemClickListener listener) {
        this.listener = listener;
    }

    private List<Product> products = new ArrayList<>();
    private ProductAdapterItemClickListener listener;

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);
        return new ProductHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product currentProduct = products.get(position);
        holder.textViewTitle.setText(currentProduct.getC());
        holder.textViewPrice.setText("cena: " + currentProduct.getP() + " RSD");
        holder.textViewRabat.setText("rabat: " + currentProduct.getR() + " %");

        if (currentProduct.getQuantity() == 0) {
            holder.textViewQuantity.setText("");
            holder.textViewTotalPrice.setText("");
        } else {
            holder.textViewQuantity.setText(currentProduct.getQuantityAsString() + " " + currentProduct.getE());
            holder.textViewTotalPrice.setText(currentProduct.calcPriceWithRabatAsString());
        }

        if (currentProduct == selectedProduct) {
            holder.wrapper.setBackgroundColor(Color.rgb(229, 255, 204));
            holder.keyboardLayout.setVisibility(View.VISIBLE);
            holder.keyboardLayout.removeAllViews();
            holder.keyboard = new ProductKeyboard(holder.keyboardLayout.getContext(), products.get(position), holder.keyboardLayout, holder.textViewTotalPrice);
        } else {
            holder.keyboardLayout.setVisibility(View.GONE);
            holder.wrapper.setBackgroundColor(Color.rgb(255, 255, 255));
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void addProducts(List<Product> products) {
        this.products.addAll(products);
        notifyDataSetChanged();
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        if (selectedProduct != null) {
            for (Product p : products) {
                if (selectedProduct.getB().equals(p.getB())) {
                    selectedProduct = p;
                    return;
                }
            }
            selectedProduct = null;
        }
        notifyDataSetChanged();
        //this.selectedProduct = null;
        //notifyDataSetChanged();
    }

    public Product getProduct(int position) {
        try {
            return this.products.get(position);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    class ProductHolder extends RecyclerView.ViewHolder {

        private CardView wrapper;
        private TextView textViewTitle;
        private TextView textViewQuantity;
        private TextView textViewPrice;
        private TextView textViewRabat;
        private TextView textViewTotalPrice;
        private LinearLayout keyboardLayout;
        private ProductKeyboard keyboard;

        public ProductHolder(@NonNull View itemView, final ProductAdapterItemClickListener listener) {
            super(itemView);
            wrapper = itemView.findViewById(R.id.cardview_wrapper);
            textViewTitle = itemView.findViewById(R.id.text_view_productName);
            textViewQuantity = itemView.findViewById(R.id.text_view_productQuantity);
            textViewPrice = itemView.findViewById(R.id.text_view_price);
            textViewRabat = itemView.findViewById(R.id.text_view_rabat);
            textViewTotalPrice = itemView.findViewById(R.id.text_view_price_total);
            keyboardLayout = itemView.findViewById(R.id.item_keyboard);

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
