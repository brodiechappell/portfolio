import java.util.Date;

class ModelStudent extends ModelUser {
    private String courseTitle;
    private String degree;
    private int compensations;
    private int semesters;
    private Date startDate;
    private Date endDate;
    private String[] modTitle;
    private int[] modMaxAttempts;
    private int[] modPassingGrade;
    private int[] modGrade;

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public int getCompensations() {
        return compensations;
    }

    public void setCompensations(int compensations) {
        this.compensations = compensations;
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

    public String[] getModTitle() {
        return modTitle;
    }

    public void setModTitle(String[] modTitle) {
        this.modTitle = modTitle;
    }

    public int[] getModMaxAttempts() {
        return modMaxAttempts;
    }

    public void setModMaxAttempts(int[] modMaxAttempts) {
        this.modMaxAttempts = modMaxAttempts;
    }

    public int[] getModPassingGrade() {
        return modPassingGrade;
    }

    public void setModPassingGrade(int[] modPassingGrade) {
        this.modPassingGrade = modPassingGrade;
    }

    public int[] getModGrade() {
        return modGrade;
    }

    public void setModGrade(int[] modGrade) {
        this.modGrade = modGrade;
    }

}