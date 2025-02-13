package com.nsz.kotlin.utils;

public class Util {

    final private static char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * Convert a byte-array to a hexadecimal String with bytes separated by ':'.
     *
     * @return Colon-separated hex-string from byte-array.
     */
    public static String bytesToHex(byte[] bytes) {
        return bytesToHex(bytes, ':');
    }

    /**
     * Convert a byte-array to a hexadecimal String. from https://stackoverflow.com/a/9855338/207861
     *
     * @param bytes     Byte[] to convert to String
     * @param separator Separator between bytes
     * @return Byte[] as hex-string
     */
    public static String bytesToHex(byte[] bytes, char separator) {
        char[] hexChars = new char[bytes.length * 3];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = hexArray[v >>> 4];
            hexChars[j * 3 + 1] = hexArray[v & 0x0F];
            hexChars[j * 3 + 2] = separator;
        }
        return new String(hexChars, 0, hexChars.length - 1);
    }

}
