package org.tensorflow.lite.examples.classification.rover;

import org.tensorflow.lite.examples.classification.Bluetooth.UartService;
import org.tensorflow.lite.examples.classification.model.SensorDataObject;

import java.nio.ByteBuffer;

public class Swarm {

    UartService uart = new UartService();
    SensorDataObject sdo = new SensorDataObject();
    FieldActivity fieldActivity = new FieldActivity();
    RoverParams rover = new RoverParams();

    int stop = 0;
    int goForward = 1;
    int alignWheels = 2;
    int goAroundVictim =3;
    int rightUTurn = 4;
    int leftUTurn = 5;

    float oppositeDepartureDirection;
    float departureDirection;
    int blocksPerLane = fieldActivity.getBlocksPerLane();
    int lanes = fieldActivity.getLanes();
    int roverId = rover.getRoverId();
    int startingLane = roverId;
    String generalRoverDirection = (rover.isEven() == 0)? "down": "up";
    int startingBlock = (generalRoverDirection == "up")? 0: blocksPerLane;
    int objectsFound = 0;


    //Rovers 1 and 3 are going to start at the bottom of the first and third lanes
    //Rovers 2 and 4 are going to start at the top of the second and fourth lanes
    public void startSwarm() {
        departureDirection = sdo.getCompass();
        setOtherDirections();
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
        /*  If rovers get to the end of their searches, but found != 4, the rover with
            unsearched blocks where they should've searched, will go around victim and continue
            Requirements:
            1. How many objects are found
            2. We need to know the status of the rest of the grid
            3. We need to know if a rover still has blocks to search
        */

    }

    void runSwarmLogic(int currentLane, int currentBlock){
        //Goes forward and marks each block in the grid if it's not the last block
        if (currentBlock != blocksPerLane - 1 || currentBlock != 1) {
            //Making objectScanned = pumpkin just so it doesn't fail
            String objectScanned = "pumpkin";
            goForward();
            if (objectScanned != "soccer ball")
                fieldActivity.searchedBlock(currentLane, currentBlock);
                sdo.setU_object_found(false);

            if (objectScanned == "soccer ball")
            fieldActivity.foundInBlock(currentLane, currentBlock);
            found(objectsFound++);
            sdo.setU_object_found(true);
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
        byte [] bytes = ByteBuffer.allocate(4).putInt(rightUTurn).array();
        uart.writeRXCharacteristic(bytes);
    }

    void leftUturn(){
        byte [] bytes = ByteBuffer.allocate(4).putInt(leftUTurn).array();
        uart.writeRXCharacteristic(bytes);
    }

    //Turns wheels all the way to the left, then right, then aligns to the
    //middle
    void alignWheels(){
        byte [] floatBytes = ByteBuffer.allocate(4).putFloat(departureDirection).array();
        byte [] bytes = ByteBuffer.allocate(4).putInt(alignWheels).array();
        byte[] destination = new byte[bytes.length + floatBytes.length];

        System.arraycopy(bytes, 0, destination, 0, bytes.length);
        System.arraycopy(floatBytes, 0, destination, bytes.length, floatBytes.length);

        uart.writeRXCharacteristic(destination);
    }

    //Adjusts wheel direction to stay straight and moves the rover forward one block
    void goForward(){
        float degreeAdjustment = adjustWheelAlignment();

        byte [] orderByte = ByteBuffer.allocate(4).putInt(goForward).array();
        byte [] degreeBytes = ByteBuffer.allocate(4).putFloat(degreeAdjustment).array();
        byte[] combinedBytes = new byte[orderByte.length + degreeBytes.length];

        System.arraycopy(orderByte, 0, combinedBytes, 0, orderByte.length);
        System.arraycopy(degreeBytes, 0, combinedBytes, orderByte.length, degreeBytes.length);

        uart.writeRXCharacteristic(combinedBytes);
    }

//    //Called when rover reaches the beginning of the last block
//    void turnRight() {
//        byte [] bytes = ByteBuffer.allocate(4).putInt(turnRight).array();
//        uart.writeRXCharacteristic(bytes);
//    }
//
//    //Called when rover reaches the beginning of the last block
//    void turnLeft(){
//        byte [] bytes = ByteBuffer.allocate(4).putInt(turnLeft).array();
//        uart.writeRXCharacteristic(bytes);
//
//    }

    //Called at the end of finding everything, if there are still lanes unsearched, the rover that
    //is assigned those lanes will go around the ball and continue searching
    void goAroundVictim(){
        byte [] bytes = ByteBuffer.allocate(4).putInt(goAroundVictim).array();
        uart.writeRXCharacteristic(bytes);
    }

    //Called when TensorFlow object confidence %60 && soccer ball
    //Sends notification to ServiceNow. Notifies other raptors
    void found(int objectsFound){
        stop();
    }

    //Called when something is found and just stops the rover
    void stop(){
        byte [] bytes = ByteBuffer.allocate(4).putInt(stop).array();
        uart.writeRXCharacteristic(bytes);

    }

    //Turns wheels in proper direction and angle to realign arduino
    //back into original orientation while continuing forward
    float adjustWheelAlignment(){
        float currentDirection = sdo.getCompass();

        //calulcate degree necessary
        return getAdjustedDegreeForSending(currentDirection);
    }

    //Initialize rover orientation using android compass
    //determine compass degree rover is facing for purpose of lane departure direction and lane return direction
    public float getAdjustedDegreeForSending(float currentDirection) {
        float degreeForAdjusting = (departureDirection - currentDirection) % 360;

        if (degreeForAdjusting < -180)
            degreeForAdjusting += 360;
        if (degreeForAdjusting >= 180)
            degreeForAdjusting -= 360;

        return degreeForAdjusting;
    }

    public void setOtherDirections(){
        /* Add 180 to your current bearing.
           If the result is more than 360, subtract 360.
           This number is your opposite direction.
        */
        float sum = departureDirection + 180;
        oppositeDepartureDirection = (sum > 360)? sum - 360 : sum;
    }

}
