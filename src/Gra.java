
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

public class Gra extends JPanel implements Runnable {

    private BufferedImage dbimage;
    private Graphics dbg;
    ArrayList<Kulka> lista = new ArrayList<Kulka>();
    private boolean aktywna = true;
    private int temp = 0;
    private double Szansa = 0.2;
    private PanelGlowny panel;
    private int zmiana = 10000;
    private Random random = new Random();
    private Color Kolor = Kulka.losuj_kolor(null, 0);

    public Gra() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (me.getButton() == MouseEvent.BUTTON1) {
                    int c = dbimage.getRGB(me.getX(), me.getY());
                    if (c == Kolor.getRGB()) {
                        for (int i = lista.size() - 1; i >= 0; i--) {
                            if (lista.get(i).Click(me.getPoint(), Kolor)) {
                                break;
                            }
                        }
                    } else if (c != getBackground().getRGB()) {

                    }
                }
            }
        });
        new Thread(this).start();
    }

    @Override
    public void run() {
        long current_time = System.currentTimeMillis();
        while (aktywna) {
            long temp = System.currentTimeMillis();
            long time = temp - current_time;
            update(time);
            repaint();
            current_time = temp;
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void update(long time) {
        temp += time;
        zmiana -= time;
        if (temp >= 100) {
            lista.add(new Kulka(Kolor, Szansa, getSize().width, getSize().height));
            temp -= 100;
        }
        for (int i = 0; i < lista.size(); i++) {
            lista.get(i).update(time);
            if (lista.get(i).remove()) {
                if (lista.get(i).isKliknieta()) {
                    panel.addPunkt();
                    panel.czasplus();
                } else {
                    if (lista.get(i).getKolor().getRGB() == Kolor.getRGB()) {
                        panel.czasminus();
                    }
                }
                lista.remove(i);
                i--;
            }
        }
        if (panel != null) {
            panel.setKolor(Kolor);
        }
        if (zmiana <= 0) {

            Toolkit.getDefaultToolkit().beep();
            Kolor = Kulka.losuj_kolor(null, 0);
            zmiana = (random.nextInt(5) + 10) * 1000;
        }

        if (panel != null) {
            panel.update(time);
            if (panel.getCzas() <= 0) {
                aktywna = false;
            }
        }

    }

    @Override
    public void update(Graphics grphcs) {
        paint(dbg);
        dbg.dispose();
        grphcs.dispose();
    }

    @Override
    public void paint(Graphics g) {
        if (dbimage != null) {
            dbg.setColor(Color.white);
            dbg.fillRect(0, 0, dbimage.getWidth(), dbimage.getHeight());
            for (int i = 0; i < lista.size(); i++) {
                lista.get(i).paint(dbg);
            }
            panel.paint(dbg);
            g.drawImage(dbimage, 0, 0, this);
        } else {
            Dimension s = getSize();
            if (s.width > 0 && s.height > 0) {
                dbimage = new BufferedImage(s.width, s.height, BufferedImage.TYPE_INT_ARGB);
            }
            panel = new PanelGlowny(getWidth(), getHeight());
            dbg = dbimage.getGraphics();
        }
    }

}
