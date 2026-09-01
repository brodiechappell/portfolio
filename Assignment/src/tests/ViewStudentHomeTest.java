import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ViewStudentHomeTest {

    ViewStudentHome vStu = new ViewStudentHome();

    @Test
    void formatDesc() {
        String test1 = "hello.";
        String test2 = "This is a sentence being used for testing. Break: !!!";
        String test3 = "This is a sentence being used for testing. Break: This is a sentence being used for testing. Break: !!!";

        assertEquals("<p>hello.</p>",vStu.formatDesc(test1));
        assertEquals("<p>This is a sentence being used for testing. Break:<br>!!!</p>",vStu.formatDesc(test2));
        assertEquals("<p>This is a sentence being used for testing. Break:<br>This is a sentence being used for testing. Break:<br>!!!</p>",vStu.formatDesc(test3));
    }

    @Test
    void approvedToString() {
        assertEquals("No",vStu.approvedToString(0));
        assertEquals("Yes",vStu.approvedToString(1));
    }
}