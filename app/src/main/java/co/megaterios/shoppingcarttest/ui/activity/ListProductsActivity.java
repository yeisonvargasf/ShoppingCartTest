package co.megaterios.shoppingcarttest.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Map;

import co.megaterios.shoppingcarttest.R;
import co.megaterios.shoppingcarttest.service.ShoppingCartApiAdapter;
import co.megaterios.shoppingcarttest.service.model.ProductsResponse;
import co.megaterios.shoppingcarttest.ui.adapter.ListProductsRecyclerViewAdapter;
import co.megaterios.shoppingcarttest.util.Helpers;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ListProductsActivity extends AppCompatActivity implements
        ListProductsRecyclerViewAdapter.AdapterInteractionListener, Callback<ProductsResponse> {

    private static final String TAG = "ListProductsActivity";
    private RecyclerView mRecList;
    private ListProductsRecyclerViewAdapter mListProductAdapter = new
            ListProductsRecyclerViewAdapter(this);
    public static String ORDER_ID = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products);
        ((TextView)findViewById(R.id.text_view_right_counter))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent showShoppingCart = new Intent(getApplicationContext(),
                                ShoppingCartActivity.class);
                        startActivity(showShoppingCart);
                    }
                });

        mRecList = (RecyclerView) findViewById(R.id.recycler_view_list_products);
        mRecList.setHasFixedSize(true);

        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecList.setLayoutManager(llm);

        // API Request here! :)
        ShoppingCartApiAdapter.getApiService().getProducts(this);

        mRecList.setAdapter(mListProductAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ShoppingCartApiAdapter.getApiService().getProducts(this);
    }

    @Override
    public void onBuyProduct(String productId) {
        ShoppingCartApiAdapter.getApiService().addProductToOrder(ListProductsActivity.ORDER_ID,
                productId, new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        Log.i(TAG, "Success adding the product.");
                        ShoppingCartApiAdapter.getApiService().getProducts(ListProductsActivity.this);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(),
                                "¡Sin conexión a Internet!, vuelve a intentarlo.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void success(ProductsResponse productsResponse, Response response) {
        this.mListProductAdapter.addAll(productsResponse.getProducts());
        ShoppingCartApiAdapter.getApiService().getOrderResume(ListProductsActivity.ORDER_ID,
                new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        Map jsonJavaRootObject = new Gson().fromJson(
                                Helpers.getResponseBody(response), Map.class);

                        ((TextView)findViewById(R.id.text_view_right_counter))
                                .setText(jsonJavaRootObject.get("total")
                                        + " - " + jsonJavaRootObject.get("quantity"));

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(),
                                "¡Sin conexión a Internet!, vuelve a intentarlo.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(getApplicationContext(),
                "¡Sin conexión a Internet!, vuelve a intentarlo.",
                Toast.LENGTH_SHORT).show();
    }
}
