package com.jan.todo.core.helpers;

import java.util.Arrays;
import java.util.List;

import com.jan.todo.R;
import com.jan.todo.core.models.IconModel;

public class IconHelper
{
    private static final IconModel[] icons = new IconModel[]{
            new IconModel(1, "Home", R.drawable.home),
            new IconModel(2, "Hobbies", R.drawable.hobbies),
            new IconModel(3, "Finances", R.drawable.finances),
            new IconModel(4, "Social", R.drawable.people),
            new IconModel(5, "Exercise", R.drawable.exercise),
            new IconModel(6, "Shopping", R.drawable.shopping_cart),
            new IconModel(7, "Study", R.drawable.study)
    };

    public static List<IconModel> getIcons()
    {
        return Arrays.asList(icons);
    }
}
