package com.liuzw.springbootshiro.utils;


public class EncryptBase64Util {

    private final static String BASE_TABLE =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    /**
     * Encode a byte array.
     * 
     * @param bytes a byte array to be encoded.
     * @return encoded object as a String object.
     */
    public static String encode(byte[] bytes) {

        StringBuilder tmp = new StringBuilder();
        int i;
        byte pos;
        for (i = 0; i < (bytes.length - bytes.length % 3); i += 3) {

            pos = (byte) ((bytes[i] >> 2) & 63);

            tmp.append(BASE_TABLE.charAt(pos));

            pos = (byte) (((bytes[i] & 3) << 4) + ((bytes[i + 1] >> 4) & 15));
            tmp.append(BASE_TABLE.charAt(pos));

            pos = (byte) (((bytes[i + 1] & 15) << 2) + ((bytes[i + 2] >> 6) & 3));
            tmp.append(BASE_TABLE.charAt(pos));

            pos = (byte) (((bytes[i + 2]) & 63));
            tmp.append(BASE_TABLE.charAt(pos));

            // Add a new line for each 76 chars.
            // 76*3/4 = 57
            if (((i + 2) % 56) == 0) {
                tmp.append("\r\n");
            }
        }

        if (bytes.length % 3 != 0) {

            if (bytes.length % 3 == 2) {

                pos = (byte) ((bytes[i] >> 2) & 63);
                tmp.append(BASE_TABLE.charAt(pos));

                pos = (byte) (((bytes[i] & 3) << 4) + ((bytes[i + 1] >> 4) & 15));
                tmp.append(BASE_TABLE.charAt(pos));

                pos = (byte) ((bytes[i + 1] & 15) << 2);
                tmp.append(BASE_TABLE.charAt(pos));

                tmp.append("=");

            } else if (bytes.length % 3 == 1) {

                pos = (byte) ((bytes[i] >> 2) & 63);
                tmp.append(BASE_TABLE.charAt(pos));

                pos = (byte) ((bytes[i] & 3) << 4);
                tmp.append(BASE_TABLE.charAt(pos));

                tmp.append("==");
            }
        }
        return tmp.toString();

    }

    /**
     * Encode a String object.
     * 
     * @param src a String object to be encoded with Base64 schema.
     * @return encoded String object.
     */
    public static String encode(String src) {

        return encode(src.getBytes());
    }

    public static byte[] decode(String src) throws Exception {

        byte[] bytes;

        StringBuffer buf = new StringBuffer(src);

        // First, Remove white spaces (\r\n, \t, " ");
        int i = 0;
        char c = ' ';
        char oc = ' ';
        while (i < buf.length()) {
            oc = c;
            c = buf.charAt(i);
            if (oc == '\r' && c == '\n') {
                buf.deleteCharAt(i);
                buf.deleteCharAt(i - 1);
                i -= 2;
            } else if (c == '\t') {
                buf.deleteCharAt(i);
                i--;
            } else if (c == ' ') {
                i--;
            }
            i++;
        }

        // The source should consists groups with length of 4 chars.
        if (buf.length() % 4 != 0) {
            throw new Exception("Base64 decoding invalid length");
        }

        // pre-set byte array size.
        bytes = new byte[3 * (buf.length() / 4)];
        int index = 0;

        // Now decode each group
        for (i = 0; i < buf.length(); i += 4) {

            byte data;
            int nGroup = 0;

            for (int j = 0; j < 4; j++) {

                char theChar = buf.charAt(i + j);

                if (theChar == '=') {
                    data = 0;
                } else {
                    data = getBaseTableIndex(theChar);
                }

                if (data == -1) {
                    throw new Exception("Base64 decoding bad character");
                }

                nGroup = 64 * nGroup + data;
            }

            bytes[index] = (byte) (255 & (nGroup >> 16));
            index++;

            bytes[index] = (byte) (255 & (nGroup >> 8));
            index++;

            bytes[index] = (byte) (255 & (nGroup));
            index++;
        }

        byte[] newBytes = new byte[index];
        for (i = 0; i < index; i++) {
            newBytes[i] = bytes[i];
        }

        return newBytes;
    }

    /**
     * Find index number in base table for a given character.
     * 
     */
    protected static byte getBaseTableIndex(char c) {

        byte index = -1;

        for (byte i = 0; i < BASE_TABLE.length(); i++) {

            if (BASE_TABLE.charAt(i) == c) {
                index = i;
                break;
            }
        }

        return index;
    }

    public static void main(String[] args) throws Exception {
        String encodedString = EncryptBase64Util.encode("Hello PHPRPC".getBytes());
        System.out.println(encodedString);
        byte[] str = EncryptBase64Util.decode(encodedString);
        System.out.println(new String(str));
    }

}
