import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Ventana extends JFrame {

	
	
	
	public Ventana() {

		setTitle("Pokémon Coldwar");
		setBounds(100, 100, 1040, 807);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setIconImage(new ImageIcon("src/resources/icon2.png").getImage());
		
		PantallaInicio e = new PantallaInicio() ;
		add(e);
	
	
		

	
		
		

		setVisible(true);

		
		
	}
	
	
}
