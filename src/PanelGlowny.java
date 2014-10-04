
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class PanelGlowny {

    private int Punkty;
    private Color Kolor;
    private int Czas;
    private final int width, height;
    private int temp;

    public PanelGlowny(int width, int height) {
        Punkty = 0;
        Czas = 60;
        Kolor = new Color(0);
        this.width = width;
        this.height = height / 10;
        temp = 0;
    }

    public void setKolor(Color Kolor) {
        this.Kolor = Kolor;
    }

    public void addPunkt() {
        Punkty++;
    }

    public void removePunkt() {
        Punkty--;
    }

    public void czasplus() {
        Czas += 1;
    }

    public void czasminus() {
        Czas -= 1;
    }

    public void update(long time) {
        temp += time;
        if (temp >= 1000) {
            temp -= 1000;
            Czas -= 1;
        }
    }

    public int getCzas() {
        return Czas;
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, width, height);
        g2.setColor(Color.BLACK);
        Font f = g2.getFont();
        g2.setFont(new Font(f.getName(), Font.BOLD, 50));
        FontMetrics fm = g2.getFontMetrics();
        //int text_height = fm.getAscent()+fm.getDescent()+fm.getLeading();
        int polozenie_textu = (height - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString("Wynik: " + Punkty, 0, polozenie_textu);

        String text = "Kolor:";

        g2.drawString(text, width - height - fm.stringWidth(text), polozenie_textu);

        String text2 = "Czas: " + Czas + "     ";
        g2.drawString(text2, width / 2, polozenie_textu);
        g2.setColor(Kolor);
        g2.fillOval(width - height, 0, height, height);

    }
}
