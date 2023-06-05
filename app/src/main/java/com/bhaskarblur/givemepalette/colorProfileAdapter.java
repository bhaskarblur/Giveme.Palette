package com.bhaskarblur.givemepalette;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class colorProfileAdapter extends RecyclerView.Adapter<colorProfileAdapter.viewHolder> {

    private Context context;
    public List<colorGenerateModel> modelList;
    public List<Boolean> locked = new ArrayList<>();

    public colorProfileAdapter(Context context, List<colorGenerateModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    public void setModelList(List<colorGenerateModel> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public colorProfileAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.colors_profile_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull colorProfileAdapter.viewHolder holder, int position) {
        holder.hex.setText(modelList.get(position).getRand());

        try {
            holder.layout.getBackground().setTint(
                    Color.parseColor(modelList.get(position).getRand()));

        } catch (Exception e) {
        }

        Animation slide_down = AnimationUtils.loadAnimation(context,
                R.anim.layout_fall_down);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                holder.itemView.startAnimation(slide_down);
            }
        }, 100);

    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout layout;
        private TextView hex;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            hex = itemView.findViewById(R.id.colorHex);
            layout = itemView.findViewById(R.id.colorBg);

            hex.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                            view,
                            PropertyValuesHolder.ofFloat("alpha", 0.6f)
                    );
                    scaleDown.setDuration(150);
                    scaleDown.start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ObjectAnimator scaleup = ObjectAnimator.ofPropertyValuesHolder(
                                    view,
                                    PropertyValuesHolder.ofFloat("alpha", 1f)
                            );
                            scaleup.setDuration(150);
                            scaleup.start();
                            hex.setText(" COPIED");

                        }
                    }, 150);

                    int sdk = android.os.Build.VERSION.SDK_INT;
                    if(getAdapterPosition()!=RecyclerView.NO_POSITION) {
                        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                            android.text.ClipboardManager clipboard = (android.text.ClipboardManager)
                                    context.
                                            getSystemService(Context.CLIPBOARD_SERVICE);
                            clipboard.setText(modelList.get(getAdapterPosition()).getRand());
                        } else {
                            android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                                    context.
                                            getSystemService(Context.CLIPBOARD_SERVICE);
                            android.content.ClipData clip = android.content.ClipData.newPlainText("text label",
                                    modelList.get(getAdapterPosition()).getRand());
                            clipboard.setPrimaryClip(clip);

                        }

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                                        view,
                                        PropertyValuesHolder.ofFloat("alpha", 0.6f)
                                );
                                scaleDown.setDuration(150);
                                scaleDown.start();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        ObjectAnimator scaleup = ObjectAnimator.ofPropertyValuesHolder(
                                                view,
                                                PropertyValuesHolder.ofFloat("alpha", 1f)
                                        );
                                        scaleup.setDuration(150);
                                        scaleup.start();
                                        scaleDown.setDuration(150);
                                        scaleDown.start();
                                        hex.setText( modelList.get(getAdapterPosition()).getRand());

                                    }
                                }, 150);

                            }

                        }, 2500);
                    }
                }
            });

        }

    }
}
