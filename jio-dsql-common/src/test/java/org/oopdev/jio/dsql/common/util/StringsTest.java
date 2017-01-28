package org.oopdev.jio.dsql.common.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kamilbukum on 28/01/2017.
 */
public class StringsTest {
    @Test
    public void isEmptyOrNull() throws Exception {
        assertTrue(Strings.isEmptyOrNull(null));
        assertTrue(Strings.isEmptyOrNull(""));
        assertTrue(Strings.isEmptyOrNull("      "));
        assertFalse(Strings.isEmptyOrNull("E"));
        assertFalse(Strings.isEmptyOrNull("Example"));
    }

    @Test
    public void trim(){
        assertEquals("", Strings.trim(null));
        assertEquals("", Strings.trim(""));
        assertEquals("", Strings.trim("    "));
        assertEquals("Test", Strings.trim("    Test"));
        assertEquals("Test", Strings.trim("Test"));
    }

    @Test
    public void capitalizeFirstChar() throws Exception {
        assertEquals("Test", Strings.capitalizeFirstChar("test"));
        assertEquals("Test", Strings.capitalizeFirstChar("Test"));
        assertEquals("", Strings.capitalizeFirstChar(""));
        assertEquals(null, Strings.capitalizeFirstChar(null));
    }

    @Test
    public void unCapitalizeFirstChar() throws Exception {
        assertEquals("test", Strings.unCapitalizeFirstChar("test"));
        assertEquals("test", Strings.unCapitalizeFirstChar("Test"));
        assertEquals("", Strings.unCapitalizeFirstChar(""));
        assertEquals(null, Strings.unCapitalizeFirstChar(null));
    }

}