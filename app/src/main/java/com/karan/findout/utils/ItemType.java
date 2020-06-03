package com.karan.findout.utils;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        ItemType.CUISINE_HEADER,
        ItemType.RESTAURANT
})
@Retention(RetentionPolicy.SOURCE)
public @interface ItemType {
    int CUISINE_HEADER = 1;
    int RESTAURANT = 2;
}