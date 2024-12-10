package GUI;

import java.io.PrintStream;
import javax.swing.JTextArea;

public class RedirectSystemOut {
    public static void redirectToTextArea(JTextArea textArea) {
        PrintStream printStream = new PrintStream(new TextAreaOutputStream(textArea));
        System.setOut(printStream); // Redirect System.out to the JTextArea
    }
}
