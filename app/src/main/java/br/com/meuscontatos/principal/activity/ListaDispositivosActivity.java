package br.com.meuscontatos.principal.activity;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.Set;

/**
 * Created by patricknasc on 21/09/16.
 */

public class ListaDispositivosActivity extends ListActivity {

    private BluetoothAdapter myBluetoothAdapter = null;
    private String MAC_ADDRESS = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String> arrayBluetoothAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> pairedDevices = myBluetoothAdapter.getBondedDevices();

        if(pairedDevices.size() > 0){
            for(BluetoothDevice device : pairedDevices){
                String deviceName = device.getName();
                String deviceMac = device.getAddress();
                arrayBluetoothAdapter.add(deviceName + "\n" + deviceMac);
            }
        }
        setListAdapter(arrayBluetoothAdapter);


    }
}
