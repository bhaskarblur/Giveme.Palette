package com.bhaskarblur.givemepalette;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class colorAdapter extends RecyclerView.Adapter<colorAdapter.viewHolder> {

    private Context context;
    private List<colorModel> modelList;
    private onClickListener listener;

    public colorAdapter(Context context, List<colorModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    public void setModelList(List<colorModel> modelList) {
        this.modelList = modelList;
    }
    @NonNull
    @Override
    public colorAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.colors_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull colorAdapter.viewHolder holder, int position) {
        holder.name.setText(modelList.get(position).getName());
        holder.hex.setText(modelList.get(position).getHex());
        holder.rgb.setText(modelList.get(position).getRgb());
        holder.hsl.setText(modelList.get(position).getHsl());
        try {
            holder.layout.setBackgroundColor(
                    Color.parseColor(modelList.get(position).getHex()));
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
        private TextView rgb;
        private TextView hsl;
        private TextView name;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.colorName);
            hex= itemView.findViewById(R.id.colorHex);
            rgb= itemView.findViewById(R.id.colorRgb);
            hsl= itemView.findViewById(R.id.colorHsl);
            layout= itemView.findViewById(R.id.colorBg);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION) {
                        listener.onClick(getAdapterPosition(), view);

                    }
                }
            });
        }
    }

    public void Animation(View view) {
        Animation slide_down = AnimationUtils.loadAnimation(context,
                R.anim.slide_out_up);
        view.startAnimation(slide_down);
    }
    interface onClickListener {
        void onClick(int index, View view);
    }
     void setOnClickListener(onClickListener listener) {

        this.listener= listener;
    }
}
