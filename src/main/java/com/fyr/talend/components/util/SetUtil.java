package com.fyr.talend.components.util;


import com.google.common.collect.Multiset;

import javax.annotation.Nullable;
import java.util.Set;

public class SetUtil {

    @Nullable
    public static <T> T nthElement(Multiset<T> set, int n) {
        if (null != set && n >= 0 && n < set.size()) {
            int count = 0;
            for (T element : set) {
                if (n == count)
                    return element;
                count++;
            }
        }
        return null;
    }
}
