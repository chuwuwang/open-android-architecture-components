package com.nsz.kotlin.nfc;

import com.github.devnied.emvnfccard.exception.CommunicationException;
import com.github.devnied.emvnfccard.parser.IProvider;
import com.nsz.kotlin.nfc.reader.IsoDepReader;
import com.nsz.kotlin.nfc.reader.NFCTagReader;

public class Provider implements IProvider {

//    private IsoDep mTagCom;
//
//    @Override
//    public byte[] transceive(final byte[] pCommand) throws CommunicationException {
//
//        byte[] response;
//        try {
//            // send command to emv card
//            response = mTagCom.transceive(pCommand);
//        } catch (IOException e) {
//            throw new CommunicationException(e.getMessage());
//        }
//
//        return response;
//    }
//
//    @Override
//    public byte[] getAt() {
//        // For NFC-A
//        return mTagCom.getHistoricalBytes();
//        // For NFC-B
//        // return mTagCom.getHiLayerResponse();
//    }
//
//
//    public void setmTagCom(final IsoDep mTagCom) {
//        this.mTagCom = mTagCom;
//    }

    private final NFCTagReader mTagReader;

    public Provider(NFCTagReader tagReader) {
        this.mTagReader = tagReader;
    }

    @Override
    public byte[] transceive(byte[] command) throws CommunicationException {
        try {
            if (mTagReader != null && mTagReader.isConnected()) {
                return mTagReader.transceive(command);
            } else {
                throw new CommunicationException("TagReader is null or not connected");
            }
        } catch (Exception e) {
            throw new CommunicationException(e.getMessage());
        }
    }

    public void close() {
        try {
            if (mTagReader != null) {
                mTagReader.close();
            }
        } catch (Exception e) {
            // Log.e(TAG, "Error closing TagReader", e);
        }
    }

    public void log(String message) {
        // Log.d(TAG, message);
    }

    @Override
    public byte[] getAt() {
        if (mTagReader instanceof IsoDepReader) {
            return ((IsoDepReader) mTagReader).getHistoricalBytes();
        }
        return null;
    }

}
