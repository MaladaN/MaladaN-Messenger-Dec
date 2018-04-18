package receiving;

import crypto.MySignalProtocolStore;
import crypto.PreKeyPublic;
import crypto.ResponsePreKeyBundle;
import net.i2p.client.streaming.I2PSocket;
import receiving.receiveables.PreKeyBundleRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.interfaces.ECPublicKey;
import java.util.Random;

public class ReceiveThread implements Runnable {

    private Thread t;
    private ObjectInputStream objectsIn;
    private ObjectOutputStream objectsOut;
    private I2PSocket sock;
    private MySignalProtocolStore protocolStore;

    public ReceiveThread(I2PSocket sock) {
        this.sock = sock;
        protocolStore = new MySignalProtocolStore();
        try {
            this.objectsOut = new ObjectOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error creating the object output stream.");
        }
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

    private void handleResponsePreKeyBundle(ResponsePreKeyBundle bundle) {

    }

    private void handlePreKeyBundleRequest(PreKeyBundleRequest bundleRequest) {

        int registrationId = protocolStore.getLocalRegistrationId();
        PreKeyPublic ourPubPreKey = protocolStore.loadRandomPreKey();
        //TODO figure out an efficient way to have the signed pre key id here
        int signedPreKeyId;
        byte[] signedPreKeyPublic;
        byte[] signedPreKeySignature;
        byte[] identityKey = protocolStore.getIdentityKeyPair().getPublicKey().serialize();
    }

    void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
}
