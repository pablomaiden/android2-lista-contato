package br.com.meuscontatos.principal.util;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by patricknasc on 01/10/16.
 */

public class ChatBluetoothDelegate {

    private static final String TAG = "chat";
    private BluetoothSocket socket;
    private InputStream in;
    private OutputStream out;
    private ChatListener listener;
    private boolean running;

    public interface ChatListener {
        public void onMessageReceived(String msg); //Sempre que uma msg é lida no InputStream o evento eh entregue aqui.
    }

    public ChatBluetoothDelegate(BluetoothSocket socket, ChatListener listener) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
        this.listener = listener;
        this.running = true;
    }

    //Iniciando a leitura da InputStream
    public void start(){
        new Thread(){

            @Override
            public void run(){

                running = true;
                //Fazendo a leitura
                byte [] bytes = new byte[1024];
                int length;
                //Fica em loop para receber as mensagens
                while (running){
                    try{
                        Log.d(TAG, "Aguardando mensagem");
                        //Lê a mensagem (fica bloqueado até receber)
                        length = in.read(bytes);
                        String msg = new String(bytes, 0, length);
                        Log.d(TAG, "Mensagem: "+msg);
                        //Recebeu a mensagem (informando ao Listener)
                        listener.onMessageReceived(msg);
                    } catch (Exception e){
                        running = false;
                        Log.e(TAG, "Error: "+ e.getMessage(), e);
                    }
                }
            }
        }.start();
    }


    //Método para enviar mensagens ao socket aberto, escrevendo-as na OutputStream
    public void sendMessage(String msg) throws IOException {
        if(out != null){ out.write(msg.getBytes()); }
    }

    public void stop(){
        running = false;
        try{
            if(socket != null) { socket.close(); }
            if(in != null ) { in.close(); }
            if(out != null ) { out.close(); }
        } catch (IOException e) { }
    }
}

