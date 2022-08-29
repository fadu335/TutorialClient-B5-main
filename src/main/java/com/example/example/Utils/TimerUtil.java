package com.example.examplemod.Utils;

public class TimerUtil {
    private static long ms = getCurrentMS();

    private static long getCurrentMS() {
        return System.currentTimeMillis();
    }

    public static boolean hasReached(float milliseconds) {
        return getCurrentMS() - ms > milliseconds;
    }

    public static void reset() {
        ms = getCurrentMS();
    }

    public static void strafe() {
    }

    public long getTime() {
        return getCurrentMS() - ms;
    }
}
