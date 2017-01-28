package org.oopdev.jio.dsql.common.util;

/**
 * Provides frequently needed methods for string operations.
 */
public final class Strings {

    private Strings() {
    }



    public static boolean isEmptyOrNull(String value) {
        return value == null || value.trim().equals("");
    }
    /**
     * Capitalizes the first character of the word given.
     * It will convert i to I
     *
     * @param word
     * @return result
     */
    public static final String capitalizeFirstChar(String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

    /**
     * Un capitalizes the first character of the word given.
     * It will convert i to I
     *
     * @param word
     * @return result
     */
    public static final String unCapitalizeFirstChar(String word) {
        return Character.toLowerCase(word.charAt(0)) + word.substring(1);
    }
}
