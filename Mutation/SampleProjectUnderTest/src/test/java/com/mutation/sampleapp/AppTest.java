package com.mutation.sampleapp;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testIsTrue_true()
    {
        assertTrue( new App().isTrue(true) );
    }
    public void testIsTrue_false()
    {
        assertFalse( new App().isTrue(false) );
    }
}
