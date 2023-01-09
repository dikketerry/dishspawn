package io.eho.dishspawn.controller.util;

import java.util.List;
import java.util.stream.Collectors;

public class ListSkipper {

    public static <E> List<E> skipFirst(List<E> list) {
        return list.stream()
                .skip(1)
                .collect(Collectors.toList());
    }
}
