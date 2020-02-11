import java.awt.*;
import javax.swing.*;

// Pour les details sur les fonctions de dessin, voir :
// http://docs.oracle.com/javase/1.5.0/docs/api/java/awt/Graphics.html
// voir aussi : http://docs.oracle.com/javase/tutorial/2d/index.html
/**
 * Classe representant un objet mobile dans un JPanel
 * @author Jean-Baptiste Mouret <mouret@isir.upmc.fr>
 *
 */
class Move extends JPanel 
{
  private int x;
  private int prev_x;
  private int y;
  private int prev_y;
  public Move()
  {
    x = 0;
    y = 0;
    setBackground(Color.white);
    setOpaque(true);
  }

  /**
   * Deplace l'objet a la position indiquee
   * @param x Nouvelle coordonnee x
   * @param y Nouvelle coordonnee y
   */
  public void moveRobot(int x, int y)
  {
    this.x = x;
    this.y = y;
  }

  /**
   * Fonction appelee automatiquement quand le JPanel est (re)dessine a l'ecran
   */
  public void paint(Graphics g) {
      super.paint(g); // Dessine le contenu d'un panel
    // on recupere la zone de dessin
    Graphics2D g2 = (Graphics2D) g;
    // on efface une zone un peu plus grande que le cercle
    g2.setColor(Color.white);
    g2.fillRect(prev_x, prev_y , 40, 40);
    // on dessin un disque rouge
    g2.setColor(Color.red);
    g2.fillOval(x, y, 40, 40);
    // on rend la main
    g2.dispose();
    // on retient x,y pour pouvoir effacer au prochain appel
    prev_x = x;
    prev_y = y;
  }

  // Exemple simple
  public static void main (String [] args) 
  {
    JFrame ma_fenetre = new JFrame("Cercle rouge"); // Nouvelle fenetre
    Move m = new Move(); // Nouvel objet Move
    m.setPreferredSize(new Dimension(400, 400)); // Taille de la fenetre
    ma_fenetre.setContentPane(m); // Le contenu est l'objet Move
    ma_fenetre.pack();
    ma_fenetre.setVisible(true);
    int x = 0;
    boolean sens = true;

    while (true)
      {
	m.moveRobot(x, 0); // On deplace le robot a la coordonnee (x,0)
	if (sens)
	  x += 1; // Augmenter la vitesse
	else
	  x -= 1; // Diminuer la vitesse
	if (x == 0 || x == 100)
	  sens = !sens; // Changement de sens quand la coordonnee x atteint 0 ou 100
	// attend 0.01 sec
	try  { Thread.sleep(10); }
	catch (Exception e) {}
	// Redessine la fenetre (appelle automatiquement entre autres m.paint())
	m.repaint();
      }
  }
}
