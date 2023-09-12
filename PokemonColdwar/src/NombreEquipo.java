import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class NombreEquipo extends JPanel implements ActionListener {

	static Clip bgmNombre;
	
	private JButton jbOk;
	private JLabel jlFondo;
	private JLabel jlTexto;
	private JTextField jtNombre;
	private Font sizeFont;
	private File myFont;
	private Font fuente;

	public NombreEquipo() {

		MenuPrincipal.bgmMenu.close();
		

		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/resources/music/BGM/equipos.wav").getAbsoluteFile());
			bgmNombre = AudioSystem.getClip();
			bgmNombre.open(audioInputStream);
			bgmNombre.loop(9999);


		} catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("Error al reproducir el sonido.");
		}
		
		
		setLayout(null);

		//Fuente de letra
		String fontName = "src/resources/Fonts/Font3.ttf" ;
		myFont = new File(fontName);

		try{

			fuente = Font.createFont(Font.TRUETYPE_FONT,myFont);
			sizeFont = fuente.deriveFont(26f);

		}catch(FontFormatException ex){

			System.err.println("Error estableciendo fuente tipográfica");

		}catch (IOException ex) {

			System.err.println("Error I/O");
		}

		//Cursor 
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("src/resources/cursor.png");
		Cursor c = toolkit.createCustomCursor(image , new Point(this.getX(),this.getY()), "img");
		this.setCursor (c);

		//Botón de OK
		jbOk = new JButton();
		jbOk.setIcon(new ImageIcon("src/resources/botones/ok.png"));
		jbOk.setPressedIcon(new ImageIcon("src/resources/botones/ok2.png"));
		jbOk.setBounds(890, 646, 104, 92);
		jbOk.setBorderPainted(false); 
		jbOk.setContentAreaFilled(false);
		add(jbOk);
		jbOk.addActionListener(this);

		//Texto nombre entrenador
		jlTexto = new JLabel("\nIntroduce el nombre de tu entrenador:");
		jlTexto.setBounds(65,580,700,60);
		jlTexto.setFont(sizeFont);
		jlTexto.setOpaque(false);
		add(jlTexto);

		//Introducir Nombre
		jtNombre = new JTextField();
		jtNombre.setOpaque(false);
		jtNombre.setBorder(null);
		jtNombre.setBounds(75,653,700,40);
		jtNombre.setFont(sizeFont);
		add(jtNombre);


		//Fondo de nombre equipo
		jlFondo = new JLabel();
		jlFondo.setBounds(0,0,1024,768);
		jlFondo.setIcon(new ImageIcon("src/resources/fondos/fondo_nombre.png"));
		add(jlFondo); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(jbOk)) {
			JFrame evento1 = (JFrame)SwingUtilities.getWindowAncestor(this);
			evento1.remove(this);
			evento1.getContentPane().add(new TipoEquipo());
			evento1.setVisible(true);
			
		}

	}

}
