package com.myitem.middleware.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: yr
 * @desc:
 * @date: 2022/2/22 8:45
 */
public class Constants {
    public static Map<String , RateLimiter> rateLimiterMap = Collections.synchronizedMap(new HashMap<String , RateLimiter>());
}
