package com.shrinkcom.expensemanagementapp.adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shrinkcom.expensemanagementapp.MainActivity;
import com.shrinkcom.expensemanagementapp.R;
import com.shrinkcom.expensemanagementapp.utils.RecyclerButtonclick;

public class CustomAdapter extends BaseAdapter {

    String [] result;
    Context context;
    RecyclerButtonclick recyclerButtonclick;
 int [] imageId;
      private static LayoutInflater inflater=null;
    public CustomAdapter(MainActivity mainActivity, String[] prgmNameList, int[] prgmImages , RecyclerButtonclick recyclerButtonclick) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        imageId=prgmImages;
        this.recyclerButtonclick=recyclerButtonclick;
         inflater = ( LayoutInflater )context.
                 getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

             rowView = inflater.inflate(R.layout.row_layout, null);
         //    holder.tv=(TextView) rowView.findViewById(R.id.textView1);
             holder.img=(ImageView) rowView.findViewById(R.id.imageView1);

//         holder.tv.setText(result[position]);
         holder.img.setImageResource(imageId[position]);

         rowView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();

                recyclerButtonclick.onItemClick(position,0);

          

            }
        });

        return rowView;
    }

} 