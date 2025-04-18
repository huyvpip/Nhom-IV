import javax.swing.*;
import java.awt.*;

public class FontAndColorApp {
    private JFrame frame;
    private JTextArea display;

    public FontAndColorApp() {
        frame = new JFrame("Chọn Phông Chữ và Màu Nền");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        display = new JTextArea("Xin chào! Thử chọn phông chữ và màu nền nhé!");
        display.setFont(new Font("Serif", Font.PLAIN, 32));
        display.setLineWrap(true);
        display.setWrapStyleWord(true);
        frame.add(new JScrollPane(display), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton fontButton = new JButton("Chọn Phông Chữ");
        JButton colorButton = new JButton("Chọn Màu Nền");

        FontAndColorChooser chooser = new FontAndColorChooser(frame, display);

        fontButton.addActionListener(e -> chooser.openFontChooser());
        colorButton.addActionListener(e -> chooser.openColorChooser());

        buttonPanel.add(fontButton);
        buttonPanel.add(colorButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FontAndColorApp::new);
    }
}

