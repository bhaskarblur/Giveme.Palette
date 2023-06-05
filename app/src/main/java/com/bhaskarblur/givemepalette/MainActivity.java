package com.bhaskarblur.givemepalette;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import com.bhaskarblur.givemepalette.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.gray, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.gray));
        }
        manageNavBar();
    }

    private void manageNavBar() {
        binding.homelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Picasso.get().load(R.drawable.ic_homeselected).into(binding.homeIcon);
                binding.homeIcon.setImageResource(R.drawable.homeselected);
                binding.homeText.setTextColor(Color.parseColor("#141414"));


                binding.generateIcon.setImageResource(R.drawable.generatenot);
                binding.generateText.setTextColor(Color.parseColor("#737373"));


                binding.profileIcon.setImageResource(R.drawable.profilenot);
                binding.profileText.setTextColor(Color.parseColor("#737373"));

                homeFragment homeFragment=new homeFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fade_2, R.anim.fade);
                transaction.replace(R.id.mainFragment, homeFragment);
                transaction.commit();

            }
        });

        binding.generateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.homeIcon.setImageResource(R.drawable.homenot);
                binding.homeText.setTextColor(Color.parseColor("#737373"));

                binding.generateIcon.setImageResource(R.drawable.generateselected);
                binding.generateText.setTextColor(Color.parseColor("#141414"));


                binding.profileIcon.setImageResource(R.drawable.profilenot);
                binding.profileText.setTextColor(Color.parseColor("#737373"));

                generate homeFragment=new generate();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fade_2, R.anim.fade);
                transaction.replace(R.id.mainFragment, homeFragment);
                transaction.commit();
            }
        });


        binding.profilelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.homeIcon.setImageResource(R.drawable.homenot);
                binding.homeText.setTextColor(Color.parseColor("#737373"));

                binding.generateIcon.setImageResource(R.drawable.generatenot);
                binding.generateText.setTextColor(Color.parseColor("#737373"));

                binding.profileIcon.setImageResource(R.drawable.profileselected);
                binding.profileText.setTextColor(Color.parseColor("#141414"));

                savedFragment homeFragment=new savedFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fade_2, R.anim.fade);
                transaction.replace(R.id.mainFragment, homeFragment);
                transaction.commit();
            }
        });
    }
    private void manageUI() {
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().popBackStack();

            if (getSupportFragmentManager().getBackStackEntryCount() < 2) {
                // set the home tab as default;
                binding.homeIcon.setImageResource(R.drawable.homeselected);
                binding.homeText.setTextColor(Color.parseColor("#141414"));


                binding.generateIcon.setImageResource(R.drawable.generatenot);
                binding.generateText.setTextColor(Color.parseColor("#737373"));


                binding.profileIcon.setImageResource(R.drawable.profilenot);
                binding.profileText.setTextColor(Color.parseColor("#737373"));

                homeFragment homeFragment=new homeFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fade_2, R.anim.fade);
                transaction.replace(R.id.mainFragment, homeFragment);
                transaction.commit();

            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Exit?")
                    .setMessage("Do you want to exit the app?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.show();
        }

    }

}