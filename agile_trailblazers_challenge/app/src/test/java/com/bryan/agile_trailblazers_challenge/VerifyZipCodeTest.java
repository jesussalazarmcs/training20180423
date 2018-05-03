package com.bryan.agile_trailblazers_challenge;

import com.bryan.agile_trailblazers_challenge.util.ZipCodeUtil;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *  The VerifyZipCodeTest simply verifies that the given zipcode entry is valid.
 *
 */
public class VerifyZipCodeTest {

    @Test
    public void zipcode_isCorrect() throws Exception {
        assertTrue(ZipCodeUtil.isZipCode("30303"));
        assertTrue(ZipCodeUtil.isZipCode("55555"));
    }

    @Test
    public void zipcode_isFalse() throws Exception {
        assertFalse(ZipCodeUtil.isZipCode("303"));
        assertFalse(ZipCodeUtil.isZipCode("5"));
        assertFalse(ZipCodeUtil.isZipCode("abcde"));
    }
}
