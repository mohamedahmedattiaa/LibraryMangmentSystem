package GUI;

import java.io.OutputStream;
import javax.swing.JTextArea;

public class TextAreaOutputStream extends OutputStream {
    private final JTextArea textArea;

    public TextAreaOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b) {
        textArea.append(String.valueOf((char) b)); // Append each byte (character) to the text area
        textArea.setCaretPosition(textArea.getDocument().getLength()); // Automatically scroll to the bottom
    }
}
