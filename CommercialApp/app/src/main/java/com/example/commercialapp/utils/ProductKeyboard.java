package com.example.commercialapp.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.example.commercialapp.R;
import com.example.commercialapp.roomDatabase.products.Product;
import com.example.commercialapp.roomDatabase.products.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.FILL_PARENT;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;


public class ProductKeyboard {

    //private LinearLayout container;

    private LinearLayout numericContainer;
    private List<Button> numericButtons = new ArrayList<>();
    private Button decimalPointButton;

    private LinearLayout shortcutsContainer;
    private ImageView minusButton;
    private ImageView plusButton;
    private ImageView xButton;
    private ImageView saveButton;
    private ImageView deleteButton;

    private TextView resultTextView;
    private TextView totalPriceTextView;

    private Product product = null;
    private boolean isDecimal = false;
    private boolean decimalModeOn = false;
    private boolean decimalModeWasChanged = false;

    public ProductKeyboard(final Context context, Product product, LinearLayout container, TextView totalPriceTextView) {
        //this.container = container;

        this.totalPriceTextView = totalPriceTextView;
        this.product = product;
        double quantity = product.getQuantity();
        int quantityDecimal = product.getQuantityInt();
        if (quantity - quantityDecimal > 0.00) {
            isDecimal = true;
        }
        decimalModeOn = false;

        numericRowSetup(context);
        shortcutRowSetup(context);
        container.addView(shortcutsContainer);
        container.addView(numericContainer);

        setResultTextView();
    }

    private void setProduct(Product product) {
        this.product = product;
        setResultTextView();
    }

    public void saveProductState(ProductViewModel productViewModel, long orderId) {
        if (product != null)
            productViewModel.insert(product, orderId);
    }

