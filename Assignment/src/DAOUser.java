import java.sql.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DAOUser {
    private Connection connection;
    public DAOUser() {
        this.connection = MySQLConnect.getMysqlConnection();
    }
    // insert new user into User
    public boolean signup(String username, String email, String forename, String surname, String year, String month, String day, String gender, String accountType, String qualification, char[] password) {
        String sql = "INSERT INTO User (userID,accountType,username,forename,surname,gender,email,DOB,accountApproved,decision,password,qualification) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, generateUserID()); // set UserID
            statement.setString(2, accountType); // set accountType
            statement.setString(3, username); // set username
            statement.setString(4, forename); // set forename
            statement.setString(5, surname); // set surname
            statement.setString(6, gender); // set gender
            statement.setString(7, email); // set email
            statement.setDate(8, toDate(day,month,year)); // set DOB, need method for converting day,month,year strings into date format, agin implement it in modeluser i think
            statement.setBoolean(9, false); // set accountApproved, will always be false when signing up
            statement.setString(10, null); // set decision, will always be null
            statement.setString(11, new String(password)); // set password, char[] must be changed to string
            statement.setString(12, validateQualification(accountType,qualification)); // set qualification, method ensures that student qualification is set to null
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0; // if no rows inserted, return false
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int login(String username, char[] password) {
        String sql = "SELECT userID FROM User WHERE username = ? AND password = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, new String(password));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("userID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // 0 means "not found" or "login failed"
    }

    public List<String> accountDetails(int userID) {
        String sql = "SELECT * FROM User WHERE userID = ?";
        List<String> details = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                details.add(resultSet.getString("userID"));          // 0
                details.add(resultSet.getString("accountType"));     // 1
                details.add(resultSet.getString("username"));        // 2
                details.add(resultSet.getString("forename"));        // 3
                details.add(resultSet.getString("surname"));         // 4
                details.add(resultSet.getString("gender"));          // 5
                details.add(resultSet.getString("email"));           // 6
                details.add(resultSet.getString("DOB"));             // 7
                details.add(resultSet.getString("accountApproved")); // 8
                details.add(resultSet.getString("decision"));        // 9
                details.add(resultSet.getString("password"));        // 10
                details.add(resultSet.getString("qualification"));   // 11
            } else {
                System.out.println("No user found with ID: " + userID);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return details;
    }

    public List<String> unapprovedAccounts() {
        String sql = "SELECT * " +
                "FROM User " +
                "WHERE accountApproved = false";
        List<String> details = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                details.add(resultSet.getString("userID"));          // 0
                details.add(resultSet.getString("accountType"));     // 1
                details.add(resultSet.getString("username"));        // 2
                details.add(resultSet.getString("forename"));        // 3
                details.add(resultSet.getString("surname"));         // 4
                details.add(resultSet.getString("gender"));          // 5
                details.add(resultSet.getString("email"));           // 6
                details.add(resultSet.getString("DOB"));             // 7
                details.add(resultSet.getString("accountApproved")); // 8
                details.add(resultSet.getString("decision"));        // 9
                details.add(resultSet.getString("password"));        // 10
                details.add(resultSet.getString("qualification"));   // 11
            }
            return details;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> studentAccounts() {
        String sql = "SELECT * " +
                "FROM User " +
                "WHERE accountType = 'student' " +
                "AND accountApproved = true";
        List<String> details = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                details.add(resultSet.getString("userID"));          // 0
                details.add(resultSet.getString("accountType"));     // 1
                details.add(resultSet.getString("username"));        // 2
                details.add(resultSet.getString("forename"));        // 3
                details.add(resultSet.getString("surname"));         // 4
                details.add(resultSet.getString("gender"));          // 5
                details.add(resultSet.getString("email"));           // 6
                details.add(resultSet.getString("DOB"));             // 7
                details.add(resultSet.getString("accountApproved")); // 8
                details.add(resultSet.getString("decision"));        // 9
                details.add(resultSet.getString("password"));        // 10
                details.add(resultSet.getString("qualification"));   // 11
            }
            return details;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> lecturerAccounts() {
        String sql = "SELECT * " +
                "FROM User " +
                "WHERE accountType = 'lecturer' " +
                "AND accountApproved = true";
        List<String> details = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                details.add(resultSet.getString("userID"));          // 0
                details.add(resultSet.getString("accountType"));     // 1
                details.add(resultSet.getString("username"));        // 2
                details.add(resultSet.getString("forename"));        // 3
                details.add(resultSet.getString("surname"));         // 4
                details.add(resultSet.getString("gender"));          // 5
                details.add(resultSet.getString("email"));           // 6
                details.add(resultSet.getString("DOB"));             // 7
                details.add(resultSet.getString("accountApproved")); // 8
                details.add(resultSet.getString("decision"));        // 9
                details.add(resultSet.getString("password"));        // 10
                details.add(resultSet.getString("qualification"));   // 11
            }
            return details;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // check if userID exists already
    public boolean validateUserID(int ID) {
        String sql = "SELECT userID " +
                "FROM User " +
                "WHERE userID = ?;";
        //System.out.println(sql);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,ID); // set id
            ResultSet resultSet = statement.executeQuery();
            return !resultSet.next(); // return true if user with given ID is NOT found
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // check if courseID exists already
    public boolean validateCourseID(int ID) {
        String sql = "SELECT courseID " +
                "FROM Course " +
                "WHERE courseID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,ID); // set id
            ResultSet resultSet = statement.executeQuery();
            return !resultSet.next(); // return true if user with given ID is NOT found
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // check if moduleID exists already
    public boolean validateModuleID(int ID) {
        String sql = "SELECT moduleID " +
                "FROM Module " +
                "WHERE moduleID = ?;";
        //System.out.println(sql);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,ID); // set id
            ResultSet resultSet = statement.executeQuery();
            return !resultSet.next(); // return true if user with given ID is NOT found
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // check if courseModuleID exists already
    public boolean validateCourseModuleID(int ID) {
        String sql = "SELECT courseModuleID " +
                "FROM CourseModule " +
                "WHERE courseModuleID = ?;";
        //System.out.println(sql);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,ID); // set id
            ResultSet resultSet = statement.executeQuery();
            return !resultSet.next(); // return true if user with given ID is NOT found
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // check if courseUserID exists already
    public boolean validateCourseUserID(int ID) {
        String sql = "SELECT courseUserID " +
                "FROM CourseUser " +
                "WHERE courseUserID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,ID); // set id
            ResultSet resultSet = statement.executeQuery();
            return !resultSet.next(); // return true if user with given ID is NOT found
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // check if resultID exists already
    public boolean validateResultID(int ID) {
        String sql = "SELECT resultID " +
                "FROM Result " +
                "WHERE resultID = ?;";
        //System.out.println(sql);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,ID); // set id
            ResultSet resultSet = statement.executeQuery();
            return !resultSet.next(); // return true if user with given ID is NOT found
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // check if userID exists already
    public boolean validateNotesID(int ID) {
        String sql = "SELECT noteID " +
                "FROM Notes " +
                "WHERE noteID = ?;";
        //System.out.println(sql);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,ID); // set id
            ResultSet resultSet = statement.executeQuery();
            return !resultSet.next(); // return true if user with given ID is NOT found
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // find type of specified account
    public String findAccType(int userID) {
        String sql = "SELECT accountType " +
                "FROM User " +
                "WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,userID); // set userID
            ResultSet resultSet = statement.executeQuery();
            resultSet.next(); // move result set forward
            //System.out.println("type is '"+resultSet.getString("accountType")+"'");
            return resultSet.getString("accountType"); // return accountType of user
        }
        catch(SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public int generateUserID() {
        Random rand = new Random();
        String idString = Integer.toString(rand.nextInt(1,9)); // use string for concatenation, id is an int so first digit CANNOT be 0
        for (int i=0; i<4; i++) {
            idString += Integer.toString(rand.nextInt(0,9)); // concatenate new random digit
        }
        if (!validateUserID(Integer.parseInt(idString))) { // if generated ID already exists...
            return generateUserID(); // call method again, recursion means that it will keep calling the method until a unique id is generated
        }
        else {
            return Integer.parseInt(idString); // convert string to int and return it
        }
    }

    public int generateCourseID() {
        Random rand = new Random();
        String idString = Integer.toString(rand.nextInt(1,9)); // use string for concatenation, id is an int so first digit CANNOT be 0
        for (int i=0; i<4; i++) {
            idString += Integer.toString(rand.nextInt(0,9)); // concatenate new random digit
        }
        if (!validateCourseID(Integer.parseInt(idString))) { // if generated ID already exists...
            return generateCourseID(); // call method again, recursion means that it will keep calling the method until a unique id is generated
        }
        else {
            return Integer.parseInt(idString); // convert string to int and return it
        }
    }

    public int generateModuleID() {
        Random rand = new Random();
        String idString = Integer.toString(rand.nextInt(1,9)); // use string for concatenation, id is an int so first digit CANNOT be 0
        for (int i=0; i<4; i++) {
            idString += Integer.toString(rand.nextInt(0,9)); // concatenate new random digit
        }
        if (!validateModuleID(Integer.parseInt(idString))) { // if generated ID already exists...
            return generateModuleID(); // call method again, recursion means that it will keep calling the method until a unique id is generated
        }
        else {
            return Integer.parseInt(idString); // convert string to int and return it
        }
    }

    public int generateCourseModuleID() {
        Random rand = new Random();
        String idString = Integer.toString(rand.nextInt(1,9)); // use string for concatenation, id is an int so first digit CANNOT be 0
        for (int i=0; i<4; i++) {
            idString += Integer.toString(rand.nextInt(0,9)); // concatenate new random digit
        }
        if (!validateCourseModuleID(Integer.parseInt(idString))) { // if generated ID already exists...
            return generateCourseModuleID(); // call method again, recursion means that it will keep calling the method until a unique id is generated
        }
        else {
            return Integer.parseInt(idString); // convert string to int and return it
        }
    }

    public int generateCourseUserID() {
        Random rand = new Random();
        String idString = Integer.toString(rand.nextInt(1,9)); // use string for concatenation, id is an int so first digit CANNOT be 0
        for (int i=0; i<4; i++) {
            idString += Integer.toString(rand.nextInt(0,9)); // concatenate new random digit
        }
        if (!validateCourseUserID(Integer.parseInt(idString))) { // if generated ID already exists...
            return generateCourseUserID(); // call method again, recursion means that it will keep calling the method until a unique id is generated
        }
        else {
            return Integer.parseInt(idString); // convert string to int and return it
        }
    }

    public int generateResultID() {
        Random rand = new Random();
        String idString = Integer.toString(rand.nextInt(1,9)); // use string for concatenation, id is an int so first digit CANNOT be 0
        for (int i=0; i<4; i++) {
            idString += Integer.toString(rand.nextInt(0,9)); // concatenate new random digit
        }
        if (!validateResultID(Integer.parseInt(idString))) { // if generated ID already exists...
            return generateResultID(); // call method again, recursion means that it will keep calling the method until a unique id is generated
        }
        else {
            return Integer.parseInt(idString); // convert string to int and return it
        }
    }

    public int generateNotesID() {
        Random rand = new Random();
        String idString = Integer.toString(rand.nextInt(1,9)); // use string for concatenation, id is an int so first digit CANNOT be 0
        for (int i=0; i<4; i++) {
            idString += Integer.toString(rand.nextInt(0,9)); // concatenate new random digit
        }
        if (!validateNotesID(Integer.parseInt(idString))) { // if generated ID already exists...
            return generateNotesID(); // call method again, recursion means that it will keep calling the method until a unique id is generated
        }
        else {
            return Integer.parseInt(idString); // convert string to int and return it
        }
    }

    public String validateQualification(String accType, String qualification) {
        if (accType == "lecturer") {
            return qualification;
        }
        else {
            return null;
        }
    }

    // take course details and insert them into a list
    public List<String> courseDetails(int userID) {
        String sql = "SELECT Course.courseID, title, degree, semesters, startDate, endDate, compensationsAllowed, managerID, description, graduateLevel " +
                "FROM Course, CourseUser, User " +
                "WHERE CourseUser.courseID = Course.courseID " +
                "AND CourseUser.userID = User.userID " +
                "AND User.userID = ?";
        List<String> details = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userID); // set userID
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                details.add(resultSet.getString("courseID"));               // 0
                details.add(resultSet.getString("title"));                  // 1
                details.add(resultSet.getString("degree"));                 // 2
                details.add(resultSet.getString("semesters"));              // 3
                details.add(resultSet.getString("startDate"));              // 4
                details.add(resultSet.getString("endDate"));                // 5
                details.add(resultSet.getString("compensationsAllowed"));   // 6
                details.add(resultSet.getString("managerID"));              // 7
                details.add(resultSet.getString("description"));            // 8
                details.add(resultSet.getString("graduateLevel"));          // 9
            }
            return details;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<String> managerCourseDetails(int managerID) {
        String sql = "SELECT courseID, title, degree, semesters, startDate, endDate, compensationsAllowed, managerID, description, graduateLevel " +
                "FROM Course " +
                "WHERE managerID = ?";
        List<String> details = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, managerID); // set userID
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                details.add(resultSet.getString("courseID"));               // 0
                details.add(resultSet.getString("title"));                  // 1
                details.add(resultSet.getString("degree"));                 // 2
                details.add(resultSet.getString("semesters"));              // 3
                details.add(resultSet.getString("startDate"));              // 4
                details.add(resultSet.getString("endDate"));                // 5
                details.add(resultSet.getString("compensationsAllowed"));   // 6
                details.add(resultSet.getString("managerID"));              // 7
                details.add(resultSet.getString("description"));            // 8
                details.add(resultSet.getString("graduateLevel"));          // 9
            }
            return details;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // take course details and insert them into a list
    public List<String> courseDetailsByCourseID(int courseID) {
        String sql = "SELECT courseID, title, degree, semesters, startDate, endDate, compensationsAllowed, managerID, description, graduateLevel " +
                "FROM Course " +
                "WHERE courseID = ?;";
        List<String> details = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, courseID); // set userID
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                details.add(resultSet.getString("courseID"));               // 0
                details.add(resultSet.getString("title"));                  // 1
                details.add(resultSet.getString("degree"));                 // 2
                details.add(resultSet.getString("semesters"));              // 3
                details.add(resultSet.getString("startDate"));              // 4
                details.add(resultSet.getString("endDate"));                // 5
                details.add(resultSet.getString("compensationsAllowed"));   // 6
                details.add(resultSet.getString("managerID"));              // 7
                details.add(resultSet.getString("description"));            // 8
                details.add(resultSet.getString("graduateLevel"));          // 9
            }
            return details;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // take module details and insert them into a list, each set of 4 values will represent one module
    public List<String> modules(int userID) {
        String sql = "SELECT Module.moduleID, title, maxAttempts, description,lecturerID,credits,markingType,assignmentNum,semesters " +
                "FROM Module, Result, User " +
                "WHERE User.userID = Result.userID " +
                "AND Result.moduleID = Module.moduleID " +
                "AND User.userID = ?";
        List<String> modules = new  ArrayList<String>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userID); // set userID
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                modules.add(resultSet.getString("moduleID"));       // 0
                modules.add(resultSet.getString("title"));          // 1
                modules.add(resultSet.getString("maxAttempts"));    // 2
                modules.add(resultSet.getString("description"));    // 3
                modules.add(resultSet.getString("lecturerID"));     // 6
                modules.add(resultSet.getString("credits"));        // 5
                modules.add(resultSet.getString("markingType"));    // 6
                modules.add(resultSet.getString("assignmentNum"));  // 7
                modules.add(resultSet.getString("semesters"));      // 8

            }
            //System.out.println("within dao after loop, module ="+module);
            //System.out.println("number of modules = " +  (modules.size()/4) + "\nnumber of values = " + modules.size());
            return modules;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String>  lecturerModuleDetails (int userID, int courseID) {
        String sql = "SELECT Module.moduleID, Module.title, maxAttempts, Module.description, lecturerID, credits, markingType, assignmentNum, Module.semesters " +
                "FROM Module, CourseUser, CourseModule " +
                "WHERE Module.moduleID = CourseModule.moduleID " +
                "AND CourseModule.courseID = CourseUser.courseID " +
                "AND CourseUser.userID = ? " +
                "AND lecturerID = ? " +
                "AND CourseModule.courseID = ?;";
        List<String> modules = new  ArrayList<String>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userID); // set userID
            statement.setInt(2, userID); // set lecturerID
            statement.setInt(3, courseID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                modules.add(resultSet.getString("moduleID"));       // 0
                modules.add(resultSet.getString("title"));          // 1
                modules.add(resultSet.getString("maxAttempts"));    // 2
                modules.add(resultSet.getString("description"));    // 3
                modules.add(resultSet.getString("lecturerID"));     // 4
                modules.add(resultSet.getString("credits"));        // 5
                modules.add(resultSet.getString("markingType"));    // 6
                modules.add(resultSet.getString("assignmentNum"));  // 7
                modules.add(resultSet.getString("semesters")); // 8
            }
            return modules;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<String>  lecturerCourseDetails (int userID) {
        String sql = "SELECT Course.courseID, Course.title, Course.degree, Course.semesters, Course.startDate, Course.endDate, Course.compensationsAllowed, Course.description, Course.graduateLevel " +
                "FROM Course,CourseUser, User " +
                "WHERE User.userID = CourseUser.userID " +
                "AND CourseUser.courseID = Course.courseID " +
                "AND User.userID = ?;";
        List<String> courses = new  ArrayList<String>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userID); // set userID
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                courses.add(resultSet.getString("courseID"));               // 0
                courses.add(resultSet.getString("title"));                  // 1
                courses.add(resultSet.getString("degree"));                 // 2
                courses.add(resultSet.getString("semesters"));              // 3
                courses.add(resultSet.getString("startDate"));              // 4
                courses.add(resultSet.getString("endDate"));                // 5
                courses.add(resultSet.getString("compensationsAllowed"));   // 6
                courses.add(resultSet.getString("description"));            // 7
                courses.add(resultSet.getString("graduateLevel"));          // 8

            }
            //System.out.println("within dao after loop, module ="+module);
            //System.out.println("number of modules = " +  (modules.size()/4) + "\nnumber of values = " + modules.size());
            return courses;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get result details
    public List<String> results(int userID, int moduleID) {
        String sql = "SELECT resultID, Result.moduleID, Result.userID, moduleGrade " +
                "FROM Module, Result, User " +
                "WHERE User.userID = Result.userID " +
                "AND Result.moduleID = Module.moduleID " +
                "AND User.userID = ? " +
                "AND Module.moduleID = ?";
        List<String> result = new  ArrayList<String>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userID); // set userID
            statement.setInt(2, moduleID); // set moduleID
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString("resultID"));       // 0
                result.add(resultSet.getString("moduleID"));       // 1
                result.add(resultSet.getString("userID"));         // 2
                result.add(resultSet.getString("moduleGrade"));    // 3
            }
            return result;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String notes (int moduleID, int week, String Type) {
        String sql = "SELECT content " +
                " FROM Notes " +
                " WHERE moduleID = ? " +
                " AND week = ? " +
                "AND type = ?";
        String note = "";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, moduleID);
            statement.setInt(2, week);
            statement.setString(3, Type);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                note = resultSet.getString("content");
                if (note == ""){
                    System.out.println("notes not posted yet");
                    return null;
                }
            }
            else {
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(note);
        return note;
    }
    // update password
    public boolean resetPass(int userID, char[] newPass) {
        String sql = "UPDATE User " +
                "SET password = ? " +
                "WHERE userID = ?;";
        List<String> result = new  ArrayList<String>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, new String(newPass)); // set userID
            statement.setInt(2, userID); // set moduleID
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0; // if no rows inserted, return false
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // take module details and insert them into a list, each set of 4 values will represent one module
    public List<String> modulesByCourse(int courseID) {
        String sql = "SELECT Module.moduleID, Module.title, maxAttempts, Module.description, lecturerID, credits, markingType, assignmentNum, Module.semesters " +
                "FROM Module, CourseModule, Course " +
                "WHERE Module.moduleID = CourseModule.moduleID " +
                "AND CourseModule.courseID = Course.courseID " +
                "AND Course.courseID = ?";
        List<String> modules = new  ArrayList<String>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, courseID); // set userID
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                modules.add(resultSet.getString("moduleID"));       // 0
                modules.add(resultSet.getString("title"));          // 1
                modules.add(resultSet.getString("maxAttempts"));    // 2
                modules.add(resultSet.getString("description"));    // 3
                modules.add(resultSet.getString("lecturerID"));     // 4
                modules.add(resultSet.getString("credits"));        // 5
                modules.add(resultSet.getString("markingType"));    // 6
                modules.add(resultSet.getString("assignmentNum"));  // 7
                modules.add(resultSet.getString("semesters"));      // 8
            }
            //System.out.println("within dao after loop, module ="+module);
            //System.out.println("number of modules = " +  (modules.size()/4) + "\nnumber of values = " + modules.size());
            return modules;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addCourse(String title, String degree, String startDay, String startMonth, String startYear, String endDay, String endMonth, String endYear, int compensationsAllowed, int managerID, String description, String graduateLevel) {
        String sql = "INSERT INTO Course " +
                "(courseID, title, degree, semesters, startDate, endDate, compensationsAllowed, managerID, description, graduateLevel) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, generateCourseID()); // set courseID
            statement.setString(2,title); // set title
            statement.setString(3,degree); // set degree
            statement.setInt(4,2); // set semesters
            statement.setDate(5,toDate(startDay,startMonth,startYear)); // set start date
            statement.setDate(6,toDate(endDay,endMonth,endYear)); // set end date
            statement.setInt(7,compensationsAllowed); // set allowed compensations
            statement.setInt(8,managerID); // set manager ID
            statement.setString(9,description); // set description
            statement.setString(10,graduateLevel); // set graduate level
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0; // if no rows inserted, return false
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editCourse(int courseID, String title, String degree, String startDay, String startMonth, String startYear, String endDay, String endMonth, String endYear, int compensationsAllowed, String description, String graduateLevel) {
        String sql = "UPDATE Course " +
                "SET title = ?, " +
                "degree = ?, " +
                "semesters = ?, " +
                "startDate = ?, " +
                "endDate = ?, " +
                "compensationsAllowed = ?, " +
                "description = ?, " +
                "graduateLevel = ? " +
                "WHERE courseID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,title); // set title
            statement.setString(2,degree); // set degree
            statement.setInt(3,2); // set semesters
            statement.setDate(4,toDate(startDay,startMonth,startYear)); // set start date
            statement.setDate(5,toDate(endDay,endMonth,endYear)); // set end date
            statement.setInt(6,compensationsAllowed); // set compensations Allowed
            statement.setString(7,description); // set description
            statement.setString(8,graduateLevel); // set graduate level
            statement.setInt(9,courseID); // set course ID
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // if no rows updated, return false
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int addModule(String title, int maxAttempts, String description, int credits, String markingType, int assignmentNum, int semesters) {
        int id = generateModuleID();
        String sql = "INSERT INTO Module " +
                "(moduleID,title,maxAttempts,description,lecturerID,credits,markingType,assignmentNum,semesters) " +
                "VALUES (?,?,?,?,?,?,?,?,?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,id); // set module ID
            statement.setString(2,title);
            statement.setInt(3,maxAttempts);
            statement.setString(4,description);
            statement.setObject(5,null);
            statement.setInt(6,credits);
            statement.setString(7,markingType);
            statement.setInt(8,assignmentNum);
            statement.setInt(9,semesters);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) { // if rows inserted
                return id; // return newly generated module ID
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    public boolean addCourseModule(int courseID, int moduleID) {
        String sql = "INSERT INTO CourseModule " +
                "(courseModuleID, courseID, moduleID) " +
                "VALUES (?,?,?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,generateCourseModuleID()); // set courseModule ID
            statement.setInt(2,courseID); // set course ID
            statement.setInt(3,moduleID); // set module ID
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0; // if rows inserted, return true
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editModule(int moduleID, String title, int maxAttempts, String description, int credits, String markingType, int assignmentNum, int semesters) {
        String sql = "UPDATE Module " +
                "SET title = ?, " +
                "maxAttempts = ?, " +
                "description = ?, " +
                "credits = ?, " +
                "markingType = ?, " +
                "assignmentNum = ?, " +
                "semesters = ? " +
                "WHERE moduleID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,title); // set title
            statement.setInt(2,maxAttempts);
            statement.setString(3,description);
            statement.setInt(4,credits);
            statement.setString(5,markingType);
            statement.setInt(6,assignmentNum);
            statement.setInt(7,semesters);
            statement.setInt(8,moduleID);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // if rows updated
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> moduleDetailsByID(int moduleID) {
        String sql = "SELECT * " +
                "FROM Module " +
                "WHERE moduleID = ?;";
        List<String> module = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, moduleID); // set module id
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                module.add(resultSet.getString("moduleID"));       // 0
                module.add(resultSet.getString("title"));          // 1
                module.add(resultSet.getString("maxAttempts"));    // 2
                module.add(resultSet.getString("description"));    // 3
                module.add(resultSet.getString("lecturerID"));     // 4
                module.add(resultSet.getString("credits"));        // 5
                module.add(resultSet.getString("markingType"));    // 6
                module.add(resultSet.getString("assignmentNum"));  // 7
                module.add(resultSet.getString("semesters")); // 8
                return module;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public List<String> courseIDsTitles() {
        String sql = "SELECT courseID, title " +
                "FROM Course;";
        List<String> courses = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                courses.add(resultSet.getString("courseID"));
                courses.add(resultSet.getString("title"));
            }
            return courses;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> allLecturers() {
        String sql = "SELECT userID, forename, surname " +
                "FROM User " +
                "WHERE accountType = 'lecturer';";
        List<String> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(resultSet.getString("userID"));
                users.add(resultSet.getString("forename"));
                users.add(resultSet.getString("surname"));
            }
            return users;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addCourseUser (int userID, int courseID) {
        String sql = "INSERT INTO CourseUser " +
                "(courseUserID, userID, courseID) " +
                "VALUES (?,?,?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,generateCourseUserID());
            statement.setInt(2,userID);
            statement.setInt(3,courseID);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean assignLecturerToCourse(int moduleID, int lecturerID) {
        String sql = "UPDATE Module " +
                "SET lecturerID = ? " +
                "WHERE moduleID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,lecturerID); // set lecturer ID
            statement.setInt(2,moduleID); // set module ID
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean approveUser(int userID) {
        String sql = "UPDATE User " +
                "SET accountApproved = true " +
                "WHERE userID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,userID); // set user ID
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // return true if rows have been updated
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkEnrollment(int userID) {
        String sql = "SELECT * " +
                "FROM CourseUser " +
                "WHERE userID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,userID); // set user ID
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // return true if anything found
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean addEmptyResult(int userID,int moduleID) {
        String sql = "INSERT INTO Result " +
                "(resultID,userID,moduleID) " +
                "VALUES (?,?,?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,generateResultID());
            statement.setInt(2,userID);
            statement.setInt(3,moduleID);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> allStudentResults(int studentID) {
        List<String> results = new ArrayList<>();
        String sql = "SELECT moduleID, moduleGrade " +
                "FROM Result " +
                "WHERE userID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,studentID); // set student ID
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                results.add(resultSet.getString("moduleGrade"));
            }
            return results;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean decision(int userID,String decision) {
        String sql = "UPDATE User " +
                "SET decision = ? " +
                "WHERE userID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,decision); // set decision
            statement.setInt(2,userID); // set user ID
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String moduleTitleFromID(int moduleID) {
        String sql = "SELECT title " +
                "FROM Module " +
                "WHERE moduleID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,moduleID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("title");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public boolean deactivate(int userID) {
        String sql = "UPDATE User " +
                "SET accountApproved = false " +
                "WHERE userID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,userID); // set user ID
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // return true if rows have been updated
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> studentsInModule(int moduleID) {
        List<String> students = new ArrayList<>();
        String sql = "SELECT User.userID, username, forename, surname, gender, email, DOB, decision " +
                "FROM User, Result, Module " +
                "WHERE User.userID = Result.userID " +
                "AND Result.moduleID = Module.moduleID " +
                "AND Module.moduleID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,moduleID); // set module ID
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                students.add(resultSet.getString("userID"));    // 0
                students.add(resultSet.getString("username"));  // 1
                students.add(resultSet.getString("forename"));  // 2
                students.add(resultSet.getString("surname"));   // 3
                students.add(resultSet.getString("gender"));    // 4
                students.add(resultSet.getString("email"));     // 5
                students.add(resultSet.getString("DOB"));       // 6
                students.add(resultSet.getString("decision"));  // 7
            }
            return students;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean uploadNotes(int week, String content, int moduleID, String type) {
        String sql = "INSERT INTO Notes " +
                "(noteID,week,content,moduleID,type) " +
                "VALUES (?,?,?,?,?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,generateNotesID()); // set note ID
            statement.setInt(2,week); // set week
            statement.setString(3,content); // set content
            statement.setInt(4,moduleID); // set module ID
            statement.setString(5,type); // set type
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateNote(int week, String content, int moduleID, String type) {
        String sql = "UPDATE Notes " +
                "SET content = ? " +
                "WHERE week = ? " +
                "AND moduleID = ? " +
                "AND type = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,content); // set new content
            statement.setInt(2,week); // set week
            statement.setInt(3,moduleID); // set module ID
            statement.setString(4,type); // set note type
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkForNote(int week, int moduleID, String type) {
        String sql = "SELECT * " +
                "FROM Notes " +
                "WHERE week = ? " +
                "AND moduleID = ? " +
                "AND type = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,week); // set week
            statement.setInt(2,moduleID); // set module ID
            statement.setString(3,type); // set note type
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // return true if note is found
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setMark(int userID, int moduleID, int mark) {
        String sql = "UPDATE Result " +
                    "SET moduleGrade = ? " +
                    "WHERE userID = ? " +
                    "AND moduleID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,mark);
            statement.setInt(2,userID);
            statement.setInt(3,moduleID);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public Date toDate(Object day,Object month,Object year) {
        if (month == "January") {
            month = "01";
        }
        else if (month == "February") {
            month = "02";
        }
        else if (month == "March") {
            month = "03";
        }
        else if (month == "April") {
            month = "04";
        }
        else if (month == "May") {
            month = "05";
        }
        else if (month == "June") {
            month = "06";
        }
        else if (month == "July") {
            month = "07";
        }
        else if (month == "August") {
            month = "08";
        }
        else if (month == "September") {
            month = "09";
        }
        else if (month == "October") {
            month = "10";
        }
        else if (month == "November") {
            month = "11";
        }
        else {
            month = "12";
        }
        String str = year + "-" + month + "-" + day;
                return java.sql.Date.valueOf(str);
    }

    public List<String> allUsers() {
        List<String> results = new ArrayList<>();
        String sql = "SELECT username, email " +
                "FROM User; ";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                results.add(resultSet.getString("username"));
                results.add(resultSet.getString("email"));
            }
            return results;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> allCourses() {
        List<String> results = new ArrayList<>();
        String sql = "SELECT courseID, title " +
                "FROM Course;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                results.add(resultSet.getString("courseID"));
                results.add(resultSet.getString("title"));
            }
            return results;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> allModules() {
        List<String> results = new ArrayList<>();
        String sql = "SELECT moduleID, title " +
                "FROM Module;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                results.add(resultSet.getString("moduleID"));
                results.add(resultSet.getString("title"));
            }
            return results;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkCourseModule(int courseID, int moduleID) {
        String sql = "SELECT * " +
                "FROM CourseModule " +
                "WHERE courseID = ? " +
                "AND moduleID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,courseID); // set course ID
            statement.setInt(2,moduleID); // set module ID
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // return true if user course-module connection is found
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkLecturerModule(int moduleID, int lecturerID) {
        String sql = "SELECT * " +
                "FROM Module " +
                "WHERE moduleID = ? " +
                "AND lecturerID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,moduleID); // set module ID
            statement.setInt(2,lecturerID); // set lecturer ID
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // return true if user module-lecturer connection is found
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
