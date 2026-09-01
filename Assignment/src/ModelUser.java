import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class ModelUser {
    private String username;
    protected int userID;
    protected String email;
    protected char[] password;
    protected String forename;
    protected String surname;
    protected String year;
    protected String month;
    protected String day;
    protected String gender;
    private int[][] noteWeek;
    private Long[][] noteContent;
    protected String accountType;
    private String qualification;
    protected DAOUser daoUser;

    public ModelUser() {
        this.daoUser = new DAOUser();
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public int[][] getNoteWeek() {
        return noteWeek;
    }

    public void setNoteWeek(int[][] noteWeek) {
        this.noteWeek = noteWeek;
    }

    public Long[][] getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(Long[][] noteContent) {
        this.noteContent = noteContent;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public boolean signup(String username, String email, String forename, String surname, String year, String month, String day, String gender, String accountType, String qualification, char[] password, char[] retypePassword) {
        if (Arrays.equals(password, retypePassword)) {
            if (!(username.isEmpty()||email.isEmpty()||forename.isEmpty()||surname.isEmpty()||password.toString().isEmpty())) { // if strings are not empty
                if (username.length() <= 20 || forename.length() <= 20 || surname.length() <= 20 || (email.length() <= 20 && email.contains("@")) || password.toString().length() <= 20) { // if strings lengths are within database varchar amounts, and if email contains an @ symbol
                    List<String> compare = daoUser.allUsers();
                    for (int i = 0; i<compare.size(); i += 2) {
                        //System.out.println("s ="+s+" username= "+username);
                        if (compare.get(i).equals(username) || compare.get(i + 1).equals(email)) {
                            return false;
                        }
                    }
                }
                return daoUser.signup(username, email, forename, surname, year, month, day, gender, accountType, qualification, password);
            }
        }
        return false;
    }

    public boolean login(String username, char[] password) {
        int id = daoUser.login(username, password);
        if (id > 0) { // found a valid user
            this.userID = id;        // store in model
            this.username = username; // returns true if logged in and a correct id number is found
            this.password = password;
            return true;
        } else {
            return false;
        }
    }

    public String accType(int id) {
        return daoUser.findAccType(id);
    }

    public boolean resetPass(int userID, char[] newPass, char[] newRetype) {
        if (Arrays.equals(newPass, newRetype) && !(new String(newPass).isEmpty())) {
            return daoUser.resetPass(userID, newPass);
        }
        return false;
    }

    public boolean validateDate(Object day, Object month, Object year) {
        if ((month == "April" || month == "June" || month == "September" || month == "November") && (Integer.parseInt((String) day) > 30)) {
            return false;
        }
        if (month == "February" && (Integer.parseInt((String) day)) > 29) { // if february and over 28th
            return false;
        }
        else if (Integer.parseInt((String) day) == 29 && (Integer.parseInt((String) year) % 4) != 0) { // if 29th and not a leap year
            return false;
        }
        return true;
    }

    public String yearFromString(String date) {
        return date.substring(0, 4);
    }

    public String monthFromString(String date) {
        String m = date.substring(5, 7);
        return switch (m) {
            case "01" -> "January";
            case "02" -> "February";
            case "03" -> "March";
            case "04" -> "April";
            case "05" -> "May";
            case "06" -> "June";
            case "07" -> "July";
            case "08" -> "August";
            case "09" -> "September";
            case "10" -> "October";
            case "11" -> "November";
            case "12" -> "December";
            default -> null;
        };
    }

    public String dayFromString(String date) {
        return date.substring(8);
    }

    public List<String> accountDetails(int userID) {
        return daoUser.accountDetails(userID);
    }

    public List<String> studentsInModule(int moduleID) {
        return daoUser.studentsInModule(moduleID);
    }

    public String notes(int moduleID, int week, String Type) {
        return daoUser.notes(moduleID, week, Type);
    }

    public List<String> results(int userID, int moduleID) {
        return daoUser.results(userID, moduleID);
    }

}
