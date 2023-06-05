package com.bhaskarblur.givemepalette;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bhaskarblur.givemepalette.databinding.FragmentHomeBinding;

import java.util.HashSet;
import java.util.Set;


public class homeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SharedPreferences sharedPreferences;
    private boolean saveTrue= false;
    private FragmentHomeBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int totalColors;
    public homeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment();
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
        binding = FragmentHomeBinding.inflate(inflater);
        sharedPreferences = getActivity().getSharedPreferences("savedColors",0);
         totalColors= sharedPreferences.getInt("totalColors", 0);
        manageUI();

        return binding.getRoot();
    }

    private void manageUI() {
        binding.rgbEdit.setEnabled(false);

        final String[] oldColor = new String[1];
        binding.colorEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>2) {
                    oldColor[0] ="#f0f0f0";
                            try {
                                binding.colorEditText.setError(null);
                                int colorFrom = Color.parseColor(oldColor[0]);
                                int colorTo = Color.parseColor("#"+String.valueOf(editable));
                                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                                colorAnimation.setDuration(200); // milliseconds
                                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animator) {
                                        binding.bg.setBackgroundColor((int) animator.getAnimatedValue());
                                        rgbConverter(colorTo);
                                        oldColor[0]= String.valueOf(colorTo);
                                        binding.saveButton.
                                                setBackgroundResource(R.drawable.button1bgactive);
                                        binding.saveButton.setTextColor(
                                                Color.parseColor("#141414"));
                                        saveTrue=true;
                                    }

                                });
                                colorAnimation.start();

                            } catch (Exception e) {
                                saveTrue=false;
                                //       throw new RuntimeException(e);
                                if (e.toString().toLowerCase().contains("unknown")) {
                                if (editable.length() == 3 || editable.length() == 6) {
                                        binding.colorEditText.setError("No such Color Exist, please check your hex code");
                                        binding.rgbEdit.setText("");
                                    binding.saveButton.
                                            setBackgroundResource(R.drawable.button1bg);
                                    binding.saveButton.setTextColor(
                                            Color.parseColor("#737373"));

                                    }
                                else {
                                    binding.colorEditText.setError(null);
                                    int colorFrom = Color.parseColor(oldColor[0]);
                                    int colorTo = Color.parseColor("#f0f0f0");
                                    ValueAnimator colorAnimation_ = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                                    colorAnimation_.setDuration(200); // milliseconds
                                    colorAnimation_.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator animator) {
                                            binding.bg.setBackgroundColor((int) animator.getAnimatedValue());
                                            rgbConverter(colorTo);
                                            oldColor[0]= String.valueOf(colorTo);
                                            binding.saveButton.
                                                    setBackgroundResource(R.drawable.button1bg);
                                            binding.saveButton.setTextColor(
                                                    Color.parseColor("#737373"));
                                        }

                                    });
                                    colorAnimation_.start();
                                }
                                }

                            }

                }

                else {
                    binding.saveButton.
                            setBackgroundResource(R.drawable.button1bg);
                    saveTrue=false;
                }
            }
        });

        binding.clearText.setOnClickListener(new View.OnClickListener() {
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
                binding.rgbEdit.setText("");
                binding.colorEditText.setText("");
                binding.colorEditText.setError(null);
            }
        });

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveTrue) {
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

                    saveColor("#" + binding.colorEditText.getText().toString());
                }
            }
        });

        binding.profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile homeFragment=new profile();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fade_2, R.anim.fade);
                transaction.replace(R.id.mainFragment, homeFragment);
                transaction.commit();
            }
        });
    }


    SharedPreferences.Editor editor;

    private void saveColor(String color) {
        editor= sharedPreferences.edit();
        editor.putString("Color"+totalColors, color);
        totalColors=totalColors+1;
        editor.putInt("totalColors", totalColors);
        editor.commit();
        Toast.makeText(getActivity(), "Color Saved! Restart your app to see new saved", Toast.LENGTH_SHORT).show();
    }

    private void rgbConverter(int color) {
        try {
            int r = Color.red(color);
            int g = Color.green(color);
            int b = Color.blue(color);
            binding.rgbEdit.setText("rgb("+r+","+g+"," +
                    b+")");

        }
        catch (Exception e){
            Toast.makeText(getActivity(), color, Toast.LENGTH_SHORT).show();
            binding.rgbEdit.setText("");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}