package co.megaterios.shoppingcarttest.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import co.megaterios.shoppingcarttest.R;
import co.megaterios.shoppingcarttest.models.Cart;
import co.megaterios.shoppingcarttest.models.CartProduct;
import co.megaterios.shoppingcarttest.ui.adapter.ShoppingCartRecyclerViewAdapter;

import static co.megaterios.shoppingcarttest.ui.activity.ListProductsActivity.ORDER_ID;

public class ShoppingCartActivity extends AppCompatActivity implements
        ShoppingCartRecyclerViewAdapter.AdapterShoppingCartInteractionListener {

    private static final String TAG = "ShoppingCartActivity";
    private RecyclerView mRecList;
    private ShoppingCartRecyclerViewAdapter mListShoppingCartProductsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        this.mListShoppingCartProductsAdapter = new ShoppingCartRecyclerViewAdapter(this);

        mRecList = (RecyclerView) findViewById(R.id.recycler_view_list_products_shopping_cart);
        mRecList.setHasFixedSize(true);

        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecList.setLayoutManager(llm);

        // Load data here! :)
        mListShoppingCartProductsAdapter.addAll(Cart.findById(Cart.class, ORDER_ID).getProducts());

        //ShoppingCartApiAdapter.getApiService().getOrder(ListProductsActivity.ORDER_ID, this);

        mRecList.setAdapter(mListShoppingCartProductsAdapter);
    }

    @Override
    public void onDeleteProduct(Long orderProductId) {
        Cart currentOrder = Cart.findById(Cart.class, ORDER_ID);

        CartProduct productInOrder = null;
        for (CartProduct obj : currentOrder.getProducts()) {
            if (obj.getId().equals(orderProductId)) {
                productInOrder = obj;
                Log.d(TAG, obj.getMyProduct().getName());
                break;
            }
        }

        if (productInOrder == null || productInOrder.getQuantityCurrentOrder() < 1) {
            Toast.makeText(getApplicationContext(),
                    "Error!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        productInOrder.setQuantityCurrentOrder(productInOrder.getQuantityCurrentOrder() - 1);

        productInOrder.getMyProduct().setStock(productInOrder.getMyProduct()
                .getStock() + 1);


        if (productInOrder.getQuantityCurrentOrder() < 1) {
            CartProduct.findById(CartProduct.class, productInOrder.getId()).delete();
            this.mListShoppingCartProductsAdapter.removeItem(productInOrder);

        }

        productInOrder.getMyProduct().save();
        productInOrder.save();

        mListShoppingCartProductsAdapter.addAll(Cart.findById(Cart.class, ORDER_ID).getProducts());

    }

}
