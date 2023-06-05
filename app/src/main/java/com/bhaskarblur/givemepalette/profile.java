package com.bhaskarblur.givemepalette;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhaskarblur.givemepalette.databinding.FragmentProfileBinding;
import com.bhaskarblur.givemepalette.databinding.FragmentSavedBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentProfileBinding binding;
    private List<colorGenerateModel> colorModelList;
    private List<colorGenerateModel> paletteModelList;
    private SharedPreferences sharedPreferences;
    private colorProfileAdapter colorAdapter;
    private colorProfileAdapter colorAdapter2;

    public profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile.
     */
    // TODO: Rename and change types and number of parameters
    public static profile newInstance(String param1, String param2) {
        profile fragment = new profile();
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
        binding = FragmentProfileBinding.inflate(inflater);
        sharedPreferences = getActivity().getSharedPreferences("savedColors",0);
        manageUI();
        return binding.getRoot();
    }

    private void manageUI() {
        colorModelList= new ArrayList<>();
        colorAdapter= new colorProfileAdapter(getActivity(), colorModelList);
        paletteModelList= new ArrayList<>();
        colorAdapter2= new colorProfileAdapter(getActivity(), paletteModelList);
        LinearLayoutManager llm= new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.HORIZONTAL);
        LinearLayoutManager llm2= new LinearLayoutManager(getActivity());
        llm2.setOrientation(RecyclerView.HORIZONTAL);
        binding.svcolor1.setLayoutManager(llm);
        binding.svcolor1.setAdapter(colorAdapter);
        binding.svcolor2.setLayoutManager(llm2);
        binding.svcolor2.setAdapter(colorAdapter2);
        addData();

        binding.share1.setOnClickListener(new View.OnClickListener() {
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
            }
        });

        binding.share2.setOnClickListener(new View.OnClickListener() {
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
            }
        });
    }

    private void addData() {
        Set<String> set1= sharedPreferences.getStringSet("colorList", new HashSet<>());
        Set<String> set2= sharedPreferences.getStringSet("paletteList", new HashSet<>());
        int totalColors= sharedPreferences.getInt("totalColors", 0);
        int totalPalettes= sharedPreferences.getInt("totalColorsPalette", 0);


        for(int i=0; i<=totalColors; i++) {
            String color_ = sharedPreferences.getString("Color" + i, "");
            if(color_!=null & !color_.equals("") &&
                    !color_.equals(" ")) {

                colorModelList.add(new colorGenerateModel(
                        Color.parseColor(color_), color_
                ));
            }
        }


        for(int i=0; i<=totalPalettes; i++) {
            String color_ = sharedPreferences.getString("ColorPalette" + i, "");
            if(color_!=null & !color_.equals("") &&
                    !color_.equals(" ")) {
                paletteModelList.add(new colorGenerateModel(
                        Color.parseColor(color_), color_
                ));
            }
        }
        colorAdapter.notifyDataSetChanged();
        colorAdapter2.notifyDataSetChanged();
    }

    private String RgbConverter(int color) {
        try {
            int r = Color.red(color);
            int g = Color.green(color);
            int b = Color.blue(color);
            return "rgb("+r+","+g+"," + b+")";
        }catch (Exception e) {
            return "";
        }


    }

    private String HslConverter(int color) {
        try {
            float[] hsv = new float[3];
            Color.colorToHSV(color, hsv);
            return "hsv("+ (int) hsv[0]+","+ (int) hsv[1]+","+ (int) hsv[2]+")";
        }catch (Exception e) {
            return "";
        }

    }
}