import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Animation {
    static int i = 255;
    static Vector<Thread> threadsAnim = new Vector<>(2);


    public int getI() {
        return i;
    }


    public void setI(int i) {
        Animation.i = i;
    }


    private void HeroAnimate() {
        threadsAnim.add(new Thread() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    while (Main.AnimR) {
                        Main.jlHero.setIcon(new ImageIcon("Sprites/hero_1_step.png"));
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        Main.jlHero.setIcon(new ImageIcon("Sprites/hero_2_step.png"));
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        Main.jlHero.setIcon(new ImageIcon("Sprites/hero.png"));
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        Main.AnimR = false;
                    }
                    while (Main.AnimU) {
                        Main.jlHero.setIcon(new ImageIcon("Sprites/hero_1_step_UP.png"));
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        Main.jlHero.setIcon(new ImageIcon("Sprites/hero_2_step_UP.png"));
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        Main.jlHero.setIcon(new ImageIcon("Sprites/hero.png"));
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        Main.AnimU = false;
                    }
                    Main.jlHero.setIcon(new ImageIcon("Sprites/hero.png"));
                }
            }

        });
        if(!threadsAnim.get(0).isAlive()){
            threadsAnim.get(0).start();
        }
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            System.out.println("AnimationHero Stop");
        }

    }

    private void DmgAnimate() {
        threadsAnim.add(new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    System.out.println("AnimationDMG Stop");
                    return;
                }
                i = getI();
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (; i > 0; i--) {
                    Shoot.Dmg.setForeground(new Color(255, 255, 0, i));
                    setI(i);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }));
        if(!threadsAnim.get(1).isAlive()){
            threadsAnim.get(1).start();
        }


    }

    public void StartAnimate() {
        HeroAnimate();
        DmgAnimate();
    }

    public void StopAnimate() {
        threadsAnim.get(0).interrupt();
        threadsAnim.get(1).interrupt();
    }


}
