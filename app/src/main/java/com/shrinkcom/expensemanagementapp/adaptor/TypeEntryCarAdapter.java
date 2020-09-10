package com.shrinkcom.expensemanagementapp.adaptor;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shrinkcom.expensemanagementapp.Pojo.expenseListPojo.ExpenseListPojo;
import com.shrinkcom.expensemanagementapp.Pojo.typeEntryCarPojo.TypeCarList;
import com.shrinkcom.expensemanagementapp.Pojo.typeEntryCarPojo.User;
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.reInterface.RecyclerbuttonClick;
import com.shrinkcom.expensemanagementapp.utils.StaticVariablesStorage;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import java.util.List;

import static com.shrinkcom.expensemanagementapp.utils.ApiService.EXPENSE_TYPE_IMAGE;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.CAREGORY_IDD;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.CATEGORY_NAME_D;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.EXPENCE_IMAGE_VALUE;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.EXPENSE_PAYMENT_TYPE;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.EXPENSE_PAYMENT_TYPE_NAME;


public class TypeEntryCarAdapter extends RecyclerView.Adapter<TypeEntryCarAdapter.MyViewHolder> {
    RecyclerbuttonClick recyclerbuttonClick;
    private Context context;
    private List<User> cartModelList;

    public TypeEntryCarAdapter(Context context, List<User> cartModelList , RecyclerbuttonClick recyclerbuttonClick) {
        this.context = context;
        this.recyclerbuttonClick = recyclerbuttonClick;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_expense_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

      //  TypeCarList notifyModel = cartModelList.get(i);

        myViewHolder.tv_date.setText(""+cartModelList.get(i).getName());

        final String img_res =""+cartModelList.get(i).getImage();
        final int id = cartModelList.get(i).getCarCategoryId();
       final String name = cartModelList.get(i).getName();

       Log.v("DIKSHAA","IMG"+EXPENSE_TYPE_IMAGE+img_res);
       Log.v("DIKSHAA","IMG"+EXPENSE_TYPE_IMAGE+img_res);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_add_circle);
        requestOptions.error(R.drawable.ic_add_circle);
       /* Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(EXPENSE_TYPE_IMAGE+img_res).into(myViewHolder.caregory_id);*/



       myViewHolder.caregory_id.setImageResource(Integer.parseInt(img_res));



        myViewHolder.tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerbuttonClick.onItemClick(i, id);

                new UserSession(context).writePrefs(EXPENSE_PAYMENT_TYPE,""+id);
                new UserSession(context).writePrefs(CATEGORY_NAME_D,name);
                new UserSession(context).writePrefs(EXPENCE_IMAGE_VALUE,img_res);
                new UserSession(context).writePrefs(CAREGORY_IDD,""+id);

                Log.v("Joy", "languwedsghfage "+name);

                Log.v("DIKSHAA","IMDDDDDG"+EXPENSE_TYPE_IMAGE+name);
                StaticVariablesStorage.CATECORY_TYPE=name;
                StaticVariablesStorage.CATEGORY_IMAGE=img_res;
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date ,tv_vehicle_name, tv_vehicle_number , tv_paid_out;
        public ImageView img_finished ,img_invoiced, caregory_id ;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_date =  itemView.findViewById(R.id.category_name);

            caregory_id =  itemView.findViewById(R.id.caregory_id);


        }
    }
}