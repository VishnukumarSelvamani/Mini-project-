import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculator {
    private JFrame frame;
    private JTextField display;
    private String currentInput = "";
    private String previousInput = "";
    private String operator = "";
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SimpleCalculator().createAndShowGUI();
        });
    }

    public SimpleCalculator() {
        frame = new JFrame("Simple Calculator");
        display = new JTextField();
    }

    private void createAndShowGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.PLAIN, 30));
        display.setPreferredSize(new Dimension(400, 60)); 
        display.setBackground(Color.BLACK);
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        frame.add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 10, 10)); 
        frame.add(panel, BorderLayout.CENTER);
        
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        for (String label : buttons) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 24));
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }
        
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if ("0123456789.".contains(command)) {
                currentInput += command;
                display.setText(currentInput);
            } else if (command.equals("=")) {
                calculateResult();
            } else {
                if (!currentInput.isEmpty()) {
                    previousInput = currentInput;
                    currentInput = "";
                    operator = command;
                }
            }
        }
    }

    private void calculateResult() {
        if (previousInput.isEmpty() || currentInput.isEmpty() || operator.isEmpty()) {
            return;
        }
        
        double num1 = Double.parseDouble(previousInput);
        double num2 = Double.parseDouble(currentInput);
        double result = 0;

        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    display.setText("Error");
                    return;
                }
                break;
        }
        
        display.setText(String.valueOf(result));
        currentInput = String.valueOf(result);
        previousInput = "";
        operator = "";
    }
}
