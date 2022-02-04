package com.cyr1en.airscape.handler;

import com.cyr1en.airscape.gamestate.CreditState;
import com.cyr1en.airscape.gamestate.GameOverState;
import com.cyr1en.airscape.gamestate.MenuState;
import com.cyr1en.airscape.gamestate.PlayingState;
import com.cyr1en.cgdl.GameState.GameState;
import com.cyr1en.cgdl.GameState.GameStateManager;

public class StateManager extends GameStateManager {

    //constants that we can just call through out the code
    public static final int MENU_STATE = 0;
    public static final int PLAYING_STATE = 1;
    public static final int CREDIT_STATE = 2;
    public static final int GAME_OVER_STATE = 3;

    public StateManager() {
        super(MENU_STATE);
    }

    public void setState(GameState state) {
        gameState = state;
    }

    //loads specific states, stated in the parameter
    public void loadState(int state) {
        this.gameState = getState(state);
    }

    @Override
    public void loadState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public GameState getState(int i) {
        switch (i) {
            case MENU_STATE:
                return new MenuState(this);
            case PLAYING_STATE:
                return new PlayingState(this);
            case CREDIT_STATE:
                return new CreditState(this);
            case GAME_OVER_STATE:
                return new GameOverState(this);
            default:
                return null;
        }
    }

    @Override
    public int getState(GameState gameState) {
        if (gameState instanceof MenuState)
            return MENU_STATE;
        if (gameState instanceof PlayingState)
            return PLAYING_STATE;
        if (gameState instanceof GameOverState)
            return GAME_OVER_STATE;
        if (gameState instanceof CreditState)
            return CREDIT_STATE;
        return -1;
    }
}
