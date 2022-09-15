package io.eho.dishspawn.play.sketchtest.processing;

import java.util.Random;

public class PlayHexa {

    public static void main(String[] args)
    {
        // Random instance
        Random r = new Random();
//        System.out.println("random: " + r);

        // what range do i want? 6/16 to 14/16 = 96 to 224
        int n = r.nextInt(256);
        System.out.println("int: " + n);

        // n stores the random integer in decimal form
        String Hexadecimal = Integer.toHexString(n);

        // toHexString(n) converts n to hexadecimal form
        System.out.println("Random Hexadecimal Byte: "
                                   + Hexadecimal);
    }
}

