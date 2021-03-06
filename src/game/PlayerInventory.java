package game;

import java.io.*;
import java.util.Scanner;

/**
 * Manages the player's inventory, high score, and settings.
 */
public class PlayerInventory {

    private static boolean settingsMusicOn = true;
    private static boolean settingSoundsOn = true;
    private static boolean showFPSOverlay = false;
    private static int highScore = 0;
    private static int fogLightsCount = 0;
    private static int slowMotionCount = 0;
    private static int invincibilityCount = 0;
    private static int currency = 0;

    private static boolean truckOwned = false;
    private static boolean atvOwned = false;

    public enum Vehicles {
        CAR,
        TRUCK,
        ATV
    }

    private static Vehicles equippedVehicle = Vehicles.CAR;

    /**
     * Gets the currently equipped value.
     *
     * @return Vehicle equipped vehicle
     */
    public static Vehicles getEquippedVehicle() {
        return equippedVehicle;
    }

    /**
     * Sets the currently equipped value.
     *
     * @param vehicle Vehicle equipped vehicle
     */
    public static void setEquippedVehicle(Vehicles vehicle) {
        equippedVehicle = vehicle;
    }


    /**
     * Gets the value for the background music setting.
     *
     * @return settingsMusicOn boolean
     */
    public static boolean isSettingMusicOn() {
        return settingsMusicOn;
    }

    /**
     * Turns background music on.
     *
     * @param settingsMusicOn boolean
     */
    public static void setSettingMusicOn(boolean settingsMusicOn) {
        PlayerInventory.settingsMusicOn = settingsMusicOn;
    }

    /**
     * Gets the value for the sounds effect setting.
     *
     * @return settingSoundsOn boolean
     */
    public static boolean isSettingSoundsOn() {
        return settingSoundsOn;
    }

    /**
     * Turns sound effects on.
     *
     * @param settingSoundsOn boolean
     */
    public static void setSettingSoundsOn(boolean settingSoundsOn) {
        PlayerInventory.settingSoundsOn = settingSoundsOn;
    }

    /**
     * Gets the player's high score.
     *
     * @return high score
     */
    public static int getHighScore() {
        return highScore;
    }

    /**
     * Sets the player's high score.
     *
     * @param currentScore int current game score
     */
    public static void setHighScore(int currentScore) {
        if (currentScore > highScore) {
            highScore = currentScore;
        }
    }

    /**
     * Get the showFPSOverlay setting value.
     *
     * @return showFPSOverlay boolean true if FPS overlay setting enabled
     */
    public static boolean isShowFPSOverlayOn() {
        return showFPSOverlay;
    }

    /**
     * Sets value for showFPSOverlay setting.
     *
     * @param showFPSOverlay boolean true if FPS overlay setting enabled
     */
    public static void setShowFPSOverlay(boolean showFPSOverlay) {
        PlayerInventory.showFPSOverlay = showFPSOverlay;
    }

    /**
     * Gets the player's total currency.
     *
     * @return currency int
     */
    public static int getCurrency() {
        return currency;
    }

    /**
     * Adds additional currency to the player's inventory.
     *
     * @param additionalCurrency int amount of currency to add
     */
    public static void addCurrency(int additionalCurrency) {
        currency += additionalCurrency;
    }

