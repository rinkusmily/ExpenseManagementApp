package com.shrinkcom.expensemanagementapp.adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shrinkcom.expensemanagementapp.Pojo.expenseListPojo.ExpenseListPojo;
import com.shrinkcom.expensemanagementapp.Pojo.expenseListPojo.User;
import com.shrinkcom.expensemanagementapp.Pojo.typeEntryCarPojo.TypeCarList;
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.activities.ConfigTypeEntryCar;
import com.shrinkcom.expensemanagementapp.activities.EditCarType;
import com.shrinkcom.expensemanagementapp.reInterface.RecyclerbuttonClick;
import com.shrinkcom.expensemanagementapp.utils.RecyclerButtonclick;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import java.util.List;

import static com.shrinkcom.expensemanagementapp.utils.ApiService.EXPENSE_TYPE_IMAGE;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.EXPENSE_PAYMENT_TYPE;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.EXPENSE_PAYMENT_TYPE_NAME;


public class ConfigTypeEntryCarAdapter extends RecyclerView.Adapter<ConfigTypeEntryCarAdapter.MyViewHolder> {
    RecyclerbuttonClick recyclerbuttonClick;
    private Context context;
    private List<com.shrinkcom.expensemanagementapp.Pojo.typeEntryCarPojo.User> cartModelList;
    RecyclerButtonclick recyclerButtonclick;

    public ConfigTypeEntryCarAdapter(Context context, List<com.shrinkcom.expensemanagementapp.Pojo.typeEntryCarPojo.User> cartModelList , RecyclerbuttonClick recyclerbuttonClick) {
        this.context = context;
        this.recyclerbuttonClick = recyclerbuttonClick;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_config_entry_car, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        try {


            myViewHolder.tv_date.setText(""+cartModelList.get(i).getName());

            String img_res =""+cartModelList.get(i).getImage();
            final int id = cartModelList.get(i).getCarCategoryId();
            final String name = cartModelList.get(i).getName();

            Log.v("DIKSHAA","IMG"+EXPENSE_TYPE_IMAGE+img_res);
            Log.v("DIKSHAA","IMG"+EXPENSE_TYPE_IMAGE+img_res);


        /*    RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_add_circle);
            requestOptions.error(R.drawable.ic_add_circle);
            Glide.with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(EXPENSE_TYPE_IMAGE+img_res).into(myViewHolder.caregory_id);*/

            myViewHolder.caregory_id.setImageResource(Integer.parseInt(img_res));

            myViewHolder.tv_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerbuttonClick.onItemClick(i, id);

                    new UserSession(context).writePrefs(EXPENSE_PAYMENT_TYPE,""+id);
                    new UserSession(context).writePrefs(EXPENSE_PAYMENT_TYPE_NAME,name);
                }
            });

            myViewHolder.img_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Intent intent = new Intent(context,)


                    //Creating the instance of PopupMenu
                    PopupMenu popup = new PopupMenu(context, myViewHolder.img_menu);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.button_menu, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            Toast.makeText(context,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();

                            if(item.getTitle().equals("Edit")){

                                Intent intent = new Intent(context , EditCarType.class);
                                intent.putExtra("name",cartModelList.get(i).getName());
                                intent.putExtra("id_category",""+cartModelList.get(i).getCarCategoryId());
                                intent.putExtra("image",cartModelList.get(i).getImage());


                                Log.v("DIKSHA_SHA "," adaptr name>>"+cartModelList.get(i).getName());
                                Log.v("DIKSHA_SHA "," adaptr name>>"+cartModelList.get(i).getImage());
                                Log.v("DIKSHA_SHA "," adaptr id>>"+cartModelList.get(i).getCarCategoryId());

                                context.startActivity(intent);
                                ((Activity)context).finish();
                            }else {
                                recyclerbuttonClick.onItemClick(i, 10);

                            }
                            return true;
                        }
                    });

                    popup.show();//showing popup menu

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date ,tv_vehicle_name, tv_vehicle_number , tv_paid_out;
        public ImageView img_finished ,img_invoiced, caregory_id , img_menu ;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_date =  itemView.findViewById(R.id.category_name);
            caregory_id =  itemView.findViewById(R.id.caregory_id);
            img_menu =  itemView.findViewById(R.id.img_menu);


        }
    }


    public void removeItem(int position) {
        cartModelList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
}