package com.warehouse.inventory.util;

import java.util.Random;

public class PinGenerator {
    private static final Random random = new Random();
    private static final int min = 1111111;
    private static final int max = 9999999;

    public static int generate() {
        return random.nextInt(max - min + 1) + min;
    }
}
