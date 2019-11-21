package org.tensorflow.lite.examples.classification.Blockchain;/*
package org.tensorflow.lite.examples.classification.Blockchain;

import android.os.Bundle;

import com.google.gson.Gson;

import org.tensorflow.lite.examples.classification.Blockchain.Block;
import org.tensorflow.lite.examples.classification.Bluetooth.Bluetooth;
import org.tensorflow.lite.examples.classification.model.SensorDataObject;

import java.util.ArrayList;

public class Main extends Bluetooth {
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 1;

    @Override
    protected void BlockChain(Bundle savedInstanceState) {


    //////BLOCKCHAIN BEGINS ///////

        System.out.println("Trying to Mine block 1... ");
    //call the addBlock method and pass a new block object with an AES encrypted POJO as the data for the block
    //the previous hash value passed into the new block object is zero because this is the genesis block
    // Get an instance of the sensor manager.

    SensorDataObject Temp = new SensorDataObject();
        Temp.setAmbient_temp(convertToFloat(u_temperature));
    Gson gson = new Gson();
    String jsonTemp = gson.toJson(Temp);
    String encryptedTemperature = AES.encrypt(jsonTemp, secretKey);
    // designates temperature block object as the genesis block
    addBlock(new Block(encryptedTemperature, "0"));
        TempInfo.setText(jsonTemp);

        System.out.println("Trying to Mine block 2... ");
    //call the addBlock method and pass a new block object with an AES encrypted POJO as the data for the block
    //the previous hash value passed into the new block object is zero because this is the genesis block

    SensorDataObject Hum = new SensorDataObject();
        Hum.setRelativeHumidity();
    String jsonHumidity = gson.toJson(Hum);
    String encryptedHumidity = AES.encrypt(jsonHumidity, secretKey);
    addBlock(new Block(encryptedHumidity, blockchain.get(blockchain.size() - 1).hash));
        HumidityInfo.setText(jsonHumidity);

        System.out.println("Trying to Mine block 3... ");
    //call the addBlock method and pass a new block object with an AES encrypted POJO as the data for the block
    //the previous hash value passed into the new block object is zero because this is the genesis block

    SensorDataObject pressure = new SensorDataObject();
        pressure.setPressure(convertToFloat(currentPressure));
    String jsonPressure = gson.toJson(pressure);
    String encryptedPressure = AES.encrypt(jsonPressure, secretKey);
    addBlock(new Block(encryptedPressure, blockchain.get(blockchain.size() - 1).hash));
        PressureInfo.setText(jsonHumidity);

    //call the isChainValid() method to check the validity of the block hashes
    String blockchainJson = StringUtil.getJson(blockchain);
        System.out.println("\nThe block chain: ");
        System.out.println(blockchainJson);

        for (int i = 0; i < blockchain.size(); i++) {
        System.out.println("\nDecrypted block data for block #" + (i + 1) + ": " + AES.decrypt(blockchain.get(i).getData(), secretKey));
    }
}

    //method for checking the integriy of the blockchain
    public static Boolean isChainValid() {
        //initialization of local variables for the method
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        //loop through the entire blockchain to check hashes:
        for (int i = 1; i < blockchain.size(); i++) {
            //set the local variables to reference the current block in the chain and the previous block in the chain
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            //calculate the hash of the current block again and compare it to the current block's hash that was calculated before. If the hash changed in the time
            // since the block was initially created, the entire chain is invalid and return false
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current Hashes not equal");
                return false;
            }
            //calculate the hash of the previous block again and compare it to the previous block's hash that was calculated before. If the hash changed in the time
            // since the block was initially created, the entire chain is invalid and return false
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            //If the current block hasn't been processed through the mining method, assume the chain is invalid and return false.
            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        //If everything looks ok, return true
        return true;
    }

    //method for adding a new block into the blockchain, accepts a Block object as a parameter
    public static void addBlock(Block newBlock) {
        //make the computer do work by mining the passed block before adding the block to the blockchain arraylist.
        // Pass in the difficulty of mining the block that was defined above.
        //This makes it harder or easier for the computer to mine the block.
        newBlock.mineBlock(difficulty);
        blockchain.add(newBlock);
    }

/////BLOCKCHAIN ENDS ////////


}
*/