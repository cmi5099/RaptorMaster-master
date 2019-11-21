package org.tensorflow.lite.examples.classification.Blockchain;
import java.util.Date;

public class Block {
    //initialization of variables
    public String hash;
    public String previousHash;
    private String data; //our data will be a simple message.
    private long timeStamp; //as number of milliseconds since 1/1/1970.
    private int nonce;

    // Code for a block object for storing a block's data and the hash of the previous block.
    // Calls the calculateHash() method after variables are initialized so that they can be passed into the calculateHash() method.
    public Block(String data, String previousHash ) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();

        this.hash = calculateHash();
    }

    //Calculates the hash of the new block object being created. Uses custom hash method stored in StringUtil.java.
    // Takes input of previous block's hash, the timestamp of the new block's creation, the nonce of the block since
    // this is a cryptocurrency mining simulator, and the block's data, which is a plaintext message.
    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        data
        );
        return calculatedhash; //returns the new calculated hash.
    }

    //Increases nonce value until hash target is reached. Keep hashing the block until the block's calculated hash is equal to the target hash.
    //Accepts the difficulty of the block to mine as a parameter. The higher the difficulty, the longer it will take the block to mine.
    public void mineBlock(int difficulty) {
        //Set the target hash so that the block will stop being hashed once it reaches the target hash.
        String target = StringUtil.getDificultyString(difficulty); //Create a string with difficulty * "0"
        //Calculate the hash of the block and increase the nonce for every iteration until the hash substring reaches the target value
        //the difficulty sets the number of zeros at the beginning of the hash. We are hashing until the number of zeros at the beginning of
        // the hash value is equal to the integer set in the difficulty variable.
        while(!hash.substring( 0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }

    //A simple getter to return our block data for decryption to make sure that it works
    public String getData() {return data;}

}
