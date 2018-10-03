package com.fyr.talend.components.util;


import com.google.common.collect.Multiset;

import javax.annotation.Nullable;

/**
 * Set Utility Class.
 */
public class SetUtil {

    /**
     * Returns the n-th element of a set
     *
     * @param set - Any MultiSet
     * @param n   - Position in Set
     * @param <T> - Type of entries in MultiSet
     * @return T
     */
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
