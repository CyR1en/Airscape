package com.cyr1en.airscape.gamestate;

import com.cyr1en.airscape.Launcher;
import com.cyr1en.airscape.handler.StateManager;
import com.cyr1en.cgdl.Entity.BackGround;
import com.cyr1en.cgdl.Entity.Button.GameButton;
import com.cyr1en.cgdl.Entity.Particle;
import com.cyr1en.cgdl.GameState.GameState;
import com.cyr1en.cgdl.GameState.GameStateManager;
import com.cyr1en.cgdl.GameState.Transition;
import com.cyr1en.cgdl.Handlers.Mouse;
import com.cyr1en.cgdl.Handlers.ParticleFactory;
import com.cyr1en.cgdl.Main.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class CreditState extends GameState {


    private int fadeInTimer;
    private int fadeInDelay;
    private int fadeOutTimer;
    private int fadeOutDelay;
    private int alpha;
    private int nextState;

    private BackGround backGround;
    private Transition transition;
    private ArrayList<Particle> particles;
    private GameButton<CreditState> backButton;

    private long startTime;

    public CreditState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    public void init() {
        backGround = new BackGround("/bg.jpg");
        backGround.setVector(-.3, 0);
        transition = new Transition(gsm, 0, 30, -1, 60);
        startTime = System.nanoTime();
        particles = new ArrayList<>();
        ParticleFactory.init(particles);
        String s = " B A C K ";
        backButton = new GameButton<>((int) (GamePanel.WIDTH * 0.485), (int) (GamePanel.HEIGHT * 0.95));
        backButton.setText(s, new Font("Fira Code", Font.BOLD, 20));
        backButton.setColor(GameButton.DEFAULT_COLOR);
        backButton.setObjType(this);
        backButton.setOnClick(state -> {
            state.transition.nextState(StateManager.MENU_STATE);
        });
    }

    public void update() {
        backGround.update();
        handleInput();

        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if (elapsed > 200) {
            Random rand = new Random();
            int randX = rand.nextInt(GamePanel.WIDTH);
            int randY = rand.nextInt(GamePanel.HEIGHT);
            int randR = rand.nextInt(255);
            int randG = rand.nextInt(255);
            int randB = rand.nextInt(255);
            Color c = new Color(randR, randG, randB);
            ParticleFactory.createExplosion(randX, randY, c);
            ParticleFactory.createSmallWave(randX, randY, 5, c);
            startTime = System.nanoTime();
        }
        for (int i = 0; i < particles.size(); i++) {
            if (particles.get(i).updateBool()) {
                particles.remove(i);
                i--;
            }
        }

        backButton.update();

        transition.update();
        if (Launcher.frame.isShowTitleInfo())
            Launcher.frame.updateTitleBar();
        else
            Launcher.frame.clearTitleBar();
    }

    public void draw(Graphics2D g) {

        backGround.draw(g, getInterpolation());

        g.setColor(new Color(30, 30, 30, 230));
        g.fillRect(GamePanel.WIDTH / 8, GamePanel.HEIGHT / 8, GamePanel.WIDTH * 3 / 4, GamePanel.HEIGHT * 3 / 4);

        for (Particle particle : particles) {
            particle.draw(g, getInterpolation());
        }

        g.setColor(new Color(100, 100, 100, 210));
        g.setStroke(new BasicStroke(5));
        g.drawRect(GamePanel.WIDTH / 8, GamePanel.HEIGHT / 8, GamePanel.WIDTH * 3 / 4, GamePanel.HEIGHT * 3 / 4);
        g.setStroke(new BasicStroke(1));

        g.setColor(new Color(40, 40, 40, 150));
        g.setFont(new Font("Fira Code", Font.BOLD, 60));
        String s = "C R E D I T S";
        int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, GamePanel.WIDTH / 2 - (length / 2), 50);

        g.setFont(new Font("Tahoma", Font.PLAIN, 20));
        g.setColor(new Color(102, 51, 0));

        s = "Ethan B. (CyR1en) : Programmer / CGDL";
        length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, GamePanel.WIDTH / 2 - (length / 2), (int) (GamePanel.HEIGHT * 0.2));

        s = "Darielle C. : Programmer / Concept Originator";
        length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, GamePanel.WIDTH / 2 - (length / 2), (int) (GamePanel.HEIGHT * 0.2) + 50);

        s = "Joy F. : Programmer / Concept Editor";
        length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, GamePanel.WIDTH / 2 - (length / 2), (int) (GamePanel.HEIGHT * 0.2) + 100);

        backButton.draw(g, getInterpolation());

        transition.draw(g, getInterpolation());
    }

    public void handleInput() {
        if (Mouse.isPressed()) {
            Random rand = new Random();
            ParticleFactory.createExplosion(Mouse.x, Mouse.y, new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            ParticleFactory.createSmallWave(Mouse.x, Mouse.y, 8, new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
        }
    }

    @Override
    public void setInterpolation(float v) {
        interpolation = v;
    }

    @Override
    public float getInterpolation() {
        return interpolation;
    }
}
