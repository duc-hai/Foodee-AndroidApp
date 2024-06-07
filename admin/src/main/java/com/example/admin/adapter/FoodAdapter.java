package com.example.admin.adapter;

import android.content.Context;
import android.content.DialogInterface;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.activity.EditFood;
import com.example.admin.activity.Signin;
import com.example.admin.model.Food;
import com.example.admin.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editFood (fd);
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder b = new AlertDialog.Builder(context);
                b.setTitle("Xác nhận xóa món ăn");
                b.setMessage("Bạn có đồng ý xóa món " + holder.tvName.getText().toString() + " không ?");
                b.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Foods/" + fd.getId());
                        myRef.removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
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

        holder.sublayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickSub(fd);
            }
        });
    }

    private void editFood(Food fd) {
        Intent i =new Intent(context, EditFood.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_food", fd);
        i.putExtras(bundle);
        context.startActivity(i);
    }

    private void onclickSub(Food fd) {
        Intent i =new Intent(context, EditFood.class);
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
        ImageView ivPicture;
        TextView tvName, tvInfo, tvPrice;
        LinearLayout sublayout;

        ImageView ivEdit, ivDelete;
        public ViewHolder (@NonNull View itemView) {
            super (itemView);
            ivPicture = itemView.findViewById(R.id.ivPicture);
            tvName = itemView.findViewById(R.id.tvName);
            tvInfo = itemView.findViewById(R.id.tvInfor);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            layout_item = itemView.findViewById(R.id.layout_item);
            sublayout = itemView.findViewById(R.id.sublayout);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }
}