    /**
     * Spends player currency after determining whether the player has enough to purchase an item.
     *
     * @param cost int Cost of an item
     * @return Whether sufficient currency exists
     */
    public static boolean spendCurrency(int cost) {

        if (currency >= cost) {
            currency -= cost;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks to see if truck is owned.
     *
     * @return Whether truck is owned
     */
    public static boolean isTruckOwned() {
        return truckOwned;
    }

    /**
     * Sets truck ownership value to true following truck purchase.
     */
    public static void buyTruck() {
        truckOwned = true;
    }

    /**
     * Checks to see if ATV is owned.
     *
     * @return Whether ATV is owned
     */
    public static boolean isAtvOwned() {
        return atvOwned;
    }

    /**
     * Sets truck ownership value to true following truck purchase.
     */
    public static void buyATV() {
        atvOwned = true;
    }


    /**
     * Get value for fogLightsCount
     *
     * @return fog lights count
     */
    public static int getFogLightsCount() {
        return fogLightsCount;
    }

    /**
     * Gets value for slowMotionCount
     *
     * @return slow motion count
     */
    public static int getSlowMotionCount() {
        return slowMotionCount;
    }

    /**
     * Gets value for invincibilityCount
     *
     * @return invincibility count
     */
    public static int getInvincibilityCount() {
        return invincibilityCount;
    }

    /**
     * Increases fog lights count
     */
    public static void incrementFogLights() {
        fogLightsCount++;
    }

    /**
     * Increases invincibility count
     */
    public static void incrementInvincibility() {
        invincibilityCount++;
    }

    /**
     * Increments slow motion count
     */
    public static void incrementSlowMotion() {
        slowMotionCount++;
    }

    /**
     * Allows for use of fog lights powerup by player.
     *
     * @return whether fog lights powerup is available
     */
    public static boolean useFogLightsPowerup() {
        if (getFogLightsCount() > 0) {
            fogLightsCount--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Allows for use of invincibility powerup by player.
     *
     * @return whether invincibility powerup is available
     */
    public static boolean useInvincibilityPowerup() {
        if (getInvincibilityCount() > 0) {
            invincibilityCount--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Allows for use of slow motion powerup by player.
     *
     * @return whether slow motion powerup is available
     */
    public static boolean useSlowMotionPowerup() {
        if (getSlowMotionCount() > 0) {
            slowMotionCount--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Resets the number of powerups available to zero.
     */
    public static void clearPowerups() {
        fogLightsCount = 0;
        invincibilityCount = 0;
        slowMotionCount = 0;
    }

    /**
     * Saves game settings to a file.
     * <p>
     * Settings include high score, currency, background music, sound effects, FPS overlay,
     * and vehicle selection/ownership.
     */
    public static void saveToFile() {

        String equippedVehicleSaveString = "";

        if (getEquippedVehicle() == Vehicles.CAR) {
            equippedVehicleSaveString = "Car";
        } else if (getEquippedVehicle() == Vehicles.TRUCK) {
            equippedVehicleSaveString = "Truck";
        } else if (getEquippedVehicle() == Vehicles.ATV) {
            equippedVehicleSaveString = "ATV";
        }

        String s = (Integer.toString(highScore) + "," +
                Integer.toString(currency) + "," +
                Boolean.toString(settingsMusicOn) + "," +
                Boolean.toString(settingSoundsOn) + "," +
                Boolean.toString(showFPSOverlay) + "," +
                equippedVehicleSaveString + "," +
                Boolean.toString(truckOwned) + "," +
                Boolean.toString(atvOwned)
        );
        File saveFile = new File("./save.dat");
        try {
            FileWriter fw = new FileWriter(saveFile);
            fw.write(s);
            fw.close();
        } catch (IOException e) {
            System.out.println("IOException occurred");
        }
    }

    /**
     * Loads game settings from a save file.
     */
    public static void loadFromFile() {

        File saveFile = new File("./save.dat");

        if (!saveFile.exists()) {
            return;
        }

        try {
            Scanner scanner = new Scanner(saveFile);
            String line = scanner.nextLine();
            String[] s = line.split(",");

            highScore = Integer.parseInt(s[0]);
            currency = Integer.parseInt(s[1]);
            settingsMusicOn = Boolean.parseBoolean(s[2]);
            settingSoundsOn = Boolean.parseBoolean(s[3]);
            showFPSOverlay = Boolean.parseBoolean(s[4]);

            if (s[5].equals("Car")) {
                equippedVehicle = Vehicles.CAR;
            } else if (s[5].equals("Truck")) {
                equippedVehicle = Vehicles.TRUCK;
            } else if (s[5].equals("ATV")) {
                equippedVehicle = Vehicles.ATV;
            }
            truckOwned = Boolean.parseBoolean(s[6]);
            atvOwned = Boolean.parseBoolean(s[7]);


        } catch (IOException e) {
            System.out.println("IOException occurred");
        }

    }

    /**
     * Clears the save file contents.
     * <p>
     * Returns the high score and player currency values to 0, and settings to their default values.
     */
    public static void clearSave() {
        highScore = 0;
        currency = 0;
        settingsMusicOn = true;
        settingSoundsOn = true;
        showFPSOverlay = false;
        equippedVehicle = Vehicles.CAR;
        truckOwned = false;
        atvOwned = false;
        saveToFile();
    }


}
