package com.ramveltrader.activity.otp.received;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramveltrader.R;
import com.ramveltrader.activity.BaseAppCompatActivity;
import com.ramveltrader.activity.otp.verify.OtpVerifyActivity;


/**
 * Created by system2 on 30-Jan-18.
 */

public class OtpReceiveActivity extends BaseAppCompatActivity implements OtpReceiveMvpView{
    TextView manual;
    ImageView rotate_image;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_otp_receive);

        manual = (TextView) findViewById(R.id.button_manual);
        rotate_image = (ImageView) findViewById(R.id.image_progress);

        Runnable runnable = new Runnable() {
            @SuppressLint("NewApi")
            @Override
            public void run() {
                rotate_image.animate().rotationBy(360).withEndAction(this).setDuration(2000).setInterpolator(new LinearInterpolator()).start();
            }
        };rotate_image.animate().rotationBy(360).withEndAction(runnable).setDuration(2000).setInterpolator(new LinearInterpolator()).start();


        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OtpReceiveActivity.this, OtpVerifyActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onContinueButtonClick(View v) {

    }

    @Override
    public void onExistingUserClick(View v) {

    }


}
