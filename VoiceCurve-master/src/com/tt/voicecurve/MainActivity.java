package com.tt.voicecurve;

import com.tt.voicecurve.R;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private RecordThread recordThread;
    private LightSensorManager lightSensorManager;
    private CurveView mCurveView;
    private TextView mTextView;
    private Button mButton;
    private boolean mIsStop = true;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
            case RecordThread.UPDATE_VIEW:
                mCurveView.addVisiablePoint(new Point(msg.arg2, msg.arg1));
                break;
            case LightSensorManager.LIGHT_WHAT:
                mTextView.setText(String.valueOf(msg.obj));
                break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_main);
        mCurveView = (CurveView) this.findViewById(R.id.curve);
        mTextView = (TextView) this.findViewById(R.id.textView1);
        lightSensorManager = new LightSensorManager(MainActivity.this, mHandler);

        mButton = (Button) this.findViewById(R.id.button1);
        mButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (mIsStop) {
                    recordThread = new RecordThread(mHandler);
                    recordThread.startRecord();
                    mCurveView.clearScreen();
                    mButton.setText("Í£Ö¹");
                    mIsStop = false;
                } else {
                    recordThread.stopRecord();
                    mButton.setText("¿ªÊ¼");
                    mIsStop = true;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        lightSensorManager.unRegisterSensor();
    }
}
