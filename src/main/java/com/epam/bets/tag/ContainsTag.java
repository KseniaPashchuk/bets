package com.epam.bets.tag;

import java.util.List;
/**
 * Class provides custom function for using on jsp pages, that checks if list contains string .
 *
 * @author Pashchuk Ksenia
 */
public class ContainsTag {
    public static boolean contains(List list, String o) {
        if (list == null) {
            return false;
        }
        return list.contains(o);
    }
}
