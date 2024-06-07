package com.example.foodee.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodee.R;
import com.example.foodee.activity.MainActivity;
import com.example.foodee.model.Cart;
import com.example.foodee.model.Food;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<Cart> lstCart;
    private Context context;
    public CartAdapter(List<Cart> lstCart, Context context) {
        this.lstCart = lstCart;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cart, parent, false);
        //CartAdapter.ViewHolder holder = new CartAdapter.ViewHolder(v);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart ct = lstCart.get(position);
        if (ct == null)
            return;
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvPrice.setText(decimalFormat.format(ct.getPrice() )+ "");
//        holder.ivPicture.setImageResource(ct.getImageFood());
//        Picasso.get().load(link).into(rImage);
        Picasso.with(context).load(ct.getImageFood()).into(holder.ivPicture);
        holder.tvName.setText(ct.getFoodName());
        holder.btnAmounCart.setText(ct.getAmount() + "");
        if (ct.getAmount() == 1) {
            holder.btnMinusAmount.setVisibility(View.INVISIBLE);
        }
        if (ct.getAmount() == 20) {
            holder.btnAddAmount.setVisibility(View.INVISIBLE);
        }
        holder.btnAddAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = 0;
                for (int i = 0; i < MainActivity.arrayCart.size(); i++) {
                    if (MainActivity.arrayCart.get(i).getFoodName().equals(holder.tvName.getText().toString())) {
                        price = MainActivity.arrayCart.get(i).getPrice() / MainActivity.arrayCart.get(i).getAmount();
                    }
                }
                int newAmount = Integer.parseInt(holder.btnAmounCart.getText().toString()) + 1;
                if (newAmount == 20) {
                    holder.btnAddAmount.setVisibility(View.INVISIBLE);
                }
                else {
                    holder.btnMinusAmount.setVisibility(View.VISIBLE);
                    holder.btnAddAmount.setVisibility(View.VISIBLE);
                }
                holder.btnAmounCart.setText(newAmount + "");
                int newPrice = newAmount * price;
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                holder.tvPrice.setText(decimalFormat.format(newPrice) + "");
                for (int i = 0; i < MainActivity.arrayCart.size(); i++) {
                    if (MainActivity.arrayCart.get(i).getFoodName().equals(holder.tvName.getText().toString())) {
                        MainActivity.arrayCart.get(i).setPrice(newPrice);
                        MainActivity.arrayCart.get(i).setAmount(newAmount);
                    }
                }
                com.example.foodee.activity.Cart.setTotalPrice();
            }
        });
        holder.btnMinusAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = 0;
                for (int i = 0; i < MainActivity.arrayCart.size(); i++) {
                    if (MainActivity.arrayCart.get(i).getFoodName().equals(holder.tvName.getText().toString())) {
                        price = MainActivity.arrayCart.get(i).getPrice() / MainActivity.arrayCart.get(i).getAmount();
                    }
                }
                int newAmount = Integer.parseInt(holder.btnAmounCart.getText().toString()) - 1;
                if (newAmount == 1) {
                    holder.btnMinusAmount.setVisibility(View.INVISIBLE);
                }
                else {
                    holder.btnAddAmount.setVisibility(View.VISIBLE);
                    holder.btnMinusAmount.setVisibility(View.VISIBLE);
                }
                holder.btnAddAmount.setVisibility(View.VISIBLE);
                holder.btnAmounCart.setText(newAmount + "");
                int newPrice = newAmount * price;
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                holder.tvPrice.setText(decimalFormat.format(newPrice) + "");
                for (int i = 0; i < MainActivity.arrayCart.size(); i++) {
                    if (MainActivity.arrayCart.get(i).getFoodName().equals(holder.tvName.getText().toString())) {
                        MainActivity.arrayCart.get(i).setPrice(newPrice);
                        MainActivity.arrayCart.get(i).setAmount(newAmount);
                    }
                }
                com.example.foodee.activity.Cart.setTotalPrice();
            }
        });
        holder.ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(context);
                b.setTitle("Xác nhận xóa món ăn");
                b.setMessage("Bạn có đồng ý xóa món " + holder.tvName.getText().toString() + " trong giỏ hàng không ?");
                b.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        for (int i = 0; i < MainActivity.arrayCart.size(); i++){
                            if (MainActivity.arrayCart.get(i).getFoodName().equals(holder.tvName.getText().toString())) {
                                MainActivity.arrayCart.remove(MainActivity.arrayCart.get(i));
                                com.example.foodee.activity.Cart.cartAdapter.notifyDataSetChanged();
                                com.example.foodee.activity.Cart.setTotalPrice();
                                if (MainActivity.arrayCart.size() <= 0) {
                                    com.example.foodee.activity.Cart.tvEmpty.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                });
                b.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog al = b.create();
                al.show();
            }
        });
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
        CardView layout_cart;
        ImageView ivPicture;
        TextView tvName, tvPrice;
        Button btnMinusAmount, btnAddAmount, btnAmounCart;
        ImageButton ibDelete;
        public ViewHolder (@NonNull View itemView) {
            super (itemView);
            tvName = itemView.findViewById(R.id.tvFoodName);
            tvPrice = itemView.findViewById(R.id.tvFoodPrice);
            ivPicture = itemView.findViewById(R.id.ivImage);
            layout_cart = itemView.findViewById(R.id.layout_cart);
            btnMinusAmount = itemView.findViewById(R.id.btnMinusAmount);
            btnAddAmount = itemView.findViewById(R.id.btnAddAmount);
            btnAmounCart = itemView.findViewById(R.id.btnAmountCart);
            ibDelete = itemView.findViewById(R.id.ibDelete);
        }
    }
}