package org.tensorflow.lite.examples.classification.rover;

import org.tensorflow.lite.examples.classification.Bluetooth.UartService;

import java.nio.ByteBuffer;

public class Swarm {
    UartService uart = new UartService();

    int stop = 0;
    int goForward = 1;
    int alignWheels = 2;
    int adjustWheels = 3;
    int turnLeft = 4;
    int turnRight = 5;
    int goAroundVictim =6;

    FieldActivity fieldActivity = new FieldActivity();
    RoverParams rover = new RoverParams();
    int blocksPerLane = fieldActivity.getBlocksPerLane();
    int lanes = fieldActivity.getLanes();
    int roverId = rover.getRoverId();
    int startingLane = roverId;
    String generalRoverDirection = (rover.isEven() == 0)? "down": "up";
    int startingBlock = (generalRoverDirection == "up")? 0: blocksPerLane;

    //Rovers 1 and 3 are going to start at the bottom of the first and third lanes
    //Rovers 2 and 4 are going to start at the top of the second and fourth lanes
    public void startSwarm() {
        alignWheels();
        //start TensorFlow recording

        for (int i = startingLane; i < lanes; i += 4) {
            switch(generalRoverDirection){
                case "down":
                    for (int j = startingBlock - 1; j >= 0; j--) {
                        runSwarmLogic(i, j);
                    }
                    break;
                case "up":
                    for (int j = startingBlock; j < blocksPerLane; j++) {
                        runSwarmLogic(i, j);
                    }
                    break;
            }
        }
    }

    void runSwarmLogic(int currentLane, int currentBlock){
        //Goes forward and marks each block in the grid if it's not the last block
        if (currentBlock != blocksPerLane - 1 || currentBlock != 1) {
            //Making objectScanned = pumpkin just so it doesn't fail
            String objectScanned = "pumpkin";
            goForward();
            if (objectScanned != "soccer ball")
                fieldActivity.searchedBlock(currentLane, currentBlock);
            else
                fieldActivity.foundInBlock(currentLane, currentBlock);
                found();
        }
        //Logic for making the proper U-turn to get into their next designated lane
        else {
            fieldActivity.searchedBlock(currentLane, currentBlock);
            switch(generalRoverDirection){
                case "down":
                    leftUturn();
                    generalRoverDirection = "up";
                    break;
                case "up":
                    rightUturn();
                    generalRoverDirection = "down";
                    break;
            }
        }
    }

    void rightUturn(){
        turnRight();
        goForward();
        goForward();
        goForward();
        turnRight();
    }

    void leftUturn(){
        turnLeft();
        goForward();
        goForward();
        goForward();
        turnLeft();
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

    //Adjusts wheel direction to stay straight and moves the rover forward one block
    void goForward(){
        adjustWheelAlignment();
        byte [] bytes = ByteBuffer.allocate(4).putInt(goForward).array();
        uart.writeRXCharacteristic(bytes);

    }

    //Called when TensorFlow object confidence %60 && soccer ball
    //Sends notification to ServiceNow. Notifies other raptors
    void found(){
        stop();
    }

    //Called when something is found and just stops the rover
    void stop(){
        byte [] bytes = ByteBuffer.allocate(4).putInt(stop).array();
        uart.writeRXCharacteristic(bytes);

    }

}
