package com.example.tabunganku.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tabunganku.model.wallet.WalletDetailModel;
import com.example.tabunganku.R;

import java.util.List;

public class WalletDetailAdapter extends RecyclerView.Adapter<WalletDetailAdapter.MyHolder> {
    List<WalletDetailModel> mList;
    Context context;

    public WalletDetailAdapter (Context context, List<WalletDetailModel> mList) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public WalletDetailAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_item_list, parent, false);
        WalletDetailAdapter.MyHolder holder = new WalletDetailAdapter.MyHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(WalletDetailAdapter.MyHolder holder, final int position) {
        WalletDetailModel walletDetailModel = mList.get(position);
        Integer totalInteger = Integer.parseInt(walletDetailModel.getMoney());
        String str1 = "Rp ";
        String total = str1.concat((String.format("%,d", totalInteger)).replace(',', '.'));
        holder.money.setText(total);
        holder.description.setText(walletDetailModel.getDescription());

        if (walletDetailModel.getType().equals("income")) {
            Glide.with(context)
                    .load(R.drawable.ic_arrow_up)
                    .placeholder(R.drawable.ic_arrow_up)
                    .error(R.drawable.ic_arrow_up)
                    .into(holder.type);
        } else {
            Glide.with(context)
                    .load(R.drawable.ic_arrow_down)
                    .placeholder(R.drawable.ic_arrow_down)
                    .error(R.drawable.ic_arrow_down)
                    .into(holder.type);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView money, description;
        ImageView type;

        public MyHolder(View v) {
            super(v);
            money = (TextView) v.findViewById(R.id.activity_money);
            description = (TextView) v.findViewById(R.id.activity_description);
            type = (ImageView) v.findViewById(R.id.activity_recycle_image);
        }
    }
}
