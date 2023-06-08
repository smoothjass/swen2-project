package fhtw.swen2.duelli.duvivie.swen2project.Services;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoadingSpinnerService {

    private JFrame frame;
    private JLabel spinnerLabel;

    public void showSpinnerWindow() {
        frame = new JFrame();
        frame.setSize(200, 200);
        //the user should not be able to close the window
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null); // Center the window
        //the user should not be able to click anything behind the window
        frame.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        // the window should be always on top
        frame.setAlwaysOnTop(true);

        JPanel panel = new JPanel(new BorderLayout());

        spinnerLabel = new JLabel();
        spinnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon spinnerIcon = new ImageIcon("src/main/resources/fhtw/swen2/duelli/duvivie/swen2project/gifs/Ball in a pipe.gif");
        spinnerLabel.setIcon(spinnerIcon);

        panel.add(spinnerLabel, BorderLayout.CENTER);
        //the panel should have a white background
        panel.setBackground(Color.WHITE);
        //the panel should have a black border with rounded corners
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        panel.add(new JLabel("Loading..."), BorderLayout.SOUTH);

        //the panel should not be larger than the image
        panel.setPreferredSize(new Dimension(spinnerIcon.getIconWidth(), spinnerIcon.getIconHeight()));
        frame.add(panel);
        frame.setVisible(true);
    }

    public void hideSpinnerWindow() {
        if (frame != null) {
            frame.setVisible(false);
            frame.dispose();
        }
    }
}
