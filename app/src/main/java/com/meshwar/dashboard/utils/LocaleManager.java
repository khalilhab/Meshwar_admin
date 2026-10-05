package com.meshwar.dashboard.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import androidx.annotation.StringDef;


import com.meshwar.dashboard.data.network.model.ItemAny;
import com.meshwar.dashboard.data.prefs.AppPreferencesHelper;
import com.meshwar.dashboard.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocaleManager {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ARABIC, ENGLISH})

    public @interface LocaleDef {
        String[] SUPPORTED_LOCALES = {ARABIC, ENGLISH};
    }


    public static final String ARABIC = "ar";
    public static final String ENGLISH = "en";

    public static List<ItemAny<Object, String>> getLanguagesList(Context context) {
        List<ItemAny<Object, String>> items = new ArrayList<>();
        items.add(new ItemAny<>("ar", context.getString(R.string.arabic), R.drawable.lang_ar));
        items.add(new ItemAny<>("en", context.getString(R.string.english), R.drawable.lang_en));
        return items;
    }

    public static int getSelectedIndex(Context context) {
        for (int i = 0; i < getLanguagesList(context).size(); i++) {
            if (getLanguagesList(context).get(i).getId().equals(new AppPreferencesHelper().getLocal()))
                return i;
        }
        return 0;
    }

    /**
     * Set new Locale
     *
     * @return
     */
    public static void setNewLocale(Context context, @LocaleDef String language) {
        new AppPreferencesHelper().setLocal(language);
        updateResources(context);
    }

    /**
     * Set new Locale
     *
     * @return
     */
    public static Context setNewLocale(Context context) {
        return updateResources(context);
    }

    /**
     * update resource
     *
     * @return
     */
    private static Context updateResources(Context context) {
        Locale myLocale = new Locale(new AppPreferencesHelper().getLocal());

        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();

        conf.setLocale(myLocale);
        conf.setLayoutDirection(myLocale);
        res.updateConfiguration(conf, dm);
        Locale.setDefault(myLocale);
        return context;
    }
}
