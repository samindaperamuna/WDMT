/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Saminda Permauna
 */
public class DataValidatorTest {
    
    public DataValidatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isInteger method, of class DataValidator.
     */
    @Test
    public void testIsInteger_String() {
        System.out.println("isInteger");
        String s = "12";
        boolean expResult = true;
        boolean result = DataValidator.isInteger(s);
        assertEquals(expResult, result);
    }

    /**
     * Test of isInteger method, of class DataValidator.
     */
    @Test
    public void testIsInteger_String_int() {
        System.out.println("isInteger");
        String s = "12A";
        int radix = 10;
        boolean expResult = false;
        boolean result = DataValidator.isInteger(s, radix);
        assertEquals(expResult, result);
    }
    
}
