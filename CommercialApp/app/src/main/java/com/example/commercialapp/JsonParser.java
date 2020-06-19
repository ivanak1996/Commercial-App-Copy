package com.example.commercialapp;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import org.json.*;

import com.example.commercialapp.models.LoginUserResult;
import com.example.commercialapp.models.orderHistoryModels.OrderHistoryModel;
import com.example.commercialapp.models.OrderModel;
import com.example.commercialapp.models.ProductGroupModel;
import com.example.commercialapp.models.orderHistoryModels.ProductHistoryModel;
import com.example.commercialapp.roomDatabase.deliveryPlaces.DeliveryPlace;
import com.example.commercialapp.roomDatabase.products.Product;
import com.example.commercialapp.roomDatabase.user.User;

import android.util.Log;

// TODO: think of a more intelligent way to generate query string
public class JsonParser {

    final static String TAG = "JsonParser.java";
    final static String API_URL = "http://89.216.122.162:8080/tkomserver/jsonData/api";
    public final static int ID_PRODUCT_NOT_SAVED = -1;
    static JSONObject jObj = null;
    static String json = "";

    public static boolean sendOrderData(String userEmail, String userPassword, OrderModel orderModel) {

        String charset = "UTF-8";
        String action = "order";
        String data;
        String query;

        try {
            JSONObject jsonData = orderModel.toJSON();
            data = jsonData.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

        try {
            query = String.format("userEmail=%s&userPass=%s&action=%s&data=%s",
                    URLEncoder.encode(userEmail, charset),
                    URLEncoder.encode(userPassword, charset),
                    URLEncoder.encode(action, charset),
                    URLEncoder.encode(data, charset));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }

        JSONObject result = getJSONFromUrlQuery(API_URL, query);

        try {
            if (result != null && result.get("acKey") != null) return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String getJSONFromUrlStrQuery(String url, String q) throws IOException {
        String charset = "UTF-8";
        String query = q;

        Log.d("url", query);

        String urlQuery = url;

        URLConnection connection = new URL(urlQuery).openConnection();
        connection.setDoOutput(true); // Triggers POST.
        connection.setRequestProperty("Accept-Charset", charset);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        OutputStream output = connection.getOutputStream();
        output.write(query.getBytes(charset));

        InputStream is = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 1024 * 100);
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        is.close();
        json = sb.toString();

        return json;
    }

    public static JSONObject getJSONFromUrlQuery(String url, String q) {

        try {
            json = getJSONFromUrlStrQuery(url, q);
            Log.e(TAG, "Error String" + json);
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing data " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // return JSON String
        return jObj;
    }

    public static List<ProductGroupModel> getProductGroupsFromApi(String userEmail, String userPassword) {
        ArrayList<ProductGroupModel> productGroups = new ArrayList<>();

        String charset = "UTF-8";
        String action = "groups";
        String query;

        try {
            query = String.format("userEmail=%s&userPass=%s&action=%s",
                    URLEncoder.encode(userEmail, charset),
                    URLEncoder.encode(userPassword, charset),
                    URLEncoder.encode(action, charset));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return productGroups;
        }

        JSONObject result = getJSONFromUrlQuery(API_URL, query);

        if (result != null) {
            try {
                JSONArray list = result.getJSONArray("groups");
                for (int j = 0; j < list.length(); j++) {
                    JSONObject o = list.getJSONObject(j);
                    String a = o.getString("a").trim();
                    String b = o.getString("b");
                    productGroups.add(new ProductGroupModel(a, b.isEmpty() ? "Izaberite grupu prozvoda" : b));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return productGroups;

    }

    public static List<Product> getProductsFromApi(String userEmail, String userPassword, String keyword, String classif, int fromRows, int toRows) {

        ArrayList<Product> productModels = new ArrayList<>();

        String charset = "UTF-8";
        String action = "products";
        String query;

        try {
            query = String.format("userEmail=%s&userPass=%s&action=%s&data=%%25%s%%25&classif=%s&fromRows=%s&toRows=%s",
                    URLEncoder.encode(userEmail, charset),
                    URLEncoder.encode(userPassword, charset),
                    URLEncoder.encode(action, charset),
                    URLEncoder.encode(keyword, charset),
                    URLEncoder.encode(classif, charset),
                    URLEncoder.encode("" + fromRows, charset),
                    URLEncoder.encode("" + toRows, charset)
            );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        JSONObject result = getJSONFromUrlQuery(API_URL, query);

        if (result != null) {
            try {
                JSONArray list = result.getJSONArray("products");
                for (int j = 0; j < list.length(); j++) {
                    JSONObject o = list.getJSONObject(j);
                    String a = o.getString("a");
                    String b = o.getString("b");
                    String c = o.getString("c");
                    String d = o.getString("d");
                    String e = o.getString("e");
                    String f = o.getString("f");
                    String v = o.getString("v");
                    String p = o.getString("p");
                    String r = o.getString("r");
                    String i = o.getString("i");
                    // TODO: edit this
                    productModels.add(new Product(ID_PRODUCT_NOT_SAVED, 0, a, b, c, d, e, f, v, p, r, i));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }
        return productModels;
    }


    public static List<ProductHistoryModel> getOrderHistoryDetailsFromApi(String userEmail, String userPassword, String orderId) {
        ArrayList<ProductHistoryModel> products = new ArrayList<>();

        String charset = "UTF-8";
        String action = "ordersitem";
        String query;

        try {
            query = String.format("userEmail=%s&userPass=%s&action=%s&data=%s",
                    URLEncoder.encode(userEmail, charset),
                    URLEncoder.encode(userPassword, charset),
                    URLEncoder.encode(action, charset),
                    URLEncoder.encode(orderId, charset));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return products;
        }

        JSONObject result = getJSONFromUrlQuery(API_URL, query);

        if (result != null) {
            try {
                JSONArray list = result.getJSONArray("ordersitem");
                for (int j = 0; j < list.length(); j++) {
                    JSONObject o = list.getJSONObject(j);
                    String acIdent = o.getString("acIdent");
                    String anPrice = o.getString("anPrice");
                    String anNo = o.getString("anNo");
                    String acName = o.getString("acName");
                    String anQty = o.getString("anQty");
                    String anVat = o.getString("anVat");
                    String acUM = o.getString("acUM");
                    products.add(new ProductHistoryModel(acIdent, anPrice, anNo, acName, anQty, anVat, acUM));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return products;
    }

    public static List<OrderHistoryModel> getOrderHistoryFromApi(String userEmail, String userPassword) {
        ArrayList<OrderHistoryModel> orderModels = new ArrayList<>();

        String charset = "UTF-8";
        String action = "orders";
        String query;

        try {
            query = String.format("userEmail=%s&userPass=%s&action=%s",
                    URLEncoder.encode(userEmail, charset),
                    URLEncoder.encode(userPassword, charset),
                    URLEncoder.encode(action, charset));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return orderModels;
        }

        JSONObject result = getJSONFromUrlQuery(API_URL, query);

        if (result != null) {
            try {
                JSONArray list = result.getJSONArray("orders");
                for (int j = 0; j < list.length(); j++) {
                    JSONObject o = list.getJSONObject(j);
                    String acReceiver = o.getString("acReceiver");
                    String adDate = o.getString("adDate");
                    String anForPay = o.getString("anForPay");
                    String acKey = o.getString("acKey");
                    orderModels.add(new OrderHistoryModel(acReceiver, adDate, anForPay, acKey));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return orderModels;
    }

    public static LoginUserResult getUserDataFromApi(String userEmail, String userPassword) {

        String charset = "UTF-8";
        String action = "login";
        String query;

        try {
            query = String.format("userEmail=%s&userPass=%s&action=%s",
                    URLEncoder.encode(userEmail, charset),
                    URLEncoder.encode(userPassword, charset),
                    URLEncoder.encode(action, charset));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        JSONObject result = getJSONFromUrlQuery(API_URL, query);

        if (result != null) {
            try {
                JSONArray list = result.getJSONArray("login");

                JSONObject o = list.getJSONObject(0);
                User u = getUserFromJSONObject(o);

                // add delivery places as well
                JSONArray deliveryPlaces = o.getJSONArray("MESTOISPORUKE");
                ArrayList<DeliveryPlace> deliveryPlacesList = new ArrayList<>();

                for (int i = 0; i < deliveryPlaces.length(); i++) {
                    JSONObject dp = deliveryPlaces.getJSONObject(i);
                    deliveryPlacesList.add(getDeliveryPlaceFromJSONObject(dp, u.getRowId()));
                }

                return new LoginUserResult(u, deliveryPlacesList);

            } catch (Exception e) {
                //TODO: handle exception
            }
        }
        return null;
    }


    private static DeliveryPlace getDeliveryPlaceFromJSONObject(JSONObject o, long userRowId) throws JSONException {
        String acName2 = o.getString("acName2");
        String acAddress = o.getString("acAddress");
        String acCity = o.getString("acCity");
        String anQId = o.getString("anQId");
        String acPost = o.getString("acPost");
        String acSubject = o.getString("acSubject");

        return new DeliveryPlace(userRowId, acName2, acAddress, acCity, anQId, acPost, acSubject);
    }

    private static User getUserFromJSONObject(JSONObject o) throws JSONException {
        String acName2 = o.getString("acName2");
        String acAddress = o.getString("acAddress");
        String acRegNo = o.getString("acRegNo");
        String acSubject = o.getString("acSubject");
        String password = o.getString("password");
        String anDoeplo = o.getString("anDoeplo");
        String acCity = o.getString("acCity");
        String anNedospelo = o.getString("anNedospelo");
        String acCode = o.getString("acCode");
        String id = o.getString("id");
        String acClerk = o.getString("acClerk");
        String acPost = o.getString("acPost");
        String email = o.getString("email");
        String anLimit = o.getString("anLimit");
        return new User(acName2, acAddress, acRegNo, acSubject, password, anDoeplo, acCity, anNedospelo, acCode, id, acClerk, acPost, email, anLimit);
    }

}
