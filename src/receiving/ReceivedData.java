package receiving;

import crypto.ResponsePreKeyBundle;

import java.util.Vector;

public class ReceivedData {
    private static Vector<ResponsePreKeyBundle> receivedPreKeyBundles = new Vector<>();

    public static Vector<ResponsePreKeyBundle> getReceivedPreKeyBundles() {
        return new Vector<>(receivedPreKeyBundles);
    }

    public static boolean addReceivedPreKeyBundle(ResponsePreKeyBundle bundle) {
        if (bundle != null) {
            receivedPreKeyBundles.add(bundle);
            return true;
        }
        return false;
    }
}
