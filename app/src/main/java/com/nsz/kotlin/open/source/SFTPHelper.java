package com.nsz.kotlin.open.source;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.Properties;

public class SFTPHelper {

    private static Session session = null;
    private static Channel channel = null;
    private static ChannelSftp chSftp = null;

    public static void connect(String host, String username, String password, int port) {
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(username, host, port);
            session.setPassword(password);
            session.setTimeout(100000);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
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

}