    private void shortcutRowSetup(Context context) {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(MATCH_PARENT, 0);
        p.weight = 2;
        shortcutsContainer = new LinearLayout(context);
        shortcutsContainer.setLayoutParams(p);
        shortcutsContainer.setOrientation(LinearLayout.HORIZONTAL);
        shortcutsContainer.setGravity(Gravity.CENTER_VERTICAL);

        minusButton = generateImageButton(context, R.drawable.ic_remove_circle);
        resultTextView = generateTextView(context, 2);
        plusButton = generateImageButton(context, R.drawable.ic_add_circle);
        xButton = generateImageButton(context, R.drawable.ic_multi);
        saveButton = generateImageButton(context, R.drawable.ic_save);
        deleteButton = generateImageButton(context, R.drawable.ic_delete);

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product != null) {
                    double quantity = product.getQuantity();
                    if (quantity >= 1.0) {
                        product.setQuantity(quantity - 1);
                        setResultTextView();
                    }
                }
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product != null) {
                    product.setQuantity(product.getQuantity() + 1);
                    setResultTextView();
                }
            }
        });

        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product != null) {
                    product.setQuantity(product.getQuantity() + 10);
                    setResultTextView();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product != null) {
                    product.setQuantity(0);
                    if (decimalPointButton != null)
                        decimalModeReset();
                    setResultTextView();
                }
            }
        });

        shortcutsContainer.addView(deleteButton);
        shortcutsContainer.addView(minusButton);
        shortcutsContainer.addView(resultTextView);
        shortcutsContainer.addView(plusButton);
        shortcutsContainer.addView(xButton);
        shortcutsContainer.addView(saveButton);

    }

    private void decimalModeReset() {
        isDecimal = false;
        decimalModeOn = false;
        setButtonClickedAppearance(decimalPointButton, false);
    }

    private void setResultTextView() {
        String txt = "";
        if (isDecimal) {
            txt += product.getQuantity();
        } else {
            txt += product.getQuantityInt();
        }
        resultTextView.setText(txt);
        totalPriceTextView.setText(product.calcPriceWithRabatAsString());
    }

    private void numericRowSetup(Context context) {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(MATCH_PARENT, 0);
        p.weight = 4;
        numericContainer = new LinearLayout(context);
        numericContainer.setLayoutParams(p);
        numericContainer.setOrientation(LinearLayout.HORIZONTAL);

        // add numeric buttons
        for (int i = 0; i <= 9; i++) {
            final Button button = generateNewButton(context, "" + i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (product != null) {
                        int buttonValue = Integer.parseInt(((Button) v).getText().toString());
                        double quantity = product.getQuantity();
                        if (isDecimal) {
                            String resString = resultTextView.getText().toString();
                            String[] parts = resString.split("\\.");

                            String intPartString = parts[0];
                            int intPart = Integer.parseInt(parts[0]);
                            String decPartString = "";
                            if (parts.length > 1) {
                                decPartString = parts[1];
                            }

                            String decPartFinal = decPartString;
                            String intPartFinal = intPartString;

                            if (decimalModeOn) { // we are changing the decimal part
                                //intPartFinal = intPartString;
                                if (decimalModeWasChanged) {
                                    decPartFinal = "";
                                    decPartString = "";
                                }
                                if (decPartString.length() < 2)
                                    decPartFinal = decPartString + buttonValue;
                            } else { // changing the int part
                                //decPartFinal = decPartString;
                                if (decimalModeWasChanged) {
                                    intPart = 0;
                                }
                                if (intPartString.length() < 5) {
                                    intPart = intPart * 10 + buttonValue;
                                }
                                intPartFinal = "" + intPart;
                            }


                            String newResult = decPartFinal != null ? intPartFinal + "." + decPartFinal : intPartFinal;
                            quantity = Double.parseDouble(newResult);
                            decimalModeWasChanged = false;

                        } else {
                            if (String.valueOf(product.getQuantityInt()).length() < 5)
                                quantity = quantity * 10 + buttonValue;
                        }
                        product.setQuantity(quantity);
                        setResultTextView();
                    }
                }
            });
            numericButtons.add(new Button(context));
            numericContainer.addView(button);
        }

        if (product.getE().replaceAll("\\s+", "").equals("KG")) {
            // add decimal button
            decimalPointButton = generateNewButton(context, ".");
            decimalPointButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    decimalModeOn = !decimalModeOn;
                    if (!decimalModeOn) {
                        setButtonClickedAppearance(decimalPointButton, false);
                    } else {
                        setButtonClickedAppearance(decimalPointButton, true);
                    }
                    decimalModeWasChanged = true;
                    if (!isDecimal) {
                        isDecimal = true;
                        //setResultTextView();
                        resultTextView.setText(resultTextView.getText().toString() + ".");
                    }
                }
            });
            numericContainer.addView(decimalPointButton);
        }
    }

    private TextView generateTextView(Context context, int weight) {
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(0, 180);
        p.weight = weight;
        textView.setLayoutParams(p);
        textView.setText("res");
        textView.setTextSize(24.0f);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        return textView;
    }

    private ImageView generateImageButton(Context context, int resourceId) {
        ImageView imageButton = new ImageView(context);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(0, FILL_PARENT);
        p.weight = 1;
        imageButton.setLayoutParams(p);
        imageButton.setImageResource(resourceId);
        imageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageButton.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
        return imageButton;
    }

    private void setButtonClickedAppearance(Button b, boolean pressed) {
        ViewCompat.setBackgroundTintList(b, ContextCompat.getColorStateList(b.getContext(), pressed ? android.R.color.white : android.R.color.darker_gray));
    }

    private Button generateNewButton(Context context, String label) {
        Button button = new Button(context);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(0, FILL_PARENT);
        p.weight = 1;
        p.leftMargin = -6;
        p.rightMargin = -6;
        ViewCompat.setBackgroundTintList(button, ContextCompat.getColorStateList(context, android.R.color.darker_gray));
        button.setLayoutParams(p);
        button.setText(label);
        button.setTextSize(24.0f);
        return button;
    }

}
