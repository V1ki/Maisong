package com.yuanshi.maisong.utils;

import com.yuanshi.maisong.bean.ContactMember;

import java.util.Comparator;

/**
 * Created by Dengbocheng on 2017/11/29.
 */

public class PinyinComparator implements Comparator<ContactMember> {
        public int compare(ContactMember o1, ContactMember o2) {
            //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
            if (o2.getSortLetters().equals("#")) {
                return -1;
            } else if (o1.getSortLetters().equals("#")) {
                return 1;
            } else {
                return o1.getSortLetters().compareTo(o2.getSortLetters());
            }
        }
}
