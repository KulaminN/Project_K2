import javax.swing.*;
import java.awt.*;

public class Shoot {

    static double AtSpeed = 0.5;
    static int Damage = 30;
    int moveStep = 3;
    double BulletSpeed = 0.5;
    Animation animation = new Animation();

    Shoot(){
        Main.timerShoot = new Timer((int) (1000 * AtSpeed), e -> Main.Shooting = true);
        Main.timerShoot.start();
    }


    private void moveBullet() {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (!Main.Game) Thread.currentThread().interrupt();
                if (Main.Shooting && !Main.vecSimpleEnemy.isEmpty()) {
                    int A = Math.abs(Main.vecSimpleEnemy.get(Move.NearestEnemy).getX())+Main.vecSimpleEnemy.get(Move.NearestEnemy).getWidth()/2;
                    int B = Math.abs(Main.vecSimpleEnemy.get(Move.NearestEnemy).getY())+Main.vecSimpleEnemy.get(Move.NearestEnemy).getHeight()/2;
                    int KatA = Math.abs(A - Math.abs(Main.Bullet.getX()));
                    int KatB = Math.abs(B - Math.abs(Main.Bullet.getY()));
                    double tan = (double) KatB / KatA;
                    double Rad = Math.atan(tan);
                    double gipot = Math.sqrt(Math.pow(KatA, 2) + Math.pow(KatB, 2));

                    if (A <= Main.Bullet.getX() && B <= Main.Bullet.getY() && gipot > 0) {
                        gipot -= moveStep;
                        int NewKatA = (int) (Math.cos(Rad) * gipot);
                        int NewKatB = (int) (Math.sin(Rad) * gipot);
                        Main.Bullet.setLocation(A + NewKatA, B + NewKatB);
                    }

                    if (A <= Main.Bullet.getX() && B > Main.Bullet.getY() && gipot > 0) {
                        gipot -= moveStep;
                        int NewKatA = (int) (Math.cos(Rad) * gipot);
                        int NewKatB = (int) (Math.sin(Rad) * gipot);
                        Main.Bullet.setLocation(A + NewKatA, B - NewKatB);
                    }

                    if (A > Main.Bullet.getX() && B <= Main.Bullet.getY() && gipot > 0) {
                        gipot -= moveStep;
                        int NewKatA = (int) (Math.cos(Rad) * gipot);
                        int NewKatB = (int) (Math.sin(Rad) * gipot);
                        Main.Bullet.setLocation(A - NewKatA, B + NewKatB);
                    }

                    if (A > Main.Bullet.getX() && B > Main.Bullet.getY() && gipot > 0) {
                        gipot -= moveStep;
                        int NewKatA = (int) (Math.cos(Rad) * gipot);
                        int NewKatB = (int) (Math.sin(Rad) * gipot);
                        Main.Bullet.setLocation(A - NewKatA, B - NewKatB);
                    }
                    try {
                        Thread.sleep(0);
                    } catch (InterruptedException e) {
                        System.out.println("BulletMove Stop");
                    }

                    try {
                        Thread.sleep(Math.round(BulletSpeed * 10));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
    }

    public void BulletHit() {
        Main.Bullet.setLocation(Main.jlHero.getX() + Main.jlHero.getWidth() - 30, Main.jlHero.getY() + 25);
        moveBullet();
        Thread thread1 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (!Main.Game) Thread.currentThread().interrupt();
                if (Main.Shooting && !Main.vecSimpleEnemy.isEmpty()) {
                    int A = Math.abs(Main.vecSimpleEnemy.get(Move.NearestEnemy).getX())+Main.vecSimpleEnemy.get(Move.NearestEnemy).getWidth()/2;
                    int B = Math.abs(Main.vecSimpleEnemy.get(Move.NearestEnemy).getY())+Main.vecSimpleEnemy.get(Move.NearestEnemy).getHeight()/2;
                    Main.Bullet.setVisible(true);
                    int KatA = Math.abs(A - Math.abs(Main.Bullet.getX()));
                    int KatB = Math.abs(B - Math.abs(Main.Bullet.getY()));
                    double gipot = Math.sqrt(Math.pow(KatA, 2) + Math.pow(KatB, 2));
                    double MinGipot = (Main.vecSimpleEnemy.get(Move.NearestEnemy)).getWidth();

                    if (gipot < MinGipot) {

                        Main.Bullet.setLocation(Main.jlHero.getX() + Main.jlHero.getWidth() - 30, Main.jlHero.getY() + 25);
                        Main.Bullet.setVisible(false);
                        Main.Shooting = false;

                        Main.Healths.get(Move.NearestEnemy).setValue(Main.Healths.get(Move.NearestEnemy).getValue() - Damage);
                        Main.Healths.get(Move.NearestEnemy).setString((int) (Main.Healths.get(Move.NearestEnemy).getPercentComplete() * 100) + "% / 100%");

                        Main.Dmg.setFont(new Font("Arial", Font.ITALIC, 30));
                        Main.Dmg.setForeground(new Color(255, 255, 0, 255));
                        animation.setI(255);
                        Main.Dmg.setText(String.valueOf(Damage));
                        Main.Dmg.setBounds(A, B - 100, 40, 40);


                        if (Main.Healths.get(Move.NearestEnemy).getPercentComplete() == 0.0) {
                            int randX = (int) (Main.jlHero.getX() + (Math.random() * 1001 - 500));
                            while (randX >= Main.mainWindow.getWidth() || randX <= 0 || Math.abs(randX - Main.jlHero.getX()) < 300) {
                                randX = (int) (Main.jlHero.getX() + (Math.random() * 1001 - 500));
                            }
                            int randY = (int) (Main.jlHero.getX() + (Math.random() * 1001 - 500));
                            while (randY >= Main.mainWindow.getHeight() || randY <= 0 || Math.abs(randY - Main.jlHero.getY()) < 300) {
                                randY = (int) (Main.jlHero.getY() + (Math.random() * 1001 - 500));
                            }
                            (Main.vecSimpleEnemy.get(Move.NearestEnemy)).getLocation();
                            (Main.vecSimpleEnemy.get(Move.NearestEnemy)).setLocation(randX, randY);
                            Main.Healths.get(Move.NearestEnemy).setValue(Main.Max_Health);
                            Main.Healths.get(Move.NearestEnemy).setString((int) (Main.Healths.get(Move.NearestEnemy).getPercentComplete() * 100) + "% / 100%");
                            Main.Dmg.setForeground(new Color(255, 255, 0, 255));
                        }
                        Main.Bullet.setLocation(Main.jlHero.getX() + Main.jlHero.getWidth() - 30, Main.jlHero.getY() + 25);
                        Main.Bullet.setVisible(false);
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        Main.Bullet.setVisible(false);
                    }
                }

            }
        });

        thread1.start();
    }

}
