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
        threadsAnim.add(new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                while (Main.AnimR) {
                    if (!Move.iTemp) {
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
                    }
                    else{
                        Main.jlHero.setIcon(new ImageIcon("Sprites/hero_1_step_dmg.png"));
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        Main.jlHero.setIcon(new ImageIcon("Sprites/hero_2_step_dmg.png"));
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        Main.jlHero.setIcon(new ImageIcon("Sprites/hero1.png"));
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    } Main.AnimR = false;

                }
                while (Main.AnimU) {
                    if (!Move.iTemp) {
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
                    }else {
                        Main.jlHero.setIcon(new ImageIcon("Sprites/hero_1_step_UP_dmg.png"));
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        Main.jlHero.setIcon(new ImageIcon("Sprites/hero_2_step_UP_dmg.png"));
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        Main.jlHero.setIcon(new ImageIcon("Sprites/hero1.png"));
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    Main.AnimU = false;
                }

            }
        }));
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
                    Main.Dmg.setForeground(new Color(255, 255, 0, i));
                    Main.mainWindow.repaint();
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
}
