package game;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * Handles resources to be implemented within the game environment.
 */
public class ResourceLoader implements ImageObserver {


    private Map<String, BufferedImage> images = new HashMap<String, BufferedImage>();
    private Map<String, AudioClip> sounds = new HashMap<String, AudioClip>();

    private static ResourceLoader instance = new ResourceLoader();

    /**
     * Constructs a ResourceLoader.
     */
    private ResourceLoader() {
    }

    /**
     * Get value for instance
     *
     * @return instance of resource
     */
    public static ResourceLoader getInstance() {
        return instance;
    }

    /**
     * Stop sounds and clean up resources.
     */
    public void cleanup() {
        for (AudioClip sound : sounds.values()) {
            sound.stop();
        }

    }

    /**
     * Checks whether sound is available and loads it.
     *
     * @param name location of sound
     * @return status of sound
     */
    public AudioClip getSound(String name) {
        AudioClip sound = sounds.get(name);
        if (null != sound)
            return sound;

        URL url = null;
        try {
            url = getClass().getClassLoader().getResource("res/" + name);
            sound = Applet.newAudioClip(url);
            sounds.put(name, sound);
        } catch (Exception e) {
            System.err.println("Could not locate sound " + name + ": " + e.getMessage());
        }

        return sound;
    }

    /**
     * Creates a compatible image in memory, faster than using the original image format
     *
     * @param width        image width
     * @param height       image height
     * @param transparency type of transparency
     * @return a compatible BufferedImage
     */
    public static BufferedImage createCompatible(int width, int height,
                                                 int transparency) {
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage compatible = gc.createCompatibleImage(width, height,
                transparency);
        return compatible;
    }

    /**
     * Checks if image is cached. If not, loads image.
     *
     * @param name name of image
     * @return
     */
    public BufferedImage getSprite(String name) {
        BufferedImage image = images.get(name);
        if (null != image)
            return image;

        URL url = null;
        try {
            url =
                    getClass().getClassLoader().getResource("res/" + name);
            image = ImageIO.read(url);
            //store a compatible image instead of the original format
            BufferedImage compatible = createCompatible(image.getWidth(), image.getHeight(), Transparency.BITMASK);
            compatible.getGraphics().drawImage(image, 0, 0, this);

            images.put(name, compatible);
        } catch (Exception e) {
            System.err.println("Cound not locate image " + name + ": " + e.getMessage());
        }

        return image;
    }

    /**
     * @param img
     * @param infoflags
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return (infoflags & (ALLBITS | ABORT)) == 0;
    }

}
