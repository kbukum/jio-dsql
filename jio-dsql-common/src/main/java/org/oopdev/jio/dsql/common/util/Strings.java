package org.oopdev.jio.dsql.common.util;

/**
 * Provides frequently needed methods for string operations.
 */
public final class Strings {

    private Strings() {
    }

    /**
     * checks String value is empty or null
     * @param value
     * @return { boolean }
     */
    public static boolean isEmptyOrNull(String value) {
        return value == null || value.trim().equals("");
    }


    /**
     * trims given string value. if it is null then return empty string.
     * @param value
     * @return
     */
    public static String trim(String value) {
        return value == null ? "": value.trim();
    }
    /**
     * Capitalizes the first character of the word given.
     * It will convert i to I
     *
     * @param word
     * @return {String} result
     */
    public static final String capitalizeFirstChar(String word) {
        if(isEmptyOrNull(word)) return word;
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

    /**
     * Un capitalizes the first character of the word given.
     * It will convert i to I
     *
     * @param word
     * @return {String} result
     */
    public static final String unCapitalizeFirstChar(String word) {
        if(isEmptyOrNull(word)) return word;
        return Character.toLowerCase(word.charAt(0)) + word.substring(1);
    }
}
