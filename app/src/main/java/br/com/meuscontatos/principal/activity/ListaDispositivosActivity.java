package br.com.meuscontatos.principal.activity;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

/**
 * Created by patricknasc on 21/09/16.
 */

public class ListaDispositivosActivity extends ListActivity {

    private BluetoothAdapter myBluetoothAdapter = null;
    static String MAC_ADDRESS = null;


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

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String itemText = ((TextView)v).getText().toString();

        String macAddress = itemText.substring(itemText.length() - 17); //MacAddress tem 17 caracteres
        //Toast.makeText(getApplicationContext(), "mac: "+macAddress, Toast.LENGTH_LONG).show();

        Intent intentGetMacAddress = new Intent();
        intentGetMacAddress.putExtra(MAC_ADDRESS, macAddress);
        setResult(RESULT_OK, intentGetMacAddress);
        finish();
    }
}
