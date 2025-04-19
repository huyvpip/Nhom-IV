// Import thêm nếu chưa có
// Thêm phần import đầu file nếu chưa có
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// ... Trong phương thức initialize(), sau khi tạo frame và display:

// Cho phép frame nhận sự kiện bàn phím
frame.setFocusable(true);
frame.requestFocusInWindow();

// Thêm KeyListener để hỗ trợ nhập từ bàn phím
frame.addKeyListener(new KeyAdapter() {
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        char keyChar = e.getKeyChar();

        // Nếu là số hoặc toán tử
        if (Character.isDigit(keyChar) || "+-*/().^%".indexOf(keyChar) != -1) {
            input.append(keyChar);
            display.setText(input.toString());
        }
        // Enter hoặc dấu '=' để tính
        else if (keyChar == '=' || keyCode == KeyEvent.VK_ENTER) {
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
        // Xử lý Backspace
        else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            if (input.length() > 0) {
                input.deleteCharAt(input.length() - 1);
                display.setText(input.toString());
            }
        }
    }
});
