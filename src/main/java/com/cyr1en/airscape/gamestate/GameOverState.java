package com.cyr1en.airscape.gamestate;

import com.cyr1en.airscape.Launcher;
import com.cyr1en.airscape.handler.StateManager;
import com.cyr1en.cgdl.Entity.BackGround;
import com.cyr1en.cgdl.Entity.Button.GameButton;
import com.cyr1en.cgdl.GameState.GameState;
import com.cyr1en.cgdl.GameState.GameStateManager;
import com.cyr1en.cgdl.GameState.Transition;
import com.cyr1en.cgdl.Handlers.GameData;
import com.cyr1en.cgdl.Handlers.Mouse;
import com.cyr1en.cgdl.Handlers.ParticleFactory;
import com.cyr1en.cgdl.Main.GamePanel;

import java.awt.*;

public class GameOverState extends GameState {

    private int currentChoice = 0;

    private int fadeInTimer;
    private int fadeInDelay;
    private int fadeOutTimer;
    private int fadeOutDelay;
    private int alpha;
    private int nextState;

    private BackGround bg;

    private GameButton<GameOverState> replayButton;
    private GameButton<GameOverState> menuButton;

    private Transition transition;

    public GameOverState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    public void init() {
        GameData.load();
        bg = new BackGround("/bg.jpg");
        bg.setVector(-1, 0);
        transition = new Transition(gsm, 0, 30, -1, 60);

        replayButton = new GameButton<>(100, GamePanel.HEIGHT - 50);
        replayButton.setText(" R e p l a y ", new Font("Fira Code Retina Medium", Font.BOLD, 20));
        replayButton.setColor(GameButton.DEFAULT_COLOR);
        replayButton.setObjType(this);
        replayButton.setOnClick(state -> {
            state.transition.nextState(StateManager.PLAYING_STATE);
        });
        menuButton = new GameButton<>(GamePanel.WIDTH - 100, GamePanel.HEIGHT - 50);
        menuButton.setText(" M e n u ", new Font("Fira Code Retina Medium", Font.BOLD, 20));
        menuButton.setColor(GameButton.DEFAULT_COLOR);
        menuButton.setObjType(this);
        menuButton.setOnClick(state -> {
            state.transition.nextState(StateManager.MENU_STATE);
        });
    }

    public void update() {
        handleInput();
        bg.update();
        menuButton.update();
        replayButton.update();

        transition.update();

        if (Launcher.frame.isShowTitleInfo())
            Launcher.frame.updateTitleBar();
        else
            Launcher.frame.clearTitleBar();
    }

    public void draw(Graphics2D g) {
        bg.draw(g, getInterpolation());
        g.setColor(new Color(30, 30, 30, 230));
        g.fillRect(GamePanel.WIDTH / 8, GamePanel.HEIGHT / 8, GamePanel.WIDTH * 3 / 4, GamePanel.HEIGHT * 3 / 4);

        g.setColor(new Color(30, 30, 30, 180));
        g.setFont(new Font("Fira Code", Font.BOLD, 50));
        String s = "G A M E O V E R";
        int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, GamePanel.WIDTH / 2 - length / 2, 50);

        g.setColor(new Color(100, 100, 100, 210));
        g.setStroke(new BasicStroke(5));
        g.drawRect(GamePanel.WIDTH / 8, GamePanel.HEIGHT / 8, GamePanel.WIDTH * 3 / 4, GamePanel.HEIGHT * 3 / 4);
        g.setStroke(new BasicStroke(1));

        g.setFont(new Font("Tahoma", Font.PLAIN, 20));
        g.setColor(new Color(102, 51, 0));

        s = "= S C O R E =";
        length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, GamePanel.WIDTH / 2 - length / 2, 200);

        s = Integer.toString(PlayingState.player.getScore());
        length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, GamePanel.WIDTH / 2 - length / 2, 250);

        s = "= H I G H  S C O R E =";
        length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, GamePanel.WIDTH / 2 - length / 2, GamePanel.HEIGHT / 2);

        s = Integer.toString(GameData.getDataAt(0));
        length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, GamePanel.WIDTH / 2 - length / 2, GamePanel.HEIGHT / 2 + 50);

        g.setFont(new Font("Fira Code", Font.BOLD, 20));
        g.setColor(new Color(0, 100, 100));

        replayButton.draw(g, getInterpolation());
        menuButton.draw(g, getInterpolation());

        transition.draw(g, getInterpolation());
    }


    public void handleInput() {
        if (Mouse.isPressed()) {
            ParticleFactory.createExplosion(Mouse.x, Mouse.y, Color.white);
            ParticleFactory.createSmallWave(Mouse.x, Mouse.y, 50);
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
