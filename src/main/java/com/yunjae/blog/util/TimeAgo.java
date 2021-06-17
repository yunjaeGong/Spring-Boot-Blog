package com.yunjae.blog.util;


import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class TimeAgo {
    public static final Map<String, Long> times = new HashMap<>();

    static {
        times.put("long time", TimeUnit.DAYS.toMillis(365*6));
        times.put("year", TimeUnit.DAYS.toMillis(365));
        times.put("month", TimeUnit.DAYS.toMillis(30));
        times.put("week", TimeUnit.DAYS.toMillis(7));
        times.put("day", TimeUnit.DAYS.toMillis(1));
        times.put("hour", TimeUnit.HOURS.toMillis(1));
        times.put("minute", TimeUnit.MINUTES.toMillis(1));
        times.put("second", TimeUnit.SECONDS.toMillis(1));
    }

    public String toRelative(long lastUpdated, int maxLevel) {
        StringBuilder res = new StringBuilder();
        int level = 0;

        for(Map.Entry<String, Long> time : times.entrySet()) {
            long timeDelta = (System.currentTimeMillis() - lastUpdated) / time.getValue();

            if (timeDelta > 0){
                res.append(timeDelta) // n years hours minutes seconds .. ago
                        .append(" ")
                        .append(time.getKey())
                        .append(timeDelta > 1 ? "s" : "")
                        .append(", ");
                lastUpdated -= time.getValue() * timeDelta;
                level++;

                if (level == maxLevel){
                    break;
                }
            }
        }

        if ("".equals(res.toString())) { // null string -> just about now
            return "0 seconds ago";
        } else {
            res.setLength(res.length() - 2);
            res.append(" ago");
            return res.toString();
        }
    }

    public String toRelative(long lastUpdated) {
        return toRelative(lastUpdated, 1);
    }
}
