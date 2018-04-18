import crypto.SignalCrypto;
import receiving.ReceiveMain;

public class ClientMain {

    public static void main(String[] args) {
        //initialize signal identity
        SignalCrypto.initStore();

        //open server-socket to receive messages
        ReceiveMain receiveMain = new ReceiveMain();
        receiveMain.start();
    }
}
