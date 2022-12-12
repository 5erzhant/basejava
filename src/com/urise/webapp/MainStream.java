package com.urise.webapp;

import java.util.Arrays;
import java.util.List;

public class MainStream {
    public static int minValue(int... values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> (a*10 + b));
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream()
                .mapToInt(x -> x)
                .sum();
        boolean isHonest = sum % 2 == 0;
        return integers.stream()
                .filter(x -> isHonest == (x % 2 != 0))
                .toList();
    }

    public static void main(String[] args) {
        System.out.println(minValue(1, 2, 3, 3, 2, 3));
        System.out.println(oddOrEven(Arrays.asList(1, 2, 5, 2)));
    }
}
