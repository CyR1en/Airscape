package com.cyr1en.airscape.gamestate;

import com.cyr1en.airscape.Launcher;
import com.cyr1en.airscape.handler.StateManager;
import com.cyr1en.cgdl.Entity.BackGround;
import com.cyr1en.cgdl.Entity.Button.GameButton;
import com.cyr1en.cgdl.Entity.Particle;
import com.cyr1en.cgdl.Entity.Title;
import com.cyr1en.cgdl.GameState.GameState;
import com.cyr1en.cgdl.GameState.GameStateManager;
import com.cyr1en.cgdl.GameState.Transition;
import com.cyr1en.cgdl.Handlers.Mouse;
import com.cyr1en.cgdl.Handlers.ParticleFactory;
import com.cyr1en.cgdl.Main.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class MenuState extends GameState {

    // entities/objects
    private BackGround bg;
    private Title title;
    private Transition transition;

    // game button variables
    private int currentChoice = 0;
    private GameButton<MenuState> playButton;
    private GameButton<MenuState> creditsButton;

    // particles
    private ArrayList<Particle> particles;


    private int nextState;

    // constructor
    public MenuState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    // initialize all the instance variables
    public void init() {
        title = new Title("A I R S C A P E", new Font("Fira Code Retina Medium", Font.BOLD, 70));
        bg = new BackGround("/bg.jpg");
        bg.setVector(-1, 0);


        playButton = new GameButton(GamePanel.WIDTH / 2, (int) (GamePanel.HEIGHT * 0.6));
        playButton.setText(" P L A Y ", new Font("Fira Code Retina Medium", Font.PLAIN, 30));
        playButton.setColor(GameButton.DEFAULT_COLOR);
        playButton.setType(GameButton.CENTER);
        playButton.setObjType(this);
        playButton.setOnClick((state) -> {
            transition.nextState(state.getStateManager().getState(StateManager.PLAYING_STATE));
        });

        creditsButton = new GameButton(GamePanel.WIDTH / 2, (int) (GamePanel.HEIGHT * 0.6) + 50);
        creditsButton.setText(" C R E D I T S ", new Font("Fira Code Retina Medium", Font.PLAIN, 30));
        creditsButton.setColor(GameButton.DEFAULT_COLOR);
        creditsButton.setObjType(this);
        creditsButton.setOnClick(state -> {
            transition.nextState(state.getStateManager().getState(StateManager.CREDIT_STATE));
        });

        particles = new ArrayList<>();
        ParticleFactory.init(particles);

        transition = new Transition(gsm, 0, 30, -1, 30);
    }

    //update all the variables that's used in this class and other components of it
    public void update() {
        handleInput(); // handle the inputs
        bg.update(); // update the background
        title.update(); // update the title

        // particles update
        for (int i = 0; i < particles.size(); i++) {
            if (particles.get(i).updateBool()) {
                particles.remove(i);
                i--;
            }
        }

        playButton.update();
        creditsButton.update();
        transition.update();
        if (Launcher.frame.isShowTitleInfo())
            Launcher.frame.updateTitleBar();
        else
            Launcher.frame.clearTitleBar();
    }

    // draws the menu state
    public void draw(Graphics2D g) {
        bg.draw(g, getInterpolation()); //draw the background

        //draw each gameButton
        playButton.draw(g, getInterpolation());
        creditsButton.draw(g, getInterpolation());

        //draw the title
        title.draw(g, getInterpolation());

        //draws all the particles
        for (Particle p : particles)
            p.draw(g, getInterpolation());

        transition.draw(g, getInterpolation());
    }


    public GameStateManager getStateManager() {
        return gsm;
    }

    public void handleInput() {
        //when mouse is pressed make a cute little fireworks effect
        if (Mouse.isPressed()) {
            ParticleFactory.createExplosion(Mouse.x, Mouse.y, new Color(40, 40, 40));
            ParticleFactory.createSmallWave(Mouse.x, Mouse.y, 10, new Color(40, 40, 40));
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
