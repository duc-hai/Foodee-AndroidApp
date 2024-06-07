package com.example.foodee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodee.R;
import com.example.foodee.activity.MainActivity;
import com.example.foodee.model.Cart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private List<Cart> lstCart;
    private Context context;

    public OrderAdapter(List<Cart> lstCart, Context context) {
        this.lstCart = lstCart;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order, parent, false);
        OrderAdapter.ViewHolder holder = new OrderAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart ct = lstCart.get(position);
        if (ct == null)
            return;
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvPrice.setText("Giá: " + decimalFormat.format(ct.getPrice()) + " VND");
        Picasso.with(context).load(ct.getImageFood()).into(holder.ivPicture);
        holder.tvName.setText(ct.getFoodName());
        holder.tvAmount.setText("Số lượng: " + ct.getAmount());
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        if (lstCart != null)
            return lstCart.size();
        return 0;
    }

    public void release(){
        context=null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPicture;
        TextView tvName, tvPrice, tvAmount;
        public ViewHolder (@NonNull View itemView) {
            super (itemView);
            tvName = itemView.findViewById(R.id.tvFoodNameOrder);
            tvPrice = itemView.findViewById(R.id.tvFoodPriceOrder);
            ivPicture = itemView.findViewById(R.id.ivImageOrder);
            tvAmount = itemView.findViewById(R.id.tvAmountCartOrder);
        }
    }
}
