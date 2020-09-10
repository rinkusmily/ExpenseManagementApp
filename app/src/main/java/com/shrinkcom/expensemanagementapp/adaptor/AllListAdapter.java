package com.shrinkcom.expensemanagementapp.adaptor;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shrinkcom.expensemanagementapp.Pojo.allListPojo.AllListPojo;

import com.shrinkcom.expensemanagementapp.Pojo.allListPojo.User;
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.model.All_model_list;
import com.shrinkcom.expensemanagementapp.model.CartModel;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.shrinkcom.expensemanagementapp.utils.ApiService.EXPENSE_TYPE_IMAGE;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.CURENCY_ONSELECT;

import static com.shrinkcom.expensemanagementapp.utils.UserSession.SYMBOL_ONSELECT;

public class AllListAdapter extends RecyclerView.Adapter<AllListAdapter.MyViewHolder> {

    private Context context;
    private List<User> allModelList;

    ArrayList arraylist ;

    public AllListAdapter(Context context, List<User> allModelList) {
        this.context = context;
        this.allModelList = allModelList;

        this.arraylist = new ArrayList<User>();
        this.arraylist.clear();
        this.arraylist.addAll(allModelList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_all_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        String currency = new UserSession(context).readPrefs(CURENCY_ONSELECT);
        String   symbol = new UserSession(context).readPrefs(SYMBOL_ONSELECT);


        myViewHolder.tv_date.setText(allModelList.get(i).getDate());



    /*    if(allModelList.get(i).getType().equals("car")){
            myViewHolder.tv_type_name.setText(""+allModelList.get(i).getPlateNo());
            myViewHolder.tv_note.setText(allModelList.get(i).getCompany());

        }else {
            myViewHolder.tv_type_name.setText(""+allModelList.get(i).getType());
            myViewHolder.tv_note.setText(allModelList.get(i).getNote());
        }*/

        myViewHolder.tv_price.setText(allModelList.get(i).getPrice());  // this is price

       String type_icon =allModelList.get(i).getIcon();

       String type =allModelList.get(i).getType();

       if(type.equals("car")){

           myViewHolder.tv_price.setText(symbol+allModelList.get(i).getPrice());
           myViewHolder.tv_type_name.setText(""+allModelList.get(i).getModel()+"-"+allModelList.get(i).getPlateNo());


           myViewHolder.tv_note.setText(allModelList.get(i).getCompany());

           Log.v("DIKSHAAAA",">>"+""+allModelList.get(i).getPlateNo());
           Log.v("DIKSHAAAA",">>"+""+type);
           myViewHolder.img_icon.setImageResource(Integer.parseInt(type_icon));


           myViewHolder.tv_price.setTextColor((ContextCompat.getColor(context, R.color.grey9)));
       }else if(type.equals("expence")){

            myViewHolder.tv_price.setText(symbol+" -"+allModelList.get(i).getPrice());
            myViewHolder.tv_price.setTextColor(Color.parseColor("#f6383d"));
           myViewHolder.tv_type_name.setText(""+allModelList.get(i).getType_name());
           myViewHolder.tv_note.setText(allModelList.get(i).getNote());
           myViewHolder.img_icon.setImageResource(Integer.parseInt(type_icon));
        }

       else if(type.equals("payment")){

           myViewHolder.tv_price.setText(symbol+allModelList.get(i).getPrice());
           myViewHolder.tv_price.setTextColor(Color.parseColor("#77D353"));
           myViewHolder.tv_type_name.setText(""+allModelList.get(i).getType_name());
           myViewHolder.tv_note.setText(allModelList.get(i).getNote());
           myViewHolder.img_icon.setImageResource(Integer.parseInt(type_icon));

       }

      /*  RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.img);
        requestOptions.error(R.drawable.img);
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(EXPENSE_TYPE_IMAGE+type_icon).into(myViewHolder.img_icon);*/

//        myViewHolder.img_icon.setImageResource(Integer.parseInt(type_icon));



    }

    @Override
    public int getItemCount() {
        return allModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date ,tv_note, tv_type_name , tv_price ;
        ImageView img_icon;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_date =  itemView.findViewById(R.id.tv_date);
            tv_type_name =  itemView.findViewById(R.id.tv_type_name);
            tv_note =  itemView.findViewById(R.id.tv_note);
            tv_price =  itemView.findViewById(R.id.price_tv);
            img_icon =  itemView.findViewById(R.id.type_icon);

        }
    }



    public void updateList(List<User> list){

        allModelList = list;
        Log.v("DIKSHA","LISTTTT"+list.size());
        Log.v("DIKSHA","LISTTTT222222"+allModelList.size());
        notifyDataSetChanged();
    }


/*    public void filter(String charText, int textlength)
    {
        charText = charText;
        Log.v("DIKSHA",">>>>>>>>"+charText);
        Log.v("DIKSHA",">>>>>>>>"+textlength);
        allModelList.clear();
        if (charText.length() == 0) {
            allModelList.addAll(arraylist);
        } else {
            Log.v("DIKSHA",">>else>>>>>>"+textlength);
            Log.v("DIKSHA",">>else>>>>>>"+charText);
            Log.v("DIKSHA",">>else>>>>>>"+allModelList.size());
            Log.v("DIKSHA",">>else>>>>>>");


            for (User listModel : allModelList) {

                Log.v("DIKSHA",">>for>>>>>>"+charText);
                try {

                    Log.v("DIKSHA",">>try>>>>>>"+charText);
                    String plate = (""+listModel.getPlateNo()).toLowerCase();
                    String model = (""+listModel.getPlateNo()).toLowerCase();
                    String type = (""+listModel.getType()).toLowerCase();
                    String price = (""+listModel.getPrice()).toLowerCase();
                    String name = listModel.getName().toLowerCase();
                    String note = (""+listModel.getNote()).toLowerCase();

                    name = name.trim();



                    Log.v("DIKSHA",">>>>>>>>"+charText);
                  *//*  if (name.contains(charText.toString().toLowerCase()))
                    {
                        allModelList.add(listModel);
                    }
*//*


                    if (plate.contains(charText))
                    {
                        allModelList.add(listModel);
                    }

                   else if (model.contains(charText))
                    {
                        allModelList.add(listModel);
                    }
                   else if (type.contains(charText))
                    {
                        allModelList.add(listModel);
                    }
                  else   if (price.contains(charText))
                    {
                        allModelList.add(listModel);
                    }
                   else if (name.contains(charText))
                    {
                        allModelList.add(listModel);
                    }
                  else   if (note.contains(charText.toString().toLowerCase()))
                    {
                        allModelList.add(listModel);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.v("DIKSHA",">>>>Error>>>>"+e);
                }
            }
        }

        notifyDataSetChanged();
    }*/
}

