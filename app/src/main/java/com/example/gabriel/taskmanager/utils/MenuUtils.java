package com.example.gabriel.taskmanager.utils;

import android.content.Context;
import android.util.TypedValue;

import com.example.gabriel.taskmanager.R;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;

import java.util.ArrayList;
import java.util.List;

public final class MenuUtils {
        private MenuUtils() {
        }

        public static ContextMenuDialogFragment setupMenu(Context context) {
            MenuObject closeMenu = new MenuObject();
            closeMenu.setResource(R.drawable.ic_close);

            MenuObject infoMenu = new MenuObject("1");
            infoMenu.setResource(R.drawable.ic_info);

            MenuObject shareMenu = new MenuObject("2");
            shareMenu.setResource(R.drawable.ic_share);

            MenuObject emailMenu = new MenuObject("3");
            emailMenu.setResource(R.drawable.ic_email);

            MenuObject feedbackMenu = new MenuObject("4");
            feedbackMenu.setResource(R.drawable.ic_feedback);

            List<MenuObject> objects = new ArrayList<>();
            objects.add(closeMenu);
            objects.add(infoMenu);
            objects.add(shareMenu);
            objects.add(emailMenu);
            objects.add(feedbackMenu);

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
