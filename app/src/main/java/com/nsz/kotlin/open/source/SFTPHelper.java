package com.nsz.kotlin.open.source;

import android.annotation.SuppressLint;
import android.os.Environment;

import com.google.android.gms.security.ProviderInstaller;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.nsz.kotlin.App;
import com.nsz.kotlin.open.source.ftp.SSLSessionReuseFTPSClient;
import com.nsz.kotlin.ux.common.CommonLog;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Properties;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class SFTPHelper {

    private static Session session = null;
    private static Channel channel = null;
    private static ChannelSftp chSftp = null;

    public static void connect(String host, String username, String password, int port) {
        try {

            // FTPClient ftpsClient = new FTPClient();
            FTPSClient ftpsClient = new FTPSClient();

//            SecureRandom secureRandom = new SecureRandom();
//            TrustManager[] trustManagers = { x509TrustManager };
//            SSLContext sslContext = SSLContext.getInstance("SSL");
//            sslContext.init(null, trustManagers, secureRandom);
//            // sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
//            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            InputStream clientCertInputStream = App.context.getAssets().open("flash_trader_rsa4096");
            SSLSocketFactory socketFactory = createSocketFactory(clientCertInputStream, "");

            // ftpsClient.setKeyManager();
            ftpsClient.setTrustManager(x509TrustManager);
            ftpsClient.setSocketFactory(socketFactory);

            FTPClientConfig config = new FTPClientConfig();
            ftpsClient.configure(config);
            ftpsClient.connect(host, port);
            ftpsClient.login(username, password);
            int reply = ftpsClient.getReplyCode();
            boolean positiveCompletion = FTPReply.isPositiveCompletion(reply);
            CommonLog.e("replyCode:" + reply);
            CommonLog.e("positiveCompletion:" + positiveCompletion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void connectBy(String host, String username, String password, int port) {
        try {
            SSLSessionReuseFTPSClient ftpsClient = new SSLSessionReuseFTPSClient();
            FTPClientConfig config = new FTPClientConfig();
            ftpsClient.configure(config);
            ftpsClient.connect(host, port);
            ftpsClient.login(username, password);
            int reply = ftpsClient.getReplyCode();
            boolean positiveCompletion = FTPReply.isPositiveCompletion(reply);
            CommonLog.e("replyCode:" + reply);
            CommonLog.e("positiveCompletion:" + positiveCompletion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void connectByJSch(String host, String username, String password, int port) {
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(username, host, port);
            session.setConfig("PreferredAuthentications", "password");
            session.setPassword(password);
            session.setTimeout(100000);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            // session.setConfig(config);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            SFTPHelper.download("current/HandShaker_1.2.0.apk", Environment.getExternalStorageDirectory().getAbsolutePath() + "/Flash/" + System.currentTimeMillis() + ".apk");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void download(String ftpPath, String downloadPath) {
        try {
            channel = session.openChannel("sftp");
            channel.connect();
            chSftp = (ChannelSftp) channel;
            chSftp.get(ftpPath, downloadPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            chSftp.quit();
            channel.disconnect();
            session.disconnect();
        }
    }

    public static SSLSocketFactory createSocketFactory(InputStream privateKeyInputStream, String password) throws Exception {
        BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();
        Security.addProvider(bouncyCastleProvider);

        // X509Certificate caX509Certificate = parseCert(caCertInputStream);
        // X509Certificate clientX509Certificate = parseCert(clientCertInputStream);

        InputStreamReader reader = new InputStreamReader(privateKeyInputStream);
        PemReader pemReader = new PemReader(reader);
        byte[] content = pemReader.readPemObject().getContent();
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(content);
        // PrivateKey privateKey = KeyFactory.getInstance("OPENSSH").generatePrivate(privateKeySpec);

        String defaultType = KeyStore.getDefaultType();
        // KeyStore caKeyStore = KeyStore.getInstance(defaultType);
        // caKeyStore.load(null, null);
        // caKeyStore.setCertificateEntry("ca-certificate", caX509Certificate);

        String defaultAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(defaultAlgorithm);
        // trustManagerFactory.init(caKeyStore);

        defaultType = KeyStore.getDefaultType();
        // java.security.cert.Certificate[] chain = new java.security.cert.Certificate[] { clientX509Certificate };
        java.security.cert.Certificate[] chain = new java.security.cert.Certificate[] {};
        KeyStore keyStore = KeyStore.getInstance(defaultType);
        keyStore.load(null, null);
        // keyStore.setCertificateEntry("certificate", clientX509Certificate);
        // keyStore.setKeyEntry("private-key", privateKey, password.toCharArray(), chain);

        char[] charArray = password.toCharArray();
        defaultAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(defaultAlgorithm);
        keyManagerFactory.init(keyStore, charArray);

        SecureRandom secureRandom = new SecureRandom();
        TrustManager[] trustManagers = { x509TrustManager };
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagers, secureRandom);
        // sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
        return sslContext.getSocketFactory();
    }

    @SuppressLint("CustomX509TrustManager")
    private final static X509TrustManager x509TrustManager = new X509TrustManager() {

        @SuppressLint("TrustAllX509TrustManager")
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @SuppressLint("TrustAllX509TrustManager")
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

    };

}