package states;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import view.ui.buttons.ReturnButton;

public class Settings extends State {

    private static final Settings INSTANCE = new Settings("Settings");

    public Settings(String name) {
        super(name);
    }

    public static Settings getInstance(){
        return INSTANCE;
    }

    public void enter(){
        System.out.println("Settings");
        // Create a check box for the vsync
        JPanel managerJPanel = new JPanel(new GridBagLayout());
        managerJPanel.setOpaque(false);
        managerJPanel.setBounds(0, 0, Menu.getInstance().background.getWidth(), 
        Menu.getInstance().background.getHeight());
        managerJPanel.setOpaque(false);
        JCheckBox vsync = new JCheckBox("VSync");
        JCheckBox prout = new JCheckBox("Prout");
        // Uncheck the prout checkbox
        prout.setSelected(false);
        prout.addItemListener(new ItemListener() {
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                try {
                    // Open an audio input stream.
                    URL url = this.getClass().getClassLoader().getResource("fart-03.wav");
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                    // Get a sound clip resource.
                    Clip clip = AudioSystem.getClip();
                    // Open audio clip and load samples from the audio input stream.
                    clip.open(audioIn);
                    clip.start();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    ex.printStackTrace();
                }
            }
        }
    });
    
        Font font = new Font("Arial", Font.PLAIN, 24);
        vsync.setFont(font);
        prout.setFont(font);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
    
        managerJPanel.add(vsync, gbc); // Add the checkboxes with the GridBagConstraints
        managerJPanel.add(prout, gbc);
    
        vsync.setOpaque(false);
        prout.setOpaque(false);
        vsync.setSelected(true);
        prout.setSelected(true);
        managerJPanel.validate();
        Menu.getInstance().background.add(managerJPanel);
        Menu.getInstance().background.validate();
    
        ReturnButton returnButton = new ReturnButton(Menu.getInstance());
        managerJPanel.add(returnButton, gbc); // Add the button with the GridBagConstraints
        managerJPanel.validate();
    }
    

    public void exit(){
        System.out.println("Exit Settings");
        // We clear the screen
        Menu.getInstance().background.removeAll();
    }

    @Override
    public void updateLogic(long deltaT) {
        
    }

    public void transitionTo(State s){

    }

    public void mouseClicked(int x, int y){

    }
}
