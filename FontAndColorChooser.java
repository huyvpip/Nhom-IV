import javax.swing.*;
import java.awt.*;

public class FontAndColorChooser {
    private JFrame frame;
    private JTextArea display;

    public FontAndColorChooser(JFrame frame, JTextArea display) {
        this.frame = frame;
        this.display = display;
    }

    public void openFontChooser() {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        String selectedFont = (String) JOptionPane.showInputDialog(
            frame,
            "Chọn phông chữ:",
            "Tùy chỉnh phông chữ",
            JOptionPane.PLAIN_MESSAGE,
            null,
            fonts,
            fonts[0]
        );
        if (selectedFont != null) {
            display.setFont(new Font(selectedFont, Font.PLAIN, 32));
        }
    }

    public void openColorChooser() {
        Color chosenColor = JColorChooser.showDialog(frame, "Chọn màu nền", display.getBackground());
        if (chosenColor != null) {
            display.setBackground(chosenColor);
        }
    }
}
