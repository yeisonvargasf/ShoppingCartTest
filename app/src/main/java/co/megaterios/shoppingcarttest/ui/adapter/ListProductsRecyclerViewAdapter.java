package co.megaterios.shoppingcarttest.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import co.megaterios.shoppingcarttest.R;
import co.megaterios.shoppingcarttest.models.Product;

/**
 * Created by yeison on 04/05/17.
 */

public class ListProductsRecyclerViewAdapter extends
        RecyclerView.Adapter<ListProductsRecyclerViewAdapter.ProductViewHolder> {


    private static final String TAG = "ListProductsRecyclerViewAdapter";
    private ArrayList<Product> mProducts;
    private Context context;


    public ListProductsRecyclerViewAdapter(Context context) {
        this.mProducts = new ArrayList<Product>();
        this.context = context;
    }


    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Return the item view inflate for Recycler View.
        ProductViewHolder viewHolder = new ProductViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_product, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {

        final Product boundProduct = this.mProducts.get(position);

        holder.vName.setText(boundProduct.getName());
        holder.vPrice.setText(String.valueOf(boundProduct.getPrice()));
        holder.vStock.setText(String.valueOf(boundProduct.getStock()));
        holder.vBuy.setOnClickListener( new View.OnClickListener() {
                                                         @Override
                                                         public void onClick(View v) {
                                                             ((AdapterInteractionListener)context).onBuyProduct(boundProduct.getId());
                                                         }
                                                     }

        );
    }

    @Override
    public int getItemCount() {
        return this.mProducts.size();
    }

    public void addAll(@NonNull ArrayList<Product> extraProducts) {
        this.mProducts.clear();
        this.mProducts.addAll(extraProducts);
        notifyDataSetChanged();
    }

    public int indexOf(Long productId) {
        Product obj = new Product();
        obj.setId(productId);
        return this.mProducts.indexOf(obj);
    }

    public int removeItem(Product soldOutProduct) {
        int i = this.mProducts.indexOf(soldOutProduct);
        this.mProducts.remove(soldOutProduct);
        notifyDataSetChanged();

        return i;
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder {
        protected TextView vName;
        protected TextView vPrice;
        protected TextView vStock;
        protected Button vBuy;

        public ProductViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.text_view_name_product);
            vPrice = (TextView) v.findViewById(R.id.text_view_price_product);
            vStock = (TextView) v.findViewById(R.id.text_view_stock_product);
            vBuy = (Button) v.findViewById(R.id.button_buy_product);
        }

    }

    public interface AdapterInteractionListener {
        public void onBuyProduct(Long productId);
    }


}
