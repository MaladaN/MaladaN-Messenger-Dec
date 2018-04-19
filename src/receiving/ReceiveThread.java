package receiving;

import crypto.MySignalProtocolStore;
import crypto.PreKeyPublic;
import crypto.ResponsePreKeyBundle;
import crypto.SignalCrypto;
import net.i2p.client.streaming.I2PSocket;
import org.whispersystems.libsignal.state.SignedPreKeyRecord;
import receiving.receiveables.PreKeyBundleRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class ReceiveThread implements Runnable {

    private Thread t;
    private ObjectInputStream objectsIn;
    private ObjectOutputStream objectsOut;
    private I2PSocket sock;
    private MySignalProtocolStore protocolStore;
    private String ourAddress;
    private String theirAddress;

    public ReceiveThread(I2PSocket sock, String ourAddress) {
        this.sock = sock;
        this.ourAddress = ourAddress;
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
                    handleResponsePreKeyBundle((ResponsePreKeyBundle) incomingObject);
                } else if (incomingObject instanceof PreKeyBundleRequest) {
                    handlePreKeyBundleRequest((PreKeyBundleRequest) incomingObject);
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

    //handle a user requesting a bundle so that they can send us a message.
    private void handlePreKeyBundleRequest(PreKeyBundleRequest bundleRequest) throws IOException {
        this.theirAddress = bundleRequest.getBase64RequesterUsername();

        List<SignedPreKeyRecord> signedKeys = protocolStore.loadSignedPreKeys();

        //get the newest key from the list. (loadSignedPreKeys orders by ascending ID)
        SignedPreKeyRecord record = signedKeys.get(0);

        int registrationId = protocolStore.getLocalRegistrationId();
        PreKeyPublic ourPubPreKey = protocolStore.loadRandomPreKey();
        int signedPreKeyId = record.getId();
        byte[] signedPreKeyPublic = record.getKeyPair().getPublicKey().serialize();
        byte[] signedPreKeySignature = record.getSignature();
        byte[] identityKey = protocolStore.getIdentityKeyPair().getPublicKey().serialize();

        if (ourPubPreKey == null) {
            SignalCrypto.generateMorePreKeys(100);
            ourPubPreKey = protocolStore.loadRandomPreKey();
        }

        if (ourPubPreKey != null) {
            ResponsePreKeyBundle newBundle = new ResponsePreKeyBundle(registrationId, ourPubPreKey,
                    signedPreKeyId, signedPreKeyPublic, signedPreKeySignature, identityKey, ourAddress);

            objectsOut.writeObject(newBundle);
            objectsOut.flush();
        }
    }

    void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
}
