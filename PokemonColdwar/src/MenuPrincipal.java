import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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

	

public class MenuPrincipal extends JPanel implements ActionListener {

	
	static Clip bgmMenu;
	
	private JButton jbJugar;
	private JButton jbReglas;
	private JButton jbInfo;
	private JButton jbRank;
	private JLabel jlFondo;
	private JButton jbExit;

	public MenuPrincipal() {
		

		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/resources/music/BGM/menu.wav").getAbsoluteFile());
			bgmMenu = AudioSystem.getClip();
			bgmMenu.open(audioInputStream);
			bgmMenu.loop(9999);


		} catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("Error al reproducir el sonido.");
		}
		

		setLayout(null);

//Cambio de cursor
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("src/resources/cursor.png");
		Cursor c = toolkit.createCustomCursor(image , new Point(this.getX(),this.getY()), "img");
		this.setCursor (c);
		
//Botón de jugar
		jbJugar = new JButton();
		jbJugar.setIcon(new ImageIcon("src/resources/botones/jugar.png"));
		jbJugar.setPressedIcon(new ImageIcon("src/resources/botones/jugar2.png"));
		jbJugar.setBounds(214, 284, 260, 92);
		jbJugar.setBorderPainted(false); 
		jbJugar.setContentAreaFilled(false);
		add(jbJugar);
		jbJugar.addActionListener(this);
		

//Botón de reglas
		jbReglas = new JButton();
		jbReglas.setIcon(new ImageIcon("src/resources/botones/reglas.png"));
		jbReglas.setPressedIcon(new ImageIcon("src/resources/botones/reglas2.png"));
		jbReglas.setBounds(550, 284, 260, 92);
		jbReglas.setBorderPainted(false); 
		jbReglas.setContentAreaFilled(false);
		add(jbReglas);
		jbReglas.addActionListener(this);
		
//Botón de infromación
		jbInfo = new JButton();
		jbInfo.setIcon(new ImageIcon("src/resources/botones/info.png"));
		jbInfo.setPressedIcon(new ImageIcon("src/resources/botones/info2.png"));
		jbInfo.setBounds(214, 452, 260, 92);
		jbInfo.setBorderPainted(false); 
		jbInfo.setContentAreaFilled(false);
		add(jbInfo);
		jbInfo.addActionListener(this);

//Botón de ranking
		jbRank = new JButton();
		jbRank.setIcon(new ImageIcon("src/resources/botones/rank.png"));
		jbRank.setPressedIcon(new ImageIcon("src/resources/botones/rank2.png"));
		jbRank.setBounds(550, 452, 260, 92);
		jbRank.setBorderPainted(false); 
		jbRank.setContentAreaFilled(false);
		add(jbRank);
		jbRank.addActionListener(this);

//Botón de flecha
		jbExit = new JButton();
		jbExit.setIcon(new ImageIcon("src/resources/botones/x.png"));
		jbExit.setPressedIcon(new ImageIcon("src/resources/botones/x2.png"));
		jbExit.setBounds(890, 675, 96, 72);
		jbExit.setBorderPainted(false); 
		jbExit.setContentAreaFilled(false);
		add(jbExit);
		jbExit.addActionListener(this);
		
//Fondo Menu
		jlFondo = new JLabel();
		jlFondo.setBounds(0,0,1024,768);
		jlFondo.setIcon(new ImageIcon("src/resources/fondos/fondo_menu.gif"));
		add(jlFondo); 

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(jbJugar)) {
			JFrame evento1 = (JFrame)SwingUtilities.getWindowAncestor(this);
			evento1.remove(this);
			evento1.getContentPane().add(new NombreEquipo());
			evento1.setVisible(true);
		}

		else if (e.getSource().equals(jbReglas)) {
			JFrame evento1 = (JFrame)SwingUtilities.getWindowAncestor(this);
			evento1.remove(this);
			evento1.getContentPane().add(new Reglas());
			evento1.setVisible(true);
		}

		else if (e.getSource().equals(jbInfo)) {
			JFrame evento1 = (JFrame)SwingUtilities.getWindowAncestor(this);
			evento1.remove(this);
			evento1.getContentPane().add(new Info());
			evento1.setVisible(true);
		}

		else if (e.getSource().equals(jbRank)) {
			JFrame evento1 = (JFrame)SwingUtilities.getWindowAncestor(this);
			evento1.remove(this);
			evento1.getContentPane().add(new Ranking());
			evento1.setVisible(true);
		}

		else if (e.getSource() == jbExit) {
			System.exit(0);
		}

	}

}
