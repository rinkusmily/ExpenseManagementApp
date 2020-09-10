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
import com.shrinkcom.expensemanagementapp.Pojo.carlistPojo.CARLISTPOJO;
import com.shrinkcom.expensemanagementapp.Pojo.carlistPojo.User;
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.activities.MainTotalBalanceAcitvity;
import com.shrinkcom.expensemanagementapp.activities.UpdateEntryCar;
import com.shrinkcom.expensemanagementapp.model.CartModel;
import com.shrinkcom.expensemanagementapp.utils.RecyclerButtonclick;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import java.util.List;

import static com.shrinkcom.expensemanagementapp.utils.ApiService.EXPENSE_TYPE_IMAGE;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.CURENCY_ONSELECT;

import static com.shrinkcom.expensemanagementapp.utils.UserSession.SYMBOL_ONSELECT;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder> {

    private Context context;
    private List<User> cartModelList;
    RecyclerButtonclick recyclerButtonclick;

    public CartListAdapter(Context context, List<User> cartModelList , RecyclerButtonclick recyclerButtonclick) {
        this.context = context;
        this.cartModelList = cartModelList;
        this.recyclerButtonclick = recyclerButtonclick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_cart_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
       // try {

            String finish = null;
            String invoiced;
            String payment;

     /*   if(cartModelList.get(0).getUser().get(i).getFinishedStatus()==1){

            myViewHolder.tv_date.setText(cartModelList.get(0).getUser().get(i).getDate());
            myViewHolder.tv_vehicle_name.setText(cartModelList.get(0).getUser().get(i).getModel());
            myViewHolder.tv_vehicle_number.setText(cartModelList.get(0).getUser().get(i).getPlateNo());
            myViewHolder.tv_paid_out.setText(cartModelList.get(0).getUser().get(i).getPrice());  // this is price

            finish = ""+cartModelList.get(0).getUser().get(i).getFinishedStatus();
            invoiced = ""+cartModelList.get(0).getUser().get(i).getInvoiceStatus();
            payment = ""+cartModelList.get(0).getUser().get(i).getPaidoutStatus();

        }else {*/


            String currency = new UserSession(context).readPrefs(CURENCY_ONSELECT);
            String   symbol = new UserSession(context).readPrefs(SYMBOL_ONSELECT);


            myViewHolder.tv_date.setText(cartModelList.get(i).getDate());
            myViewHolder.tv_vehicle_name.setText(cartModelList.get(i).getModel());
            myViewHolder.tv_vehicle_number.setText(cartModelList.get(i).getPlateNo());
            myViewHolder.tv_paid_out.setText(symbol+cartModelList.get(i).getPrice());  // this is price

            finish = ""+cartModelList.get(i).getFinishedStatus();
            invoiced = ""+cartModelList.get(i).getInvoiceStatus();
            payment = ""+cartModelList.get(i).getPaidoutStatus();
            String type_icon =cartModelList.get(i).getIcon();

        try {
            myViewHolder.img_icon.setImageResource(Integer.parseInt(type_icon));
        } catch (Exception e) {
            e.printStackTrace();
        }


      /*  String type_icon =notifyModel.getUser().get(i).getIcon();

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_add_circle);
        requestOptions.error(R.drawable.ic_add_circle);
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(EXPENSE_TYPE_IMAGE+type_icon).into(myViewHolder.img_icon);

      //  }*/


            if(finish.equalsIgnoreCase("0")){

                myViewHolder.img_finished.setImageResource(R.drawable.shape_carlist);


            }else {

                myViewHolder.img_finished.setImageResource(R.drawable.cart_shape_fill);
            }


            if(invoiced.equalsIgnoreCase("0")){

                myViewHolder.img_invoiced.setImageResource(R.drawable.shape_carlist);
            }else {

                myViewHolder.img_invoiced.setImageResource(R.drawable.cart_shape_fill);
            }


            if(payment.equalsIgnoreCase("0")){
                myViewHolder.img_paid_out.setImageResource(R.drawable.shape_carlist);

            }else {

                myViewHolder.img_paid_out.setImageResource(R.drawable.cart_shape_fill);
            }


            // click event


        /*    myViewHolder.tv_vehicle_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Log.v("DIKSHAAA",">>>"+cartModelList.get(i).getCarId());
                    Log.v("DIKSHAAA",">>>"+cartModelList.get(i).getPrice());
                    Log.v("DIKSHAAA",">>>"+cartModelList.get(i).getModel());


                    Intent intent3=new Intent(context, UpdateEntryCar.class);

                    intent3.putExtra("car_id",cartModelList.get(i).getCarId());
                    intent3.putExtra("amount",cartModelList.get(i).getPrice());
                    intent3.putExtra("plate_no",cartModelList.get(i).getPlateNo());
                    intent3.putExtra("model",cartModelList.get(i).getModel());
                    intent3.putExtra("note",cartModelList.get(i).getNote());

                    intent3.putExtra("status_finish",cartModelList.get(i).getFinishedStatus());
                    intent3.putExtra("status_invoice",cartModelList.get(i).getInvoiceStatus());
                    intent3.putExtra("status_paid",cartModelList.get(i).getPaidoutStatus());


                    context.startActivity(intent3);

                }
            });


            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Log.v("DIKSHAAA",">>>"+cartModelList.get(i).getCarId());
                    Log.v("DIKSHAAA",">>>"+cartModelList.get(i).getPrice());
                    Log.v("DIKSHAAA",">>>"+cartModelList.get(i).getModel());


                    Intent intent3=new Intent(context, UpdateEntryCar.class);

                    intent3.putExtra("car_id",cartModelList.get(i).getCarId());
                    intent3.putExtra("amount",cartModelList.get(i).getPrice());
                    intent3.putExtra("plate_no",cartModelList.get(i).getPlateNo());
                    intent3.putExtra("model",cartModelList.get(i).getModel());
                    intent3.putExtra("note",cartModelList.get(i).getNote());

                    intent3.putExtra("status_finish",cartModelList.get(i).getFinishedStatus());
                    intent3.putExtra("status_invoice",cartModelList.get(i).getInvoiceStatus());
                    intent3.putExtra("status_paid",cartModelList.get(i).getPaidoutStatus());


                    context.startActivity(intent3);

                }
            });


            myViewHolder.tv_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Log.v("DIKSHAAA",">>>"+cartModelList.get(i).getCarId());
                    Log.v("DIKSHAAA",">>>"+cartModelList.get(i).getPrice());
                    Log.v("DIKSHAAA",">>>"+cartModelList.get(i).getModel());


                    Intent intent3=new Intent(context, UpdateEntryCar.class);

                    intent3.putExtra("car_id",cartModelList.get(i).getCarId());
                    intent3.putExtra("amount",cartModelList.get(i).getPrice());
                    intent3.putExtra("plate_no",cartModelList.get(i).getPlateNo());
                    intent3.putExtra("model",cartModelList.get(i).getModel());
                    intent3.putExtra("note",cartModelList.get(i).getNote());

                    intent3.putExtra("status_finish",cartModelList.get(i).getFinishedStatus());
                    intent3.putExtra("status_invoice",cartModelList.get(i).getInvoiceStatus());
                    intent3.putExtra("status_paid",cartModelList.get(i).getPaidoutStatus());


                    context.startActivity(intent3);

                }
            });*/


            myViewHolder.ll_car_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Log.v("DIKSHAAA",">>>"+cartModelList.get(i).getCarId());
                    Log.v("DIKSHAAA",">>>"+cartModelList.get(i).getPrice());
                    Log.v("DIKSHAAA",">>>"+cartModelList.get(i).getModel());

                    new UserSession(context).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE,""+cartModelList.get(i).getCategory_id());
                    new UserSession(context).writePrefs(UserSession.EXPENSE_PAYMENT_TYPE_NAME,cartModelList.get(i).getType_name());
                    new UserSession(context).writePrefs(UserSession.EXPENCE_IMAGE_VALUE,cartModelList.get(i).getIcon());

                    Intent intent3=new Intent(context, UpdateEntryCar.class);

                    intent3.putExtra("car_id",cartModelList.get(i).getCarId());
                    intent3.putExtra("date",cartModelList.get(i).getDate());
                    intent3.putExtra("amount",cartModelList.get(i).getPrice());
                    intent3.putExtra("plate_no",cartModelList.get(i).getPlateNo());
                    intent3.putExtra("model",cartModelList.get(i).getModel());
                    intent3.putExtra("note",cartModelList.get(i).getNote());
                    intent3.putExtra("cat_name",cartModelList.get(i).getType_name());
                    intent3.putExtra("cat_image",cartModelList.get(i).getIcon());
                    intent3.putExtra("category_id",cartModelList.get(i).getCategory_id());
                    intent3.putExtra("category_name",cartModelList.get(i).getType_name());
                    intent3.putExtra("category_img",cartModelList.get(i).getIcon());
                    intent3.putExtra("company",cartModelList.get(i).getCompany());



                    Log.v("DIKSHAA","UpdateType >>>"+cartModelList.get(i).getType());
                    Log.v("DIKSHAA","UpdateType >>>"+cartModelList.get(i).getIcon());

                    intent3.putExtra("status_finish",cartModelList.get(i).getFinishedStatus());
                    intent3.putExtra("status_invoice",cartModelList.get(i).getInvoiceStatus());
                    intent3.putExtra("status_paid",cartModelList.get(i).getPaidoutStatus());


                    context.startActivity(intent3);


                    recyclerButtonclick.onItemClick(i,10);

                }
            });


            myViewHolder.img_finished.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    recyclerButtonclick.onItemClick(i,1);

                }
            });


            myViewHolder.img_invoiced.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    recyclerButtonclick.onItemClick(i,2);

                }
            });
            myViewHolder.img_paid_out.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    recyclerButtonclick.onItemClick(i,3);

                }
            });

            myViewHolder.tv_vehicle_number.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    recyclerButtonclick.onItemClick(i,10);

                }
            });
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}


    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date ,tv_vehicle_name, tv_vehicle_number , tv_paid_out;
        public ImageView img_finished ,img_invoiced, img_paid_out ;
        LinearLayout ll_car_click;
       ImageView img_icon;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_date =  itemView.findViewById(R.id.tv_date);
            tv_vehicle_name =  itemView.findViewById(R.id.tv_vehicle_name);
            tv_vehicle_number =  itemView.findViewById(R.id.tv_vehicle_number);
            tv_paid_out =  itemView.findViewById(R.id.tv_paid_out);
            ll_car_click =  itemView.findViewById(R.id.ll_car_click);

            img_finished =  itemView.findViewById(R.id.img_finished);
            img_invoiced =  itemView.findViewById(R.id.img_invoiced);
            img_paid_out =  itemView.findViewById(R.id.img_paid_out);
          //  img_icon =  itemView.findViewById(R.id.type_icon);


        }
    }


    public void updateList(List<User> list){
        cartModelList = list;

        Log.v("DIKSHA","LISTTTT"+list.size());
        Log.v("DIKSHA","LISTTTT222222"+cartModelList.size());
        notifyDataSetChanged();
    }

}

