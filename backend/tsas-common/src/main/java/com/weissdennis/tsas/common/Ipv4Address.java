package com.weissdennis.tsas.common;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Ipv4Address {

    public static int fromString(String ipString) {
        int[] ipParts = getIpParts(ipString);
        if (ipParts.length != 4) {
            throw new RuntimeException("Malformed IP String!");
        }
        int ipAsInt = 0;
        for (int i = 1; i <= 4; i++) {
            ipAsInt += ipParts[4 - i] * (int) Math.pow(2, 8 * (i - 1));
        }
        return ipAsInt;
    }

    public static int[] getIpParts(String ipString) {
        String[] ipStrArr = ipString.split("\\.");
        int[] ipArr = new int[ipStrArr.length];

        for (int i = 0; i < ipArr.length; i++) {
            try {
                ipArr[i] = Integer.parseInt(ipStrArr[i]);
            } catch (NumberFormatException e) {
                ipArr[i] = 0;
            }

        }

        return ipArr;
    }

    private static String insertLeadingZeros(String numberString, int decimalPlaces) {
        if (decimalPlaces > numberString.length()) {
            char[] temp = {'0'};
            return new String(temp, 0, decimalPlaces - numberString.length()) + numberString;
        }
        return numberString;
    }

    public static String toString(int ipAsInt) {
        String binaryString = insertLeadingZeros(Integer.toBinaryString(ipAsInt), 32);
        int[] ipParts = new int[4];
        for (int i = 0; i < 4; i++) {
            ipParts[i] = Integer.parseInt(binaryString.substring(8 * i, 8 * i + 8), 2);
        }
        return Arrays.stream(ipParts)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining("."));
    }
}
