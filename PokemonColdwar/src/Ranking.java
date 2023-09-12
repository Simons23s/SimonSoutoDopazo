import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Ranking extends JPanel implements ActionListener {

	static Clip bgmRanking;
	private JButton jbBack;
	private JLabel jlFondo;

	public Ranking() {

		MenuPrincipal.bgmMenu.close();
		

		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/resources/music/BGM/rank.wav").getAbsoluteFile());
			bgmRanking = AudioSystem.getClip();
			bgmRanking.open(audioInputStream);
			bgmRanking.loop(9999);


		} catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("Error al reproducir el sonido.");
		}
		
		
		
		setLayout(null);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("src/resources/cursor.png");
		Cursor c = toolkit.createCustomCursor(image , new Point(this.getX(),this.getY()), "img");
		this.setCursor (c);

		jbBack = new JButton();
		jbBack.setIcon(new ImageIcon("src/resources/botones/flecha.png"));
		jbBack.setPressedIcon(new ImageIcon("src/resources/botones/flecha2.png"));
		jbBack.setBounds(890, 646, 104, 92);
		jbBack.setBorderPainted(false); 
		jbBack.setContentAreaFilled(false);
		add(jbBack);
		jbBack.addActionListener(this);

//Fondo del menu ranking
		jlFondo = new JLabel();
		jlFondo.setBounds(0,0,1024,768);
		jlFondo.setIcon(new ImageIcon("src/resources/fondos/fondo_ranking.png"));
		add(jlFondo); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(jbBack)) {
			JFrame evento1 = (JFrame)SwingUtilities.getWindowAncestor(this);
			evento1.remove(this);
			evento1.getContentPane().add(new MenuPrincipal());
			evento1.setVisible(true);
			bgmRanking.close();
		}
	}

}
