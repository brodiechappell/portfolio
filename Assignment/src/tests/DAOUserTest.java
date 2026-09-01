import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class DAOUserTest {
    DAOUser daoUser = new DAOUser();

    @Test
    void generateUserID() {
        assertEquals(5,String.valueOf(daoUser.generateUserID()).length());
    }

    @Test
    void generateCourseID() {
        assertEquals(5,String.valueOf(daoUser.generateCourseID()).length());
    }

    @Test
    void generateModuleID() {
        assertEquals(5,String.valueOf(daoUser.generateModuleID()).length());
    }

    @Test
    void generateCourseModuleID() {
        assertEquals(5,String.valueOf(daoUser.generateCourseModuleID()).length());
    }

    @Test
    void generateCourseUserID() {
        assertEquals(5,String.valueOf(daoUser.generateCourseUserID()).length());
    }

    @Test
    void generateResultID() {
        assertEquals(5,String.valueOf(daoUser.generateResultID()).length());
    }

    @Test
    void generateNotesID() {
        assertEquals(5,String.valueOf(daoUser.generateNotesID()).length());
    }

    @Test
    void validateQualification() {
        assertNull(daoUser.validateQualification("student",null));
        assertNull(daoUser.validateQualification("student","qual"));
        assertNull(daoUser.validateQualification("lecturer",null));
        assertEquals("qual",daoUser.validateQualification("lecturer","qual"));
        assertEquals("",daoUser.validateQualification("lecturer",""));
        assertNull(daoUser.validateQualification("manager",""));
        assertNull(daoUser.validateQualification("manager","qual"));
    }

    @Test
    void toDate() {
        assertEquals(java.sql.Date.valueOf("2000-01-01"),daoUser.toDate("01","January","2000"));
        assertEquals(java.sql.Date.valueOf("2000-02-01"),daoUser.toDate("01","February","2000"));
        assertEquals(java.sql.Date.valueOf("2000-03-01"),daoUser.toDate("01","March","2000"));
        assertEquals(java.sql.Date.valueOf("2000-04-01"),daoUser.toDate("01","April","2000"));
        assertEquals(java.sql.Date.valueOf("2000-05-01"),daoUser.toDate("01","May","2000"));
        assertEquals(java.sql.Date.valueOf("2000-06-01"),daoUser.toDate("01","June","2000"));
        assertEquals(java.sql.Date.valueOf("2000-07-01"),daoUser.toDate("01","July","2000"));
        assertEquals(java.sql.Date.valueOf("2000-08-01"),daoUser.toDate("01","August","2000"));
        assertEquals(java.sql.Date.valueOf("2000-09-01"),daoUser.toDate("01","September","2000"));
        assertEquals(java.sql.Date.valueOf("2000-10-01"),daoUser.toDate("01","October","2000"));
        assertEquals(java.sql.Date.valueOf("2000-11-01"),daoUser.toDate("01","November","2000"));
        assertEquals(java.sql.Date.valueOf("2000-12-01"),daoUser.toDate("01","December","2000"));
    }
}