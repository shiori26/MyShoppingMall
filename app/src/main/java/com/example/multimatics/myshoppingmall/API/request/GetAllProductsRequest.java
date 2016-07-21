package com.example.multimatics.myshoppingmall.API.request;

import com.example.multimatics.myshoppingmall.API.BaseApi;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PreemptiveAuthorizationHttpRequestInterceptor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Multimatics on 21/07/2016.
 */


public class GetAllProductsRequest extends BaseApi {
    private OnGetAllProductsRequestListener onGetAllProductRequestListener;
    private AsyncHttpClient client;

    public GetAllProductsRequest(){
        client = getHttpClient();
    }

    public OnGetAllProductsRequestListener getOnGetAllProductRequestListener() {
        return onGetAllProductRequestListener;
    }

    public void setOnGetAllProductRequestListener (OnGetAllProductsRequestListener onGetAllProductRequestListener){
        this.onGetAllProductRequestListener = onGetAllProductRequestListener;

    }

    @Override
    public void callApi() {
        super.callApi();
        client.get(GET_ALL_PRODUCTS, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                ArrayList<Product> list = getAllProducts(response);
                if (list != null ) {
                    if (list.size() > 0 ) {
                        getOnGetAllProductRequestListener().onGetAllProductsSuccess(list);
                    }else {
                        getOnGetAllProductRequestListener().onGetAllProductsFailure("No data found");
                    }
                }else {
                    getOnGetAllProductRequestListener().onGetAllProductsFailure("No data found");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                getOnGetAllProductRequestListener().onGetAllProductsFailure("Could not connect to server");
            }
        });

    }

    @Override
    public void cancelRequest() {
        super.cancelRequest();
        if (client != null ) {
            client.cancelAllRequests(true);
        }
    }

    private ArrayList<Product> getAllProducts(String response) {
        ArrayList<Product> list = null;
        try {
            JSONObject jsonobject = new JSONObject(response);
            if (jsonobject.getString("status").equals("200")) {
                JSONArray productItems = jsonobject.getJSONArray("products");
                if (productItems.length() > 0 ) {
                    list = new ArrayList<>();
                    Product mProduct = null;
                    for (int i = 0; i < productItems.length(); i++) {
                        JSONObject item = productItems.getJSONObject(i);
                        mProduct = new Product();
                        mProduct.setId(Long.parseLong(item.getString("product_id")));
                        mProduct.setImageUrl(item.getString("img_url"));
                        mProduct.setName(item.getString("name"));
                        mProduct.setPrice(item.getString("price"));

                        list.add(mProduct);
                    }
                }else {
                    return null;
                }
            }else {
                return null;
            }
        }catch (Exception e) {
            return null;
        }
        return list;
    }

    public interface OnGetAllProductsRequestListener{
        void onGetAllProductsSuccess(ArrayList<Product> listproduct);
        void onGetAllProductsFailure(String errorMessage);
    }
}
