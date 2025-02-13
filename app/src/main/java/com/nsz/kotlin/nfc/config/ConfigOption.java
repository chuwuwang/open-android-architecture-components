package com.nsz.kotlin.nfc.config;

import com.nsz.kotlin.utils.Util;

/**
 * Represents a single NCI configuration option with an option code, its length and data
 */
public class ConfigOption {

    private final byte[] mData;
    private final OptionType mID;

    ConfigOption(OptionType ID, byte[] data) {
        mID = ID;
        mData = data;
    }

    ConfigOption(OptionType ID, byte data) {
        this(ID, new byte[] { data });
    }

    public int len() {
        return mData.length;
    }

    public void push(byte[] data, int offset) {
        data[offset] = mID.getID();
        data[offset + 1] = (byte) mData.length;

        System.arraycopy(mData, 0, data, offset + 2, mData.length);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("Type: ");
        String idString = mID.toString();
        result.append(idString);

        if (mData.length > 1) {
            result.append(" (");
            result.append(mData.length);
            result.append(")");
        }

        result.append(", Value: 0x");
        String hexString = Util.bytesToHex(mData);
        result.append(hexString);

        return result.toString();
    }

}
