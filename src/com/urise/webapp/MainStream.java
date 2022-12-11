package com.urise.webapp;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class MainStream {
    public static int minValue(int... values) {
        AtomicReference<Double> count = new AtomicReference<>((double) 0);
        Double sum = Arrays.stream(values)
                .distinct()
                .boxed()
                .sorted(Collections.reverseOrder())
                .map((x) -> x * Math.pow(10, count.getAndSet(count.get() + 1)))
                .reduce(0.0, Double::sum);
        return sum.intValue();
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream()
                .mapToInt(x -> x)
                .sum();
        return integers.stream()
                .filter(x -> {
                    if (sum % 2 == 0) {
                        return x % 2 != 0;
                    } else {
                        return x % 2 == 0;
                    }
                })
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println(minValue(1, 2, 3, 3, 2, 3));
        System.out.println(oddOrEven(Arrays.asList(1, 2, 5, 2)));
    }
}
