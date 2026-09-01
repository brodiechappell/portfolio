import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewLecturerHome {
    private JPanel panelMain;
    private JScrollPane paneScroll;
    private JPanel innerScroll;
    private JPanel detailsPanel;
    private JLabel accDetailsTxtLabel;
    private JPanel  courseListPanel;
    private JButton courseListButton;
    private JButton passButton;
    private JButton logoutButton;
    private DAOUser daoUser = new DAOUser();

    public JPanel getPanelMain() {
        return panelMain;
    }

    public void setPanelMain(JPanel panelMain) {
        this.panelMain = panelMain;
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

    public JPanel getCourseListPanel() {
        return courseListPanel;
    }

    public void setCourseListPanel(JPanel courseListPanel) {
        this.courseListPanel = courseListPanel;
    }

    public JButton getCourseListButton() {
        return courseListButton;
    }

    public void setCourseListButton(JButton courseListButton) {
        this.courseListButton = courseListButton;
    }

    public JButton getPassButton() {
        return passButton;
    }

    public void setPassButton(JButton passButton) {
        this.passButton = passButton;
    }

    public void setAccDetailsTxtLabel(int userID) {
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
                    + "<b>Approved:</b> " + approvedToString(Integer.parseInt(userDetails.get(8))) + "<br>"
                    + "<b>Qualification:</b> " + userDetails.get(11)
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
        List<String> courses = daoUser.courseDetails(userID);
        if (courses != null && !courses.isEmpty()) {
            for (int i = 0; i < courses.size(); i += 10) { // iterate through courses
                courseListPanel.add(new JButton(courses.get(i) + " - " + courses.get(i+1)),layout); layout.gridy++;
                // call method for populating course panel with info
                courseListPanel.add(fillCoursePanel(userID,courses,i),layout); layout.gridy++;
            }
        }
    }

    public JPanel fillCoursePanel(int userID, List courseList, int i) {
        GridBagConstraints layout = new GridBagConstraints();
        layout.anchor = GridBagConstraints.NORTH;
        layout.gridy = 1;
        layout.gridx = 0;
        layout.ipady = 10;
        layout.insets = new Insets(5, 0, 5, 0);
        JPanel coursePanel = new JPanel();
        coursePanel.setLayout(new GridBagLayout());
        coursePanel.add(new JLabel( courseList.get(i+4) + " - " +  courseList.get(i+5)),layout); layout.gridy++; // display start date to end date
        coursePanel.add(new JLabel((formatDesc(courseList.get(8).toString()))),layout); layout.gridy++; // display description
        coursePanel.add(new JLabel("<html><h2> Modules \n</h2></html>"),layout); layout.gridy++;
        List<String> modules = daoUser.lecturerModuleDetails(userID,Integer.parseInt((String) courseList.get(i))); // get modules in this course
        for (int j = 0; j < modules.size(); j+= 9) {
            coursePanel.add(new JButton(modules.get(j) + " - " +  modules.get(j+1)),layout); layout.gridy++;
            // call method for creating module panel
            coursePanel.add(fillModulePanel(modules, j),layout); layout.gridy++;
        }
        return coursePanel;
    }

    public JPanel fillModulePanel(List modules, int i) {
        GridBagConstraints layout = new GridBagConstraints();
        layout.anchor = GridBagConstraints.NORTH;
        layout.gridy = 1;
        layout.gridx = 0;
        layout.ipady = 10;
        layout.insets = new Insets(5, 0, 5, 0);
        JPanel modulePanel = new JPanel();
        modulePanel.setLayout(new GridBagLayout());
        modulePanel.add(new JLabel(formatDesc(modules.get(3).toString())),layout); layout.gridy++;
        modulePanel.add(new JButton("Upload notes"),layout); layout.gridy++;
        modulePanel.add(new JLabel("<html><h2> Students \n</h2></html>"),layout); layout.gridy++;
        List<String> students = daoUser.studentsInModule(Integer.parseInt((String) modules.get(i))); // get modules in this course
        for (int j = 0; j < students.size(); j+= 8) {
            modulePanel.add(new JButton((students.get(j) + " - " +  students.get(j+2)+" "+students.get(j+3))),layout); layout.gridy++;
            // call method for creating student panel
            modulePanel.add(fillStudentPanel(students, j),layout); layout.gridy++;
        }
        return modulePanel;
    }

    public JPanel fillStudentPanel(List<String> students, int i) {
        GridBagConstraints layout = new GridBagConstraints();
        layout.anchor = GridBagConstraints.NORTH;
        layout.gridy = 1;
        layout.gridx = 0;
        layout.ipady = 10;
        layout.insets = new Insets(5, 0, 5, 0);
        JPanel studentPanel = new JPanel();
        studentPanel.setLayout(new GridBagLayout());
        String text = "<html>"
                + "<h1>" + students.get(i+2) + " " + students.get(i+3) + "</h1>"
                + "<b>User ID:</b> " + students.get(i+0) + "<br>"
                + "<b>Username:</b> " + students.get(i+1) + "<br>"
                + "<b>Gender:</b> " + students.get(i+4) + "<br>"
                + "<b>Email:</b> " + students.get(i+5) + "<br>"
                + "<b>Date of Birth:</b> " + students.get(i+6) + "<br>"
                + "<b>Decision:</b> " + students.get(i+7) + "<br>"
                + "</html>";
        studentPanel.add(new JLabel(text),layout); layout.gridy++;
        studentPanel.add(new JButton("Set mark"),layout); layout.gridy++;
        return studentPanel;
    }

    public JButton getLogoutButton() {return logoutButton;}

    public void setLogoutButton(JButton logoutButton) {this.logoutButton = logoutButton;}

    public void setInfo(int userID) {
        setAccDetailsTxtLabel(userID);
        setCourseList(userID);// initially all drop down menus are closed
        courseListPanel.setVisible(false);
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

    public String approvedToString(int approved) {
        if (approved == 0) {
            return "No";
        }
        else {
            return "Yes";
        }
    }
}

/*
(all in a scroll pane)
account details panel
courselist panel
-hide course button (are multiple)
-course panel (are multiple)
-- hide module button (are multiple)
-- module panel (are multiple)
--- upload notes button (will open optionpane similar to student download notes button)
--- hide students button
--- student button (are multiple) (clicking button will open joptionpane with 2 options: view details, set mark)


 */
