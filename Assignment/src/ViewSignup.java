import javax.swing.*;

public class ViewSignup {
    private JPanel panelMain;
    private JLabel usernameLabel;
    private JTextField usernameTxt;
    private JLabel emailLabel;
    private JTextField emailTxt;
    private JLabel forenameLabel;
    private JTextField forenameTxt;
    private JLabel surnameLabel;
    private JTextField surnameTxt;
    private JLabel genderLabel;
    private JComboBox genderCombo;
    private JCheckBox lecturerBox;
    private JLabel qualificationLabel;
    private JComboBox qualificationCombo;
    private JLabel dobLabel;
    private JComboBox dayCombo;
    private JComboBox monthCombo;
    private JComboBox yearCombo;
    private JLabel passwordLabel;
    private JPasswordField passwordField1;
    private JLabel retypeLabel;
    private JPasswordField passwordField2;
    private JButton clearButton;
    private JButton signUpButton;
    private JButton loginPageButton;

    public JPanel getPanelMain() {
        return panelMain;
    }

    public void setPanelMain(JPanel panelMain) {
        this.panelMain = panelMain;
    }

    public JTextField getUsernameTxt() {
        return usernameTxt;
    }

    public void setUsernameTxt(JTextField usernameTxt) {
        this.usernameTxt = usernameTxt;
    }

    public JTextField getEmailTxt() {
        return emailTxt;
    }

    public void setEmailTxt(JTextField emailTxt) {
        this.emailTxt = emailTxt;
    }

    public JTextField getForenameTxt() {
        return forenameTxt;
    }

    public void setForenameTxt(JTextField forenameTxt) {
        this.forenameTxt = forenameTxt;
    }

    public JTextField getSurnameTxt() {
        return surnameTxt;
    }

    public void setSurnameTxt(JTextField surnameTxt) {
        this.surnameTxt = surnameTxt;
    }

    public JCheckBox getLecturerBox() {
        return lecturerBox;
    }

    public void setLecturerBox(JCheckBox lecturerBox) {
        this.lecturerBox = lecturerBox;
    }

    public JComboBox getGenderCombo() {
        return genderCombo;
    }

    public void setGenderCombo(JComboBox genderCombo) {
        this.genderCombo = genderCombo;
    }

    public JPasswordField getPasswordField1() {
        return passwordField1;
    }

    public void setPasswordField1(JPasswordField passwordField1) {
        this.passwordField1 = passwordField1;
    }

    public JPasswordField getPasswordField2() {
        return passwordField2;
    }

    public void setPasswordField2(JPasswordField passwordField2) {
        this.passwordField2 = passwordField2;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public void setClearButton(JButton clearButton) {
        this.clearButton = clearButton;
    }

    public JButton getSignUpButton() {
        return signUpButton;
    }

    public void setSignUpButton(JButton signUpButton) {
        this.signUpButton = signUpButton;
    }

    public JButton getLoginPageButton() {
        return loginPageButton;
    }

    public void setLoginPageButton(JButton loginPageButton) {
        this.loginPageButton = loginPageButton;
    }

    public JLabel getUsernameLabel() {
        return usernameLabel;
    }

    public void setUsernameLabel(JLabel usernameLabel) {
        this.usernameLabel = usernameLabel;
    }

    public JLabel getEmailLabel() {
        return emailLabel;
    }

    public void setEmailLabel(JLabel emailLabel) {
        this.emailLabel = emailLabel;
    }

    public JLabel getForenameLabel() {
        return forenameLabel;
    }

    public void setForenameLabel(JLabel forenameLabel) {
        this.forenameLabel = forenameLabel;
    }

    public JLabel getSurnameLabel() {
        return surnameLabel;
    }

    public void setSurnameLabel(JLabel surnameLabel) {
        this.surnameLabel = surnameLabel;
    }

    public JLabel getDobLabel() {
        return dobLabel;
    }

    public void setDobLabel(JLabel dobLabel) {
        this.dobLabel = dobLabel;
    }

    public JLabel getGenderLabel() {
        return genderLabel;
    }

    public void setGenderLabel(JLabel genderLabel) {
        this.genderLabel = genderLabel;
    }

    public JLabel getPasswordLabel() {
        return passwordLabel;
    }

    public void setPasswordLabel(JLabel passwordLabel) {
        this.passwordLabel = passwordLabel;
    }

    public JLabel getRetypeLabel() {
        return retypeLabel;
    }

    public void setRetypeLabel(JLabel retypeLabel) {
        this.retypeLabel = retypeLabel;
    }

    public JComboBox getQualificationCombo() {
        return qualificationCombo;
    }

    public void setQualificationCombo(JComboBox qualificationCombo) {
        this.qualificationCombo = qualificationCombo;
    }

    public JLabel getQualificationLabel() {
        return qualificationLabel;
    }

    public void setQualificationLabel(JLabel qualificationLabel) {
        this.qualificationLabel = qualificationLabel;
    }

    public JComboBox getDayCombo() {
        return dayCombo;
    }

    public void setDayCombo(JTextField dayTxt) {
        this.dayCombo = dayCombo;
    }

    public JComboBox getMonthCombo() {
        return monthCombo;
    }

    public void setMonthCombo(JComboBox monthCombo) {
        this.monthCombo = monthCombo;
    }

    public JComboBox getYearCombo() {
        return yearCombo;
    }

    public void setYearCombo(JComboBox yearTxt) {
        this.monthCombo = yearTxt;
    }

    public void clearTxts() {
        // clear all text fields
        usernameTxt.setText("");
        emailTxt.setText("");
        forenameTxt.setText("");
        surnameTxt.setText("");
        passwordField1.setText("");
        passwordField2.setText("");
        usernameTxt.grabFocus();
    }
}
