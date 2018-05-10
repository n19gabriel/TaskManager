package com.example.gabriel.taskmanager.utils;

import android.content.Context;
import android.util.TypedValue;

import com.example.gabriel.taskmanager.R;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;

import java.util.ArrayList;
import java.util.List;

public final class MenuUtilsSort {
        private MenuUtilsSort() {
        }

        public static ContextMenuDialogFragment setupMenu(Context context) {
            MenuObject closeMenu = new MenuObject();
            closeMenu.setResource(R.drawable.ic_close);

            MenuObject sortAZMenu = new MenuObject(context.getString(R.string.a_z));
            sortAZMenu.setResource(R.mipmap.baseline_text_rotation_down_white_24);

            MenuObject sortZAMenu = new MenuObject(context.getString(R.string.z_a));
            sortZAMenu.setResource(R.mipmap.baseline_text_rotate_up_white_24);

            MenuObject sortLaterMenu = new MenuObject(context.getString(R.string.later));
            sortLaterMenu.setResource(R.mipmap.baseline_date_range_white_24);

            MenuObject sortEarlyMenu = new MenuObject(context.getString(R.string.Early));
            sortEarlyMenu.setResource(R.mipmap.baseline_event_white_24);

            List<MenuObject> objects = new ArrayList<>();
            objects.add(closeMenu);
            objects.add(sortAZMenu);
            objects.add(sortZAMenu);
            objects.add(sortLaterMenu);
            objects.add(sortEarlyMenu);

            for (MenuObject object : objects) {
                object.setDividerColor(R.color.divider_color);
                object.setBgResource(R.color.primary);
            }

            MenuParams menuParams = new MenuParams();
            menuParams.setActionBarSize(getActionBarHeight(context));
            menuParams.setMenuObjects(objects);
            menuParams.setClosableOutside(Boolean.FALSE);

            return ContextMenuDialogFragment.newInstance(menuParams);
        }

        private static int getActionBarHeight(Context context) {
            TypedValue tv = new TypedValue();
            if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, Boolean.TRUE))
                return TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
            else
                return Math.round(context.getResources().getDimension(R.dimen.toolbar_height));
        }
    }
