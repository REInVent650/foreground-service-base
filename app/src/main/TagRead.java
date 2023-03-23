import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;

public class TagRead extends Activity {

    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This activity does not have a UI
        // Set the content view to null
        setContentView(null);

        // Get the NFC adapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Enable NFC foreground dispatch to handle tag reading
        // Define the intent filter to match NDEF data type
        Intent intent = new Intent(this, getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        nfcAdapter.enableForegroundDispatch(
                this,
                PendingIntent.getActivity(this, 0, intent, 0),
                null,
                new String[][]{{"android.nfc.action.NDEF_DISCOVERED"}}
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Disable NFC foreground dispatch when the activity is in the background
        nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // Get the NDEF message from the intent
        NdefMessage[] messages = getNdefMessages(intent);
        if (messages != null) {
            // Save the data from the tag
            saveTagData(messages[0]);
        }
    }

    private NdefMessage[] getNdefMessages(Intent intent) {
        // Extract the NDEF message from the intent
        NdefMessage[] messages = null;
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            // Get the raw NDEF message
            byte[] rawMsgs = intent.getByteArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                // Convert the raw message to NDEF message
                messages = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    messages[i] = (NdefMessage) NdefMessage.parse(rawMsgs[i]);
                }
            }
        }
        return messages;
    }

    private void saveTagData(NdefMessage message) {
        // Save the NDEF message to a database or file
        // You can access your application's context using getApplicationContext() method
        // Example:
        // MyDatabaseHelper dbHelper = new MyDatabaseHelper(getApplicationContext());
        // SQLiteDatabase db = dbHelper.getWritableDatabase();
        // ContentValues values = new ContentValues();
        // values.put("tag_data", message.toByteArray());
        // db.insert("tag_table", null, values);
        // db.close();
    }
}
