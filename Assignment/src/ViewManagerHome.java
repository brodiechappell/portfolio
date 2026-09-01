import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewManagerHome {

    private JPanel panelMain;
    private JScrollPane paneScroll;
    private JPanel innerScroll;
    private JPanel detailsPanel;
    private JLabel accDetailsTxtLabel;
    private JPanel courseListPanel;
    private JButton passButton;
    private JButton courseListButton;
    private JButton accsButton;
    private JPanel accountsPanel;
    private JButton unapprovedAccsButton;
    private JPanel unapprovedAccsPanel;
    private JButton studentAccsButton;
    private JPanel studentAccsPanel;
    private JPanel lecturerAccsPanel;
    private JButton lecturerAccsButton;
    private JButton viewCourses;
    private JButton logoutButton;
    private JButton refreshPageButton;
    private DAOUser daoUser = new DAOUser();

    public JPanel getPanelMain() {
        return panelMain;
    }

    public void setPanelMain(JPanel panelMain) {
        this.panelMain = panelMain;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public void setLogoutButton(JButton logoutButton) {
        this.logoutButton = logoutButton;
    }

    public JScrollPane getPaneScroll() {
        return paneScroll;
    }

    public void setPaneScroll(JScrollPane paneScroll) {
        this.paneScroll = paneScroll;
    }

    public JPanel getInnerScroll() {
        return innerScroll;
    }

    public void setInnerScroll(JPanel innerScroll) {
        this.innerScroll = innerScroll;
    }

    public JPanel getDetailsPanel() {
        return detailsPanel;
    }

    public void setDetailsPanel(JPanel detailsPanel) {
        this.detailsPanel = detailsPanel;
    }

    public JLabel getAccDetailsTxtLabel() {
        return accDetailsTxtLabel;
    }

    public void setAccDetailsTxtLabel(JLabel accDetailsTxtLabel) {
        this.accDetailsTxtLabel = accDetailsTxtLabel;
    }

    public JPanel getCourseListPanel() {
        return courseListPanel;
    }

    public void setCourseListPanel(JPanel courseListPanel) {
        this.courseListPanel = courseListPanel;
    }

    public JButton getPassButton() {
        return passButton;
    }

    public void setPassButton(JButton passButton) {
        this.passButton = passButton;
    }

    public JButton getCourseListButton() {
        return courseListButton;
    }

    public void setCourseListButton(JButton courseListButton) {
        this.courseListButton = courseListButton;
    }

    public JButton getAccsButton() {
        return accsButton;
    }

    public void setAccsButton(JButton accsButton) {
        this.accsButton = accsButton;
    }

    public JPanel getAccountsPanel() {
        return accountsPanel;
    }

    public void setAccountsPanel(JPanel accountsPanel) {
        this.accountsPanel = accountsPanel;
    }

    public JButton getUnapprovedAccsButton() {
        return unapprovedAccsButton;
    }

    public void setUnapprovedAccsButton(JButton unapprovedAccsButton) {
        this.unapprovedAccsButton = unapprovedAccsButton;
    }

    public JPanel getUnapprovedAccsPanel() {
        return unapprovedAccsPanel;
    }

    public void setUnapprovedAccsPanel(JPanel unapprovedAccsPanel) {
        this.unapprovedAccsPanel = unapprovedAccsPanel;
    }

    public JButton getStudentAccsButton() {
        return studentAccsButton;
    }

    public void setStudentAccsButton(JButton studentAccsButton) {
        this.studentAccsButton = studentAccsButton;
    }

    public JPanel getStudentAccsPanel() {
        return studentAccsPanel;
    }

    public void setStudentAccsPanel(JPanel studentAccsPanel) {
        this.studentAccsPanel = studentAccsPanel;
    }

    public JPanel getLecturerAccsPanel() {
        return lecturerAccsPanel;
    }

    public void setLecturerAccsPanel(JPanel lecturerAccsPanel) {
        this.lecturerAccsPanel = lecturerAccsPanel;
    }

    public JButton getLecturerAccsButton() {
        return lecturerAccsButton;
    }

    public void setLecturerAccsButton(JButton lecturerAccsButton) {
        this.lecturerAccsButton = lecturerAccsButton;
    }

    public JButton getViewCourses() {
        return viewCourses;
    }

    public void setViewCourses(JButton viewCourses) {
        this.viewCourses = viewCourses;
    }

    public DAOUser getDaoUser() {
        return daoUser;
    }

    public void setDaoUser(DAOUser daoUser) {
        this.daoUser = daoUser;
    }

    public JButton getRefreshPageButton() {
        return refreshPageButton;
    }

    public void setRefreshPageButton(JButton refreshPageButton) {
        this.refreshPageButton = refreshPageButton;
    }

    public void setInfo(int userID) {
        setAccDetailsTxt(userID);
        setCourseList(userID);
        setAccountsList(userID);
        accountsPanel.setVisible(false); // initially all drop down menus are closed
        courseListPanel.setVisible(false);
        unapprovedAccsPanel.setVisible(false);
        studentAccsPanel.setVisible(false);
        lecturerAccsPanel.setVisible(false);
    }

    public void setAccDetailsTxt(int userID) {
        List<String> userDetails = daoUser.accountDetails(userID);
        if (userDetails != null && userDetails.size() >= 10) {
            // Create a nice HTML string with all the info except password
            String text = "<html>"
                    + "<h1>" + userDetails.get(3) + " " + userDetails.get(4) + "</h1>"
                    + "<b>User ID:</b> " + userDetails.get(0) + "<br>"
                    + "<b>Username:</b> " + userDetails.get(2) + "<br>"
                    + "<b>Gender:</b> " + userDetails.get(5) + "<br>"
                    + "<b>Email:</b> " + userDetails.get(6) + "<br>"
                    + "<b>Date of Birth:</b> " + userDetails.get(7) + "<br>"
                    + "</html>";
            accDetailsTxtLabel.setText(text);
        } else {
            accDetailsTxtLabel.setText("Account info not available");
        }
    }

    public void setCourseList(int userID) {
        GridBagConstraints layout = new GridBagConstraints();
        layout.anchor = GridBagConstraints.NORTH;
        layout.gridy = 1;
        layout.gridx = 0;
        layout.ipady = 10;
        layout.insets = new Insets(5, 0, 5, 0);
        courseListPanel.add(new JLabel("<html><h2> Courses \n</h2></html>"),layout); layout.gridy++;
        courseListPanel.add(new JButton("Add new course"), layout);
        layout.gridy++;
        List<String> courses = daoUser.managerCourseDetails(userID);
        if (courseListPanel.getComponentCount() == 2) {
            if (courses != null && !courses.isEmpty()) {
                for (int i = 0; i < courses.size(); i += 10) { // iterate through courses
                    courseListPanel.add(new JButton(courses.get(i) + " - " + courses.get(i + 1)), layout);
                    layout.gridy++;
                    // call method for populating course panel with info
                    courseListPanel.add(fillCoursePanel(courses, i), layout);
                    layout.gridy++;
                }
            }
        }
    }

    public void setAccountsList(int userID) {
        List<String> unapprovedAccs = daoUser.unapprovedAccounts();
        List<String> approvedStudents = daoUser.studentAccounts();
        List<String> approvedLecturers = daoUser.lecturerAccounts();

        // add each unapproved account
        if (unapprovedAccs != null) {
            fillUnapprovedPanel(daoUser.unapprovedAccounts());
        }

        // add each student
        if (approvedStudents != null) {
            fillStudentPanel(daoUser.studentAccounts());
        }

        // add each lecturer
        if (approvedLecturers != null) {
            fillLecturerPanel(daoUser.lecturerAccounts());
        }
    }

    public JPanel fillCoursePanel(List courseList, int i) {
        GridBagConstraints layout = new GridBagConstraints();
        layout.anchor = GridBagConstraints.NORTH;
        layout.gridy = 1;
        layout.gridx = 0;
        layout.ipady = 10;
        layout.insets = new Insets(5, 0, 5, 0);
        JPanel coursePanel = new JPanel();
        coursePanel.setLayout(new GridBagLayout());
        coursePanel.add(new JLabel(courseList.get(i + 4) + " - " + courseList.get(i + 5)), layout);
        layout.gridy++; // display start date to end date
        coursePanel.add(new JLabel(formatDesc(courseList.get(i+8).toString())), layout);
        layout.gridy++; // display description
        coursePanel.add(new JButton("Edit course details"), layout);
        layout.gridy++;
        coursePanel.add(new JLabel("<html><h2> Modules \n</h2></html>"),layout); layout.gridy++;
        coursePanel.add(new JButton("Add new module"), layout);
        layout.gridy++;
        List<String> modules = daoUser.modulesByCourse(Integer.parseInt((String) courseList.get(i))); // get modules in this course
        for (int j = 0; j < modules.size(); j += 9) {
            coursePanel.add(new JButton(modules.get(j) + " - " + modules.get(j + 1)), layout);
            layout.gridy++;
            // call method for creating module panel
            coursePanel.add(fillModulePanel(modules, j), layout);
            layout.gridy++;
        }
        return coursePanel;
    }

    public void fillUnapprovedPanel(List unapprovedAccountList) {
        GridBagConstraints layout = new GridBagConstraints();
        layout.anchor = GridBagConstraints.NORTH;
        layout.gridy = 1;
        layout.gridx = 0;
        layout.ipady = 10;
        layout.insets = new Insets(5, 0, 5, 0);
        unapprovedAccsPanel.setLayout(new GridBagLayout());
        unapprovedAccsPanel.add(new JLabel("<html><h2> Unapproved Accounts \n</h2></html>"),layout); layout.gridy++;


        // create button for every unapproved account
        for (int i = 0; i < unapprovedAccountList.size(); i += 12) {
            unapprovedAccsPanel.add(new JButton(unapprovedAccountList.get(i) + " - " + unapprovedAccountList.get(i + 3) + " " + unapprovedAccountList.get(i + 4)), layout);
            layout.gridy++;
        }
    }

    public void fillStudentPanel(List studentList) {
        GridBagConstraints layout = new GridBagConstraints();
        layout.anchor = GridBagConstraints.NORTH;
        layout.gridy = 1;
        layout.gridx = 0;
        layout.ipady = 10;
        layout.insets = new Insets(5, 0, 5, 0);
        studentAccsPanel.setLayout(new GridBagLayout());
        studentAccsPanel.add(new JLabel("<html><h2> Student Accounts \n</h2></html>"),layout); layout.gridy++;

        // create panel for every student account
        for (int i = 0; i < studentList.size(); i += 12) {
            studentAccsPanel.add(new JButton(studentList.get(i) + " - " + studentList.get(i + 3) + " " + studentList.get(i + 4)), layout);
            layout.gridy++;
            JPanel studentPanel = new JPanel();
            studentPanel.setLayout(new GridBagLayout());
            String text = "<html>"
                    + "<h2>" + studentList.get(i+3) + " " + studentList.get(i+4) + "</h2>"
                    + "<b>User ID:</b> " + studentList.get(i) + "<br>"
                    + "<b>Username:</b> " + studentList.get(i+2) + "<br>"
                    + "<b>Gender:</b> " + studentList.get(i+5) + "<br>"
                    + "<b>Email:</b> " + studentList.get(i+6) + "<br>"
                    + "<b>Date of Birth:</b> " + studentList.get(i+7) + "<br>"
                    + "<b>Decision:</b> " + studentList.get(i+9) + "<br>"
                    + "</html>";
            studentPanel.add(new JLabel(text), layout); layout.gridy++;
            studentPanel.add(new JButton("Enroll in course"), layout);layout.gridy++;
            studentPanel.add(new JButton("Issue decision"), layout); layout.gridy++;
            studentPanel.add(new JButton("Reset password"), layout);
            layout.gridy++;
            studentPanel.add(new JButton("Deactivate account"), layout);
            layout.gridy++;
            studentAccsPanel.add(studentPanel,layout);
            layout.gridy++;
        }
    }

    public void fillLecturerPanel(List lecturerList) {
        GridBagConstraints layout = new GridBagConstraints();
        layout.anchor = GridBagConstraints.NORTH;
        layout.gridy = 1;
        layout.gridx = 0;
        layout.ipady = 10;
        layout.insets = new Insets(5, 0, 5, 0);
        lecturerAccsPanel.setLayout(new GridBagLayout());
        lecturerAccsPanel.add(new JLabel("<html><h2> Lecturer Accounts \n</h2></html>"),layout); layout.gridy++;

        // create panel for every student account
        for (int i = 0; i < lecturerList.size(); i += 12) {
            lecturerAccsPanel.add(new JButton(lecturerList.get(i) + " - " + lecturerList.get(i + 3) + " " + lecturerList.get(i + 4)), layout);
            layout.gridy++;
            JPanel lecturerPanel = new JPanel();
            lecturerPanel.setLayout(new GridBagLayout());
            String text = "<html>"
                    + "<h1>" + lecturerList.get(i+3) + " " + lecturerList.get(i+4) + "</h1>"
                    + "<b>User ID:</b> " + lecturerList.get(i) + "<br>"
                    + "<b>Username:</b> " + lecturerList.get(i+2) + "<br>"
                    + "<b>Gender:</b> " + lecturerList.get(i+5) + "<br>"
                    + "<b>Email:</b> " + lecturerList.get(i+6) + "<br>"
                    + "<b>Date of Birth:</b> " + lecturerList.get(i+7) + "<br>"
                    + "<b>Qualification: </b>" + lecturerList.get(i+11) + "<br>"
                    + "</html>";
            lecturerPanel.add(new JLabel(text), layout);
            layout.gridy++;
            lecturerPanel.add(new JButton("Reset password"), layout);
            layout.gridy++;
            lecturerPanel.add(new JButton("Deactivate account"), layout);
            layout.gridy++;
            lecturerAccsPanel.add(lecturerPanel,layout);
            layout.gridy++;
        }
    }

    public JPanel fillModulePanel(List moduleList, int i) {
        GridBagConstraints layout = new GridBagConstraints();
        layout.anchor = GridBagConstraints.NORTH;
        layout.gridy = 1;
        layout.gridx = 0;
        layout.ipady = 10;
        layout.insets = new Insets(5, 0, 5, 0);
        JPanel modulePanel = new JPanel();
        modulePanel.setLayout(new GridBagLayout());
        modulePanel.add(new JLabel(formatDesc(moduleList.get(i+3).toString())), layout);
        layout.gridy++;
        if (Integer.parseInt((String) moduleList.get(i+8)) == 1) {
            modulePanel.add(new JLabel( moduleList.get(i+8)+" semester"),layout); layout.gridy++;
        }
        else {
            modulePanel.add(new JLabel( moduleList.get(i+8)+" semesters"),layout); layout.gridy++;
        }
        modulePanel.add(new JButton("Edit module"), layout);
        layout.gridy++;
        modulePanel.add(new JButton("Assign to course"), layout);
        layout.gridy++;
        modulePanel.add(new JButton("Assign to lecturer"), layout);
        layout.gridy++;
        return modulePanel;
    }

    public String formatDesc(String description) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        int j = 0;
        for (int i = 0; i < description.length(); i++) {
            if (j > 45 && description.charAt(i) == ' ') {
                sb.append("<br>");
                j = 0;
            }
            j++;
            sb.append(description.charAt(i));
        }
        sb.append("</html>");
        return sb.toString();
    }
}
/*
my idea for the layout:

everything wrapped in a scroll pane with innersroll jpanel inside that
within innerscroll:
    acc details
    "courses" button that hides/reveals courselist panel
    courselist panel which contains:
        add course button
        course buttons each hiding/revealing their respective course panel
        course panel for each course which contains:
            course information
            edit course button
            add module button
            module buttons, each hiding/revealing their respective module panel
            module panel for each module in this course which contains:
                module information
                edit module button
                assign to course button
                assign to lecturer button
    "accounts" button which hides/reveals accounts panel
    accounts panel which contains:
        "unapproved accounts" button that hides/reveals unapprovedaccountlist panel
        unapprovedaccountlist panel which contains:
            unapproved account details, approve account button next to that
        approvedaccountlist panel for each approved account which contains:
            account details
            enrol user in course button
            issue student decision button (only shows up if user is student
            reset password button
            deactivate account button


        WORK IN PROGRESS but general idea is to create variable number of panels that will hold many labels and buttons for each course or module, which will need to be done manually in this class
        form can still be used for a base i.e. have scroll panel, innerscroll, courselist etc
 */
