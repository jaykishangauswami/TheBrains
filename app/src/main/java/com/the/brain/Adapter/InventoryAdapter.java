package com.the.brain.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.the.brain.Model.ModelAccountChild;
import com.the.brain.Model.ModelAccounts;
import com.the.brain.Model.ModelInventory;
import com.the.brain.Model.ModelInventoryChild;
import com.the.brain.R;

import java.util.ArrayList;

/**
 * Created by Android-2 on 04-12-2017.
 */


public class InventoryAdapter extends BaseExpandableListAdapter {
    Context context;
    ArrayList<ModelInventory> model_elvs;

    public InventoryAdapter(Context context, ArrayList<ModelInventory> model_elvs) {
        this.context = context;
        this.model_elvs = model_elvs;
    }

    @Override
    public int getGroupCount() {
        return model_elvs.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<ModelInventoryChild> chList = model_elvs.get(groupPosition).getItems();
        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return model_elvs.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ModelInventoryChild> childses = model_elvs.get(groupPosition).getItems();
        return childses.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ModelAccounts group = (ModelAccounts) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.elv_simple, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.elv_text_title);
        tv.setText(group.getTitle());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ModelInventoryChild child = (ModelInventoryChild) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) parent.getContext()
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.elv_simple, null);
//                convertView=new LayoutInflater.from(parent.getContext().inf)
        }
        final TextView tv = (TextView) convertView.findViewById(R.id.elv_text_title);

        tv.setText(child.getCtitle().toString());


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    switch (tv.getText().toString()) {

//                        case "Mobile":
//                            Intent load_Mobile = new Intent(context.getApplicationContext(), Mobile_rech.class);
//                            context.startActivity(load_Mobile);
//                            ((HomeActivityB2C)context).finish();
//                            break;
//                        default:
//                            Toast.makeText(context, "Please Click Another", Toast.LENGTH_SHORT).show();
//                            break;
                    }



            }
        });
        return convertView;


    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
