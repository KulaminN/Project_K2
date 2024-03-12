import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class Main extends JFrame {
    Animation animationobj = new Animation();
    public static Main mainWindow;
    static int countEnemy = 10;
    static boolean Game = true;
    static boolean jlHeroIsAlive = true;
    static int time = 30;
    static int Max_Health = 100;
    static Timer timer, timerShoot;
    static boolean Shooting = false;
    static Vector<JProgressBar> Healths= new Vector<>(countEnemy);
    static Vector<JLabel> vecEnemy = new Vector<>(countEnemy);
    static JLabel jlHero, GameOver, TimeScore, Bullet, Win, MoonShard, Background;
    static JButton ButRestart;
    static boolean AnimR, AnimU = false;
    final int Width = 1500;
    final int Height = 900;
    private KeyListener keyList;
    private ActionListener restartList;

    Main() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(100, 50, Width, Height);
        this.setTitle("Game");
        this.setLayout(null);
        JLayeredPane layeredPane = new JLayeredPane();
        this.add(layeredPane);
        layeredPane.setBounds(0,0,Width,Height);
        layeredPane.add(Shoot.Dmg,1);


        Win = new JLabel("You Win");
        layeredPane.add(Win,1);
        GameOver = new JLabel("You Lose");
        layeredPane.add(GameOver,1);
        ButRestart = new JButton("Restart");
        layeredPane.add(ButRestart,1);

        createHero();
        createBullet();
        createEnemies(countEnemy);
        defaultPane(vecEnemy);

        createLastTimer();
        LastTimer();
        Restart();

        Background = new JLabel();
        layeredPane.add(Background,10);
        Background.setBounds(0, 0, Width, Height);
        Background.setIcon(new ImageIcon("Sprites/Background.png"));

    }
    private void Restart(){
        ButRestart.setFont(new Font("Arial",1,30));
        ButRestart.setBounds(this.getWidth()/2-100,200,200,50);
        ButRestart.setForeground(new Color(0,0,0));
        ButRestart.setVisible(false);
        ButRestart.setFocusable(false);
        ButRestart.addActionListener(e -> {
            Game = true;
            jlHero.setLocation(220, 220);
            GameOver.setVisible(false);
            Win.setVisible(false);
            time = 30;
            timer.restart();
            LastTimer();
            createEnemies(countEnemy);
            defaultPane(vecEnemy);
            Shoot shootobj = new Shoot();
            shootobj.BulletHit();
            ButRestart.setVisible(false);
            System.out.println("Done");
        });
    }
    private void createLastTimer(){
        TimeScore = new JLabel(String.valueOf(time));
        getLayeredPane().add(TimeScore,3);
        TimeScore.setFont(new Font("Arial",1,30));
        TimeScore.setForeground(new Color(0,0,0));
        TimeScore.setBounds(this.getWidth()/2-25,100,50,50);
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time--;
            }
        });
    }
    private void LastTimer(){
        TimeScore.setVisible(true);
        Thread timeThread = new Thread(){
            @Override
            public void run(){
                while(time>=0){
                    TimeScore.setText(String.valueOf(time));
                    if(time == 0){
                        Win();
                        return;
                    }
                }
            }
        };
        Main.timer.start();
        timeThread.start();
    }

    private void Win(){
        TimeScore.setVisible(false);
        Win.setFont(new Font("Arial",1,30));
        Win.setForeground(new Color(0,0,0));
        Win.setBounds(this.getWidth()/2-75,100,150,50);
        Game = false;
        timerShoot.stop();
        timer.stop();
        ButRestart.setVisible(true);
        System.out.println("Winner");
    }
    public void Lose(){
        TimeScore.setVisible(false);
        GameOver.setFont(new Font("Arial",1,30));
        GameOver.setForeground(new Color(0,0,0));
        GameOver.setBounds(this.getWidth()/2-75,100,150,50);
        Game = false;
        timerShoot.stop();
        timer.stop();
        ButRestart.setVisible(true);
        System.out.println("LOSEr");
    }

    public static void main(String[] args) {
        mainWindow = new Main();
        Move moveobj = new Move();
        Shoot shootobj = new Shoot();
        Animation Anim = new Animation();
        moveobj.moveEnemies();
        shootobj.BulletHit();
        Anim.StartAnimate();
        mainWindow.HeroMove();
    }


    private void createHero() {
        jlHero = new JLabel();
        getLayeredPane().add(jlHero,4);
        jlHero.setBounds(220, 220, 80, 80);
        jlHero.setIcon(new ImageIcon("Sprites/hero.png"));
    }


    private void createEnemies(int i) {
        for (int l = 0; l < i; l++) {
            vecEnemy.add(new JLabel());
            int randX = (int) (Math.random() * (Width - jlHero.getWidth()) + 1);
            int randY = (int) (Math.random() * (Height - jlHero.getHeight()) + 1);
            vecEnemy.get(l).setBounds(0, 0, 80, 110);

            int KatA = Math.abs(jlHero.getX() - Math.abs(randX));
            int KatB = Math.abs(jlHero.getY() - Math.abs(randY));
            double gipot = Math.sqrt(Math.pow(KatA, 2) + Math.pow(KatB, 2));


            while (randX <= 7 || randX >= Width - vecEnemy.get(l).getWidth() - 19 || randY <= 7 || randY >= Height - vecEnemy.get(l).getHeight() - 43 || gipot < 300) {
                randX = (int) (Math.random() * (Width - vecEnemy.get(l).getWidth()) + 1);
                randY = (int) (Math.random() * (Height - vecEnemy.get(l).getHeight()) + 1);
                KatA = Math.abs(jlHero.getX() - Math.abs(randX));
                KatB = Math.abs(jlHero.getY() - Math.abs(randY));
                gipot = Math.sqrt(Math.pow(KatA, 2) + Math.pow(KatB, 2));
            }

            vecEnemy.get(l).setBounds(randX, randY, 80, 110);
            vecEnemy.get(l).setIcon(new ImageIcon("Sprites/enemy.png"));

            Healths.add(new JProgressBar());
            vecEnemy.get(l).add(Healths.get(l));
            Healths.get(l).setBounds(5, 0, 70, 15);
            Healths.get(l).setForeground(Color.RED);
            Healths.get(l).setValue(Max_Health);
            Healths.get(l).setStringPainted(true);
            Healths.get(l).setString((int) Healths.get(l).getPercentComplete() * 100 + "% / 100%");
            Healths.get(l).setVisible(true);
            vecEnemy.get(l).setVisible(true);
            System.out.println("Create");
        }
    }

    private void defaultPane(Vector<JLabel> a) {
        for (int i = 0; i < a.size(); i++) {
            getLayeredPane().add(a.get(i),1);
        }
    }

    private void createBullet() {
        Bullet = new JLabel();
        getLayeredPane().add(Bullet,1);
        Bullet.setBounds(jlHero.getX() + jlHero.getWidth() - 30, jlHero.getY() + 25, 10, 10);
        Bullet.setIcon(new ImageIcon("Sprites/Bullet.png"));
        Bullet.setVisible(false);
    }

    private void HeroMove(){
        mainWindow.addKeyListener(keyList = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                    if (Game) {
                        switch (e.getKeyCode()) {
                            case 87 -> {
                                if (jlHero.getY() > 7) {
                                    AnimU = true;
                                    jlHero.setLocation(jlHero.getX(), jlHero.getY() - 8);

                                } else {
                                    jlHero.setLocation(jlHero.getX(), 0);
                                }
                            }
                            case 83 -> {
                                if (jlHero.getY() + jlHero.getHeight() + 43 < mainWindow.getHeight()) {
                                    AnimU = true;
                                    jlHero.setLocation(jlHero.getX(), jlHero.getY() + 8);
                                }

                            }
                            case 65 -> {
                                if (jlHero.getX() > 7) {
                                    AnimR = true;
                                    jlHero.setLocation(jlHero.getX() - 8, jlHero.getY());
                                } else {
                                    jlHero.setLocation(0, jlHero.getY());
                                }
                            }
                            case 68 -> {
                                if (jlHero.getX() < mainWindow.getWidth() - jlHero.getWidth() - 19) {
                                    AnimR = true;
                                    jlHero.setLocation(jlHero.getX() + 8, jlHero.getY());
                                }
                            }
                        }
                    } else {
                        try {
                            Thread.sleep(0);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
    public boolean StatusGame(){
        return Main.Game;
    }
}
