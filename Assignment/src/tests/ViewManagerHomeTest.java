import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ViewManagerHomeTest {

    ViewManagerHome vMan = new ViewManagerHome();

    @Test
    void formatDesc() {
        String test1 = "hello.";
        String test2 = "This is a sentence being used for testing. Break: !!!";
        String test3 = "This is a sentence being used for testing. Break: This is a sentence being used for testing. Break: !!!";

        assertEquals("<p>hello.</p>",vMan.formatDesc(test1));
        assertEquals("<p>This is a sentence being used for testing. Break:<br>!!!</p>",vMan.formatDesc(test2));
        assertEquals("<p>This is a sentence being used for testing. Break:<br>This is a sentence being used for testing. Break:<br>!!!</p>",vMan.formatDesc(test3));
    }
}