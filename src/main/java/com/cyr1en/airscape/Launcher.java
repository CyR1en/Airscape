package com.cyr1en.airscape;

import com.cyr1en.airscape.handler.StateManager;
import com.cyr1en.cgdl.Main.GameFrame;
import com.cyr1en.cgdl.Main.GamePanel;
import com.cyr1en.cgdl.util.ImageUtil;

import java.awt.image.BufferedImage;

public class Launcher {

    //Loading the icon for the frame
    private static BufferedImage frameIcon = ImageUtil.loadBufferedImage("/FrameIcon2.png");

    //The frame for the game
    public static GameFrame frame;

    public static void main(String[] args) {
        GamePanel.initPanel(900 , 600); // initializing the size of the GamePanel
        frame = new GameFrame("AirScape", new StateManager(), 244, 60); // initializing the game frame
        frame.setShowTitleInfo(true); // enable the title bar debug
        frame.setIconImage(frameIcon); // set the frame icon image as the buffered image in the instance variable
    }
}
