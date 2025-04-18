import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
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

    private void initialize() {
        frame = new JFrame("Scientific Calculator");
        frame.setBounds(100, 100, 500, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(10, 10));

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
            "(", ")", "CE", "C",
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "√", "^", "%", "n!",
            "log", "ln", "sin", "cos",
            "tan", "cot", "Deg", "Rad",
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
