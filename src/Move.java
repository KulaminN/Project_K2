import javax.swing.*;

public class Move {
    
    static int NearestEnemy;
    double moveStep = 1;
    static boolean Temp = false;
    static boolean iTemp = false;

    public void HeroHit(){
        Thread thr = new Thread(()->{
                iTemp = true;
                Main.jlHero.setIcon(new ImageIcon("Sprites/hero1.png"));
                try {
                    Thread.sleep(70);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Main.HeroHealths.setValue((int) (Main.HeroHealths.getValue() - Main.DamageSimpleEnemy));
                Main.HeroHealths.setString((int) (Main.HeroHealths.getPercentComplete() * 100) + "% / 100%");
                Main.jlHero.setIcon(new ImageIcon("Sprites/hero.png"));
                if(Main.HeroHealths.getPercentComplete()==0){
                    Main.lose = true;
                }
                iTemp = false;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Temp = false;
        });
        if (!thr.isAlive()) {
            thr.start();
        }
    }

    public void moveEnemies() {


        Thread threadMoveEnemy = new Thread(()->{
            while (true) {
                if (!Main.Game) Thread.currentThread().interrupt();
                double a = 100000;
                int b = 0;
                for (int i = 0; i < Main.vecSimpleEnemy.size(); i++) {

                    int KatA_H_E = Math.abs(Main.jlHero.getX() - Math.abs((Main.vecSimpleEnemy.get(i)).getX()));
                    int KatB_H_E = Math.abs(Main.jlHero.getY() - Math.abs((Main.vecSimpleEnemy.get(i)).getY()));
                    double tan = (double) KatB_H_E / KatA_H_E;
                    double Rad = Math.atan(tan);
                    double gipot = Math.sqrt(Math.pow(KatA_H_E, 2) + Math.pow(KatB_H_E, 2));
                    double MinGipot = Main.jlHero.getWidth();

                    if (gipot < a) {
                        a = gipot;
                        b = i;
                    }
                    if (gipot < MinGipot && !Temp) {
                        HeroHit();
                        Temp = true;
                    }

                    if (Main.jlHero.getX() <= (Main.vecSimpleEnemy.get(i)).getX() && Main.jlHero.getY() <= (Main.vecSimpleEnemy.get(i)).getY() && gipot > 0) {
                        gipot -= moveStep;
                        int NewKatA_H_E = (int) (Math.cos(Rad) * gipot);
                        int NewKatB_H_E = (int) (Math.sin(Rad) * gipot);
                        (Main.vecSimpleEnemy.get(i)).setLocation(Main.jlHero.getX() + NewKatA_H_E, Main.jlHero.getY() + NewKatB_H_E);
                    }

                    if (Main.jlHero.getX() <= (Main.vecSimpleEnemy.get(i)).getX() && Main.jlHero.getY() > (Main.vecSimpleEnemy.get(i)).getY() && gipot > 0) {
                        gipot -= moveStep;
                        int NewKatA_H_E = (int) (Math.cos(Rad) * gipot);
                        int NewKatB_H_E = (int) (Math.sin(Rad) * gipot);
                        (Main.vecSimpleEnemy.get(i)).setLocation(Main.jlHero.getX() + NewKatA_H_E, Main.jlHero.getY() - NewKatB_H_E);
                    }


                    if (Main.jlHero.getX() > (Main.vecSimpleEnemy.get(i)).getX() && Main.jlHero.getY() <= (Main.vecSimpleEnemy.get(i)).getY() && gipot > 0) {
                        gipot -= moveStep;
                        int NewKatA_H_E = (int) (Math.cos(Rad) * gipot);
                        int NewKatB_H_E = (int) (Math.sin(Rad) * gipot);
                        (Main.vecSimpleEnemy.get(i)).setLocation(Main.jlHero.getX() - NewKatA_H_E, Main.jlHero.getY() + NewKatB_H_E);
                    }

                    if (Main.jlHero.getX() > (Main.vecSimpleEnemy.get(i)).getX() && Main.jlHero.getY() > (Main.vecSimpleEnemy.get(i)).getY() && gipot > 0) {
                        gipot -= moveStep;
                        int NewKatA_H_E = (int) (Math.cos(Rad) * gipot);
                        int NewKatB_H_E = (int) (Math.sin(Rad) * gipot);
                        (Main.vecSimpleEnemy.get(i)).setLocation(Main.jlHero.getX() - NewKatA_H_E, Main.jlHero.getY() - NewKatB_H_E);
                    }
                }
                NearestEnemy = b;

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    for (int i = 0; i < Main.vecSimpleEnemy.size(); i++) {
                        Main.vecSimpleEnemy.get(i).setVisible(false);
                    }
                    Main.vecSimpleEnemy.clear();
                }
            }
        });
        threadMoveEnemy.start();
    }

}
