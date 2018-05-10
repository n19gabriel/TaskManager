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

        MenuObject addTaskMenu = new MenuObject(context.getString(R.string.add_task));
        addTaskMenu.setResource(R.mipmap.baseline_add_white_24);

        MenuObject addRandomTaskMenu = new MenuObject(context.getString(R.string.add_random_task));
        addRandomTaskMenu.setResource(R.mipmap.baseline_queue_white_24);

        MenuObject deleteAllTaskMenu = new MenuObject(context.getString(R.string.delete_all));
        deleteAllTaskMenu.setResource(R.drawable.ic_delete_all);

        MenuObject settingsMenu = new MenuObject(context.getString(R.string.settings));
        settingsMenu.setResource(R.mipmap.baseline_settings_white_24);

        MenuObject exitMenu = new MenuObject(context.getString(R.string.exit));
        exitMenu.setResource(R.mipmap.baseline_power_settings_new_white_24);

        List<MenuObject> objects = new ArrayList<>();
        objects.add(closeMenu);
        objects.add(addTaskMenu);
        objects.add(addRandomTaskMenu);
        objects.add(deleteAllTaskMenu);
        objects.add(settingsMenu);
        objects.add(exitMenu);

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
