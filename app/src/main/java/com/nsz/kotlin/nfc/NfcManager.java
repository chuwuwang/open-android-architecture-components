package com.nsz.kotlin.nfc;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.github.devnied.emvnfccard.exception.CommunicationException;
import com.github.devnied.emvnfccard.model.EmvCard;
import com.github.devnied.emvnfccard.parser.EmvTemplate;
import com.nsz.kotlin.nfc.reader.NFCTagReader;
import com.nsz.kotlin.utils.Util;
import com.nsz.kotlin.ux.common.ByteHelper;
import com.nsz.kotlin.ux.common.CommonLog;

public class NfcManager implements NfcAdapter.ReaderCallback {

    public static NfcManager mInstance;

    private final Activity mActivity;
    private final NfcAdapter mAdapter;

    private Tag currentTag;
    private boolean isMaster = false;               // Add this flag
    private boolean isPaused = false;
    private boolean mReaderMode = false;
    private final Handler handler = new Handler();

    private NFCTagReader mReader;

    public NfcManager(Activity activity) {
        mActivity = activity;
        mAdapter = NfcAdapter.getDefaultAdapter(activity);
        mInstance = this;
    }

    public static NfcManager getInstance() {
        return mInstance;
    }

    public void onResume() {
        setBroadcastReceiverEnabled(true);
        boolean bool = isEnabled() && ! isPaused;
        if (bool) {
            enableForegroundDispatch();
            enableDisableReaderMode();
        }
    }

    public void onPause() {
        setBroadcastReceiverEnabled(false);
        boolean bool = isEnabled();
        if (bool) {
            disableForegroundDispatch();
        }
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        if (isPaused) {
            return;
        }
        isMaster = true;
        currentTag = tag;

        mReader = NFCTagReader.create(tag);
        mReader.close();
        mReader.connect();

        byte[] build = mReader.getConfig().build();
        String hexString = ByteHelper.bytes2HexString(build);
        CommonLog.e("tag: " + tag + " - " + hexString);

        // Create provider
        Provider provider = new Provider(mReader);
        // Define config
        EmvTemplate.Config config = EmvTemplate.Config()
                .setContactLess(true)           // Enable contact less reading (default: true)
                .setReadAllAids(true)           // Read all aids in card (default: true)
                .setReadTransactions(true)      // Read all transactions (default: true)
                .setReadCplc(false)             // Read and extract CPCLC data (default: false)
                .setRemoveDefaultParsers(false) // Remove default parsers for GeldKarte and EmvCard (default: false)
                .setReadAt(true);               // Read and extract ATR/ATS and description
        // Create Parser
        EmvTemplate parser = EmvTemplate.Builder()
                .setProvider(provider)      // Define provider
                .setConfig(config)          // Define config
                //.setTerminal(terminal)    // (optional) you can define a custom terminal implementation to create APDU
                .build();
        try {
            EmvCard emvCard = parser.readEmvCard();
            String string = emvCard.toString();
            CommonLog.e("EmvCard: " + string);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendData(byte[] data) {
        byte[] reply = mReader.transceive(data);
        String hexString = ByteHelper.bytes2HexString(reply);
        CommonLog.e("sendData: " + hexString);
    }

    private void enableForegroundDispatch() {
        Class<? extends Activity> clazz = mActivity.getClass();
        Intent intent = new Intent(mActivity, clazz);
        PendingIntent pendingIntent = PendingIntent.getActivity(mActivity, 0, intent, PendingIntent.FLAG_MUTABLE);
        mAdapter.enableForegroundDispatch(mActivity, pendingIntent, null, null);
    }

    private void disableForegroundDispatch() {
        mAdapter.disableForegroundDispatch(mActivity);
    }

    private void enableDisableReaderMode() {
        if (mReaderMode) {
            int flags = NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_NFC_B;
            enableForegroundDispatch();
            mAdapter.enableReaderMode(mActivity, this, flags, null);
        } else {
            mAdapter.disableReaderMode(mActivity);
        }
    }

    public void pauseNfc() {
        isPaused = true;
        if (currentTag != null) {
            mAdapter.ignore(currentTag, 10000, null, null);
        }
        disableForegroundDispatch();
    }

    public void resumeNfc() {
        isPaused = false;
        mAdapter.ignore(currentTag, 0, null, null);
        Looper mainLooper = Looper.getMainLooper();
        new Handler(mainLooper).postDelayed(this::enableForegroundDispatch, 1000);
    }

    public void setReaderMode(boolean enabled) {
        mReaderMode = enabled;
        boolean bool = isEnabled();
        if (bool) {
            enableDisableReaderMode();
        }
    }

    private void setBroadcastReceiverEnabled(boolean enabled) {
        IntentFilter filter = new IntentFilter(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED);
        if (enabled) {
            mActivity.registerReceiver(mReceiver, filter);
        } else {
            mActivity.unregisterReceiver(mReceiver);
        }
    }

    public boolean isEnabled() {
        return hasNfc() && mAdapter.isEnabled();
    }

    public boolean hasNfc() {
        return mAdapter != null;
    }

    public boolean hasHce() {
        return mActivity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC_HOST_CARD_EMULATION);
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean bool = TextUtils.equals(intent.getAction(), NfcAdapter.ACTION_ADAPTER_STATE_CHANGED);
            if (bool) { }
        }

    };

}
