package com.vypeensoft.routehelper.utils;

import java.util.*;

public class LogUtil {
    public static void printList(List list, String message) {
		//System.out.println(message);
    	for(Object obj: list) {
    		System.out.println(message+" : "+obj.toString());
    	}
    }
}
