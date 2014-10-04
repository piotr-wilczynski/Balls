
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.Random;

public class Kulka {

    private Random random;
    private Color Kolor;
    private int Promien;//1-10
    private int Predkosc;//1-7
    private int x = 0, y = 0;
    final int width, height;
    private int temp = 0;
    private boolean remove = false;
    private boolean kliknieta = false;

    public Kulka(Color kolor, double szansa_na_kolor, int GameWidth, int GameHeight) {
        width = GameWidth;
        height = GameHeight;
        losuj_promien();
        losuj_predkosc();
        Kolor = losuj_kolor(kolor, szansa_na_kolor);
        this.x = losuj_polozenie();
        this.y = 0 - Promien * 2;
    }

    private int losuj_polozenie() {
        random = new Random();
        int kulka = 50 * 2 + 50;
        int temp_width = width - (kulka);
        return random.nextInt(temp_width) + kulka / 2;

    }

    private void losuj_promien() {
        random = new Random();
        Promien = random.nextInt(10) + 1;
        Promien *= 5;
        Promien += 50;
    }

    private void losuj_predkosc() {
        random = new Random();
        Predkosc = random.nextInt(10) + 1;
    }

    public static Color losuj_kolor(Color kolor, double szansa_na_kolor) {
        Random random = new Random();
        if (szansa_na_kolor > 0 && szansa_na_kolor <= 1) {
            if (random.nextInt(1001) <= szansa_na_kolor * 1000) {
                return kolor;
            }
        }
        return new Color(random.nextInt(4) * 64, random.nextInt(4) * 64, random.nextInt(4) * 64);
    }

    public Color getKolor() {
        return Kolor;
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Kolor);
        g2.fillOval(x - Promien, y - Promien, (Promien * 2), (Promien * 2));
    }

    public void update(long time) {
        temp += time;
        if (temp >= 10) {
            y += Predkosc;
            temp -= 10;
        }
    }

    public boolean Click(Point p, Color main_color) {
        if (Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2) <= Math.pow(Promien, 2) && Kolor.getRGB() == main_color.getRGB()) {
            remove = true;
            kliknieta = true;
            return true;
        }
        return false;
    }

    public void isOutofScrean() {
        if (y - Promien > height) {
            remove = true;
        }
    }

    public boolean remove() {
        isOutofScrean();
        if (remove) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isKliknieta() {
        return kliknieta;
    }

}
