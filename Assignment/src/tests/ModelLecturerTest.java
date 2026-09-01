import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class ModelLecturerTest {

    ModelLecturer mLect = new ModelLecturer();

    @Test
    void average() {
        JTextField text1 = new JTextField();
        text1.setText("0");
        JTextField text2 = new JTextField();
        text2.setText("85");
        JTextField text3 = new JTextField();
        text3.setText("20");
        JTextField text4 = new JTextField();
        text4.setText("100");
        JTextField text5 = new JTextField();
        text5.setText("62");

        // assertions
        Object[] inputs0 = {"text1",text1,"text2",text1};
        assertEquals(0,mLect.average(inputs0));

        Object[] inputs1 = {"text2",text2,"text3",text3};
        assertEquals(52,mLect.average(inputs1));

        Object[] inputs2 = {"text1",text1,"text2",text2,"text3",text3,"text4",text4,"text5",text5};
        assertEquals(53, mLect.average(inputs2));

        assertThrows(java.lang.ArithmeticException.class, () -> {mLect.average(new Object[]{});});
    }
}