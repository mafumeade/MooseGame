package actors;

import game.MooseGame;
import game.PlayerInventory;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Spawns, renders graphics for, and removes pickup items during gameplay.
 */
public class PickupManager {

    private static ArrayList<Pickup> activePickups = new ArrayList<>();

    private MooseGame canvas;

    private static final int SPAWN_WAIT_TIME = 2 * 1000;

    private static final int COIN_MIN_SPAWN_TIME = 500;
    private static final int COIN_MAX_SPAWN_TIME = 1000;
    private Timer coinTimer = new Timer();

    private static final int FOG_LIGHTS_MIN_SPAWN_TIME = 8 * 1000;
    private static final int FOG_LIGHTS_MAX_SPAWN_TIME = 18 * 1000;
    private Timer fogLightsTimer = new Timer();

    private static final int SLOW_MOTION_MIN_SPAWN_TIME = 10 * 1000;
    private static final int SLOW_MOTION_MAX_SPAWN_TIME = 20 * 1000;
    private Timer slowMotionTimer = new Timer();

    private static final int INVINCIBILITY_MIN_SPAWN_TIME = 12 * 1000;
    private static final int INVINCIBILITY_MAX_SPAWN_TIME = 22 * 1000;
    private Timer invincibilityTimer = new Timer();

    private int coinsPickedUp = 0;

    /**
     * Constructs a new PickupManager.
     *
     * @param canvas game window
     */
    public PickupManager(MooseGame canvas) {
        this.canvas = canvas;

        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        spawnFogLightsPickup();
                        spawnSlowMotionPickup();
                        spawnInvincibilityPickup();
                        spawnCoinPickup();
                    }
                }, SPAWN_WAIT_TIME);
    }

    /**
     * Gets number of coins picked up.
     *
     * @return coinsPickedUp int number of coins picked up in current game session
     */
    public int getCoinsPickedUp() {
        return coinsPickedUp;
    }

    /**
     * Clears active pickups and stops pickup-spawning timers.
     */
    public void stop() {
        activePickups = new ArrayList<Pickup>();

        fogLightsTimer.cancel();
        fogLightsTimer.purge();

        slowMotionTimer.cancel();
        slowMotionTimer.purge();

        invincibilityTimer.cancel();
        invincibilityTimer.purge();

        coinTimer.cancel();
        coinTimer.purge();
    }

    /**
     * Spawns a Fog Lights pickup item based on a timer.
     */
    public void spawnFogLightsPickup() {
        Random random = new Random();

        fogLightsTimer.cancel();
        fogLightsTimer.purge();
        fogLightsTimer = new Timer();
        fogLightsTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Pickup pickup = new FogLightsPickup(canvas);
                        pickup.spawn();
                        activePickups.add(pickup);
                        spawnFogLightsPickup();
                    }
                }, (random.nextInt(FOG_LIGHTS_MAX_SPAWN_TIME - FOG_LIGHTS_MIN_SPAWN_TIME) + FOG_LIGHTS_MIN_SPAWN_TIME));
    }

    /**
     * Spawns a Slow Motion pickup item based on a timer.
     */
    public void spawnSlowMotionPickup() {
        Random random = new Random();

        slowMotionTimer.cancel();
        slowMotionTimer.purge();
        slowMotionTimer = new Timer();
        slowMotionTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Pickup pickup = new SlowMotionPickup(canvas);
                        pickup.spawn();
                        activePickups.add(pickup);
                        spawnSlowMotionPickup();
                    }
                }, (random.nextInt(SLOW_MOTION_MAX_SPAWN_TIME - SLOW_MOTION_MIN_SPAWN_TIME) + SLOW_MOTION_MIN_SPAWN_TIME));
    }


    /**
     * Spawns an Invincibility pickup item based on a timer.
     */
    public void spawnInvincibilityPickup() {

        Random random = new Random();

        invincibilityTimer.cancel();
        invincibilityTimer.purge();
        invincibilityTimer = new Timer();
        invincibilityTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Pickup pickup = new InvincibilityPickup(canvas);
                        pickup.spawn();
                        activePickups.add(pickup);
                        spawnInvincibilityPickup();
                    }
                }, (random.nextInt(INVINCIBILITY_MAX_SPAWN_TIME - INVINCIBILITY_MIN_SPAWN_TIME) + INVINCIBILITY_MIN_SPAWN_TIME));
    }

    /**
     * Spawns a coin for pickup.
     */
    public void spawnCoinPickup() {

        Random random = new Random();

        coinTimer.cancel();
        coinTimer.purge();
        coinTimer = new Timer();
        coinTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Pickup pickup = new CoinPickup(canvas);
                        pickup.spawn();
                        activePickups.add(pickup);
                        spawnCoinPickup();
                    }
                }, (random.nextInt(COIN_MAX_SPAWN_TIME - COIN_MIN_SPAWN_TIME) + COIN_MIN_SPAWN_TIME));
    }


    /**
     * Updates the position of all items in the activePickups array during gameplay.
     */
    public void update() {
        for (int i = 0; i < activePickups.size(); i++) {

            Pickup p = activePickups.get(i);
            p.update();

            if (p.posY > MooseGame.HEIGHT || p.isActive == false) {
                activePickups.remove(p);
            }
        }

    }

    /**
     * Renders graphics for pickup items
     *
     * @param g Graphics object to paint on
     */
    public void paint(Graphics g) {

        for (int i = 0; i < activePickups.size(); i++) {
            activePickups.get(i).paint(g);
        }
    }

    /**
     * Checks to see if current player has collected a pickup item.
     *
     * @param player Actor player object
     */
    public void checkCollision(Actor player) {

        for (int i = 0; i < activePickups.size(); i++) {
            Pickup p = activePickups.get(i);

            if (p.getBounds().intersects(player.getBounds())) {
                p.despawn();
                if (p instanceof FogLightsPickup) {
                    PlayerInventory.incrementFogLights();
                } else if (p instanceof SlowMotionPickup) {
                    PlayerInventory.incrementSlowMotion();
                } else if (p instanceof InvincibilityPickup) {
                    PlayerInventory.incrementInvincibility();

                } else if (p instanceof CoinPickup) {
                    coinsPickedUp++;
                    canvas.playSound("coin.wav");
                }
            }
        }
    }
}



