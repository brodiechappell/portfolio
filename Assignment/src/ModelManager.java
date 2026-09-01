import java.util.Date;
import java.util.List;

public class ModelManager extends ModelLecturer {
    private List<String> courseList;
    private List<String> moduleList;
    private List<String> unapprovedAccs;
    private List<String> lecturerList;
    private List<String> studentList;

    public ModelManager() {
        this.daoUser = new DAOUser();
    }

    public List<String> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<String> courseList) {
        this.courseList = courseList;
    }

    public List<String> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<String> moduleList) {
        this.moduleList = moduleList;
    }

    public List<String> getUnapprovedAccs() {
        return unapprovedAccs;
    }

    public void setUnapprovedAccs(List<String> unapprovedAccs) {
        this.unapprovedAccs = unapprovedAccs;
    }

    public List<String> getLecturerList() {
        return lecturerList;
    }

    public void setLecturerList(List<String> lecturerList) {
        this.lecturerList = lecturerList;
    }

    public List<String> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<String> studentList) {
        this.studentList = studentList;
    }

    public boolean approveUser(int userID) {
        return daoUser.approveUser(userID);
    }

    public String enrol(int userID, int courseID) {
        if (daoUser.checkEnrollment(userID)) {
            return "already enrolled";
        }
        if (daoUser.addCourseUser(userID, courseID)) {
            List<String> mods = daoUser.modulesByCourse(courseID);
            for (int i = 0; i < mods.size(); i += 9) {
                if (!daoUser.addEmptyResult(userID, Integer.parseInt(mods.get(i)))) {
                    return "false";
                }
            }
            return "success";
        }
        return "fail";
    }

    public List<String> allStudentResults(int studentID) {
        return daoUser.allStudentResults(studentID);
    }

    public String moduleTitleFromID(int moduleID) {
        return daoUser.moduleTitleFromID(moduleID);
    }

    public boolean decision(int userID, String decision) {
        return daoUser.decision(userID, decision);
    }

    public boolean deactivate(int userID) {
        return daoUser.deactivate(userID);
    }

    public boolean editModule(int moduleID, String title, int maxAttempts, String description, int credits, String markingType, int assignmentNum, int semesters) {
        if (!(title.isEmpty() || description.isEmpty() || maxAttempts < 1)) { // if fields are not empty and max attempts is more than 1
            if (title.length() <= 20 && credits <= 20 && maxAttempts < 10) { // if fields are within database varchar limits
                List<String> compare = daoUser.allModules();
                for (int i = 0; i < compare.size(); i += 2) {
                    if (Integer.parseInt(compare.get(i)) != moduleID && compare.get(i + 1).equals(title)) {
                        return false;
                    }
                }
                return daoUser.editModule(moduleID, title, maxAttempts, description, credits, markingType, assignmentNum, semesters);
            }
        }
        return false;
    }

    public List<String> moduleDetailsByID(int moduleID) {
        if (moduleID > 9999 && moduleID < 1000000) {
            return daoUser.moduleDetailsByID(moduleID);
        }
        return null;
    }

    public List<String> courseIDsTitles() {
        return daoUser.courseIDsTitles();
    }

    public boolean addCourseModule(int courseID, int moduleID) {
        return daoUser.addCourseModule(courseID, moduleID);
    }

    public List<String> allLecturers() {
        return daoUser.allLecturers();
    }

    public boolean addCourseUser(int userID, int courseID) {
        return daoUser.addCourseUser(userID, courseID);
    }

    public boolean assignLecturerToCourse(int moduleID, int lecturerID) {
        return daoUser.assignLecturerToCourse(moduleID, lecturerID);
    }

    public List<String> courseDetailsByCourseID(int courseID) {
        return daoUser.courseDetailsByCourseID(courseID);
    }

    public boolean addCourse(String title, String degree, String startDay, String startMonth, String startYear, String endDay, String endMonth, String endYear, String compensationsAllowed, int managerID, String description, String graduateLevel) {
        graduateLevel = graduateLevel.toLowerCase();
        if (!(title.isEmpty() || degree.isEmpty() || compensationsAllowed.isEmpty() || description.isEmpty() || graduateLevel.isEmpty())) { // if fields are not empty
            if (title.length() <= 20 && degree.length() <= 25) { // if fields are within database varchar limits
                if (validateDate(startDay, startMonth, startYear) && validateDate(endDay, endMonth, endYear)) { // if dates are valid
                    if (Integer.parseInt(startYear) < Integer.parseInt(endYear)) { // if end year is after start year
                        List<String> compare = daoUser.allCourses();
                        for (int i = 0; i < compare.size(); i += 2) {
                            if (compare.get(i + 1).equals(title)) {
                                return false;
                            }
                        }
                        return daoUser.addCourse(title, degree, startDay, startMonth, startYear, endDay, endMonth, endYear, Integer.parseInt(compensationsAllowed), managerID, description, graduateLevel);
                    }
                }
            }
        }
        return false;
    }

    public boolean editCourse(int courseID, String title, String degree, String startDay, String startMonth, String startYear, String endDay, String endMonth, String endYear, String compensationsAllowed, String description, String graduateLevel) {
        graduateLevel = graduateLevel.toLowerCase();
        if (!(title.isEmpty() || degree.isEmpty() || compensationsAllowed.isEmpty() || description.isEmpty() || graduateLevel.isEmpty())) { // if fields are not empty
            if (title.length() <= 20 && degree.length() <= 25) { // if fields are within database varchar limits
                if (validateDate(startDay, startMonth, startYear) && validateDate(endDay, endMonth, endYear)) { // if dates are valid
                    if (Integer.parseInt(startYear) < Integer.parseInt(endYear)) { // if end year is after start year
                        List<String> compare = daoUser.allCourses();
                        for (int i = 0; i < compare.size(); i += 2) {
                            if (Integer.parseInt(compare.get(i)) != courseID && compare.get(i + 1).equals(title)) {
                                return false;
                            }
                        }
                        return daoUser.editCourse(courseID, title, degree, startDay, startMonth, startYear, endDay, endMonth, endYear, Integer.parseInt(compensationsAllowed), description, graduateLevel);
                    }
                }
            }
        }
        return false;
    }


    public boolean addModule(int courseID, String title, int maxAttempts, String description, int credits, String markingType, int assignmentNum, int semesters) {
        if (!(title.isEmpty() || description.isEmpty() || maxAttempts < 1)) { // if fields are not empty and max attempts is more than 1
            if (title.length() <= 20 && credits <= 20 && maxAttempts < 10) { // if fields are within database varchar limits
                List<String> compare = daoUser.allModules();
                for (int i = 0; i < compare.size(); i += 2) {
                    if (compare.get(i + 1).equals(title)) {
                        return false;
                    }
                }
                return addCourseModule(courseID, daoUser.addModule(title, maxAttempts, description, credits, markingType, assignmentNum, semesters)); // return true if sql query works

            }
        }
        return false;
    }

    public boolean checkCourseModule(int courseID, int moduleID) {
        return daoUser.checkCourseModule(courseID, moduleID);
    }

    public boolean checkLecturerModule(int moduleID, int lecturerID) {
        return daoUser.checkLecturerModule(moduleID, lecturerID);
    }
}
