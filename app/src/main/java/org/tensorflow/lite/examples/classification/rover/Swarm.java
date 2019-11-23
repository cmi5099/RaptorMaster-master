package org.tensorflow.lite.examples.classification.rover;

public class Swarm {
    //static (insert datatype here) startingDirection;
    //(insert datatype here) currentDirection

    //Initializes the rover
    void initializeRover(){
        //usesFieldParams
        //usesCurrentDirection()
        //alignWheels()
    }

    //Turns wheels all the way to the left, then right, then aligns to the
    //middle
    void alignWheels(){

    }

    //Turns wheels in proper direction and angle to realign arduino
    //back into original orientation while continuing forward
    void adjustWheelAlignment(){

    }

    //Called when rover reaches the beginning of the last block
    //Uses fieldParams.getTurningRadius() to decide the angle
    //to turn the rover into the other lane
    void turnRight() {

    }

    //Called at the end of finding everything, if there are still lanes unsearched, the rover that
    //is assigned those lanes will go around the ball and continue searching
    void goAroundVictim(){

    }

    //Called when rover reaches the beginning of the last block
    //Uses fieldParams.getTurningRadius() to decide the angle
    //to turn the rover into the other lane
    void turnLeft(){

    }

    void goForward(){

    }

    //Called when TensorFlow object confidence %>75 && soccer ball
    //Sends notification to ServiceNow. Notifies other raptors
    void found(){

    }

    void stop(){

    }

}
