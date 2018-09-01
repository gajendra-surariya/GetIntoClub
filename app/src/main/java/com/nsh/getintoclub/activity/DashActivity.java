package com.nsh.getintoclub.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.nsh.getintoclub.R;
import com.nsh.getintoclub.adapter.MainAdapter;
import com.nsh.getintoclub.model.Quote;

import java.util.ArrayList;
import java.util.List;

public class DashActivity extends AppCompatActivity {

    TextView knowApp;
    RelativeLayout topText;
    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    List<Quote> quoteList;
    CardView submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        initUI();
    }

    public void initUI() {
        recyclerView = findViewById(R.id.recyclerView);
        submitBtn = findViewById(R.id.submitBtn);
        knowApp = findViewById(R.id.knowApp);
        topText = findViewById(R.id.topText);
        recyclerView.setAlpha(0f);
        setupData();
    }

    public void setupData() {

        recyclerView.setAlpha(0f);

        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(recyclerView, "alpha", 0, 1);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(recyclerView, "translationY", 100, 0);

        objectAnimator2.setDuration(750);
        objectAnimator2.setInterpolator(new AnticipateOvershootInterpolator());
        objectAnimator3.setDuration(750);
        objectAnimator3.setInterpolator(new AnticipateOvershootInterpolator());

        objectAnimator3.setStartDelay(100);
        objectAnimator2.setStartDelay(100);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator2, objectAnimator3);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                recyclerView.setAlpha(1f);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        quoteList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            quoteList.add(new Quote("\"If you choke a smurf, what color does it turn to?\"", getDrawable(R.drawable.green_gradient), "Question.", "Answer"));
            quoteList.add(new Quote("\"I am so good at sleeping, I can do it with my eyes closed.\"", getDrawable(R.drawable.red_gradient), "Skills.", "Set"));
            quoteList.add(new Quote("\"It doesn't matter where you come from, what matters is what you choose to be.\"", getDrawable(R.drawable.blue_gradient), "Contact.", "Info"));
        }
        mainAdapter = new MainAdapter(quoteList, DashActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.END);
        snapHelperStart.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(mainAdapter);
        recyclerView.scrollToPosition(2);



        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContactDetail.roll.length() > 0 && SkillDetail.skillet.length() > 0) {
                    Toast.makeText(DashActivity.this, "Submitted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DashActivity.this, Database.class));
                } else
                    Toast.makeText(DashActivity.this, "Seams like you missed entering Roll Number or Skills.", Toast.LENGTH_SHORT).show();
            }
        });

        knowApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashActivity.this, AboutAppteam.class));
            }
        });
    }
}
