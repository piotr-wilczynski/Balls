
import java.awt.Dimension;
public class Okno extends javax.swing.JFrame {

    private Gra g;

    public Okno() {
        initComponents();
        setVisible(true);
        g = new Gra();
        this.add(g);
    }

    private void initComponents() {
        setTitle("Test refleksu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1200, 800));
        pack();
    }

}
