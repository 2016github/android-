package com.king.baseproject.util;

import android.view.View;

import java.lang.reflect.Field;

/**
 * 属性名反射绑定控件（去除findViewbyId的使用）
 * 需要在几类中绑定该类
 * Created by king on 2017/1/11.
 */

public class ViewBindUtil {
    public static final void bindViews(Object object, View footView) {
        if(object == null) {
            return;
        }
        //获取对象中所有属性-不包含父类私有成员
        Field[] fields = getFields(object);
        for(Field fi : fields) {
            //判断属性是否继承自view
            if(View.class.isAssignableFrom(fi.getType())) {
                //根据属性名获取id
                int id = footView.getResources().getIdentifier(fi.getName(), "id", footView.getContext().getPackageName());
                if(id > 0) {
                    try {
                        //查找到id时绑定控件到对应属性上
                        fi.set(object, footView.findViewById(id));
                    } catch(IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static Field[] getFields(Object o) {
        Field[] f1 = o.getClass().getDeclaredFields();
        // 设置不检查访问
        for(int i = 0; i < f1.length; i++) {
            f1[i].setAccessible(true);
        }
        return f1;
    }

}
