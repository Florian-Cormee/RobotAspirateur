/**
 * 
 */
package fr.rob4.simulation.geometrie;

/**
 * Cette classe représente un segment de droite délimité par 2 Point2D. Il peut
 * également être manipulé à partir de son centre.
 * 
 * @author Florentin BEROUJON & Florian CORMEE
 * @version 0.0.1
 * @see Point2D
 * @see Vecteur2D
 * @see Forme
 * @see Cercle
 * @see ArcDeCercle
 * @see Rectangle
 * @see Polygone
 */
public class Segment extends Forme {

	// Attributs
	protected Point2D a;
	protected Point2D b;

	/**
	 * 
	 * @param a
	 * @param b
	 */
	public Segment(Point2D a, Point2D b) {
		// double x = (a.getPositionAbsolue().x + b.getPositionAbsolue().x) / 2;
		// double y = (a.getPositionAbsolue().y + b.getPositionAbsolue().y) / 2;
		// centre = new Point2D(new Vecteur2D(x, y));
		super(new Point2D(new Vecteur2D((a.getPositionAbsolue().x + b.getPositionAbsolue().x) / 2,
				(a.getPositionAbsolue().y + b.getPositionAbsolue().y) / 2)));
		this.a = a;
		this.b = b;
	}

	/*
	 * /**
	 * 
	 * @param x
	 * 
	 * @param y / public Segment(double x, double y) { super(x, y); // TODO
	 * Auto-generated constructor stub }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.rob4.simulation.geometrie.Forme#rotation(double,
	 * fr.rob4.simulation.geometrie.Point2D)
	 */
	@Override
	public Segment rotation(double alpha, Point2D p) {
		return new Segment(a.rotation(alpha, p), b.rotation(alpha, p));
	}

	public Point2D intersecte(Segment seg) {
		Vecteur2D p = a.getPositionAbsolue();
		Vecteur2D q = seg.a.getPositionAbsolue();
		Vecteur2D r = a.getPositionRelative(b);
		Vecteur2D s = seg.a.getPositionRelative(seg.b);

		Vecteur2D tempV = q.soustraction(p);
		double tempD = r.produitCroix(s);
		if (tempD == 0) { // les segments sont soi parallèles soi en contacte mais ne se coupent pas.
			return null;
		} else {
			double t = tempV.produitCroix(s) / tempD;
			double u = tempV.produitCroix(r) / tempD;
			if ((t >= 0) && (t <= 1) && (u >= 0) && (u <= 1)) { // Les 2 segments se croisent
				return new Point2D(p.addition(r.produit(t)));
			} else {
				return null;
			}
		}
	}

	/*
	 * public Point2D instersecte(Cercle c) { Vecteur2D depart, arrivee; if(
	 * a.getPositionRelative(c.centre).norme() >
	 * b.getPositionRelative(c.centre).norme()){ depart = a.getPositionAbsolue();
	 * arrivee = a.getPositionRelative(b); } else { depart = b.getPositionAbsolue();
	 * arrivee = b.getPositionRelative(a); } double p; for( int i=0 ; i<100 ;i++) {
	 * p = i/100; Vecteur2D test = depart.addition(arrivee).produit(p); if(
	 * test.norme() < c.diametre) { return new Point2D(test); } } return null; }
	 */

	public Point2D instersecte(Cercle c) {
		double dx = a.getPositionAbsolue().x - b.getPositionAbsolue().x;
		double dy = a.getPositionAbsolue().y - b.getPositionAbsolue().y;
		double xc = c.centre.getPositionAbsolue().x;
		double yc = c.centre.getPositionAbsolue().y;

		double alpha = Math.pow(dx, 2) + Math.pow(dy, 2);
		double beta = 2 * (dx * (a.getPositionAbsolue().x - xc) + dy * (a.getPositionAbsolue().y - yc));
		double gamma = Math.pow(a.getPositionAbsolue().x - xc, 2) + Math.pow(a.getPositionAbsolue().y - yc, 2)
				+ Math.pow(c.diametre, 2);
		double delta = Math.pow(beta, 2) - 4 * alpha * gamma;
		// Conjecture
		if (delta == 0) { // le cercle et est tangent au cercle
			return null;
		} else if (delta > 0 && alpha != 0) {
			double t1 = (beta - Math.sqrt(delta)) / (2 * alpha);
			double t2 = (beta + Math.sqrt(delta)) / (2 * alpha);
			if (t1 >= 0 && t1 <= 1) {
				return new Point2D(a.getPositionAbsolue().addition(a.getPositionRelative(b).produit(t1)));
			}
			if (t2 >= 0 && t2 <= 1) {
				return new Point2D(a.getPositionAbsolue().addition(a.getPositionRelative(b).produit(t2)));
			}
			return null;
		}
		return null;
	}

	public double norme() {
		return a.getPositionRelative(b).norme();
	}

	@Override
	public Rectangle getDimension() {
		Vecteur2D pa = a.getPositionAbsolue();
		Vecteur2D pb = b.getPositionAbsolue();
		double lar = Math.max(pa.x, pb.x) - Math.min(pa.x, pb.x);
		double h = Math.max(pa.y, pb.y) - Math.min(pa.y, pb.y);
		return new Rectangle(centre, lar, h);
	}
}
