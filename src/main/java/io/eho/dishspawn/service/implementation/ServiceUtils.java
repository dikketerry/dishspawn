package io.eho.dishspawn.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceUtils {

    public static <E> List<E> skip(List<E> list, Integer toSkip) {
        return list.stream()
                .skip(toSkip)
                .collect(Collectors.toList());
    }

    public static Long convertStringToLong(String s) {
        Long l;

        try {
            l = Long.parseLong(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0l;
        }

        return l;
    }
}
