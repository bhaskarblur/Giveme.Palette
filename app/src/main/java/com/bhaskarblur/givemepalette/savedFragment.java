package com.bhaskarblur.givemepalette;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
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

import com.bhaskarblur.givemepalette.databinding.FragmentSavedBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link savedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class savedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<colorModel> colorModelList;
    private List<colorModel> paletteModelList;
    private SharedPreferences sharedPreferences;
    private FragmentSavedBinding binding;
    private colorAdapter colorAdapter;
    private colorAdapter colorAdapter2;
    private Set<String> lockedSet;
    private int listSize;

    public savedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment savedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static savedFragment newInstance(String param1, String param2) {
        savedFragment fragment = new savedFragment();
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
        binding = FragmentSavedBinding.inflate(inflater);
        sharedPreferences = getActivity().getSharedPreferences("savedColors",0);
        lockedSet = sharedPreferences.getStringSet("lockedColors", new HashSet<>());
        listSize= sharedPreferences.getInt("listSize",5);
        manageData();
        manageUI();

        return binding.getRoot();
    }

    private void manageUI() {
        binding.colorSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation slide_down = AnimationUtils.loadAnimation(getActivity(),
                        R.anim.layout_fall_down);
                binding.colorsLayout.setAnimation(slide_down);
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
                binding.colorSelect.setBackgroundResource(R.drawable.tab1bg);
                binding.paletteSelect.setBackgroundResource(R.drawable.tab2bg);
                binding.colorsLayout.setVisibility(View.VISIBLE);
                binding.paletteLayout.setVisibility(View.GONE);
                binding.paletteSelect.setTextColor(Color.parseColor("#737373"));
                binding.colorSelect.setTextColor(Color.parseColor("#141414"));

            }
        });

        binding.paletteSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation slide_down = AnimationUtils.loadAnimation(getActivity(),
                        R.anim.layout_fall_down);
                binding.paletteLayout.setAnimation(slide_down);
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
                binding.colorSelect.setBackgroundResource(R.drawable.tab2bg);
                binding.paletteSelect.setBackgroundResource(R.drawable.tab1bg);
                binding.colorsLayout.setVisibility(View.GONE);
                binding.paletteLayout.setVisibility(View.VISIBLE);
                binding.colorSelect.setTextColor(Color.parseColor("#737373"));
                binding.paletteSelect.setTextColor(Color.parseColor("#141414"));

            }
        });
    }
    SharedPreferences.Editor editor;
    private void manageData() {
        colorModelList= new ArrayList<>();
        colorAdapter= new colorAdapter(getActivity(), colorModelList);
        paletteModelList= new ArrayList<>();
        colorAdapter2= new colorAdapter(getActivity(), paletteModelList);
        LinearLayoutManager llm= new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        LinearLayoutManager llm2= new LinearLayoutManager(getActivity());
        llm2.setOrientation(RecyclerView.VERTICAL);
        binding.colorsRV.setLayoutManager(llm);
        binding.colorsRV.setAdapter(colorAdapter);
        binding.paletteRV.setLayoutManager(llm2);
        binding.paletteRV.setAdapter(colorAdapter2);
        addData();
        colorAdapter.setOnClickListener(new colorAdapter.onClickListener() {
            @Override
            public void onClick(int index, View view__) {
                final Dialog dialog=new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.profile_dialog);

                Button remove = dialog.findViewById(R.id.removeColor);
                Button copy= dialog.findViewById(R.id.copyColor);
                TextView cancel= dialog.findViewById(R.id.clear_text2);
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Animation slide_down = AnimationUtils.loadAnimation(getActivity(),
                                R.anim.layout_fall_down);
                        binding.paletteLayout.setAnimation(slide_down);
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
                       editor= sharedPreferences.edit();
                        int totalColors= sharedPreferences.getInt("totalColors", 0);
                        int totalColors__= sharedPreferences.getInt("totalColorsPalette", 0);
                        colorModelList.remove(index);
                        colorAdapter.notifyItemRemoved(index);
                        editor.clear();
                        editor.putInt("totalColors",totalColors-1);
                        editor.putInt("totalColorsPalette",totalColors__);
                        editor.putStringSet("lockedColors", lockedSet);
                        editor.putInt("listSize", listSize);
                        for(int i=0;i<colorModelList.size();i++) {
                            editor.putString("Color"+i, colorModelList.get(i).getHex());
                        }
                        for(int i_=0;i_<paletteModelList.size();i_++) {
                            editor.putString("ColorPalette"+i_, paletteModelList.get(i_).getHex());
                        }
                        editor.commit();

                        dialog.cancel();

                    }
                });

                copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Animation slide_down = AnimationUtils.loadAnimation(getActivity(),
                                R.anim.layout_fall_down);
                        binding.paletteLayout.setAnimation(slide_down);
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
                        int sdk = android.os.Build.VERSION.SDK_INT;
                        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                            android.text.ClipboardManager clipboard = (android.text.ClipboardManager)getActivity().
                                    getSystemService(Context.CLIPBOARD_SERVICE);
                            clipboard.setText("hex: "+colorModelList.get(index)
                                    .getHex()+", rgb: "+colorModelList.get(index)
                                    .getRgb()+", hsv: "+colorModelList.get(index)
                                    .getHsl());
                        } else {
                            android.content.ClipboardManager clipboard = (android.content.ClipboardManager)getActivity().
                                    getSystemService(Context.CLIPBOARD_SERVICE);
                            android.content.ClipData clip = android.content.ClipData.newPlainText("text label",
                                    "hex: "+colorModelList.get(index)
                                            .getHex()+", rgb: "+colorModelList.get(index)
                                            .getRgb()+", hsv: "+colorModelList.get(index)
                                            .getHsl());
                            clipboard.setPrimaryClip(clip);
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Animation slide_down = AnimationUtils.loadAnimation(getActivity(),
                                R.anim.layout_fall_down);
                        binding.paletteLayout.setAnimation(slide_down);
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
                dialog.getWindow().setGravity(Gravity.BOTTOM);

            }
        });

        colorAdapter2.setOnClickListener(new colorAdapter.onClickListener() {
            @Override
            public void onClick(int index, View view__) {
                final Dialog dialog=new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.profile_dialog);

                Button remove = dialog.findViewById(R.id.removeColor);
                Button copy= dialog.findViewById(R.id.copyColor);
                TextView cancel= dialog.findViewById(R.id.clear_text2);
                remove.setOnClickListener(new View.OnClickListener() {
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
                        editor= sharedPreferences.edit();
                        int totalColors= sharedPreferences.getInt("totalColorsPalette", 0);
                        int totalColors__= sharedPreferences.getInt("totalColors", 0);
                        paletteModelList.remove(index);
                        colorAdapter2.notifyItemRemoved(index);
                        editor.clear();
                        editor.putInt("totalColorsPalette",totalColors-1);
                        editor.putInt("totalColors",totalColors__);
                        editor.putStringSet("lockedColors", lockedSet);
                        editor.putInt("listSize", listSize);
                        for(int i=0;i<paletteModelList.size();i++) {
                            editor.putString("ColorPalette"+i, paletteModelList.get(i).getHex());
                        }
                        for(int i=0;i<colorModelList.size();i++) {
                            editor.putString("Color"+i, colorModelList.get(i).getHex());
                        }
                        editor.commit();

                        dialog.cancel();
                    }
                });

                copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Animation slide_down = AnimationUtils.loadAnimation(getActivity(),
                                R.anim.layout_fall_down);
                        binding.paletteLayout.setAnimation(slide_down);
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
                        int sdk = android.os.Build.VERSION.SDK_INT;
                        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                            android.text.ClipboardManager clipboard = (android.text.ClipboardManager)getActivity().
                                    getSystemService(Context.CLIPBOARD_SERVICE);
                            clipboard.setText("hex: "+paletteModelList.get(index)
                                    .getHex()+", rgb: "+paletteModelList.get(index)
                                    .getRgb()+", hsv: "+paletteModelList.get(index)
                                    .getHsl());
                        } else {
                            android.content.ClipboardManager clipboard = (android.content.ClipboardManager)getActivity().
                                    getSystemService(Context.CLIPBOARD_SERVICE);
                            android.content.ClipData clip = android.content.ClipData.newPlainText("text label",
                                    "hex: "+paletteModelList.get(index)
                                            .getHex()+", rgb: "+paletteModelList.get(index)
                                            .getRgb()+", hsv: "+paletteModelList.get(index)
                                            .getHsl());
                            clipboard.setPrimaryClip(clip);
                        }
                        Toast.makeText(getActivity(), "Color copied!", Toast.LENGTH_SHORT).show();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Animation slide_down = AnimationUtils.loadAnimation(getActivity(),
                                R.anim.layout_fall_down);
                        binding.paletteLayout.setAnimation(slide_down);
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

            colorModelList.add(new colorModel(
                    color_,
                    "Color " + String.valueOf(i),
                    RgbConverter(Color.parseColor(color_))
                    , HslConverter(Color.parseColor(color_))
                    , "Color"+String.valueOf(i)
            ));
            }
        }


        for(int i=0; i<=totalPalettes; i++) {
            String color_ = sharedPreferences.getString("ColorPalette" + i, "");
            if(color_!=null & !color_.equals("") &&
                    !color_.equals(" ")) {
                paletteModelList.add(new colorModel(
                        color_,
                        "Color " + i,
                        RgbConverter(Color.parseColor(color_)
                        ), HslConverter(Color.parseColor(color_)
                ), "Color" + i
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

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if(editor!=null) {
//        }
    }
}