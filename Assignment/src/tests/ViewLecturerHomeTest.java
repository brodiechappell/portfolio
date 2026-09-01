import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ViewLecturerHomeTest {

    ViewLecturerHome vLect = new ViewLecturerHome();

    @Test
    void formatDesc() {
        String test1 = "hello.";
        String test2 = "This is a sentence being used for testing. Break: !!!";
        String test3 = "This is a sentence being used for testing. Break: This is a sentence being used for testing. Break: !!!";

        assertEquals("<p>hello.</p>",vLect.formatDesc(test1));
        assertEquals("<p>This is a sentence being used for testing. Break:<br>!!!</p>",vLect.formatDesc(test2));
        assertEquals("<p>This is a sentence being used for testing. Break:<br>This is a sentence being used for testing. Break:<br>!!!</p>",vLect.formatDesc(test3));
    }

    @Test
    void approvedToString() {
        assertEquals("No",vLect.approvedToString(0));
        assertEquals("Yes",vLect.approvedToString(1));
    }
}