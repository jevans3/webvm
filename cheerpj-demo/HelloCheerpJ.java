import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Minimal Swing demo used to verify CheerpJ (Java-in-the-browser) works
 * alongside the CheerpX-powered WebVM. Compiled with --release 11 so the
 * bytecode is compatible with the CheerpJ runtime.
 */
public class HelloCheerpJ {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(HelloCheerpJ::buildUI);
    }

    private static void buildUI() {
        JFrame frame = new JFrame("CheerpJ Test - Java running in the browser");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(520, 300);

        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Hello from CheerpJ!", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel info = new JLabel(
            "<html><center>This Swing app is real Java bytecode running fully "
            + "client-side via WebAssembly.<br>JVM: "
            + System.getProperty("java.version") + "</center></html>",
            SwingConstants.CENTER);

        JLabel clock = new JLabel("", SwingConstants.CENTER);
        clock.setFont(new Font("Monospaced", Font.PLAIN, 16));

        JButton button = new JButton("Update time");
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Runnable tick = () -> clock.setText(LocalDateTime.now().format(fmt));
        button.addActionListener(e -> tick.run());
        tick.run();

        panel.add(title, BorderLayout.NORTH);
        panel.add(info, BorderLayout.CENTER);
        JPanel south = new JPanel(new BorderLayout(8, 8));
        south.add(clock, BorderLayout.CENTER);
        south.add(button, BorderLayout.SOUTH);
        panel.add(south, BorderLayout.SOUTH);

        frame.setContentPane(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
