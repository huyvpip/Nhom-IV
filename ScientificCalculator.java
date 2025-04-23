package toogle;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScientificCalculator {
    private JFrame frame;
    private JTextField display;
    private boolean isDarkMode = false; // Biến trạng thái Dark/Light mode

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ScientificCalculator window = new ScientificCalculator();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ScientificCalculator() {
        initialize();
    }

    private void toggleTheme() {
        isDarkMode = !isDarkMode;

        Color backgroundColor = isDarkMode ? Color.DARK_GRAY : Color.WHITE;
        Color textColor = isDarkMode ? Color.WHITE : Color.BLACK;

        frame.getContentPane().setBackground(backgroundColor);
        display.setBackground(backgroundColor);
        display.setForeground(textColor);

        for (Component component : frame.getContentPane().getComponents()) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                panel.setBackground(backgroundColor);
                for (Component btn : panel.getComponents()) {
                    if (btn instanceof JButton) {
                        JButton button = (JButton) btn;
                        button.setBackground(isDarkMode ? new Color(60, 60, 60) : new Color(245, 245, 245));
                        button.setForeground(textColor);
                    }
                }
            }
        }
    }

    private void initialize() {
        frame = new JFrame("Scientific Calculator");
        frame.setBounds(100, 100, 500, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(10, 10));

        // Ô hiển thị
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Segoe UI", Font.BOLD, 32));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.getContentPane().add(display, BorderLayout.NORTH);

        // Panel chứa các nút
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 4, 10, 10));
        panel.setBackground(Color.DARK_GRAY);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        String[] buttons = {
            "(", ")", "CE", "C", "7", "8", "9", "/", "4", "5", "6", "*",
            "1", "2", "3", "-", "0", ".", "=", "+", "√", "^", "%", "n!",
            "log", "ln", "sin", "cos", "tan", "cot", "Deg", "Rad",
            "Backspace", "History"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Segoe UI", Font.PLAIN, 20));
            button.setBackground(new Color(245, 245, 245));
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            panel.add(button);
            button.addActionListener(this::buttonClicked);
        }

        // Nút Toggle Theme
        JButton darkModeButton = new JButton("Toggle Theme");
        darkModeButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        darkModeButton.addActionListener(e -> toggleTheme());
        panel.add(darkModeButton);

        // Nút đổi font (chưa cài đặt)
        JButton fontButton = new JButton("Change Font");
        fontButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        fontButton.addActionListener(e -> openFontChooser());
        panel.add(fontButton);

        // Nút đổi màu (chưa cài đặt)
        JButton colorButton = new JButton("Change Color");
        colorButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        colorButton.addActionListener(e -> openColorChooser());
        panel.add(colorButton);
    }

    private void buttonClicked(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String buttonText = source.getText();
        display.setText(display.getText() + buttonText); // Tạm thời chỉ in ra text
    }

    private void openFontChooser() {
        JOptionPane.showMessageDialog(frame, "Chức năng đổi font chưa được cài đặt.");
    }

    private void openColorChooser() {
        JOptionPane.showMessageDialog(frame, "Chức năng đổi màu chưa được cài đặt.");
    }
}
