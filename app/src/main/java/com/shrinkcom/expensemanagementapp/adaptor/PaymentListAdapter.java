package com.shrinkcom.expensemanagementapp.adaptor;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shrinkcom.expensemanagementapp.Pojo.paymentPojo.PaymentList;
import com.shrinkcom.expensemanagementapp.Pojo.paymentPojo.User;
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.activities.AddPayment;
import com.shrinkcom.expensemanagementapp.activities.UpdateExpenses;
import com.shrinkcom.expensemanagementapp.activities.UpdatePayment;
import com.shrinkcom.expensemanagementapp.model.CartModel;
import com.shrinkcom.expensemanagementapp.model.PaymentListModel;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import java.util.List;

import static com.shrinkcom.expensemanagementapp.utils.ApiService.EXPENSE_TYPE_IMAGE;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.CURENCY_ONSELECT;

import static com.shrinkcom.expensemanagementapp.utils.UserSession.SYMBOL_ONSELECT;

public class PaymentListAdapter extends RecyclerView.Adapter<PaymentListAdapter.MyViewHolder> {

    private Context context;
    private List<User> cartModelList;

    public PaymentListAdapter(Context context, List<User> cartModelList) {
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
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {


        String currency = new UserSession(context).readPrefs(CURENCY_ONSELECT);
     String   symbol = new UserSession(context).readPrefs(SYMBOL_ONSELECT);

        try {
            myViewHolder.tv_date.setText(cartModelList.get(i).getDate());
            myViewHolder.tv_vehicle_name.setText(cartModelList.get(i).getType_name());
            myViewHolder.note.setText(cartModelList.get(i).getNote());
            myViewHolder.tv_paid_out.setText(symbol+cartModelList.get(i).getPrice());  // this is price

            String type_icon =cartModelList.get(i).getIcon();

            myViewHolder.img_icon.setImageResource(Integer.parseInt(type_icon));

         /*   RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_bank);
            requestOptions.error(R.drawable.ic_bank);
            Glide.with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(EXPENSE_TYPE_IMAGE+type_icon).into(myViewHolder.img_icon);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        myViewHolder.ll_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent3=new Intent(context, UpdatePayment.class);

                new UserSession(context).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE,""+cartModelList.get(i).getCategoryId());
                new UserSession(context).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE_NAME,cartModelList.get(i).getType_name());
                new UserSession(context).writePrefs(UserSession.EXPENCE_IMAGE_VALUE,cartModelList.get(i).getIcon());

                  Log.v("DIKSHA_TYPE","DIKSHA_TYPE>>>>"+ cartModelList.get(i).getType_name());


                intent3.putExtra("car_id",cartModelList.get(i).getPaymentId());
                intent3.putExtra("date",cartModelList.get(i).getDate());
                intent3.putExtra("amount",cartModelList.get(i).getPrice());
                intent3.putExtra("note",cartModelList.get(i).getNote());
                intent3.putExtra("category_id",cartModelList.get(i).getCategoryId());
                intent3.putExtra("category_name",cartModelList.get(i).getType_name());
                intent3.putExtra("category_img",cartModelList.get(i).getIcon());

                context.startActivity(intent3);


            }
        });

    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date ,tv_vehicle_name, note , tv_paid_out ;
        public ImageView img_finished ,img_invoiced, img_paid_out ;
        ImageView img_icon;
        LinearLayout ll_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_date =  itemView.findViewById(R.id.date_tv);
            tv_vehicle_name =  itemView.findViewById(R.id.name_tv);
            note =  itemView.findViewById(R.id.note_tv);
            tv_paid_out =  itemView.findViewById(R.id.price_tv);
            img_icon =  itemView.findViewById(R.id.type_icon);
            ll_layout =  itemView.findViewById(R.id.ll_layout);


        }
    }



    public void updateList(List<User> list){
        cartModelList = list;

        Log.v("DIKSHA","LISTTTT"+list.size());
        Log.v("DIKSHA","LISTTTT222222"+cartModelList.size());
        notifyDataSetChanged();
    }

}

