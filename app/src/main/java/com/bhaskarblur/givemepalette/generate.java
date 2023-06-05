package com.bhaskarblur.givemepalette;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bhaskarblur.givemepalette.databinding.FragmentGenerateBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link generate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class generate extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentGenerateBinding binding;
    private SharedPreferences sharedPreferences;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<colorGenerateModel> colorList;
    private colorGenerateAdapter adapter;
    private int totalPalettesColors;
    int totalColors;
    private int listSize=5;
    public generate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment generate.
     */
    // TODO: Rename and change types and number of parameters
    public static generate newInstance(String param1, String param2) {
        generate fragment = new generate();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentGenerateBinding.inflate(inflater);
        sharedPreferences = getActivity().getSharedPreferences("savedColors",0);
        totalPalettesColors = sharedPreferences.getInt("totalColorsPalette",0);
        totalColors= sharedPreferences.getInt("totalColors", 0);
        listSize= sharedPreferences.getInt("listSize",5);
        manageUI();
        return binding.getRoot();
    }


    private void manageUI() {
        colorList=new ArrayList<colorGenerateModel>();
        adapter= new colorGenerateAdapter(getActivity(), colorList);
        LinearLayoutManager llm= new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        binding.colorsRV.setLayoutManager(llm);
        binding.colorsRV.setAdapter(adapter);
        manageData();

        adapter.setOnClickListener(new colorGenerateAdapter.onClickListener() {
            @Override
            public void onClick(int index) {
                final Dialog dialog=new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.generator_dialog);

                Button add = dialog.findViewById(R.id.addColor);
                Button remove= dialog.findViewById(R.id.removeColor);
                Button save = dialog.findViewById(R.id.saveColor);
                Button copy= dialog.findViewById(R.id.copyColor);
                TextView cancel= dialog.findViewById(R.id.clear_text2);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(listSize<7) {
                            listSize = listSize +1;
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("listSize", listSize);
                            editor.commit();
                            com.bhaskarblur.givemepalette.colorList randomColor=new colorList();
                            String rand= randomColor.getRandomColor();
                            colorList.add(index+1,new colorGenerateModel(Color.parseColor(rand), rand));
                            adapter.notifyItemInserted(index+1);

                        }
                        else {
                            Toast.makeText(getActivity(), "You can't add more than 7 colors in this list!", Toast.LENGTH_SHORT).show();
                        }

                        dialog.cancel();
                    }
                });

                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(listSize>1) {
                            listSize = listSize - 1;
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("listSize", listSize);
                            editor.commit();
                            colorList.remove(index);
                            adapter.notifyItemRemoved(index);
                            dialog.cancel();
                        }
                        else {
                            Toast.makeText(getActivity(), "Minimum 1 color is required!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences.Editor editor;
                        editor= sharedPreferences.edit();
                        editor.putString("Color"+totalColors, colorList.get(index).getRand());
                        totalColors=totalColors+1;
                        editor.putInt("totalColors", totalColors);
                        editor.commit();
                        Toast.makeText(getActivity(), "Color Saved! Restart your app to see new saved", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                        }, 150);
                        dialog.cancel();
                    }
                });
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations=R.style.dialogAnimation;
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations=R.style.dialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });

        adapter.onLockedClickListener(new colorGenerateAdapter.onLockedClickListener() {
            @Override
            public void onLockedClick(int index, boolean bool) {
                if(bool) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Set<String> set = sharedPreferences.getStringSet("lockedColors", new HashSet<>());
                    Set<String> set_ = set;
                    set_.add(String.valueOf(colorList.get(index).getRand()));
                    editor.putStringSet("lockedColors", set_);
                    editor.commit();
                }
                else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Set<String> set = sharedPreferences.getStringSet("lockedColors", new HashSet<>());
                    set.remove(String.valueOf(colorList.get(index).getRand()));
                    editor.putStringSet("lockedColors", set);
                    editor.commit();
                }
            }
        });

        binding.generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        manageData();
                    }
                },75);

            }
        });

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                    }, 150);
                    SharedPreferences.Editor editor;
                editor= sharedPreferences.edit();

                for(int i=0;i<colorList.size();i++) {
                    editor.putString("ColorPalette" + totalPalettesColors, colorList.get(i
                    ).getRand());
                    totalPalettesColors = totalPalettesColors + 1;
                    editor.putInt("totalColorsPalette", totalPalettesColors);
                }
                editor.commit();
                Toast.makeText(getActivity(), "Color Palette Saved! Restart your app to see new saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void manageData() {
        colorList=new ArrayList<colorGenerateModel>();
        addLockedColors();
        addRandomColors();
        
    }

    private void addRandomColors() {
        int i_=listSize-colorList.size();

        for(int i=0; i<i_ ;i++) {

            try {
                com.bhaskarblur.givemepalette.colorList randomColor=new colorList();
                String rand= randomColor.getRandomColor();
                if(colorList.contains(new colorGenerateModel(Color.parseColor(rand), rand))) {
                    rand= randomColor.getRandomColor();
                }
                colorList.add(new colorGenerateModel(Color.parseColor(rand), rand));
                adapter.locked.add(false);
            }
            catch (Exception e) {

            }


        }
        adapter.setModelList(colorList);
        adapter.notifyDataSetChanged();
    }

    private void addLockedColors() {
        colorList.clear();
        adapter.locked.clear();
        Set<String > set = sharedPreferences.getStringSet("lockedColors", new HashSet<>());
        set.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                try {


                    colorList.add(new colorGenerateModel(Color.parseColor(s), s));
                    adapter.locked.add(true);
                }
                catch (Exception e) {

                }
            }
        });



    }
}