package actors;

import game.MooseGame;

/**
 * Represents a fog lights pickup item.
 */
public class FogLightsPickup extends Pickup {

    /**
     * Constructs a new fog lights object.
     *
     * @param canvas MooseGame canvas to draw to
     */
    public FogLightsPickup(MooseGame canvas) {
        super(canvas);
        sprites = new String[]{"foglights.png"};
    }

}
