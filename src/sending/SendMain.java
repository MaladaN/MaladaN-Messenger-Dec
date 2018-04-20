package sending;

import crypto.ResponsePreKeyBundle;

import java.util.ArrayList;
import java.util.List;

public class SendMain implements Runnable {

    private static List<ResponsePreKeyBundle> outgoingBundles = new ArrayList<>();

    public static boolean addBundle(ResponsePreKeyBundle outBundle) {
        if (outBundle != null) {
            outgoingBundles.add(outBundle);
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        //setup connections based on (currently) outgoing bundles.
    }
}
