package receiving;

import crypto.ResponsePreKeyBundle;
import net.i2p.client.streaming.I2PSocket;
import receiving.receiveables.PreKeyBundleRequest;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ReceiveThread implements Runnable {

    private Thread t;
    private ObjectInputStream objectsIn;
    private I2PSocket sock;

    public ReceiveThread(I2PSocket sock) {
        this.sock = sock;
    }

    @Override
    public void run() {
        try {
            objectsIn = new ObjectInputStream(sock.getInputStream());

            Object incomingObject;
            while((incomingObject = objectsIn.readObject()) != null) {

                if (incomingObject instanceof ResponsePreKeyBundle) {

                } else if (incomingObject instanceof PreKeyBundleRequest) {

                }
            }

        } catch (IOException e) {
            System.out.println("Something is wrong with the socket.");

        } catch (ClassNotFoundException e) {
            System.out.println("A class we don't have was received.");

        } finally {
            try {
                objectsIn.close();
            } catch (IOException e) {
                System.out.println("Error closing the socket.");
            }
        }
    }

    void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
}
