import javax.swing.*;
import java.util.Date;
import java.util.List;

public class ModelLecturer extends ModelUser{
    private String moduleTitle;
    private String courseTitle;
    private int semesters;
    private Date startDate;
    private Date endDate;
    private List<String> studentAttendingList;
    private int examResult;



    public ModelLecturer() {
        this.daoUser = new DAOUser();
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public int getSemesters() {
        return semesters;
    }

    public void setSemesters(int semesters) {
        this.semesters = semesters;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<String> getStudentAttendingList() {return studentAttendingList;}

    public void setStudentAttendingList(List<String> courseList) {this.studentAttendingList = studentAttendingList;}

    public int getExamResult() {
        return examResult;
    }

    public String getCourseTitle() {return courseTitle;}

    public void editNotes(String ID) {
    }

    public void deleteNotes(String ID) {
    }

    public void addNotes(Long notes){
    }

    public boolean setMark(int userID, int moduleID, int mark) {
        if (mark >= 0 && mark <= 100) {
            return daoUser.setMark(userID, moduleID, mark);
        }
        return false;
    }

    public int average(Object[] inputs) {
        int markCount = 0;
        int totalMark = 0;
        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i] instanceof JTextField) {
                totalMark += Integer.parseInt(((JTextField) inputs[i]).getText());
                markCount++;
            }
        }
        return totalMark / markCount;
    }

    public boolean uploadNotes(int week, String content, int moduleID, String type) {
        if (daoUser.checkForNote(week,moduleID,type)) {
            return  daoUser.updateNote(week,content,moduleID,type);
        }
        return daoUser.uploadNotes(week, content, moduleID, type);
    }

    public String markingType(int moduleID) {
        List<String> moduleDetails = daoUser.moduleDetailsByID(moduleID);
        return moduleDetails.get(6);
    }

    public int assignmentNum(int moduleID) {
        List<String> moduleDetails = daoUser.moduleDetailsByID(moduleID);
        return Integer.parseInt(moduleDetails.get(7));
    }
}