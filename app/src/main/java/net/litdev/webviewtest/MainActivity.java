package net.litdev.webviewtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

public class MainActivity extends Activity {

    private DrawerLayout drawer_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_layout.addDrawerListener(new DrawerListener());

        drawer_layout.openDrawer(Gravity.LEFT);
    }

    public void OpenWebHtml(View v){
        startActivity(new Intent(MainActivity.this,WebHtmlActivity.class));
    }

    public void OpenLocalHtml(View v){
        startActivity(new Intent(MainActivity.this,LocalHtmlActivity.class));
    }

    public void btn_HomeLeft(View v){
        drawer_layout.openDrawer(Gravity.LEFT);
    }

    public void btn_HomeRight(View v){
        drawer_layout.openDrawer(Gravity.RIGHT);
    }

    public void btnRight(View v){
        drawer_layout.closeDrawer(Gravity.RIGHT);
    }

    /**
     * Drawer监听器
     */
    private class DrawerListener implements DrawerLayout.DrawerListener
    {

        /**
         * 滑动中
         * @param drawerView
         * @param slideOffset
         */
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            //Log.i("app","onDrawerSlide-----"+drawerView.getId()+"----slideOffset"+slideOffset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            //Log.i("app","打开");
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            //Log.i("app","关闭");
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            switch (newState)
            {
                case DrawerLayout.STATE_DRAGGING:
                    Log.i("app","状态改变----"+"拖动状态");
                    break;
                case DrawerLayout.STATE_IDLE:
                    Log.i("app","状态改变----"+"静止状态");
                    break;
                case DrawerLayout.STATE_SETTLING:
                    Log.i("app","状态改变----"+"设置状态");
                    break;
                default:
                    break;
            }
        }
    }



}

