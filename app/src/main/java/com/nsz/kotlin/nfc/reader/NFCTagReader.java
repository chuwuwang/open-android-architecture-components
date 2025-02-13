package com.nsz.kotlin.nfc.reader;

import android.nfc.Tag;
import android.nfc.tech.TagTechnology;

import androidx.annotation.NonNull;

import com.nsz.kotlin.nfc.config.ConfigBuilder;
import com.nsz.kotlin.nfc.config.Technologies;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Interface to all NFCTagReader-Classes.
 */
public abstract class NFCTagReader {
    final TagTechnology mReader;

    NFCTagReader(TagTechnology reader) {
        mReader = reader;
    }

    /**
     * Indicates whether the connection is open.
     *
     * @return true if the connection is open, false otherwise.
     */
    public boolean isConnected() {
        try {
            return mReader.isConnected();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Opens the connection to the NFC tag.
     */
    public void connect() {
        try {
            mReader.connect();
        } catch (IOException e) {
            // Log.e("NFCTagReader", "IOException while connecting to the NFC tag", e);
        }
    }

    /**
     * Closes the connection to the NFC tag.
     */
    public void close() {
        try {
            mReader.close();
        } catch (IOException e) {
            // Log.e("NFCTagReader", "IOException while closing the connection to the NFC tag", e);
        }
    }

    /**
     * Sends a raw command to the NFC chip, receiving the answer as a byte array.
     *
     * @param command byte[]-representation of the command to be sent.
     * @return byte[]-representation of the answer of the NFC chip.
     */
    public byte[] transceive(byte[] command) {
        try {
            // There is no common interface for TagTechnology, using reflection.
            Method transceive = mReader.getClass().getMethod("transceive", byte[].class);
            return (byte[]) transceive.invoke(mReader, command);
        } catch (Exception e) {
            // Log.e("NFCTagReader", "Error during transceive operation", e);
            return null;
        }
    }

    /**
     * Returns a config object with options set to emulate this tag.
     *
     * @return ConfigBuilder object with the tag configuration.
     */
    @NonNull
    public abstract ConfigBuilder getConfig();

    /**
     * Picks the highest available technology for a given Tag.
     *
     * @param tag the NFC tag.
     * @return the appropriate NFCTagReader instance.
     */
    @NonNull
    public static NFCTagReader create(Tag tag) {
        List<String> technologies = Arrays.asList(tag.getTechList());

        // Look for higher layer technology
        if (technologies.contains(Technologies.IsoDep)) {
            // An IsoDep tag can be backed by either NfcA or NfcB technology
            if (technologies.contains(Technologies.A)) {
                return new IsoDepReader(tag, Technologies.A);
            } else if (technologies.contains(Technologies.B))
                return new IsoDepReader(tag, Technologies.B);
          //  } else
                // Log.e("SuperCard", "Unknown tag technology backing IsoDep: " + TextUtils.join(", ", technologies));

        }

        for (String tech : technologies) {
            switch (tech) {
                case Technologies.A:
                    return new NfcAReader(tag);
                case Technologies.B:
                    return new NfcBReader(tag);
            }
        }

        throw new UnsupportedOperationException("Unknown Tag type");
    }
}
