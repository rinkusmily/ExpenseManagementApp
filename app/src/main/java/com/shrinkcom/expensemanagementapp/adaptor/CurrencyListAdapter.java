package com.shrinkcom.expensemanagementapp.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shrinkcom.expensemanagementapp.Pojo.paymentPojo.PaymentList;
import com.shrinkcom.expensemanagementapp.R;

import java.util.List;

import static com.shrinkcom.expensemanagementapp.utils.ApiService.EXPENSE_TYPE_IMAGE;

public class CurrencyListAdapter extends RecyclerView.Adapter<CurrencyListAdapter.MyViewHolder> {

    private Context context;
    private List<PaymentList> cartModelList;

    public CurrencyListAdapter(Context context, List<PaymentList> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_payment_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        PaymentList notifyModel = cartModelList.get(i);
        try {
            myViewHolder.tv_date.setText(cartModelList.get(i).getUser().get(i).getDate());
            myViewHolder.tv_vehicle_name.setText(cartModelList.get(i).getUser().get(i).getName());
            myViewHolder.note.setText(cartModelList.get(i).getUser().get(i).getNote());
            myViewHolder.tv_paid_out.setText("$ "+cartModelList.get(i).getUser().get(i).getPrice());  // this is price

            String type_icon =cartModelList.get(0).getUser().get(i).getIcon();

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_bank);
            requestOptions.error(R.drawable.ic_bank);
            Glide.with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(EXPENSE_TYPE_IMAGE+type_icon).into(myViewHolder.img_icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date ,tv_vehicle_name, note , tv_paid_out;
        public ImageView img_finished ,img_invoiced, img_paid_out ;
        ImageView img_icon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_date =  itemView.findViewById(R.id.date_tv);
            tv_vehicle_name =  itemView.findViewById(R.id.name_tv);
            note =  itemView.findViewById(R.id.note_tv);
            tv_paid_out =  itemView.findViewById(R.id.price_tv);
            img_icon =  itemView.findViewById(R.id.type_icon);




        }
    }
}

