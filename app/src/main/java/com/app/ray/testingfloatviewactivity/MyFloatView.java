package com.app.ray.testingfloatviewactivity;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class MyFloatView extends ImageView {
    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;

    private WindowManager wm = (WindowManager) getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

    //此wmParams為獲取的全局變數，用以保存懸浮視窗的屬性
    private WindowManager.LayoutParams wmParams = ((MyApplication) getContext().getApplicationContext()).getMywmParams();

    public MyFloatView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    float lastX = 0, lastY = 0, nowX = 0, nowY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //獲取相對屏幕的坐標，即以屏幕左上角為原點
        x = event.getRawX();
        y = event.getRawY() - 25;   //25是系統狀態欄的高度
        Log.i("currP", "currX" + x + "====currY" + y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //獲取相對View的坐標，即以此View左上角為原點
                mTouchStartX = event.getX();
                mTouchStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                nowX = convertPixelToDp(x - mTouchStartX, getContext());
                nowY = convertPixelToDp(y - mTouchStartY, getContext());
                float judge = ((nowX - lastX) > 0 ? (nowX - lastX) : -(nowX - lastX))+
                        ((nowY - lastY) > 0 ? (nowY - lastY) : -(nowY - lastY));
                if (judge > 50) {   // 50是我隨便設的，只是要看點擊前的座標跟移動後的座標有沒有變很多而已
                    updateViewPosition();
                    mTouchStartX = mTouchStartY = 0;
                    lastX = nowX;
                    lastY = nowY;
                } else {
                    Toast.makeText(getContext(), "Hi...", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return true;
    }

    public static float convertPixelToDp(float px, Context context) {
        float dp = px / getDensity(context);
        return dp;
    }

    public static float getDensity(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.density;
    }

    private void updateViewPosition() {
        //更新浮動視窗位置參數
        wmParams.x = (int) (x - mTouchStartX);
        wmParams.y = (int) (y - mTouchStartY);
        wm.updateViewLayout(this, wmParams);
    }

}
