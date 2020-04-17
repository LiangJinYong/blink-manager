package com.blink.util;

import java.util.concurrent.ThreadLocalRandom;

public class CommonUtils {
	
	public static int createRandomNumber(int length) {
		int range = (int)Math.pow(10,length);
        int trim = (int)Math.pow(10, length-1);
        
        int result = ThreadLocalRandom.current().nextInt(range)+trim;
        
        if(result>range){
            result = result - trim;
        }
        
        return result;
	}
}
