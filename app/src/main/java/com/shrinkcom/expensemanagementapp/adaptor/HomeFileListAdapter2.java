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

import com.shrinkcom.expensemanagementapp.Pojo.getFileListPojo.GetFileListPojo;
import com.shrinkcom.expensemanagementapp.Pojo.getFileListPojo.User;
import com.shrinkcom.expensemanagementapp.R;

import com.shrinkcom.expensemanagementapp.activities.SelectDateExportActivity;
import com.shrinkcom.expensemanagementapp.activities.TutorialList;
import com.shrinkcom.expensemanagementapp.reInterface.RecyclerbuttonClick;
import com.shrinkcom.expensemanagementapp.utils.UserSession;

import java.util.List;

import static com.shrinkcom.expensemanagementapp.utils.UserSession.CURENCY_ONSELECT;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.HOME_LIST_ID;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.PTREF_LIST_NAME;
import static com.shrinkcom.expensemanagementapp.utils.UserSession.SYMBOL_ONSELECT;

public class HomeFileListAdapter2 extends RecyclerView.Adapter<HomeFileListAdapter2.MyViewHolder>   {

    private Context context;
    private List<GetFileListPojo> allModelList;
    private List<User> allModelList_2;
     String list_id  , list_name ,currency;

    RecyclerbuttonClick recyclerbuttonClick;
    private User data;


    public HomeFileListAdapter2(Context context, List<User> allModelList , RecyclerbuttonClick recyclerbuttonClick) {
        this.context = context;
       // this.allModelList = allModelList;
        this.allModelList_2 = allModelList;
        this.recyclerbuttonClick = recyclerbuttonClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_file_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {



//        GetFileListPojo notifyModel = allModelList.get(0);
        User notifyModel2 = allModelList_2.get(0);
        myViewHolder.tv_file_name.setText(allModelList_2.get(i).getListName());
        myViewHolder.tv_file_crated_date.setText(allModelList_2.get(i).getDate());

        list_id =""+allModelList_2.get(i).getListId();
         list_name =""+allModelList_2.get(i).getListName();
        currency =""+allModelList_2.get(i).getCurrency();



        Log.v("DIKSHA_09","API_DATA >>>"+list_id);
        Log.v("DIKSHA_09","API_DATA >>>"+list_name);
        Log.v("DIKSHA_09","API_DATA >>>"+""+currency);



        final String c = ""+allModelList_2.get(i).getCurrency();

        myViewHolder.ll_layout_swipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String[] separated = (""+allModelList_2.get(i).getCurrency()).split(" ");

                String currecy1= ""+separated[0];
                String  symbol1 = ""+separated[1];

                Log.v("DIKSHA_09","currecy22>>>"+currecy1);
                Log.v("DIKSHA_09","symbol22>>>"+symbol1);

                        recyclerbuttonClick.onItemClick(i , 1);

                Log.v("DIKSHA","FILER_NAME>>"+list_name);

                UserSession userSession = new UserSession(context);
                String dnt_show_msg  =  userSession.readPrefs(userSession.DONT_SHOW_MSG_HOME2);


                new UserSession(context).writePrefs(CURENCY_ONSELECT, currecy1);
                new UserSession(context).writePrefs(SYMBOL_ONSELECT ,symbol1);
                new UserSession(context).writePrefs(HOME_LIST_ID ,""+allModelList_2.get(i).getListId());
                new UserSession(context).writePrefs(PTREF_LIST_NAME ,allModelList_2.get(i).getListName());


                if(dnt_show_msg.equals("1")){

                    Log.v("DIKSHAA_","ADAAPTER currecy1 >> "+currecy1);
                    Log.v("DIKSHAA_","ADAAPTER list_id >> "+allModelList_2.get(i).getListId());
                    Log.v("DIKSHAA_","ADAAPTER symbol1 >> "+symbol1);

                  /*  Intent intent = new Intent(context, SelectDateExportActivity.class);
                    intent.putExtra("list_id",list_id);
                    context.startActivity(intent);*/

                }
                else{
                  /*  Intent intent2=new Intent(context, TutorialList.class);
                    context.startActivity(intent2);*/

                }
            }
        });


        myViewHolder.tv_file_crated_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String[] separated = (""+allModelList_2.get(i).getCurrency()).split(" ");

                String currecy1= ""+separated[0];
                String  symbol1 = ""+separated[1];

                Log.v("DIKSHA_09","currecy22>>>"+currecy1);
                Log.v("DIKSHA_09","symbol22>>>"+symbol1);


                Log.v("DIKSHA","FILER_NAME>>"+list_name);

                UserSession userSession = new UserSession(context);
                String dnt_show_msg  =  userSession.readPrefs(userSession.DONT_SHOW_MSG_HOME2);


                new UserSession(context).writePrefs(CURENCY_ONSELECT, currecy1);
                new UserSession(context).writePrefs(SYMBOL_ONSELECT ,symbol1);
                new UserSession(context).writePrefs(HOME_LIST_ID ,""+allModelList_2.get(i).getListId());
                new UserSession(context).writePrefs(PTREF_LIST_NAME ,allModelList_2.get(i).getListName());


                if(dnt_show_msg.equals("1")){




                }

                else{







                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return allModelList_2.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_file_name ,tv_file_crated_date;

        ImageView img_icon;
        LinearLayout ll_layout_swipe;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_file_name =  itemView.findViewById(R.id.tv_file_name);
            tv_file_crated_date =  itemView.findViewById(R.id.tv_file_crated_date);
            ll_layout_swipe =  itemView.findViewById(R.id.ll_layout_swipe);

        }
    }

    public void updateList(List<User> list){
        allModelList_2 = list;

        Log.v("DIKSHA","LISTTTT"+list.size());
        Log.v("DIKSHA","LISTTTT222222"+allModelList_2.size());
        notifyDataSetChanged();
    }



    public void removeItem(int position) {
        allModelList_2.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void restoreItem(User item, int position) {
        allModelList_2.add(position, item);
        notifyItemInserted(position);
    }

    public User getData(int position) {
        return data;
    }
}

