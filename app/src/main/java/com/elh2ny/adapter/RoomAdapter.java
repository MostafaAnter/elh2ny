package com.elh2ny.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elh2ny.R;
import com.elh2ny.R2;
import com.elh2ny.model.roomsResponseModel.RoomModel;
import com.elh2ny.utility.Util;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.elh2ny.fragment.RoomRegisterationDialog.newInstance;

/**
 * Created by mostafa_anter on 1/10/17.
 */

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private List<RoomModel> mDataSet;
    private Context mContext;

    private int mStackLevel = 0;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public  class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.textView1)TextView textView1;
        @BindView(R2.id.textView2)TextView textView2;
        @BindView(R2.id.textView3)TextView textView3;
        @BindView(R2.id.imageView1)ImageView avatar;

        public TextView getTextView1() {
            return textView1;
        }

        public TextView getTextView2() {
            return textView2;
        }

        public TextView getTextView3() {
            return textView3;
        }

        public ImageView getAvatar() {
            return avatar;
        }

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");

                    mStackLevel++;
                    FragmentTransaction ft = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
                    Fragment prev = ((FragmentActivity)mContext).getSupportFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);

                    // Create and show the dialog.
                    DialogFragment newFragment = newInstance(mStackLevel);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("id", mDataSet.get(getPosition()).getId());
                    bundle1.putString("name", mDataSet.get(getPosition()).getHospital());
                    newFragment.setArguments(bundle1);
                    newFragment.show(ft, "dialog");
                }
            });

        }


    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public RoomAdapter(Context mContext, List<RoomModel> dataSet) {
        this.mContext = mContext;
        mDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.room_item, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        //change text font
        Util.changeViewTypeFace(mContext, "fonts/DroidKufi-Regular.ttf", viewHolder.getTextView2());
        Util.changeViewTypeFace(mContext, "fonts/DroidKufi-Regular.ttf", viewHolder.getTextView3());

        viewHolder.getTextView1().setText(mDataSet.get(position).getHospital());
        viewHolder.getTextView2().setText(mDataSet.get(position).getType());
        viewHolder.getTextView3().setText(mDataSet.get(position).getPrice() + " ج");


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}