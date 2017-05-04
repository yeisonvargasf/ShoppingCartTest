package co.megaterios.shoppingcarttest.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import co.megaterios.shoppingcarttest.R;
import co.megaterios.shoppingcarttest.models.Cart;
import co.megaterios.shoppingcarttest.models.Customer;
import co.megaterios.shoppingcarttest.models.CartProduct;
import co.megaterios.shoppingcarttest.models.Product;
import co.megaterios.shoppingcarttest.models.Stock;
import co.megaterios.shoppingcarttest.ui.adapter.ListProductsRecyclerViewAdapter;

public class ListProductsActivity extends AppCompatActivity implements
        ListProductsRecyclerViewAdapter.AdapterInteractionListener {

    private static final String TAG = "ListProductsActivity";
    private RecyclerView mRecList;
    private ListProductsRecyclerViewAdapter mListProductAdapter = new
            ListProductsRecyclerViewAdapter(this);
    public static Long ORDER_ID = Long.valueOf(1);

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
        this.loadDummyData();
        mListProductAdapter.addAll((ArrayList) Product.listAll(Product.class));

        ((TextView)findViewById(R.id.text_view_right_counter))
                .setText(this.loadResumeOrder());

        mRecList.setAdapter(mListProductAdapter);
    }

    private String loadResumeOrder() {
        double total = 0;
        int quantity = 0;

        Cart currentOrder = Cart.findById(Cart.class, ORDER_ID);
        for (CartProduct obj : currentOrder.getProducts()) {
            quantity += obj.getQuantityCurrentOrder();
            total += obj.getMyProduct().getPrice() * obj.getQuantityCurrentOrder();
        }

        return total + " - " + quantity;
    }

    private void loadDummyData() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Sony Xperia Z5 Premium Dual Sim International Version", 444.000, 26));
        products.add(new Product("LG Stylus 2 GSM Factory Unlocked International Model- Brown", 179.000, 38));
        products.add(new Product("BlackBerry Z30 16GB GSM 4G LTE Smartphone (Unlocked), Black", 137.000, 68));
        products.add(new Product("HTC One X9 International Version (Silver)", 299.000, 17));
        products.add(new Product("BLU Z3 M Z110X GSM Phone (Unlocked)", 15.000, 10));
        products.add(new Product("LG Nexus 5X H790 32GB GSM/CDMA Smartphone (Unlocked)", 326.000, 41));
        products.add(new Product("Samsung Galaxy Alpha G850a Smartphone (Unlocked)", 164.000, 34));
        products.add(new Product("Huawei P8 lite 16GB White International Dual Sim ALE-L21", 170.000, 28));
        products.add(new Product("Galaxy S7 Dual Sim 32GB GSM International Version (Unlocked), Pink", 535.000, 14));
        products.add(new Product("Sony Xperia XA 16GB 5-inch Smartphone, Unlocked - White", 199.000, 91));
        products.add(new Product("Samsung Galaxy Note 4 N910A Smartphone (Unlocked)", 298.000, 39));
        products.add(new Product("BLU Dash JR W D141w GSM Dual-SIM Android Smartphone (Unlocked)", 28.000, 95));
        products.add(new Product("Apple iPhone 5C 16GB GSM Smartphone (Unlocked)", 127.000, 44));
        products.add(new Product("Samsung Galaxy S6, 32GB Verizon CDMA - Black Sapphire", 249.000, 95));
        products.add(new Product("Apple iPhone 4s 8GB Unlocked Smartphone w/ 8MP Camera, White", 54.000, 22));
        products.add(new Product("Samsung Galaxy S4 I337 16GB Smartphone (Unlocked), Black", 142.000, 95));
        products.add(new Product("Motorola Moto G4 16GB Smartphone (Unlocked), Black", 178.000, 22));
        products.add(new Product("Apple iPhone 5s 16GB Smartphone (Unlocked), Gold", 117.000, 73));
        SugarRecord.saveInTx(products);

        Cart currentOrder = new Cart(ORDER_ID, new Customer("Yeison Vargas"));
        currentOrder.save();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mListProductAdapter.addAll((ArrayList) Product.listAll(Product.class));
        ((TextView)findViewById(R.id.text_view_right_counter))
                .setText(this.loadResumeOrder());
    }

    @Override
    public void onBuyProduct(Long productId) {
        Product currentProduct = Product.findById(Product.class, productId);

        Log.d(TAG, currentProduct.getName());

        if (currentProduct.getStock() < 1) {
            Toast.makeText(getApplicationContext(),
                    "Error!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Cart currentOrder = Cart.findById(Cart.class, ORDER_ID);
        Log.d(TAG, currentOrder.getId() + "");

        CartProduct productInOrder = null;
        for (CartProduct obj : currentOrder.getProducts()) {
            if (obj.getMyProduct().getId().equals(productId)) {
                productInOrder = obj;
                break;
            }
        }

        if (productInOrder == null) {
            productInOrder = new CartProduct(currentProduct, 1);
            currentOrder.getProducts().add(productInOrder);
        } else {
            productInOrder.setQuantityCurrentOrder(productInOrder.getQuantityCurrentOrder() + 1);
        }

        Log.d(TAG, currentProduct.getStock() + "");

        currentProduct.setStock(currentProduct.getStock() - 1);

        Log.d(TAG, currentProduct.getStock() + "");

        if (currentProduct.getStock() < 1) {
            this.mListProductAdapter.removeItem(currentProduct);
        }

        currentProduct.save();
        productInOrder.save();

        ((TextView)findViewById(R.id.text_view_right_counter))
                .setText(this.loadResumeOrder());
        mListProductAdapter.addAll((ArrayList) Product.listAll(Product.class));
    }

}
