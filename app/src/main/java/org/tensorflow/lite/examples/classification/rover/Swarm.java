package org.tensorflow.lite.examples.classification.rover;

import java.nio.ByteBuffer;

public class Swarm {

    int stop = 0;
    int goForward = 1;
    int alignWheels = 2;
    int adjustWheels = 3;
    int turnLeft = 4;
    int turnRight = 5;
    int goAroundVictim =6;

    //Initializes the rover
    public void startSwarm(){
        //Starts all the logic, sends directions to arduino when we need to and is the main controller
        //usesFieldParams
        //usesCurrentDirection
        //alignWheels()
    }

    //Turns wheels all the way to the left, then right, then aligns to the
    //middle
    void alignWheels(){
        byte [] bytes = ByteBuffer.allocate(4).putInt(alignWheels).array();
        uart.writeRXCharacteristic(bytes);
    }

    //Turns wheels in proper direction and angle to realign arduino
    //back into original orientation while continuing forward
    void adjustWheelAlignment(){
        byte [] bytes = ByteBuffer.allocate(4).putInt(adjustWheels).array();
        uart.writeRXCharacteristic(bytes);
    }

    //Called when rover reaches the beginning of the last block
    void turnRight() {
        byte [] bytes = ByteBuffer.allocate(4).putInt(turnRight).array();
        uart.writeRXCharacteristic(bytes);
    }

    //Called when rover reaches the beginning of the last block
    void turnLeft(){
        byte [] bytes = ByteBuffer.allocate(4).putInt(turnLeft).array();
        uart.writeRXCharacteristic(bytes);

    }

    //Called at the end of finding everything, if there are still lanes unsearched, the rover that
    //is assigned those lanes will go around the ball and continue searching
    void goAroundVictim(){
        byte [] bytes = ByteBuffer.allocate(4).putInt(goAroundVictim).array();
        uart.writeRXCharacteristic(bytes);

    }

    //Goes through the int [][] and makes the next one updated to searched()
    void goForward(){
        byte [] bytes = ByteBuffer.allocate(4).putInt(goForward).array();
        uart.writeRXCharacteristic(bytes);

    }

    //Called when TensorFlow object confidence %60 && soccer ball
    //Sends notification to ServiceNow. Notifies other raptors
    void found(){

    }

    //Called when something is found and just stops the rover
    void stop(){
        byte [] bytes = ByteBuffer.allocate(4).putInt(stop).array();
        uart.writeRXCharacteristic(bytes);


    }

}
