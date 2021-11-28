package com.sora.mvvmarchitecture.preference;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zheng on 17-11-27.
 */

public class GetCodeCookiePersistor {
    private SharedPreferences sharedPreferences;

    public GetCodeCookiePersistor(Context context) {
        this(context.getSharedPreferences("GetCode", Context.MODE_PRIVATE));
    }

    public GetCodeCookiePersistor(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void save(List<String> list) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (String s : list) {
            editor.putString("param" + list.indexOf(s), s);
        }
        editor.apply();
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }

    public List<String> get() {
        Map<String, ?> map = sharedPreferences.getAll();
        List<String> list = new ArrayList<>();
        for (String in : map.keySet()) {
            list.add((String) map.get(in));
        }
        return list;
    }
}
