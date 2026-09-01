import org.junit.jupiter.api.*;
import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class ModelUserTest {

    ModelUser mUser = new ModelUser();
    DAOUser daoUser = new DAOUser();

    @Test
    void validateDate() {
        // test with months with 30 days
        assertTrue(mUser.validateDate("30","April","2000"));
        assertTrue(mUser.validateDate("30","June","2000"));
        assertTrue(mUser.validateDate("30","September","2000"));
        assertTrue(mUser.validateDate("30","November","2000"));
        assertFalse(mUser.validateDate("31","April","2000"));
        assertFalse(mUser.validateDate("31","June","2000"));
        assertFalse(mUser.validateDate("31","September","2000"));
        assertFalse(mUser.validateDate("31","November","2000"));

        // test with months with 30 days
        assertTrue(mUser.validateDate("31","January","2000"));
        assertTrue(mUser.validateDate("31","March","2000"));
        assertTrue(mUser.validateDate("31","May","2000"));
        assertTrue(mUser.validateDate("31","July","2000"));
        assertTrue(mUser.validateDate("31","August","2000"));
        assertTrue(mUser.validateDate("31","October","2000"));
        assertTrue(mUser.validateDate("31","December","2000"));



        // test with february in non-leap year
        assertTrue(mUser.validateDate("28","February","2001"));
        assertFalse(mUser.validateDate("29","February","2001"));
        assertFalse(mUser.validateDate("30","February","2001"));
        assertFalse(mUser.validateDate("31","February","2001"));

        // test with february in leap year
        assertTrue(mUser.validateDate("28","February","2000"));
        assertTrue(mUser.validateDate("29","February","2000"));
        assertFalse(mUser.validateDate("30","February","2000"));
        assertFalse(mUser.validateDate("31","February","2000"));

    }

    @Test
    void yearFromString() {
        assertEquals("2000",mUser.yearFromString("2000-01-01"));
    }

    @Test
    void monthFromString() {
        assertEquals("January",mUser.monthFromString("2000-01-01"));
        assertEquals("February",mUser.monthFromString("2000-02-01"));
        assertEquals("March",mUser.monthFromString("2000-03-01"));
        assertEquals("April",mUser.monthFromString("2000-04-01"));
        assertEquals("May",mUser.monthFromString("2000-05-01"));
        assertEquals("June",mUser.monthFromString("2000-06-01"));
        assertEquals("July",mUser.monthFromString("2000-07-01"));
        assertEquals("August",mUser.monthFromString("2000-08-01"));
        assertEquals("September",mUser.monthFromString("2000-09-01"));
        assertEquals("October",mUser.monthFromString("2000-10-01"));
        assertEquals("November",mUser.monthFromString("2000-11-01"));
        assertEquals("December",mUser.monthFromString("2000-12-01"));


    }

    @Test
    void dayFromString() {
        assertEquals("01",mUser.dayFromString("2000-01-01"));
        assertEquals("02",mUser.dayFromString("2000-01-02"));
        assertEquals("30",mUser.dayFromString("2000-01-30"));
        assertEquals("31",mUser.dayFromString("2000-01-31"));
    }
}