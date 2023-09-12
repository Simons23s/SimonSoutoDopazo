import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ResumenFuego extends JPanel implements ActionListener{
	
	private JButton jbStart;
	private JButton jbAdd;
	private JLabel jlFondoFuego;
	
	public ResumenFuego() {
		
		setLayout(null);
		
		//Cursor
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("src/resources/cursor.png");
		Cursor c = toolkit.createCustomCursor(image , new Point(this.getX(),this.getY()), "img");
		this.setCursor (c);
		
		//Botón empezar partida
		jbStart = new JButton();
		jbStart.setIcon(new ImageIcon("src/resources/botones/start.png"));
//		jbStart.setPressedIcon(new ImageIcon("src/resources/botones/start2.png"));
		jbStart.setBounds(8, 608, 448, 88);
		jbStart.setBorderPainted(false); 
		jbStart.setContentAreaFilled(false);
		add(jbStart);
		jbStart.addActionListener(this);
		
		
		//Botón añadir equipo
		
		jbAdd = new JButton();
		jbAdd.setIcon(new ImageIcon("src/resources/botones/add.png"));
		jbAdd.setPressedIcon(new ImageIcon("src/resources/botones/add2.png"));
		jbAdd.setBounds(8, 512, 448, 88);
		jbAdd.setBorderPainted(false); 
		jbAdd.setContentAreaFilled(false);
		add(jbAdd);
		jbAdd.addActionListener(this);
		
		jlFondoFuego = new JLabel();
		jlFondoFuego.setBounds(0,0,1024,768);
		jlFondoFuego.setIcon(new ImageIcon("src/resources/fondos/FondosPokedex/tipofuego.png"));
		add(jlFondoFuego);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(jbAdd)) {
			JFrame evento1 = (JFrame)SwingUtilities.getWindowAncestor(this);
			evento1.remove(this);
			evento1.getContentPane().add(new NombreEquipo());
			evento1.setVisible(true);
			NombreEquipo.bgmNombre.close();
		}
		
	}

}
