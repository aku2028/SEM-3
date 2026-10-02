import javax.swing.*;
import java.awt.*;

public class QuizForm {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Simple Quiz");
        frame.setLayout(new FlowLayout());

        JLabel question = new JLabel("Which language is used for Android development?");

        JRadioButton option1 = new JRadioButton("Python");
        JRadioButton option2 = new JRadioButton("Java");
        JRadioButton option3 = new JRadioButton("HTML");
        JRadioButton option4 = new JRadioButton("SQL");

        ButtonGroup group = new ButtonGroup();
        group.add(option1);
        group.add(option2);
        group.add(option3);
        group.add(option4);

        JButton submitButton = new JButton("Submit");

        frame.add(question);
        frame.add(option1);
        frame.add(option2);
        frame.add(option3);
        frame.add(option4);
        frame.add(submitButton);

        frame.setSize(450, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}