package com.example.foodee.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodee.activity.DetailFoodActivity;
import com.example.foodee.activity.MainActivity;
import com.example.foodee.model.Cart;
import com.example.foodee.model.Food;
import com.example.foodee.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> implements Filterable {
    private List<Food> lstFood;
    private Context context;
    private List <Food> oldLstFood;

    public FoodAdapter(List<Food> lstFood, Context context) {
        this.lstFood = lstFood;
        this.oldLstFood = lstFood;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food fd = lstFood.get(position);
        if (fd == null)
            return;
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvPrice.setText(decimalFormat.format(fd.getPrice()) + "");
        Picasso.with(context).load(fd.getImage()).into(holder.ivPicture);
        holder.tvInfo.setText(fd.getInfor());
        holder.tvName.setText(fd.getFoodName());
        holder.sublayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickSub(fd);
            }
        });
        holder.ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickCall(fd);
            }
        });
        holder.ibAddCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = false;
                for (int i = 0; i < MainActivity.arrayCart.size(); i++) {
                    if (fd.getFoodName().equals(MainActivity.arrayCart.get(i).getFoodName())) {
                        if (MainActivity.arrayCart.get(i).getAmount() == 20) {
                            Toast.makeText(context, "Món " + fd.getFoodName() + " trong giỏ hàng đã đầy", Toast.LENGTH_LONG).show();
                            return;
                        }
                        MainActivity.arrayCart.get(i).setAmount(MainActivity.arrayCart.get(i).getAmount() + 1);
                        MainActivity.arrayCart.get(i).setPrice(MainActivity.arrayCart.get(i).getPrice() + fd.getPrice());
                        check = true;
                    }
                }
                if (check == false) {
                    MainActivity.arrayCart.add(new Cart(holder.tvName.getText().toString(), fd.getImage(), 1, fd.getPrice()));
                }
                Toast.makeText(context, "Đã thêm 1 món " + fd.getFoodName() + " vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onclickCall(Food fd) {
        String phoneNumberUri = "tel: 012345678";
        Intent i =  new Intent(Intent.ACTION_DIAL,  Uri.parse(phoneNumberUri));
        context.startActivity(i);

    }

    private void onclickSub(Food fd) {
        Intent i =new Intent(context, DetailFoodActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_food", fd);
        i.putExtras(bundle);
        context.startActivity(i);
    }

    public void release(){
        context=null;
    }


    @Override
    public int getItemCount() {
        if (lstFood != null)
            return lstFood.size();
        return 0;
    }

    //Search
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()) {
                    lstFood = oldLstFood;
                }
                else {
                    List <Food> list = new ArrayList<>();
                    for (Food fd : oldLstFood) {
                        if (fd.getFoodName().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(fd);
                        }
                    }
                    lstFood = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = lstFood;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                lstFood = (List<Food>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView layout_item;
        ImageView ivPicture, ivCall;
        ImageButton ibAddCa;
        TextView tvName, tvInfo, tvPrice;
        LinearLayout sublayout;
        public ViewHolder (@NonNull View itemView) {
            super (itemView);
            ivPicture = itemView.findViewById(R.id.ivPicture);
            tvName = itemView.findViewById(R.id.tvName);
            tvInfo = itemView.findViewById(R.id.tvInfor);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            layout_item = itemView.findViewById(R.id.layout_item);
            ivCall = itemView.findViewById(R.id.ivCall);
            sublayout = itemView.findViewById(R.id.sublayout);
            ibAddCa = itemView.findViewById(R.id.ibAddCa);
        }
    }
}

