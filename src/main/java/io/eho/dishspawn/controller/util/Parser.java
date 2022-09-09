package io.eho.dishspawn.controller.util;

// utility class to convert stuff when required
public class Parser {

    public static Long convertStringIdToLong(String id) {
        Long idLong;

        try {
            idLong = Long.parseLong(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0l;
        }

        return idLong;
    }


}
