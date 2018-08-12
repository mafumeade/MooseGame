package game;

import actors.KeyboardControllable;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverScreenController implements KeyboardControllable {

    private MooseGame stage;
    private int menuSelection = 0;
    private int finalScore;

    public GameOverScreenController(MooseGame stage, int finalScore) {
        this.stage = stage;
        this.finalScore = finalScore;

    }

    public void paint(Graphics g) {

        // Draw background
        g.drawImage(ResourceLoader.getInstance().getSprite("road.png"), 0, 0, stage);

        // Show final score
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + finalScore, stage.WIDTH / 3, 200);

        // Show high score
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        g.setColor(Color.WHITE);
        g.drawString("High Score: " + PlayerInventory.getHighScore(), stage.WIDTH / 3, 250);

        // Draw play again/return to main menu options highlighted based on current choice

        if (menuSelection == 0) {
            // Play again
            g.setFont(new Font("Arial", Font.PLAIN, 40));
            g.setColor(Color.GREEN);
            g.drawString("Play Again", stage.WIDTH / 3, 500);

            // Return to main menu
            g.setFont(new Font("Arial", Font.PLAIN, 40));
            g.setColor(Color.WHITE);
            g.drawString("Return to Main Menu", stage.WIDTH / 3, 600);

        } else if (menuSelection == 1) {
            // Play again
            g.setFont(new Font("Arial", Font.PLAIN, 40));
            g.setColor(Color.WHITE);
            g.drawString("Play Again", stage.WIDTH / 3, 500);

            // Return to main menu
            g.setFont(new Font("Arial", Font.PLAIN, 40));
            g.setColor(Color.GREEN);
            g.drawString("Return to Main Menu", stage.WIDTH / 3, 600);
        }


    }

    private void handleEnterPress() {

        if (menuSelection == 0) {
            stage.initGame();
        } else if (menuSelection == 1) {
            stage.initMenu();
        }

    }

    @Override
    public void triggerKeyPress(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            menuSelection++;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            menuSelection--;
            if (menuSelection < 0) {
                menuSelection = 1;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            handleEnterPress();
        }

        menuSelection %= 2;

    }

    @Override
    public void triggerKeyRelease(KeyEvent e) {

    }
}