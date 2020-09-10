package com.shrinkcom.expensemanagementapp.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.utils.RecyclerButtonclick;
import com.shrinkcom.expensemanagementapp.model.ListModel;

import java.util.ArrayList;

public class HorizontalListAdaptor extends RecyclerView.Adapter<HorizontalListAdaptor.viewHolder> {

    Context context;
    ArrayList<ListModel> arrayList;
    RecyclerButtonclick recyclerbuttonClick;


    public HorizontalListAdaptor(Context context, ArrayList<ListModel> arrayList, RecyclerButtonclick recyclerbuttonClick) {
        this.context = context;
        this.arrayList = arrayList;
        this.recyclerbuttonClick = recyclerbuttonClick;
    }

    @Override
    public  viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_list, viewGroup, false);
        return new viewHolder(view);
    }
    @Override
    public  void onBindViewHolder(viewHolder viewHolder, final  int position) {
        viewHolder.item_expense.setText(arrayList.get(position).getName());
//        viewHolder.icon.setImageResource(arrayList.get(position).getImage());

        viewHolder.item_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerbuttonClick.onItemClick(position, 0);
            }
        });


    }
    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView item_expense;

        public viewHolder(View itemView) {
            super(itemView);

            item_expense = itemView.findViewById(R.id.item_expense) ;

        }
    }
}
