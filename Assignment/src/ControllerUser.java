import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ControllerUser {
    private ViewLogin vLogin;
    private ViewSignup vSignup;
    private ViewStudentHome vStudent;
    private ViewLecturerHome vLecturer;
    private ViewManagerHome vManager;
    private ModelUser mUser;
    private ModelLecturer mLect;
    private ModelManager mMan;

    public ControllerUser (ViewLogin vLogin, ViewSignup vSignup, ModelUser mUser, ModelLecturer mLect, ModelManager mMan, ViewStudentHome vStudent, ViewLecturerHome vLecturer, ViewManagerHome vManager) {
        this.vLogin = vLogin;
        this.vSignup = vSignup;
        this.mUser = mUser;
        this.mLect = mLect;
        this.mMan = mMan;
        this.vStudent = vStudent;
        this.vLecturer = vLecturer;
        this.vManager = vManager;

        //initialise views
        vLogin.getPanelMain().setVisible(true); // make signup ready to be displayed
        vSignup.getPanelMain().setVisible(false); // hide signup view initially

        // action listeners for login
        vLogin.getLogInButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        vLogin.getSignupButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSignupGUI();
            }
        });

        // action listeners for signup
        vSignup.getSignUpButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignup();
            }
        });

        vSignup.getClearButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vSignup.clearTxts();
            }
        });

        vSignup.getLoginPageButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginGUI();
            }
        });

        vSignup.getLecturerBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showQualification();
            }
        });

        // actionlisteners for student home
        vStudent.getLogoutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogout();
            }
        });

        vStudent.getPassButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetPass(mUser.getUserID());
            }
        });

        // actionListeners for lecturer home
        vLecturer.getLogoutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogout();
            }
        });

        vLecturer.getCourseListButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vLecturer.getCourseListPanel().isVisible()) { // if course list panel is visible, hide it
                    vLecturer.getCourseListPanel().setVisible(false);
                }
                else { // else, display panel
                    vLecturer.getCourseListPanel().setVisible(true);
                }
            }
        });

        // actionListeners for manager home
        vManager.getLogoutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogout();
            }
        });

        vManager.getPassButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetPass(mUser.getUserID());
            }
        });

        vManager.getCourseListButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vManager.getCourseListPanel().isVisible()) { // if course list panel is visible, hide it
                    vManager.getCourseListPanel().setVisible(false);
                }
                else { // else, display panel
                    vManager.getCourseListPanel().setVisible(true);
                }
            }
        });

        vManager.getAccsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vManager.getAccountsPanel().isVisible()) { // if accounts panel is visible, hide it
                    vManager.getAccountsPanel().setVisible(false);
                }
                else { // else, display panel
                    vManager.getAccountsPanel().setVisible(true);
                }
            }
        });

        vManager.getUnapprovedAccsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vManager.getUnapprovedAccsPanel().isVisible()) { // if unapproved accounts panel is visible, hide it
                    vManager.getUnapprovedAccsPanel().setVisible(false);
                }
                else { // else, display panel
                    vManager.getUnapprovedAccsPanel().setVisible(true);
                }
            }
        });

        vManager.getStudentAccsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vManager.getStudentAccsPanel().isVisible()) { // if student account list panel is visible, hide it
                    vManager.getStudentAccsPanel().setVisible(false);
                }
                else { // else, display panel
                    vManager.getStudentAccsPanel().setVisible(true);
                }
            }
        });

        vManager.getLecturerAccsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vManager.getLecturerAccsPanel().isVisible()) { // if student account list panel is visible, hide it
                    vManager.getLecturerAccsPanel().setVisible(false);
                }
                else { // else, display panel
                    vManager.getLecturerAccsPanel().setVisible(true);
                }
            }
        });

        vManager.getRefreshPageButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshManagerPage(mUser.getUserID());
            }
        });

    }

    private void handleLogin() {
        String username = vLogin.getUsernameTxt().getText();
        char[] password = vLogin.getPasswordTxt().getPassword();
        // validate login using model
        //System.out.println("username is "+username+" password is "+new String(password));
        boolean loginSuccess = mUser.login(username,password);
        if (loginSuccess) {
            showHomeGUI();
        }
        else {
            JOptionPane.showMessageDialog(null,"User " + vSignup.getUsernameTxt().getText() + "Error: unsuccessful log in.","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSignup() {
        String username = vSignup.getUsernameTxt().getText();
        String email = vSignup.getEmailTxt().getText();
        String forename = vSignup.getForenameTxt().getText();
        String surname = vSignup.getSurnameTxt().getText();
        String year = vSignup.getYearCombo().getSelectedItem().toString();
        String month = vSignup.getMonthCombo().getSelectedItem().toString();
        String day = vSignup.getDayCombo().getSelectedItem().toString();
        String gender = vSignup.getGenderCombo().getSelectedItem().toString();
        String accountType = setAccType();
        String qualification = vSignup.getQualificationCombo().getSelectedItem().toString();
        char[] password = vSignup.getPasswordField1().getPassword();
        char[] retypePassword = vSignup.getPasswordField2().getPassword();
        // validate signup using model
        boolean signupSuccess = mUser.signup(username,email,forename,surname,year,month,day,gender,accountType,qualification,password,retypePassword);
        if (signupSuccess) {
            // show feedback message
            JOptionPane.showMessageDialog(null,"User "+vSignup.getUsernameTxt().getText()+" signed up successfully!","Info.",JOptionPane.INFORMATION_MESSAGE);
            vSignup.clearTxts();
        }
        else {
            JOptionPane.showMessageDialog(null,"User "+vSignup.getUsernameTxt().getText()+"Error: unsuccessful sign up.","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void handleLogout() {
        // show pop up window with yes no options confirming logout
        var confirmation = JOptionPane.showOptionDialog(null,"Log out?","Log Out",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
        if (confirmation == JOptionPane.YES_OPTION) {
            showLoginGUI();
        }
    }

    private void showSignupGUI() {
        // show signup view, hide login view, hide qualification field in signup view
        vSignup.getPanelMain().setVisible(true);
        vSignup.getQualificationCombo().setVisible(false);
        vSignup.getQualificationLabel().setVisible(false);
        vLogin.getPanelMain().setVisible(false);
    }

    private void showLoginGUI() {
        // show login view and hide signup view
        vLogin.getPanelMain().setVisible(true);
        vSignup.getPanelMain().setVisible(false);
        vStudent.getPanelMain().setVisible(false);
        clearStudentInfo();
        clearLecturerInfo();
        clearManagerInfo();
        vLecturer.getPanelMain().setVisible(false);
        vManager.getPanelMain().setVisible(false);
    }

    private void clearStudentInfo() {
        vStudent.getAccDetailsTxtLabel().setText("");
        vStudent.getCourseDetailsTxt().setText("");
        vStudent.getModulePanelLeft().removeAll();
    }

    private void clearLecturerInfo() {
        vLecturer.getAccDetailsTxtLabel().setText("");
        vLecturer.getCourseListPanel().removeAll();
    }

    private void clearManagerInfo() {
        vManager.getAccDetailsTxtLabel().setText("");
        vManager.getCourseListPanel().removeAll();
        vManager.getUnapprovedAccsPanel().removeAll();
        vManager.getStudentAccsPanel().removeAll();
        vManager.getLecturerAccsPanel().removeAll();
    }

    private void refreshManagerPage(int userID) {
        clearManagerInfo();
        vManager.setInfo(userID);
        managerActionListeners();
    }

    private void showQualification() {
        // if lecturer box is ticked, reveal qualification field, otherwise hide it
        if (vSignup.getQualificationCombo().isVisible()) {
            vSignup.getQualificationCombo().setVisible(false);
            vSignup.getQualificationLabel().setVisible(false);
        }
        else {
            vSignup.getQualificationCombo().setVisible(true);
            vSignup.getQualificationLabel().setVisible(true);
        }
    }

    private void showHomeGUI() {
        int userID = mUser.getUserID();
        if (mUser.accType(userID).equals("student")) {
            vSignup.getPanelMain().setVisible(false);
            vLogin.getPanelMain().setVisible(false);
            vStudent.getPanelMain().setVisible(true);
            vStudent.setInfo(userID);
            studentActionListeners();

        }
        else if (mUser.accType(userID).equals("lecturer")) {
            //System.out.println("should be triggered....!?!?!!??!");
            vSignup.getPanelMain().setVisible(false);
            vLogin.getPanelMain().setVisible(false);
            vLecturer.getPanelMain().setVisible(true);
            vLecturer.setInfo(userID);
            lecturerActionListeners();
        }
        else if (mUser.accType(mUser.getUserID()).equals("manager")) {
            vSignup.getPanelMain().setVisible(false);
            vLogin.getPanelMain().setVisible(false);
            vManager.getPanelMain().setVisible(true);
            vManager.setInfo(userID);
            managerActionListeners();
        }
    }

    private String setAccType() {
        if (vSignup.getLecturerBox().isSelected()) {
            return "lecturer";
        }
        else {
            return "student";
        }
    }

    private void sModOptions(int modID, int userID) {
        String[] opts = {"Download notes","See result","Cancel"};
        var options = JOptionPane.showOptionDialog(null,"What would you like to do?","Module",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,opts,null);
        if (options == JOptionPane.YES_OPTION) {
            // seperate method for selecting notes from specific week
            sNotes(modID);
        }
        if (options ==  JOptionPane.NO_OPTION) {
            // need method for getting result of user for certain module
            result(userID,modID);
        }
        if (options == JOptionPane.CANCEL_OPTION) {
            // nothing happens
        }
    }

    private void sNotes(int modID) {
        Object[] weeks = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        JComboBox comboWeek = new JComboBox(weeks);
        String[] types = {"Lecture", "Lab"};
        JComboBox comboType = new JComboBox(types);

        Object[] inputs = {"Week: ",comboWeek,"Note type: ",comboType};
        String[] buttons = {"Download","Cancel"};

        // create option pane
        var options = JOptionPane.showOptionDialog(null, inputs, "Select week", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, null);
        if (options == JOptionPane.OK_OPTION) {
            int week = Integer.parseInt(comboWeek.getSelectedItem().toString());
            String type = comboType.getSelectedItem().toString().toLowerCase();
            String noteString = mUser.notes(modID, week, type);
            if (noteString != null) {
                if (!noteString.isEmpty()) {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Save Notes");
                    chooser.setSelectedFile(new File(modID + "_" + week + "_" + type + ".txt"));
                    int saveResult = chooser.showSaveDialog(null);
                    if (saveResult == JFileChooser.APPROVE_OPTION) {
                        File saveFile = chooser.getSelectedFile();
                        setNote(modID, week, type);
                        File generatedFile = new File(modID + week + type + ".txt");
                        generatedFile.renameTo(saveFile);
                        JOptionPane.showMessageDialog(null, "Notes downloaded to:\n" + saveFile.getAbsolutePath());
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "No notes uploaded. Please check again at a later time.","Notes",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void setNote(int modID,int week,String type) {
        String noteString = mUser.notes(modID, week, type);
        if(noteString != null) {
            String filename = (modID + week + type + ".txt");
            try {
                FileWriter myWriter = new FileWriter(filename);
                myWriter.write(noteString);
                myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void result(int userID, int modID) {
        // create new sql method in dao to grab results from Result table
        List<String> result = mUser.results(userID, modID);
        // if result != null, display result
        if (result.get(3) != null) {
            JOptionPane.showMessageDialog(null, "Your result for this module is " + result.get(3) + "%","title",JOptionPane.INFORMATION_MESSAGE);
        }
        // else show message stating that result isn't set
        else {
            JOptionPane.showMessageDialog(null, "Result not yet set. Check again at a later date.","Result",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void resetPass (int userID) {
        JPasswordField newPass = new JPasswordField();
        JPasswordField newRetype = new JPasswordField();
        Object[] inputs = {"Enter new Password: ",newPass,"Re-enter new password",newRetype};
        var options = JOptionPane.showConfirmDialog(null, inputs, "Reset Password", JOptionPane.OK_CANCEL_OPTION);
        if (options == JOptionPane.OK_OPTION && mUser.resetPass(userID, newPass.getPassword(),newRetype.getPassword())) { // if passwords are equal and SQL works
            JOptionPane.showMessageDialog(null,"The password has been successfully reset!","Success",JOptionPane.INFORMATION_MESSAGE);
        }
        else if (options == JOptionPane.CANCEL_OPTION) {

        }
        else {
            JOptionPane.showMessageDialog(null,"Password reset unsuccessful.","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void studentActionListeners() {
        Component[] comp = vStudent.getModulePanelLeft().getComponents();

        // actionlisteners for dynamic module buttons
        ActionListener modulesListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = e.getSource().toString();
                int i = str.indexOf("text=")+5;
                int j = i + 5;
                int id = Integer.parseInt(str.substring(i, j)); // get id from button name
                if (id > 9999 && id < 1000000) {
                    sModOptions(id, mUser.getUserID());
                }
                else {
                    JOptionPane.showMessageDialog(null,"id was not read correctly :(");
                }
            }};

        for (Component c : comp) { // for each component of modules panel
            if (c  instanceof JButton) {
                ((JButton) c).addActionListener(modulesListener);
            }

        }
    }

    private void lecturerActionListeners() {
        // get array of components for course list panel
        Component[] courseComponents = vLecturer.getCourseListPanel().getComponents();

        // action listener for hide/reveal course buttons
        ActionListener courseButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object button = e.getSource();
                int buttonIndex = 0;
                for (Component c : courseComponents) { // iterate through course components
                    if (c == button && courseComponents[buttonIndex+1].isVisible()) { // if button being clicked is found and respective panel is visible
                        courseComponents[buttonIndex+1].setVisible(false); // hide panel
                    }
                    else if (c == button && !courseComponents[buttonIndex+1].isVisible()) { // if button being clicked is found and respective panel is hidden
                        courseComponents[buttonIndex+1].setVisible(true); // show panel
                    }
                    else {
                        buttonIndex++;
                    }
                }
            }
        };

        // action listener for hide/reveal module button
        ActionListener moduleButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object button = e.getSource();
                Component[] moduleComponents = ((Component) button).getParent().getComponents();
                int buttonIndex = 0;
                for (Component c : moduleComponents) { // iterate through module components
                    if (c == button && moduleComponents[buttonIndex+1].isVisible()) { // if button being clicked is found and respective panel is visible
                        moduleComponents[buttonIndex+1].setVisible(false); // hide panel
                    }
                    else if (c == button && !moduleComponents[buttonIndex+1].isVisible()) { // if button being clicked is found and respective panel is hidden
                        moduleComponents[buttonIndex+1].setVisible(true); // show panel
                    }
                    else {
                        buttonIndex++;
                    }
                }
            }
        };

        // action listener for upload notes button
        ActionListener noteButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object button = e.getSource();
                Component[] moduleComponents = ((Component) button).getParent().getParent().getComponents();
                int moduleID = 0;
                int buttonIndex = 0;
                for (Component c : moduleComponents) { // iterate through module components
                    if (c instanceof JPanel) { // if button being clicked is found and respective panel is visible
                        for (Component c2 : ((JPanel) c).getComponents()) {
                            if (c2 instanceof JButton && c2 == button) {
                                moduleID = Integer.parseInt(((JButton) moduleComponents[buttonIndex-1]).getText().substring(0,5));
                            }
                        }
                    }
                    buttonIndex++;
                }
                uploadNote(moduleID);
            }
        };

        // action listener for hide/reveal student button
        ActionListener studentButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object button = e.getSource();
                Component[] studentComponents = ((Component) button).getParent().getComponents();
                int buttonIndex = 0;
                for (Component c : studentComponents) { // iterate through module components
                    if (c == button && studentComponents[buttonIndex+1].isVisible()) { // if button being clicked is found and respective panel is visible
                        studentComponents[buttonIndex+1].setVisible(false); // hide panel
                    }
                    else if (c == button && !studentComponents[buttonIndex+1].isVisible()) { // if button being clicked is found and respective panel is hidden
                        studentComponents[buttonIndex+1].setVisible(true); // show panel
                    }
                    else {
                        buttonIndex++;
                    }
                }
            }
        };

        // action listener for set marks button
        ActionListener setMarkListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object button = e.getSource();
                Component[] moduleComponents = ((Component) button).getParent().getParent().getParent().getComponents();
                int studentID = 0;
                int moduleID = 0;
                int moduleIndex = 0;
                int studentIndex = 0;
                for (Component c : moduleComponents) { // iterate through module components
                    if (c instanceof JButton) {
                        Component[] studentListComponents = ((JPanel) moduleComponents[moduleIndex+1]).getComponents();
                        for (Component c2 : studentListComponents) {
                            if (c2 instanceof JButton && ((JButton) c2).getText().indexOf(" - ") == 5) { // potential module button found
                                Component[] studentComponents = ((JPanel) studentListComponents[studentIndex+1]).getComponents();
                                for (Component c3 : studentComponents) {
                                    if (c3 instanceof JButton && ((JButton) c3) == button) {
                                        moduleID = Integer.parseInt(((JButton) c).getText().substring(0,5));
                                        studentID = Integer.parseInt(((JButton) studentListComponents[studentIndex]).getText().substring(0,5));
                                    }
                                }
                            }
                            studentIndex++;
                        }
                    }
                    moduleIndex++;
                }
                setMark(studentID, moduleID);
            }
        };

        // assign action listeners
        // hide course button
        for (Component c : courseComponents) { // for each component of modules panel
            if (c instanceof JButton && ((JButton) c).getText().indexOf(" - ") == 5) { // if component is a button and button has the correct contents
                ((JButton) c).addActionListener(courseButtonListener); // apply actionlistener
            }
        }

        // hide module button
        for (Component c : courseComponents) {
            if (c instanceof JPanel) { // if component is a course panel
                Component[] moduleComponents = ((JPanel) c).getComponents();
                for (Component c2 : moduleComponents) {
                    if (c2 instanceof JButton && ((JButton) c2).getText().indexOf(" - ") == 5) {
                        ((JButton) c2).addActionListener(moduleButtonListener);
                    }
                }
            }
        }

        // set mark button
        for (Component c : courseComponents) {
            if (c instanceof JPanel) { // if component is a course panel
                Component[] moduleComponents = ((JPanel) c).getComponents();
                for (Component c2 : moduleComponents) {
                    if (c2 instanceof JPanel) {
                        Component[] studentsComponent = ((JPanel) c2).getComponents();
                        for (Component c3 : studentsComponent) {
                            if (c3 instanceof JPanel) {
                                Component[] studentComponent = ((JPanel) c3).getComponents();
                                for (Component c4 : studentComponent) {
                                    if (c4 instanceof JButton && ((JButton) c4).getText().equals("Set mark")) {
                                        ((JButton) c4).addActionListener(setMarkListener);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // hide student button
        for (Component c : courseComponents) {
            if (c instanceof JPanel) { // if component is a course panel
                Component[] moduleComponents = ((JPanel) c).getComponents();
                for (Component c2 : moduleComponents) {
                    if (c2 instanceof JPanel) {
                        Component[] studentComponent = ((JPanel) c2).getComponents();
                        for (Component c3 : studentComponent) {
                            if (c3 instanceof  JButton && ((JButton) c3).getText().indexOf(" - ") == 5) {
                                ((JButton) c3).addActionListener(studentButtonListener);
                            }
                        }
                    }
                }
            }
        }

        // upload notes button
        for (Component c : courseComponents) {
            if (c instanceof JPanel) { // if component is a course panel
                Component[] moduleComponents = ((JPanel) c).getComponents();
                for (Component c2 : moduleComponents) {
                    if (c2 instanceof JPanel) {
                        Component[] studentComponent = ((JPanel) c2).getComponents();
                        for (Component c3 : studentComponent) {
                            if (c3 instanceof  JButton && ((JButton) c3).getText().equals("Upload notes")) {
                                ((JButton) c3).addActionListener(noteButtonListener);
                            }
                        }
                    }
                }
            }
        }
    }

    private void managerActionListeners() {
        // get array of components for named panels
        Component[] courseComponents = vManager.getCourseListPanel().getComponents();
        Component[] unapprovedComponents = vManager.getUnapprovedAccsPanel().getComponents();
        Component[] studentComponents = vManager.getStudentAccsPanel().getComponents();
        Component[] lectComponents = vManager.getLecturerAccsPanel().getComponents();

        // action listener for add course
        ActionListener addCourseListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCourse();
            }
        };

        // action listener for hide/reveal course buttons
        ActionListener courseButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object button = e.getSource();
                int buttonIndex = 0;
                for (Component c : courseComponents) { // iterate through course components
                    if (c == button && courseComponents[buttonIndex+1].isVisible()) { // if button being clicked is found and respective panel is visible
                        courseComponents[buttonIndex+1].setVisible(false); // hide panel
                    }
                    else if (c == button && !courseComponents[buttonIndex+1].isVisible()) { // if button being clicked is found and respective panel is hidden
                        courseComponents[buttonIndex+1].setVisible(true); // show panel
                    }
                    else {
                        buttonIndex++;
                    }
                }
            }
        };

        // action listener for edit course button
        ActionListener editCourseListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object button = e.getSource();
                int panelIndex = 0;
                for (Component c : courseComponents) { // iterate through components
                    if (c instanceof JPanel) { // if component is a panel
                        for (Component c2 : ((JPanel) c).getComponents()) { // iterate through components of panel
                            if (c2 instanceof JButton && c2 == button) {
                                List<String> course = mMan.courseDetailsByCourseID(Integer.parseInt(((JButton) courseComponents[panelIndex-1]).getText().substring(0,5)));
                                // create input fields and fill them with data from database
                                editCourse(course);
                            }
                        }
                    }
                    panelIndex++;
                }
            }
        };

        // action listener for hide/reveal module button
        ActionListener moduleButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object button = e.getSource();
                Component[] moduleComponents = ((Component) button).getParent().getComponents();
                int buttonIndex = 0;
                for (Component c : moduleComponents) { // iterate through module components
                    if (c == button && moduleComponents[buttonIndex+1].isVisible()) { // if button being clicked is found and respective panel is visible
                        moduleComponents[buttonIndex+1].setVisible(false); // hide panel
                    }
                    else if (c == button && !moduleComponents[buttonIndex+1].isVisible()) { // if button being clicked is found and respective panel is hidden
                        moduleComponents[buttonIndex+1].setVisible(true); // show panel
                    }
                    else {
                        buttonIndex++;
                    }
                }
            }
        };

        // action listener for adding a module
        ActionListener addModuleListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addModule((JButton) e.getSource());
            }
        };

        // action listener for editing a module
        ActionListener editModuleListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // get module id
                JButton button = (JButton) e.getSource();
                JPanel modPanel = (JPanel) button.getParent();
                JPanel coursePanel = (JPanel) modPanel.getParent();
                int i = 0;
                String id = "";
                //System.out.println(Arrays.toString(courseList.getComponents()));
                for (Component c : coursePanel.getComponents()) { // for each component in course panel
                    if (c instanceof JPanel && c == modPanel) { // if c is the module panel that the button is within
                        for (Component c2 : ((JPanel) c).getComponents()) {
                            id = ((JButton) coursePanel.getComponent(i-1)).getText().substring(0,5); // get moduleID from course hide button
                        }
                    }
                    i++;
                }
                editModule(mMan.moduleDetailsByID(Integer.parseInt(id)));
            }
        };

        // action listener for assigning a student
        ActionListener assignCourseListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // get module id
                JButton button = (JButton) e.getSource();
                JPanel modPanel = (JPanel) button.getParent();
                JPanel coursePanel = (JPanel) modPanel.getParent();
                int i = 0;
                String id = "";
                //System.out.println(Arrays.toString(courseList.getComponents()));
                for (Component c : coursePanel.getComponents()) { // for each component in course panel
                    if (c instanceof JPanel && c == modPanel) { // if c is the module panel that the button is within
                        for (Component c2 : ((JPanel) c).getComponents()) {
                            id = ((JButton) coursePanel.getComponent(i-1)).getText().substring(0,5); // get moduleID from course hide button
                        }
                    }
                    i++;
                }
                assignCourse(Integer.parseInt(id));
            }
        };

        // action listener for assigning a lecturer
        ActionListener assignLecturerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get current course ID
                JPanel modPanel = (JPanel) ((JButton) e.getSource()).getParent();
                JPanel coursePanel = (JPanel) modPanel.getParent();
                JPanel courseList =  (JPanel) coursePanel.getParent();
                int i = 0;
                String courseID = "";
                //System.out.println(Arrays.toString(courseList.getComponents()));
                for (Component c : courseList.getComponents()) { // for each component in course list
                    if (c instanceof JPanel && c == coursePanel) { // if c is the module panel that the button is within
                        courseID = ((JButton) courseList.getComponent(i-1)).getText().substring(0,5); // get courseID from course hide button
                    }
                    i++;
                }
                // get module id
                int j = 0;
                String moduleID = "";
                //System.out.println(Arrays.toString(courseList.getComponents()));
                for (Component c : coursePanel.getComponents()) { // for each component in course panel
                    if (c instanceof JPanel && c == modPanel) { // if c is the module panel that the button is within
                        for (Component c2 : ((JPanel) c).getComponents()) {
                            moduleID = ((JButton) coursePanel.getComponent(j-1)).getText().substring(0,5); // get moduleID from course hide button
                        }
                    }
                    j++;
                }
                assignLecturer(Integer.parseInt(moduleID),Integer.parseInt(courseID));
            }
        };

        // action listener for approving an account
        ActionListener approveAccListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(((JButton) e.getSource()).getText().substring(0,5));
                List<String> details = mUser.accountDetails(id);

                Object[] inputs = {"ID: "+id,"User type: "+details.get(1),"Username: "+details.get(2),"Full name: "+details.get(3)+" "+details.get(4),"Email: "+details.get(6),"Date of birth: "+details.get(7),"","Approve?"};
                if (details.get(1) == "lecturer") {
                    inputs[6] = "Qualification: "+details.get(11);
                }
                // show window
                var options = JOptionPane.showConfirmDialog(null, inputs, "Add New Course", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (options == JOptionPane.YES_OPTION && mMan.approveUser(id)) {
                    JOptionPane.showMessageDialog(null, details.get(3) + " " + details.get(4)+" has been approved!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(null,"Account approval failed.","Error",JOptionPane.ERROR_MESSAGE);
                }

            }
        };

        // action listener for hide/reveal student button
        ActionListener studentButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object button = e.getSource();
                Component[] studentComponents = ((Component) button).getParent().getComponents();
                int buttonIndex = 0;
                for (Component c : studentComponents) { // iterate through student list components
                    if (c == button && studentComponents[buttonIndex+1].isVisible()) { // if button being clicked is found and respective panel is visible
                        studentComponents[buttonIndex+1].setVisible(false); // hide panel
                    }
                    else if (c == button && !studentComponents[buttonIndex+1].isVisible()) { // if button being clicked is found and respective panel is hidden
                        studentComponents[buttonIndex+1].setVisible(true); // show panel
                    }
                    else {
                        buttonIndex++;
                    }
                }
            }
        };

        // action listener for enrolling in a course
        ActionListener enrollButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object button = e.getSource();
                int panelIndex = 0;
                for (Component c : studentComponents) { // iterate through components
                    if (c instanceof JPanel) { // if component is a panel
                        for (Component c2 : ((JPanel) c).getComponents()) { // iterate through components of panel
                            if (c2 instanceof JButton && c2 == button) {
                                int id = Integer.parseInt(((JButton) studentComponents[panelIndex-1]).getText().substring(0,5));
                                // enroll student in course
                                enroll(id);
                            }
                        }
                    }
                    panelIndex++;
                }
            }
        };

        // actiom listener for assigning a decision
        ActionListener decisionButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object button = e.getSource();
                int panelIndex = 0;
                for (Component c : studentComponents) { // iterate through components
                    if (c instanceof JPanel) { // if component is a panel
                        for (Component c2 : ((JPanel) c).getComponents()) { // iterate through components of panel
                            if (c2 instanceof JButton && c2 == button) {
                                int id = Integer.parseInt(((JButton) studentComponents[panelIndex-1]).getText().substring(0,5));
                                // enroll student in course
                                decision(id);
                            }
                        }
                    }
                    panelIndex++;
                }
            }
        };

        // action listener for resetting a student password
        ActionListener resetStudentPassListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object button = e.getSource();
                int panelIndex = 0;
                for (Component c : studentComponents) { // iterate through components
                    if (c instanceof JPanel) { // if component is a panel
                        for (Component c2 : ((JPanel) c).getComponents()) { // iterate through components of panel
                            if (c2 instanceof JButton && c2 == button) {
                                int id = Integer.parseInt(((JButton) studentComponents[panelIndex-1]).getText().substring(0,5));
                                // enroll student in course
                                resetPass(id);
                            }
                        }
                    }
                    panelIndex++;
                }
            }
        };

        // action listener for deactivating a student account
        ActionListener deactivateStudentListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object button = e.getSource();
                int panelIndex = 0;
                for (Component c : studentComponents) { // iterate through components
                    if (c instanceof JPanel) { // if component is a panel
                        for (Component c2 : ((JPanel) c).getComponents()) { // iterate through components of panel
                            if (c2 instanceof JButton && c2 == button) {
                                int id = Integer.parseInt(((JButton) studentComponents[panelIndex-1]).getText().substring(0,5));
                                // enroll student in course
                                deactivate(id);
                            }
                        }
                    }
                    panelIndex++;
                }
            }
        };

        // action listener for hide/reveal lecturer button
        ActionListener lecturerButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object button = e.getSource();
                Component[] lecturerComponents = ((Component) button).getParent().getComponents();
                int buttonIndex = 0;
                for (Component c : lecturerComponents) { // iterate through student list components
                    if (c == button && lecturerComponents[buttonIndex+1].isVisible()) { // if button being clicked is found and respective panel is visible
                        lecturerComponents[buttonIndex+1].setVisible(false); // hide panel
                    }
                    else if (c == button && !lecturerComponents[buttonIndex+1].isVisible()) { // if button being clicked is found and respective panel is hidden
                        lecturerComponents[buttonIndex+1].setVisible(true); // show panel
                    }
                    else {
                        buttonIndex++;
                    }
                }
            }
        };

        // action listener for resetting a lecturer password
        ActionListener resetLecturerPassListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object button = e.getSource();
                int panelIndex = 0;
                for (Component c : lectComponents) { // iterate through components
                    if (c instanceof JPanel) { // if component is a panel
                        for (Component c2 : ((JPanel) c).getComponents()) { // iterate through components of panel
                            if (c2 instanceof JButton && c2 == button) {
                                int id = Integer.parseInt(((JButton) lectComponents[panelIndex-1]).getText().substring(0,5));
                                // enroll student in course
                                resetPass(id);
                            }
                        }
                    }
                    panelIndex++;
                }
            }
        };

        // action listener for deactivating a lecturer account
        ActionListener deactivateLecturerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object button = e.getSource();
                int panelIndex = 0;
                for (Component c : lectComponents) { // iterate through components
                    if (c instanceof JPanel) { // if component is a panel
                        for (Component c2 : ((JPanel) c).getComponents()) { // iterate through components of panel
                            if (c2 instanceof JButton && c2 == button) {
                                int id = Integer.parseInt(((JButton) lectComponents[panelIndex-1]).getText().substring(0,5));
                                // enroll student in course
                                deactivate(id);
                            }
                        }
                    }
                    panelIndex++;
                }
            }
        };

        // assign action listeners
        // add course button
        ((JButton) courseComponents[1]).addActionListener(addCourseListener);

        // hide course button
        for (Component c : courseComponents) { // for each component of modules panel
            if (c instanceof JButton && ((JButton) c).getText().indexOf(" - ") == 5) { // if component is a button and button has the correct contents
                ((JButton) c).addActionListener(courseButtonListener); // apply actionlistener
            }
        }

        // edit course button
        for (Component c : courseComponents) {
            if (c instanceof JPanel) {
                for (Component c2 : ((JPanel) c).getComponents()) {
                    if (c2 instanceof JButton && (((JButton) c2).getText()).equals("Edit course details")) {
                        ((JButton) c2).addActionListener(editCourseListener);
                    }
                }
            }
        }

        // hide module button
        for (Component c : courseComponents) {
            if (c instanceof JPanel) { // if component is a course panel
                Component[] moduleComponents = ((JPanel) c).getComponents();
                for (Component c2 : moduleComponents) {
                    if (c2 instanceof JButton && ((JButton) c2).getText().indexOf(" - ") == 5) {
                        ((JButton) c2).addActionListener(moduleButtonListener);
                    }
                }
            }
        }

        // add module button
        for (Component c : courseComponents) {
            if (c instanceof JPanel) { // if component is a course panel
                Component[] moduleComponents = ((JPanel) c).getComponents();
                for (Component c2 : moduleComponents) {
                    if (c2 instanceof JButton && (((JButton) c2).getText()).equals("Add new module")) {
                        ((JButton) c2).addActionListener(addModuleListener);
                    }
                }
            }
        }

        // edit module button
        for (Component c : courseComponents) {
            if (c instanceof JPanel) { // if component is a course panel
                Component[] moduleComponents = ((JPanel) c).getComponents();
                for (Component c2 : moduleComponents) {
                    if (c2 instanceof JPanel) {
                        for (Component c3 : ((JPanel) c2).getComponents()) {
                            if (c3 instanceof JButton && (((JButton) c3).getText()).equals("Edit module")) {
                                ((JButton) c3).addActionListener(editModuleListener);
                            }
                        }
                    }
                }
            }
        }

        // assign course button
        for (Component c : courseComponents) {
            if (c instanceof JPanel) { // if component is a course panel
                Component[] moduleComponents = ((JPanel) c).getComponents();
                for (Component c2 : moduleComponents) {
                    if (c2 instanceof JPanel) {
                        for (Component c3 : ((JPanel) c2).getComponents()) {
                            if (c3 instanceof JButton && (((JButton) c3).getText()).equals("Assign to course")) {
                                ((JButton) c3).addActionListener(assignCourseListener);
                            }
                        }
                    }
                }
            }
        }

        // assign lecturer button
        for (Component c : courseComponents) {
            if (c instanceof JPanel) { // if component is a course panel
                Component[] moduleComponents = ((JPanel) c).getComponents();
                for (Component c2 : moduleComponents) {
                    if (c2 instanceof JPanel) {
                        for (Component c3 : ((JPanel) c2).getComponents()) {
                            if (c3 instanceof JButton && (((JButton) c3).getText()).equals("Assign to lecturer")) {
                                ((JButton) c3).addActionListener(assignLecturerListener);
                            }
                        }
                    }
                }
            }
        }

        // approve account button
        for (Component c : unapprovedComponents) {
            if (c instanceof JButton) {
                ((JButton) c).addActionListener(approveAccListener);
            }
        }

        // hide student button
        for (Component c : studentComponents) {
            if (c instanceof JButton) {
                ((JButton) c).addActionListener(studentButtonListener);
            }
        }

        // enroll student button
        for (Component c : studentComponents) {
            if (c instanceof JPanel) {
                for (Component c2 : ((JPanel) c).getComponents()) {
                    if (c2 instanceof JButton && ((JButton) c2).getText().equals("Enroll in course")) {
                        ((JButton) c2).addActionListener(enrollButtonListener);
                    }
                }
            }
        }

        // issue decision button
        for (Component c : studentComponents) {
            if (c instanceof JPanel) {
                for (Component c2 : ((JPanel) c).getComponents()) {
                    if (c2 instanceof JButton && (((JButton) c2).getText()).equals("Issue decision")) {
                        ((JButton) c2).addActionListener(decisionButtonListener);
                    }
                }
            }
        }

        // reset student account password button
        for (Component c : studentComponents) {
            if (c instanceof JPanel) {
                for (Component c2 : ((JPanel) c).getComponents()) {
                    if (c2 instanceof JButton && (((JButton) c2).getText()).equals("Reset password")) {
                        ((JButton) c2).addActionListener(resetStudentPassListener);
                    }
                }
            }
        }

        // deactivate student password button
        for (Component c : studentComponents) {
            if (c instanceof JPanel) {
                for (Component c2 : ((JPanel) c).getComponents()) {
                    if (c2 instanceof JButton && (((JButton) c2).getText()).equals("Deactivate account")) {
                        ((JButton) c2).addActionListener(deactivateStudentListener);
                    }
                }
            }
        }

        // reset lecturer account password button
        for (Component c : lectComponents) {
            if (c instanceof JPanel) {
                for (Component c2 : ((JPanel) c).getComponents()) {
                    if (c2 instanceof JButton && (((JButton) c2).getText()).equals("Reset password")) {
                        ((JButton) c2).addActionListener(resetLecturerPassListener);
                    }
                }
            }
        }

        // hide lecturer button
        for (Component c : lectComponents) {
            if (c instanceof JButton) {
                ((JButton) c).addActionListener(lecturerButtonListener);
            }
        }

        // deactivate lecturer password button
        for (Component c : lectComponents) {
            if (c instanceof JPanel) {
                for (Component c2 : ((JPanel) c).getComponents()) {
                    if (c2 instanceof JButton && (((JButton) c2).getText()).equals("Deactivate account")) {
                        ((JButton) c2).addActionListener(deactivateLecturerListener);
                    }
                }
            }
        }
    }

    private void addCourse() {
        // values for combo boxes
        Object[] day = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
        Object[] month = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        Object[] sYear = {"2025","2026","2027","2028","2029","2030","2031","2032","2033","2034","2035"};
        Object[] eYear = {"2026","2027","2028","2029","2030","2031","2032","2033","2034","2035","2036"};

        // create input fields
        JTextField title = new JTextField();
        JTextField degree = new JTextField();
        JComboBox startDay = new JComboBox(day);
        JComboBox startMonth = new JComboBox(month);
        JComboBox startYear = new JComboBox(sYear);
        JComboBox endDay = new JComboBox(day);
        JComboBox endMonth = new JComboBox(month);
        JComboBox endYear = new JComboBox(eYear);
        JTextField compensationsAllowed = new JTextField();
        JTextField description = new JTextField();
        JComboBox gradLevel = new JComboBox(new String[]{"Undergraduate","Postgraduate"});

        Object[] inputs = {"Title: ",title,"Degree: ",degree,"Start date: ",startDay,startMonth,startYear,"End date: ",endDay,endMonth,endYear,"Compensations Allowed: ",compensationsAllowed,"Description: ",description,"Graduate Level: ",gradLevel};

        // show window
        var options = JOptionPane.showConfirmDialog(null, inputs, "Add New Course", JOptionPane.OK_CANCEL_OPTION);
        if (options == JOptionPane.OK_OPTION) {
            if (mMan.addCourse(title.getText(),degree.getText(),(String) startDay.getSelectedItem(),(String) startMonth.getSelectedItem(),(String) startYear.getSelectedItem(),(String) endDay.getSelectedItem(),(String) endMonth.getSelectedItem(),(String) endYear.getSelectedItem(),compensationsAllowed.getText(),this.mUser.getUserID(), description.getText(), gradLevel.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(null,"Course has been created!","Success",JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null,"Course creation unsuccessful.","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editCourse(List<String> courseDetails) {
        // values for combo boxes
        Object[] day = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        Object[] month = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        Object[] year = {"2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035"};

        // get id from list
        int id = Integer.parseInt(courseDetails.get(0));

        // create and set input fields
        JTextField title = new JTextField();
        title.setText(courseDetails.get(1));
        JTextField degree = new JTextField();
        degree.setText(courseDetails.get(2));
        JComboBox startDay = new JComboBox(day);
        startDay.setSelectedItem(mUser.dayFromString(courseDetails.get(4)));
        JComboBox startMonth = new JComboBox(month);
        startMonth.setSelectedItem(mUser.monthFromString(courseDetails.get(4)));
        JComboBox startYear = new JComboBox(year);
        startYear.setSelectedItem(mUser.yearFromString(courseDetails.get(4)));
        JComboBox endDay = new JComboBox(day);
        endDay.setSelectedItem(mUser.dayFromString(courseDetails.get(5)));
        JComboBox endMonth = new JComboBox(month);
        endMonth.setSelectedItem(mUser.monthFromString(courseDetails.get(5)));
        JComboBox endYear = new JComboBox(year);
        endYear.setSelectedItem(mUser.yearFromString(courseDetails.get(5)));
        JTextField compensationsAllowed = new JTextField();
        compensationsAllowed.setText(courseDetails.get(6));
        JTextArea description = new JTextArea();
        description.setText(courseDetails.get(8));
        JScrollPane descScroll = new JScrollPane(description);
        descScroll.setPreferredSize(new Dimension(100,100));
        description.setBounds(0,0,100,100);
        JComboBox gradLevel = new JComboBox(new String[]{"Undergraduate","Postgraduate"});
        gradLevel.setSelectedItem(courseDetails.get(9));

        Object[] inputs = {"Title: ", title, "Degree: ", degree, "Start date: ", startDay, startMonth, startYear, "End date: ", endDay, endMonth, endYear, "Compensations Allowed: ", compensationsAllowed, "Description: ", descScroll, "Graduate level: ", gradLevel};

        // show window
        var options = JOptionPane.showConfirmDialog(null, inputs, "Add New Course", JOptionPane.OK_CANCEL_OPTION);
        if (options == JOptionPane.OK_OPTION) {
            if (mMan.editCourse(Integer.parseInt(courseDetails.get(0)),title.getText(),degree.getText(),startDay.getSelectedItem().toString(),startMonth.getSelectedItem().toString(),startYear.getSelectedItem().toString(),endDay.getSelectedItem().toString(),endMonth.getSelectedItem().toString(),endYear.getSelectedItem().toString(),compensationsAllowed.getText(),description.getText(),gradLevel.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(null,"Course has been edited!","Success",JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null,"Course edit unsuccessful.","Error",JOptionPane.ERROR_MESSAGE);

            }

        }
    }

    private void addModule(JButton button) {
        // get current course ID
        JPanel modPanel = (JPanel) button.getParent();
        JPanel coursePanel = (JPanel) modPanel.getParent();
        int i = 0;
        String id = "";
        //System.out.println(Arrays.toString(courseList.getComponents()));
        for (Component c : coursePanel.getComponents()) { // for each component in course list
            if (c instanceof JPanel && c == modPanel) { // if c is the module panel that the button is within
                    id = ((JButton) coursePanel.getComponent(i-1)).getText().substring(0,5); // get courseID from course hide button
                }
            i++;
        }

        //System.out.println("ID IS "+id);

        // create input fields
        JTextField title = new JTextField();
        JTextField maxAttempts = new JTextField();
        JTextArea description = new JTextArea(100,25);
        JScrollPane descScroll = new JScrollPane(description);
        descScroll.setPreferredSize(new Dimension(100,100));
        description.setBounds(0,0,100,100);
        JTextField credits = new JTextField();
        JComboBox markingType = new JComboBox(new String[]{"Exam", "Lab", "Assignment", "Exam and Lab", "Exam and Assignment", "Lab and Assignment"});
        JTextField assignmentNum = new JTextField();
        JComboBox semesters = new JComboBox(new String[]{"1","2"});
        //, int credits, String markingType, int assignmentNum, int semesters

        Object[] inputs = {"Title: ",title,"Maximum number of attempts: ",maxAttempts,"Description: ",descScroll,"Credits: ",credits,"Marking type: ",markingType,"Number of assignments: ",assignmentNum,"Number of semesters: ",semesters};

        // show window
        var options = JOptionPane.showConfirmDialog(null, inputs, "Add New Module", JOptionPane.OK_CANCEL_OPTION);
        if (options == JOptionPane.OK_OPTION) {
            if (mMan.addModule(Integer.parseInt(id),title.getText(),Integer.parseInt(maxAttempts.getText()),description.getText(),Integer.parseInt(credits.getText()),(String) markingType.getSelectedItem(),Integer.parseInt(assignmentNum.getText()),Integer.parseInt((String) semesters.getSelectedItem()))) {
                JOptionPane.showMessageDialog(null,"Module has been created!","Success",JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null,"Module creation unsuccessful.","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editModule(List<String> moduleDetails) {
        // get ID
        int id = Integer.parseInt(moduleDetails.get(0));

        // create input fields
        JTextField title = new JTextField();
        title.setText(moduleDetails.get(1));
        JTextField maxAttempts = new JTextField();
        maxAttempts.setText(moduleDetails.get(2));
        JTextArea description = new JTextArea();
        description.setText(moduleDetails.get(3));
        JScrollPane descScroll = new JScrollPane(description);
        descScroll.setPreferredSize(new Dimension(100,100));
        description.setBounds(0,0,100,100);
        JTextField credits = new JTextField();
        credits.setText(moduleDetails.get(5));
        JComboBox markingType = new JComboBox(new String[]{"Exam", "Lab", "Assignment", "Exam and Lab", "Exam and Assignment", "Lab and Assignment"});
        markingType.setSelectedItem(moduleDetails.get(6));
        JTextField assignmentNum = new JTextField();
        assignmentNum.setText(moduleDetails.get(7));
        JComboBox semesters = new JComboBox(new String[]{"1","2"});
        semesters.setSelectedItem(moduleDetails.get(8));

        Object[] inputs = {"Title: ",title,"Maximum number of attempts: ",maxAttempts,"Description: ",descScroll,"Credits: ",credits,"Marking type: ",markingType,"Number of assignments: ",assignmentNum,"Number of semesters: ",semesters};

        // show window
        var options = JOptionPane.showConfirmDialog(null, inputs, "Add New Module", JOptionPane.OK_CANCEL_OPTION);
        if (options == JOptionPane.OK_OPTION) {
            if (markingType.getSelectedItem().toString().contains("Assignment")) {
                if (mMan.editModule(id, title.getText(), Integer.parseInt(maxAttempts.getText()), description.getText(), Integer.parseInt(credits.getText()), (String) markingType.getSelectedItem(), Integer.parseInt(assignmentNum.getText()), Integer.parseInt((String) semesters.getSelectedItem()))) {
                    JOptionPane.showMessageDialog(null, "Module has been edited!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Module edit unsuccessful.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                if (mMan.editModule(id, title.getText(), Integer.parseInt(maxAttempts.getText()), description.getText(), Integer.parseInt(credits.getText()), (String) markingType.getSelectedItem(), 0, Integer.parseInt((String) semesters.getSelectedItem()))) {
                    JOptionPane.showMessageDialog(null, "Module has been edited!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Module edit unsuccessful.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void assignCourse(int moduleID) {
        List<String> courses = mMan.courseIDsTitles();
        Object[] comboContents = new Object[courses.size()/2]; // create array for combo box
        for (int i = 0; i < courses.size(); i+=2) {
            comboContents[i/2] = courses.get(i) + " - " + courses.get(i+1);
            //comboContents[i/2] = comboContents[i/2].toString();
        }
        //System.out.println(Arrays.toString(comboContents));

        // create combo box for Joptionpane
        JComboBox course = new JComboBox(comboContents);

        Object[] inputs = {"Course: ",course};

        // show window
        var options = JOptionPane.showConfirmDialog(null, inputs, "Assign to Course", JOptionPane.OK_CANCEL_OPTION);
        if (options == JOptionPane.OK_OPTION) {
            if (mMan.checkCourseModule(Integer.parseInt(course.getSelectedItem().toString().substring(0,5)),moduleID)) {
                JOptionPane.showMessageDialog(null,"Module is already assigned to this course.","Error",JOptionPane.ERROR_MESSAGE);
            }
            else if (mMan.addCourseModule(Integer.parseInt(course.getSelectedItem().toString().substring(0,5)),moduleID)) {
                JOptionPane.showMessageDialog(null,"Module has been assigned to course!","Success",JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null,"Module assignment unsuccessful.","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void assignLecturer(int moduleID,int courseID) {
        List<String> users = mMan.allLecturers();
        Object[] comboContents = new Object[(users.size()/3)]; // create array for combo box
        for (int i = 0; i < users.size(); i+=3) {
            comboContents[i/3] = users.get(i) + " - " + users.get(i+1) + " " +  users.get(i+2);
        }
        // System.out.println(Arrays.toString(comboContents));

        // create combo box for Joptionpane
        JComboBox user = new JComboBox(comboContents);

        Object[] inputs = {"Lecturer: ",user};
        // show window
        var options = JOptionPane.showConfirmDialog(null, inputs, "Assign to Lecturer", JOptionPane.OK_CANCEL_OPTION);
        if (options == JOptionPane.OK_OPTION) {
            if (mMan.checkLecturerModule(moduleID,Integer.parseInt(user.getSelectedItem().toString().substring(0,5)))) {
                JOptionPane.showMessageDialog(null,"Lecturer is already assigned to the course!","Error",JOptionPane.ERROR_MESSAGE);
            }
            else if (mMan.addCourseUser(Integer.parseInt(user.getSelectedItem().toString().substring(0,5)),courseID) && mMan.assignLecturerToCourse(moduleID,Integer.parseInt(user.getSelectedItem().toString().substring(0,5)))) {
                JOptionPane.showMessageDialog(null,"Lecturer has been assigned to course!","Success",JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null,"Lecturer assignment unsuccessful.","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void enroll(int userID) {
        List<String> courses = mMan.courseIDsTitles();
        Object[] comboContents = new Object[courses.size()/2]; // create array for combo box
        for (int i = 0; i < courses.size(); i+=2) {
            comboContents[i/2] = courses.get(i) + " - " + courses.get(i+1);
        }

        // create combo box for Joptionpane
        JComboBox course = new JComboBox(comboContents);

        Object[] inputs = {"Course: ",course};

        // show window
        var options = JOptionPane.showConfirmDialog(null, inputs, "Enroll Student", JOptionPane.OK_CANCEL_OPTION);
        if (options == JOptionPane.OK_OPTION) {
            if (mMan.enrol(userID,Integer.parseInt(course.getSelectedItem().toString().substring(0,5))) == "success") {
                JOptionPane.showMessageDialog(null,"Student has been enrolled!","Success",JOptionPane.INFORMATION_MESSAGE);
            }
            else if (mMan.enrol(userID,Integer.parseInt(course.getSelectedItem().toString().substring(0,5))) == "already enrolled") {
                JOptionPane.showMessageDialog(null,"Student is already enrolled in a course!.","Error",JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null,"Student enrollment unsuccessful.","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void decision(int userID) {
        List<String> results = mMan.allStudentResults(userID);
        Object[] decisions = {"Award","Resit","Withdraw"};
        JComboBox decision = new JComboBox(decisions);
        // show window
        var options = JOptionPane.showConfirmDialog(null, decision, "Issue Decision", JOptionPane.OK_CANCEL_OPTION);
        if (options == JOptionPane.OK_OPTION) {
            if (mMan.decision(userID,decision.getSelectedItem().toString().toLowerCase())) {
                JOptionPane.showMessageDialog(null,"Decision issued successfully!","Success",JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null,"Decision was not issued.","Error",JOptionPane.ERROR_MESSAGE);
            }
        }


    }

    private void deactivate(int userID) {
        // show window
        var options = JOptionPane.showConfirmDialog(null, "Deactivate this account?", "Deactivate Account", JOptionPane.OK_CANCEL_OPTION);
        if (options == JOptionPane.OK_OPTION) {
            if (mMan.deactivate(userID)) {
                JOptionPane.showMessageDialog(null,"Account successfully deactivated!","Success",JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null,"Account was not deactivated.","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void uploadNote(int modID){
        Object[] weeks = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        JComboBox comboWeek = new JComboBox(weeks);
        String[] types = {"Lecture", "Lab"};
        JComboBox comboType = new JComboBox(types);

        Object[] inputs = {"Week: ",comboWeek,"Note type: ",comboType};
        String[] buttons = {"Upload","Cancel"};

        // create option pane
        var options = JOptionPane.showOptionDialog(null, inputs, "Select week", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, null);
        if (options == JOptionPane.OK_OPTION) {
            int week = Integer.parseInt(comboWeek.getSelectedItem().toString());
            String type = comboType.getSelectedItem().toString().toLowerCase();
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Upload Notes");
            int result = chooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                StringBuilder notes = new StringBuilder();
                File noteFile = chooser.getSelectedFile();
                try (Scanner scan = new Scanner(noteFile)) {
                    while (scan.hasNextLine()) {
                        notes.append(scan.nextLine());
                        if (scan.hasNextLine()) {
                            notes.append("\n");
                        }
                    }
                    //System.out.println(String.valueOf(notes));
                    if (String.valueOf(notes).equals("")) {
                        JOptionPane.showMessageDialog(null,"Notes upload unsuccessful. Note content was empty.","Error",JOptionPane.ERROR_MESSAGE);

                    }
                    else if (mLect.uploadNotes(week, String.valueOf(notes),modID,type)) {
                        JOptionPane.showMessageDialog(null,"Notes uploaded successfully!","Success.",JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Notes upload unsuccessful.","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //JOptionPane.showMessageDialog(null, "Notes downloaded to:\n" + saveFile.getAbsolutePath());
            }
            else {
                JOptionPane.showMessageDialog(null, "No notes uploaded. Please check again at a later time.","Notes",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void setMark(int studentID, int moduleID) {
        String type = mLect.markingType(moduleID);
        if (type.equals("exam")) {
            JTextField mark = new JTextField();
            Object[] inputs = {"Exam mark: ",mark};
            setMarkWindow(studentID,moduleID,inputs);
        }
        if (type.equals("lab")) {
            JTextField mark = new JTextField();
            Object[] inputs = {"Lab mark: ",mark};
            setMarkWindow(studentID,moduleID,inputs);
        }
        if (type.equals("assignment")) {
            mLect.assignmentNum(moduleID);
            Object[] inputs = new Object[mLect.assignmentNum(moduleID)*2];
            for (int i = 0; i < ((mLect.assignmentNum(moduleID))*2); i+=2) {
                if (i == 0) {
                    inputs[i] = "Assignment 1";
                }
                else if (i == 2) {
                    inputs[i] = "Assignment 2";
                }
                else {
                    inputs[i] = "Assignment " + (i+1)/2;
                }
                inputs[i + 1] = new JTextField();
            }
            setMarkWindow(studentID,moduleID,inputs);
        }
        if (type.equals("exam&lab")) {
            JTextField examMark = new JTextField();
            JTextField labMark = new JTextField();
            Object[] inputs = {"Exam mark: ",examMark,"Lab mark: ",labMark};
            setMarkWindow(studentID,moduleID,inputs);
        }
        if (type.equals("exam&assignment")) {
            JTextField mark = new JTextField();
            mLect.assignmentNum(moduleID);
            Object[] inputs = new Object[(mLect.assignmentNum(moduleID)*2)+2];
            inputs[0] = "Exam mark: ";
            inputs[1] = mark;
            for (int i = 0; i < ((mLect.assignmentNum(moduleID)+2)*2); i+=2) {
                inputs[i] = "Assignment " + (i+1)/2 + " mark:";
                inputs[i+1] = new JTextField();
            }
            setMarkWindow(studentID,moduleID,inputs);
        }
        if (type.equals("lab&assignment")) {
            JTextField mark = new JTextField();
            mLect.assignmentNum(moduleID);
            Object[] inputs = new Object[(mLect.assignmentNum(moduleID)*2)+2];
            inputs[0] = "Exam mark: ";
            inputs[1] = mark;
            for (int i = 0; i < ((mLect.assignmentNum(moduleID)+2)*2); i+=2) {
                inputs[i] = "Assignment " + (i+1)/2 + " mark:";
                inputs[i+1] = new JTextField();
            }
            setMarkWindow(studentID,moduleID,inputs);
        }
    }

    private void setMarkWindow(int studentID,int moduleID,Object[] inputs) {
        var options = JOptionPane.showConfirmDialog(null, inputs, "Issue Decision", JOptionPane.OK_CANCEL_OPTION);
        if (options == JOptionPane.OK_OPTION) {
            Object[] answers = new Object[inputs.length/2];
            for (int i = 0; i < (inputs.length); i+=2) {
                answers[i/2] = inputs[i+1];
            }
            int avg = mLect.average(answers);
            if (mLect.setMark(studentID,moduleID,avg)) {
                JOptionPane.showMessageDialog(null,"Module grade set successfully!","Success",JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null,"Module grade was not set.","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
