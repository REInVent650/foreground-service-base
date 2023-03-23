package com.reinvent.foreground;

import android.content.Context;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.NfcManager;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;

public class NfcUtil {
    // create an interface with one method
    public interface Listener {
        void onNfcEvent(NfcEvent event);
    }

    // create an instance
    private Listener listener;

    // method to set the instance
    public void setListener(Listener l) {
        listener = l;
    }

    private final NfcManager nfcManager;
    private final NfcAdapter nfcAdapter;
    private final NfcAdapter.CreateNdefMessageCallback createNdefMessageCallback;

    // create constructor with context as argument
    public NfcUtil(Context context) {
        // create instance of nfc manager
        nfcManager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);

        // create instance of nfc adapter
        nfcAdapter = nfcManager.getDefaultAdapter();

        // create the NDEF message callback
        createNdefMessageCallback = new NfcAdapter.CreateNdefMessageCallback() {
            @Override
            public NdefMessage createNdefMessage(NfcEvent event) {
                if (listener != null) {
                    // Pass the NfcEvent to the listener
                    listener.onNfcEvent(event);
                }
                return null;
            }
        };
    }

    // create a method to register
    // for NFC events
    public void register() {
        if (nfcAdapter != null) {
            // call nfc adapter's setNdefPushMessageCallback
            // and pass the createNdefMessageCallback instance
            nfcAdapter.setNdefPushMessageCallback(createNdefMessageCallback, null);
        }
    }

    // create a method to unregister
    // from NFC events
    public void unregister() {
        if (nfcAdapter != null) {
            // call nfc adapter's setNdefPushMessageCallback
            // and pass null as argument to unregister
            nfcAdapter.setNdefPushMessageCallback(null, null);
        }
    }
}
