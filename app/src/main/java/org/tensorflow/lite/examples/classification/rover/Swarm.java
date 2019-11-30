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

    //1.if bluetooth connected to arduino start mission
    //2. allignnwheels
    //3. rover checks block to search for object
    //4. if no object found rover moves forward.
    //5. after each block rover will update field params for each block to notify that block is checked
    //6. if rover gets to final block rover will make a right turn.
    //7. if rover detects soccer ball object found
    //8.

    //Initializes the rover
    //Rovers 1 and 3 are going to start at the bottom of the first and third lanes
    //Rovers 2 and 4 are going to start at the top of the second and fourth lanes
    public void startSwarm() {
        FieldActivity fieldActivity = new FieldActivity();
        RoverParams rover = new RoverParams();
        int blocksPerLane = fieldActivity.getBlocksPerLane();
        int lanes = fieldActivity.getLanes();
        int roverId = rover.getRoverId();
        int switchCasesForDirection = 0;

        //Starts all the logic, sends directions to arduino when we need to and is the main controller
        //usesFieldParams
        //usesCurrentDirection
        //alignWheels()

        alignWheels();
        //start TensorFlow recording

        //For each lane, as long as it's not the last block, go forward
        //Else switch between turning left and right depending on their rover id and if they turned
        //right of left last time
        for(int i = 0; i < lanes; i++){
            for(int j = 0; i < blocksPerLane; j++) {
                //Logic while we check every block
                if (j != blocksPerLane - 1){
                    goForward();
                    fieldActivity.searchedBlock(i, j);
                }
                //Logic for making the proper U-turn to get into their next designated lane
                else {
                    if (switchCasesForDirection == 0) {
                        switch (roverId % 2) {
                            case 0: //Rovers 1 and 3
                                turnRight();
                                goForward();
                                goForward();
                                goForward();
                                turnRight();
                                break;
                            case 1: //Rovers 2 and 4
                                turnLeft();
                                goForward();
                                goForward();
                                goForward();
                                turnLeft();
                                break;
                        }
                        switchCasesForDirection = 1;
                    }
                    else{
                        switch (roverId % 2) {
                            case 0: //Rovers 1 and 3
                                turnLeft();
                                goForward();
                                goForward();
                                goForward();
                                turnLeft();
                                break;
                            case 1: //Rovers 2 and 4
                                turnRight();
                                goForward();
                                goForward();
                                goForward();
                                turnRight();
                                break;
                        }
                        switchCasesForDirection = 0;
                    }

                }

            }

        }


//        while (int block == FieldActivity.notSearched();
//        {
//            return goForward;
//       }
//       (int block == FieldActivity.searched {
//           return; int searched;
//        }
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
