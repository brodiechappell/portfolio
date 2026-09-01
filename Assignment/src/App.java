import javax.swing.*;
public class App {
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("University Student Management System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create instances of views and model
        ViewLogin vLogin = new ViewLogin();
        ViewSignup vSignup = new ViewSignup();
        ViewStudentHome vStudent = new ViewStudentHome();
        ViewLecturerHome vLecturer = new ViewLecturerHome();
        ViewManagerHome vManager = new ViewManagerHome();
        ModelUser mUser = new ModelUser();
        ModelLecturer mLecturer = new ModelLecturer();
        ModelManager mManager = new ModelManager();

        // create controller
        ControllerUser controller = new ControllerUser(vLogin, vSignup, mUser, mLecturer, mManager, vStudent, vLecturer, vManager);
        // add view panels to main frame
        mainFrame.setLayout(null);

        // set bounds
        vLogin.getPanelMain().setBounds(0,0,400,350);
        vSignup.getPanelMain().setBounds(0,0,400,350);
        vStudent.getPanelMain().setBounds(0,0,400,350);
        vLecturer.getPanelMain().setBounds(0,0,400,350);
        vManager.getPanelMain().setBounds(0,0,400,350);

        // show login by default
        vLogin.getPanelMain().setVisible(true);
        vSignup.getPanelMain().setVisible(false);
        vStudent.getPanelMain().setVisible(false);
        vLecturer.getPanelMain().setVisible(false);
        vManager.getPanelMain().setVisible(false);

        // add views to main frame
        mainFrame.add(vLogin.getPanelMain());
        mainFrame.add(vSignup.getPanelMain());
        mainFrame.add(vStudent.getPanelMain());
        mainFrame.add(vLecturer.getPanelMain());
        mainFrame.add(vManager.getPanelMain());

        // position the frame at centre
        mainFrame.setSize(415,385);
        mainFrame.setLocationRelativeTo(null);

        // show main frame
        mainFrame.setVisible(true);
    }
}
