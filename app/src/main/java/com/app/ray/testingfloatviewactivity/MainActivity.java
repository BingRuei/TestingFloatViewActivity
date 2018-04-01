package com.app.ray.testingfloatviewactivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class MainActivity extends Activity {
    private WindowManager wm = null;
    private WindowManager.LayoutParams wmParams = null;
    private MyFloatView myFV = null;

    
//    private Timer timer;
//    private TimerTask task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        timer = new Timer();
//        task = new TimerTask() {
//
//            @Override
//            public void run() {
//
//                Message message = new Message();
//                //傳送訊息1
//                message.what = 1;
//                handler.sendMessage(message);
//
//            }
//
//        };
//        timer.schedule(task, 0, 990);


        //創建懸浮視窗
        createView();
    }

//    //TimerTask無法直接改變元件因此要透過Handler來當橋樑
//    private Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 1:
//                    //創建懸浮視窗
//                    createView();
//                    break;
//            }
//        }
//    };

    private void createView() {
        myFV = new MyFloatView(getApplicationContext());
        myFV.setImageResource(R.mipmap.ic_launcher);
        //獲取WindowManager
        wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //設置LayoutParams(全局變數）相關參數
        wmParams = ((MyApplication) getApplication()).getMywmParams();
        wmParams.type = LayoutParams.TYPE_PHONE;   //設置window type
        wmParams.format = PixelFormat.RGBA_8888;   //設置圖片格式，效果為背景透明
        //設置Window flag
        wmParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
                | LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;   //調整懸浮視窗至左上角
        //以屏幕左上角為原點，設置x、y初始值
        wmParams.x = 0;
        wmParams.y = 0;
        //設置懸浮視窗長寬數據
        wmParams.width = 100;
        wmParams.height = 100;
        //顯示myFloatView圖像
        wm.addView(myFV, wmParams);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在程式退出(Activity銷毀）時銷毀懸浮視窗
        wm.removeView(myFV);
    }
}