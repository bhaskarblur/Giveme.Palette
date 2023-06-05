package com.bhaskarblur.givemepalette;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class colorGenerateAdapter extends RecyclerView.Adapter<colorGenerateAdapter.viewHolder> {

    private Context context;
    public List<colorGenerateModel> modelList;
    private onClickListener listener;
    public List<Boolean> locked=new ArrayList<>();
    private onLockedClickListener listener2;

    public colorGenerateAdapter(Context context, List<colorGenerateModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    public void setModelList(List<colorGenerateModel> modelList) {
        this.modelList = modelList;
    }
    @NonNull
    @Override
    public colorGenerateAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.colors_generate_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull colorGenerateAdapter.viewHolder holder, int position) {
        holder.hex.setText(modelList.get(position).getRand());
        if(position >= locked.size()) {
            if (locked.get(position - 1).equals(true)) {
                holder.icon.setImageResource(R.drawable.locked);
            } else {
                holder.icon.setImageResource(R.drawable.unlocked);
            }
        }
        else {
            if (locked.get(position).equals(true)) {
                holder.icon.setImageResource(R.drawable.locked);
            } else {
                holder.icon.setImageResource(R.drawable.unlocked);
            }
        }

        try {
            holder.layout.setBackgroundColor(
                    modelList.get(position).getHex());
        }
        catch (Exception e) {}

        Animation slide_down = AnimationUtils.loadAnimation(context,
                R.anim.layout_fall_down);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                holder.itemView.startAnimation(slide_down);
            }
        },100);

    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout layout;
        private TextView hex;
        private ImageView icon;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            hex= itemView.findViewById(R.id.colorHex);
            layout= itemView.findViewById(R.id.colorBg);
            icon= itemView.findViewById(R.id.lockedIcon);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION) {
                        listener.onClick(getAdapterPosition());

                    }
                }
            });

           icon.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   int position=getAdapterPosition();
                   if(position!=RecyclerView.NO_POSITION) {
                       ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                               view,
                               PropertyValuesHolder.ofFloat("scaleX", 0.8f),
                               PropertyValuesHolder.ofFloat("scaleY", 0.8f)
                       );
                       scaleDown.setDuration(150);
                       scaleDown.start();
                       new Handler().postDelayed(new Runnable() {
                           @Override
                           public void run() {
                               ObjectAnimator scaleup = ObjectAnimator.ofPropertyValuesHolder(
                                       view,
                                       PropertyValuesHolder.ofFloat("scaleX", 1f),
                                       PropertyValuesHolder.ofFloat("scaleY", 1f)
                               );
                               scaleup.setDuration(150);
                               scaleup.start();
                           }
                       },150);

                       if(locked.get(getAdapterPosition()).equals(true)) {
                           locked.set(getAdapterPosition(), false);
                           icon.setImageResource(R.drawable.unlocked);
                           listener2.onLockedClick(getAdapterPosition(), false);
                       }
                       else{
                           locked.set(getAdapterPosition(), true);
                           listener2.onLockedClick(getAdapterPosition(), true);
                           icon.setImageResource(R.drawable.locked);
                       }
                   }
               }
           });
        }
    }

    interface onClickListener {
        void onClick(int index);

    }

    interface onLockedClickListener {

        void onLockedClick(int index, boolean bool);
    }
     void setOnClickListener(onClickListener listener) {

        this.listener= listener;
    }

    void onLockedClickListener(onLockedClickListener listener) {

        this.listener2= listener;
    }
}
