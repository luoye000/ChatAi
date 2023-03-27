package com.luoye.chat.utils;

import android.view.MenuItem;

import java.util.List;

public class ToolbarMenuStyleUtils {


    /**
     * 更新指定id 的数据
     *
     * @param list    数据源
     * @param visible 指定的数据的更新方案
     * @param itemId  指定数据
     */
    public static void upDates(List<MenuItem> list, boolean visible, int... itemId) {
        if (list == null) return ;
        for (MenuItem MenuItem : list) {
            for (int id : itemId) {
                if (MenuItem.getItemId() == id) {
                    MenuItem.setVisible(visible);
                    break;
                }
            }
        }
        
    }


    //

    /**
     * 更新所有数据
     *
     * @param list         数据源
     * @param visible      指定更新数据的更新方案
     * @param otherVisible 指定外的数据的更新方案
     * @param itemId       指定数据
     */
    public static void upDatesAll(List<MenuItem> list, boolean visible, boolean otherVisible, int... itemId) {
        if (list == null) return ;
        for (MenuItem MenuItem : list) {
            boolean otherDate = true;
            for (int id : itemId) {
                if (MenuItem.getItemId() == id) {
                    otherDate = false;
                    break;
                }
            }
            MenuItem.setVisible(otherDate ? otherVisible : visible);

        }
        
    }


    /**
     * 更新指定id以外 的数据
     *
     * @param list         数据源
     * @param otherVisible 指定外的数据的更新方案
     * @param itemId       指定数据
     */
    public static void upDatesOther(List<MenuItem> list, boolean otherVisible, int... itemId) {
        if (list == null) return ;
        for (MenuItem MenuItem : list) {
            boolean upDate = true;
            for (int id : itemId) {
                if (MenuItem.getItemId() == id) {
                    upDate = false;
                    break;
                }
            }
            if (upDate) MenuItem.setVisible(otherVisible);
        }
    }


}
