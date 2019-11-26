package org.tensorflow.lite.examples.classification.rover;

public class RoverParams {
    static int roverQuantity;
    static int victimQuantity;
    static int roverId;

    public static int getRoverQuantity() {
        return roverQuantity;
    }

    public static void setRoverQuantity(int roverQuantity) {
        RoverParams.roverQuantity = roverQuantity;
    }

    public static int getVictimQuantity() {
        return victimQuantity;
    }

    public static void setVictimQuantity(int victimQuantity) {
        RoverParams.victimQuantity = victimQuantity;
    }

    public static int getRoverId() {
        return roverId;
    }

    public static void setRoverId(int roverId) {
        RoverParams.roverId = roverId;
    }
}
