package com.luoye.chat.utils;

import android.app.Activity;
import android.graphics.Point;

import androidx.customview.widget.ViewDragHelper;
import androidx.drawerlayout.widget.DrawerLayout;

import com.luoye.apptool.utils.BaseUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * drawerLayout 全屏划出
 */
public class DrawerLeftEdge {


    private static List<DrawerLayout> drawerLayoutList = new ArrayList<>();

    /**
     * 设置DrawerLayout全屏滑动，参数为1的时候
     *
     * @param activity               当前activity
     * @param drawerLayout           抽屉
     * @param displayWidthPercentage 滑动弹出侧滑栏的范围，0-1
     */
    public static void setDrawerLeftEdgeSize(Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null || drawerLayoutList.contains(drawerLayout))
            return;
        try {
            // 找到 ViewDragHelper 并设置 Accessible 为true
            Field leftDraggerField = drawerLayout.getClass().getDeclaredField("mLeftDragger");//Right
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);

            // 找到 edgeSizeField 并设置 Accessible 为true
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);

            // 设置新的边缘大小
            Point displaySize = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
            // edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (displaySize.x * displayWidthPercentage)));
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (BaseUtils.dip2px(activity, 360) * displayWidthPercentage)));


            //获取 Layout 的 ViewDragCallBack 实例“mLeftCallback”
            //更改其属性 mPeekRunnable
            Field leftCallbackField = drawerLayout.getClass().getDeclaredField("mLeftCallback");
            leftCallbackField.setAccessible(true);

            //因为无法直接访问私有内部类，所以该私有内部类实现的接口非常重要，通过多态的方式获取实例
            ViewDragHelper.Callback leftCallback = (ViewDragHelper.Callback) leftCallbackField.get(drawerLayout);
            Field peekRunnableField = leftCallback.getClass().getDeclaredField("mPeekRunnable");
            peekRunnableField.setAccessible(true);
            peekRunnableField.set(leftCallback, (Runnable) () -> {
            });

            //添加到集合
            drawerLayoutList.add(drawerLayout);
        } catch (NoSuchFieldException ignored) {
            ignored.getMessage();
        } catch (IllegalArgumentException ignored) {
            ignored.getMessage();
        } catch (IllegalAccessException ignored) {
            ignored.getMessage();
        }
    }
}
