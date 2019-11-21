package org.tensorflow.lite.examples.classification.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.examples.classification.R;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static android.bluetooth.BluetoothDevice.TRANSPORT_LE;
import static android.bluetooth.BluetoothGatt.GATT_SUCCESS;


public class Bluetooth extends AppCompatActivity  {

    private static final String TAG = "BLE: ";
    public TextView TempInfo;
    public TextView HumidityInfo;
    public TextView PressureInfo;

    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
    BluetoothDevice bluetoothDevice;

    //ClimateServices UUID
    UUID CLIMATESENSORS_UUID = UUID.fromString("db2d0d1f-68d4-4bd1-8692-d4cb7986b3c1");

    //Temp Characteristic UUID
    UUID TEMPERATURE_UUID = UUID.fromString("9475d433-6f83-460d-8fcc-eefd6758db50");

    //Hum Characteristic UUID
    UUID HUMIDITY_UUID = UUID.fromString("0d53afd9-c0a5-4de4-971f-30ef87bdb046");

    //Hum Characteristic UUID
    UUID PRESSURE_UUID = UUID.fromString("d770192c-a0dc-4e3b-854f-72a47247930b");

    UUID[] serviceUUIDS = new UUID[]{CLIMATESENSORS_UUID};

    List<ScanFilter> filters = null;

    byte[] currentTemperature;
    byte[] currentHumidity;
    byte[] currentPressure;

    //Convert Incoming Sensor Data from byte to float.

    public static float convertToFloat(byte[] array) {
        ByteBuffer buffer = ByteBuffer.wrap(array);
        return buffer.getFloat();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_sensor_data);
        TempInfo = findViewById(R.id.temp);
        HumidityInfo = findViewById(R.id.humidity);
        PressureInfo = findViewById(R.id.pressure);

        //Check that bluetooth is enabled - If not, request to turn on
        if (bluetoothAdapter == null || (!bluetoothAdapter.isEnabled())) {
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetoothIntent, 1);
        }

        ScanSettings scanSettings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
                .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
                .setNumOfMatches(ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT)
                .setReportDelay(0L)
                .build();

        if (serviceUUIDS != null) {
            filters = new ArrayList<>();
            for (UUID serviceUUID : serviceUUIDS) {
                ScanFilter filter = new ScanFilter.Builder()
                        .setServiceUuid(new ParcelUuid(serviceUUID))
                        .build();
                filters.add(filter);
            }
        }

        //if scanner is Initialized, Start scanning for device and call back if found
        if (bluetoothLeScanner != null) {
            bluetoothLeScanner.startScan(filters, scanSettings, scanCallback);
        } else {
            Log.d(TAG, "Couldn't get Scanner");
        }
    }

    private final ScanCallback scanCallback = new ScanCallback() {

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);

            bluetoothDevice = result.getDevice();
            bluetoothDevice.connectGatt(getApplicationContext(), true, bluetoothGattCallback, TRANSPORT_LE);
        }


        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.d(TAG, "OnScanFailed Called.");
        }
    };

    private final BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {


        public List<BluetoothGattCharacteristic> chars = new ArrayList<>();

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);

            if (status == GATT_SUCCESS) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    gatt.discoverServices();

                    final List<BluetoothGattService> services = gatt.getServices();
                    Log.i(TAG, String.format(Locale.ENGLISH, "discovered %d services for %s", services.size(), bluetoothAdapter.getName()));
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    gatt.close();
                } else {
                }
            } else {

                gatt.close();
            }
        }


        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);

            if (status == GATT_SUCCESS) {
                BluetoothGattCharacteristic temp = gatt.getService(CLIMATESENSORS_UUID).getCharacteristic(TEMPERATURE_UUID);
                BluetoothGattCharacteristic humidity = gatt.getService(CLIMATESENSORS_UUID).getCharacteristic(HUMIDITY_UUID);
                BluetoothGattCharacteristic pressure = gatt.getService(CLIMATESENSORS_UUID).getCharacteristic(PRESSURE_UUID);

                chars.add(temp);
                chars.add(humidity);
                chars.add(pressure);

                requestCharacteristics(gatt);
            }
        }

        public void requestCharacteristics(BluetoothGatt gatt) {
            gatt.readCharacteristic(chars.get(chars.size() - 1));
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic
                Characteristic, int status) {
            super.onCharacteristicRead(gatt, Characteristic, status);
            if (status == 0) {
                System.out.println("char uuid:"+Characteristic.getUuid());
                if (Characteristic.getUuid().equals(TEMPERATURE_UUID)) {
                    currentTemperature = Characteristic.getValue();
                    System.out.println("char uuid:"+Characteristic.getUuid()+"temprature:"+TEMPERATURE_UUID);
                    for (byte b : currentTemperature) {
                        System.out.println(b);
//                        SensorData.setTemperature(currentTemperature);
                        TempInfo.setText(b);
                    }

                } else if (Characteristic.getUuid().equals(HUMIDITY_UUID)) {
                    currentHumidity = Characteristic.getValue();
                    System.out.println("char uuid:"+Characteristic.getUuid()+"humidity:"+HUMIDITY_UUID);
                    for (byte b1 : currentHumidity) {
                        System.out.println(b1);
//                        SensorData.setHumidity(convertToFloat(currentHumidity));
                        HumidityInfo.setText(b1);
                    }
                } else if (Characteristic.getUuid().equals(PRESSURE_UUID)) {
                    currentPressure = Characteristic.getValue();
                    System.out.println("char uuid:"+Characteristic.getUuid()+"presure:"+PRESSURE_UUID);
                    System.out.println("presure value:"+PressureInfo);

                    for (byte b2 : currentPressure) {
                        System.out.println(b2);

                        //                     SensorData.setPressure(convertToFloat(currentPressure);
                        PressureInfo.setText(b2);
                    }
                }
            }
        }
    };
}
