public class Move {
    
    static int NearestEnemy;
    double moveStep = 1;
    

    public void moveEnemies() {
        Thread threadMoveEnemy = new Thread(()->{
            while (true) {
                if (!Main.Game) Thread.currentThread().interrupt();
                double a = 100000;
                int b = 0;
                for (int i = 0; i < Main.vecEnemy.size(); i++) {

                    if(!Main.vecEnemy.isEmpty()) {
                        int KatA_H_E = Math.abs(Main.jlHero.getX() - Math.abs((Main.vecEnemy.get(i)).getX()));
                        int KatB_H_E = Math.abs(Main.jlHero.getY() - Math.abs((Main.vecEnemy.get(i)).getY()));
                        double tan = (double) KatB_H_E / KatA_H_E;
                        double Rad = Math.atan(tan);
                        double gipot = Math.sqrt(Math.pow(KatA_H_E, 2) + Math.pow(KatB_H_E, 2));
                        double MinGipot = Main.jlHero.getWidth();

                        if (gipot < a) {
                            a = gipot;
                            b = i;
                        }
                        if (gipot < MinGipot) {
                            Main.mainWindow.Lose();
                        }
                        if (Main.jlHero.getX() <= (Main.vecEnemy.get(i)).getX() && Main.jlHero.getY() <= (Main.vecEnemy.get(i)).getY() && gipot > 0) {
                            gipot -= moveStep;
                            int NewKatA_H_E = (int) (Math.cos(Rad) * gipot);
                            int NewKatB_H_E = (int) (Math.sin(Rad) * gipot);
                            (Main.vecEnemy.get(i)).setLocation(Main.jlHero.getX() + NewKatA_H_E, Main.jlHero.getY() + NewKatB_H_E);
                        }

                        if (Main.jlHero.getX() <= (Main.vecEnemy.get(i)).getX() && Main.jlHero.getY() > (Main.vecEnemy.get(i)).getY() && gipot > 0) {
                            gipot -= moveStep;
                            int NewKatA_H_E = (int) (Math.cos(Rad) * gipot);
                            int NewKatB_H_E = (int) (Math.sin(Rad) * gipot);
                            (Main.vecEnemy.get(i)).setLocation(Main.jlHero.getX() + NewKatA_H_E, Main.jlHero.getY() - NewKatB_H_E);
                        }


                        if (Main.jlHero.getX() > (Main.vecEnemy.get(i)).getX() && Main.jlHero.getY() <= (Main.vecEnemy.get(i)).getY() && gipot > 0) {
                            gipot -= moveStep;
                            int NewKatA_H_E = (int) (Math.cos(Rad) * gipot);
                            int NewKatB_H_E = (int) (Math.sin(Rad) * gipot);
                            (Main.vecEnemy.get(i)).setLocation(Main.jlHero.getX() - NewKatA_H_E, Main.jlHero.getY() + NewKatB_H_E);
                        }

                        if (Main.jlHero.getX() > (Main.vecEnemy.get(i)).getX() && Main.jlHero.getY() > (Main.vecEnemy.get(i)).getY() && gipot > 0) {
                            gipot -= moveStep;
                            int NewKatA_H_E = (int) (Math.cos(Rad) * gipot);
                            int NewKatB_H_E = (int) (Math.sin(Rad) * gipot);
                            (Main.vecEnemy.get(i)).setLocation(Main.jlHero.getX() - NewKatA_H_E, Main.jlHero.getY() - NewKatB_H_E);
                        }
                    }
                }
                NearestEnemy = b;

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    for (int i = 0; i < Main.vecEnemy.size(); i++) {
                        Main.vecEnemy.get(i).setVisible(false);
                    }
                    Main.vecEnemy.clear();
                }
            }
        });
        threadMoveEnemy.start();
    }

}
