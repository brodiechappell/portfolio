import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewStudentHome {
    private JPanel panelMain;
    private JScrollPane paneScroll;
    private JPanel innerScroll;
    private JPanel detailsPanel;
    private JLabel accDetailsTxtLabel;
    private JLabel courseDetailsTxt;
    private JPanel modulePanelLeft;
    private JButton logoutButton;
    private JButton passButton;
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

    public void setAccDetailsTxtLabel(JLabel accDetailsTxtLabel) {
        this.accDetailsTxtLabel = accDetailsTxtLabel;
    }

    public JPanel getModulePanelLeft() {
        return modulePanelLeft;
    }

    public void setModulePanelLeft(JPanel modulePanelLeft) {
        this.modulePanelLeft = modulePanelLeft;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public void setLogoutButton(JButton logoutButton) {
        this.logoutButton = logoutButton;
    }

    public JButton getPassButton() {
        return passButton;
    }

    public void setPassButton(JButton passButton) {
        this.passButton = passButton;
    }

    public void setInfo(int userID) {
        setAccDetailsTxt(userID);
        setCourseDetailsTxt(userID);
        setModuleList(userID);
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
                    + "<b>Approved:</b> " + approvedToString(Integer.parseInt(userDetails.get(8))) + "<br>"
                    + "<b>Decision:</b> " + userDetails.get(9) + "<br>"
                    + "</html>";
            accDetailsTxtLabel.setText(text);
        } else {
            accDetailsTxtLabel.setText("Account info not available");
        }
    }

    public void setCourseDetailsTxt(int userID) {
        List<String> courseDetails = daoUser.courseDetails(userID);
        if (courseDetails != null && courseDetails.size() >= 9) {
            String text = "<html>"
                    + "<h1>" + courseDetails.get(1) + " - " + courseDetails.get(2) +"</h1>"
                    + formatDesc(courseDetails.get(8)) + "<br>"
                    + "</html>";
            courseDetailsTxt.setText(text);
        } else {
            courseDetailsTxt.setText("Not enrolled in a course!");
        }
    }

    public void setModuleList(int userID) {
        // define constraints for label and buttons
        GridBagConstraints layout = new GridBagConstraints();
        layout.anchor = GridBagConstraints.NORTH;
        layout.gridy = 1;
        layout.gridx = 0;
        layout.ipady = 10;
        layout.insets = new Insets(5, 0, 5, 0);
        modulePanelLeft.add(new JLabel("<html><h2> Your Modules \n</h2></html>"),layout);
        List<String> modules = daoUser.modules(userID);
        layout.gridy ++; // increment placement on y axis
        if (!modules.isEmpty()) { // if not empty
            for (int i = 0; i < modules.size(); i += 9) { // iterate through list for every module
                layout.gridy ++; // increment y axis placement
                modulePanelLeft.add(new JButton(modules.get(i) + " - " + modules.get(i + 1)),layout); // add button based on current module id + title
            }
        }
        else  {
            //modulePanelLeft.add(new JLabel("No modules"));
        }
    }

    public String formatDesc(String description) {
        StringBuilder sb = new StringBuilder();
        sb.append("<p>");
        int j = 0;
        for (int i = 0; i < description.length(); i++) {
            if (j > 45 && description.charAt(i) == ' ') {
                sb.append("<br>");
                j = 0;
            }
            else {
                sb.append(description.charAt(i));
            }
            j++;
        }
        sb.append("</p>");
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

    public JLabel getCourseDetailsTxt() {
        return courseDetailsTxt;
    }

    public void setCourseDetailsTxt(JLabel courseDetailsTxt) {
        this.courseDetailsTxt = courseDetailsTxt;
    }
}
