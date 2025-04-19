import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculator {

    private JFrame frame;
    private JTextField display;
    private StringBuilder input = new StringBuilder();
    private List<String> history = new ArrayList<>();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Calculator window = new Calculator();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Calculator() {
        initialize();
    }

    private void toggleTheme(boolean isDarkMode) {
        Color backgroundColor = isDarkMode ? Color.DARK_GRAY : Color.WHITE;
        Color textColor = isDarkMode ? Color.WHITE : Color.BLACK;

        frame.getContentPane().setBackground(backgroundColor);
        display.setBackground(backgroundColor);
        display.setForeground(textColor);

        // Cập nhật màu sắc cho các nút
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

        // Khởi tạo giao diện
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Segoe UI", Font.BOLD, 32));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.getContentPane().add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 4, 10, 10));
        panel.setBackground(Color.DARK_GRAY);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        String[] buttons = {
            "1", "2", "3", "CE", "C", "4", "5", "6", "+", "-", "7", "8", 
            "9", "*", "/", ".", "0", "=", "(", ")", "√", "^", "%", "n!", 
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

        // Thêm nút để chuyển chế độ Dark/Light
        JButton darkModeButton = new JButton("Toggle Theme");
        darkModeButton.addActionListener(e -> toggleTheme(true));
        panel.add(darkModeButton);

        // Thêm các tùy chọn Dark/Light mode và phông chữ
        JButton fontButton = new JButton("Change Font");
        fontButton.addActionListener(e -> openFontChooser());
        panel.add(fontButton);

        JButton colorButton = new JButton("Change Color");
        colorButton.addActionListener(e -> openColorChooser());
        panel.add(colorButton);

        // Thêm KeyListener để hỗ trợ keyboard shortcuts
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                String command = String.valueOf(keyChar);

                // Nếu là phím số hoặc phép toán, thêm vào input
                if (command.matches("[0-9\\+\\-\\*/=()\\.\\^%]")) {
                    input.append(command);
                    display.setText(input.toString());
                }
                // Phím "=" để tính kết quả
                else if (keyChar == '\n' || keyChar == '=') {
                    try {
                        String result = calculateResult(input.toString());
                        history.add(input + " = " + result);
                        input.setLength(0);
                        input.append(result);
                        display.setText(result);
                    } catch (Exception ex) {
                        display.setText("Lỗi: " + ex.getMessage());
                    }
                }
                // Phím "Backspace" để xóa
                else if (keyChar == KeyEvent.VK_BACK_SPACE) {
                    if (input.length() > 0) {
                        input.deleteCharAt(input.length() - 1);
                        display.setText(input.toString());
                    }
                }
            }
        });
        frame.setFocusable(true); // Cho phép nhận sự kiện bàn phím
    }

    private void openFontChooser() {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        String selectedFont = (String) JOptionPane.showInputDialog(frame, "Chọn phông chữ:", 
            "Tùy chỉnh phông chữ", JOptionPane.PLAIN_MESSAGE, null, fonts, fonts[0]);
        if (selectedFont != null) {
            display.setFont(new Font(selectedFont, Font.PLAIN, 32));
        }
    }

    private void openColorChooser() {
        Color chosenColor = JColorChooser.showDialog(frame, "Chọn màu nền", display.getBackground());
        if (chosenColor != null) {
            display.setBackground(chosenColor);
        }
    }

    private void buttonClicked(ActionEvent e) {
        String command = e.getActionCommand();

        if ("C".equals(command)) {
            input.setLength(0);
            display.setText("");
        } else if ("Backspace".equals(command)) {
            if (input.length() > 0) {
                input.deleteCharAt(input.length() - 1);
                display.setText(input.toString());
            }
        } else if ("CE".equals(command)) {
            input.setLength(0);
            display.setText("");
        } else if ("History".equals(command)) {
            String historyText = String.join("\n", history);
            JOptionPane.showMessageDialog(frame, historyText, "Lịch sử phép tính", JOptionPane.INFORMATION_MESSAGE);
        } else if ("=".equals(command)) {
            try {
                String result = calculateResult(input.toString());
                history.add(input.toString() + " = " + result);
                input.setLength(0);
                input.append(result);
                display.setText(result);
            } catch (Exception ex) {
                display.setText("Error");
            }
        } else if ("n!".equals(command)) {
            try {
                int num = Integer.parseInt(input.toString());
                input.setLength(0);
                input.append(factorial(num));
                display.setText(input.toString());
            } catch (Exception ex) {
                display.setText("Error");
            }
        } else if ("log".equals(command)) {
            try {
                double num = Double.parseDouble(input.toString());
                input.setLength(0);
                input.append(Math.log10(num));
                display.setText(input.toString());
            } catch (Exception ex) {
                display.setText("Error");
            }
        } else if ("ln".equals(command)) {
            try {
                double num = Double.parseDouble(input.toString());
                input.setLength(0);
                input.append(Math.log(num));
                display.setText(input.toString());
            } catch (Exception ex) {
                display.setText("Error");
            }
        } else if ("sin".equals(command) || "cos".equals(command) || "tan".equals(command) || "cot".equals(command)) {
            try {
                double angle = Math.toRadians(Double.parseDouble(input.toString()));
                double result = switch (command) {
                    case "sin" -> Math.sin(angle);
                    case "cos" -> Math.cos(angle);
                    case "tan" -> Math.tan(angle);
                    case "cot" -> 1.0 / Math.tan(angle);
                    default -> throw new IllegalArgumentException("Invalid trig function");
                };
                input.setLength(0);
                input.append(result);
                display.setText(input.toString());
            } catch (Exception ex) {
                display.setText("Error");
            }
        } else if ("Deg".equals(command)) {
            try {
                double rad = Double.parseDouble(input.toString());
                input.setLength(0);
                input.append(Math.toDegrees(rad));
                display.setText(input.toString());
            } catch (Exception ex) {
                display.setText("Error");
            }
        } else if ("Rad".equals(command)) {
            try {
                double deg = Double.parseDouble(input.toString());
                input.setLength(0);
                input.append(Math.toRadians(deg));
                display.setText(input.toString());
            } catch (Exception ex) {
                display.setText("Error");
            }
        } else {
            input.append(command);
            display.setText(input.toString());
        }
    }

    private String calculateResult(String expression) throws Exception {
        expression = expression.replace(" ", "");

        if (expression.contains("+")) {
            return performOperation(expression, "\\+");
        } else if (expression.contains("-")) {
            return performOperation(expression, "-");
        } else if (expression.contains("*")) {
            return performOperation(expression, "\\*");
        } else if (expression.contains("/")) {
            return performOperation(expression, "/");
        } else if (expression.contains("√")) {
            double num = Double.parseDouble(expression.replace("√", ""));
            return String.valueOf(Math.sqrt(num));
        } else if (expression.contains("^")) {
            String[] parts = expression.split("\\^");
            double base = Double.parseDouble(parts[0]);
            double exponent = Double.parseDouble(parts[1]);
            return String.valueOf(Math.pow(base, exponent));
        } else if (expression.contains("%")) {
            String[] parts = expression.split("%");
            double num = Double.parseDouble(parts[0]);
            return String.valueOf(num / 100);
        }

        throw new IllegalArgumentException("Biểu thức không hợp lệ");
    }

    private String performOperation(String expression, String operator) throws Exception {
        String[] parts = expression.split(operator);

        if (parts.length != 2) {
            throw new IllegalArgumentException("Biểu thức không hợp lệ");
        }

        double num1 = Double.parseDouble(parts[0]);
        double num2 = Double.parseDouble(parts[1]);

        return switch (operator) {
            case "\\+" -> String.valueOf(num1 + num2);
            case "-" -> String.valueOf(num1 - num2);
            case "\\*" -> String.valueOf(num1 * num2);
            case "/" -> {
                if (num2 == 0) throw new ArithmeticException("Không thể chia cho 0");
                yield String.valueOf(num1 / num2);
            }
            default -> throw new IllegalArgumentException("Toán tử không hợp lệ: " + operator);
        };
    }

    private long factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("Số âm không có giai thừa");
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
