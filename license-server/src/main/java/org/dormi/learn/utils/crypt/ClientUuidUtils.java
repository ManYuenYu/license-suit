/**
 * @author : dormi330
 * @date : 2020-03-05
 * description : 文件描述
 */

package org.dormi.learn.utils.crypt;

import java.io.*;
import java.util.UUID;

public class ClientUuidUtils {

    public static String getClientUuid() {
        String uuid = getDockerId();
        if (uuid != null) {
            return uuid;
        }

        uuid = GetNetworkAddress.GetAddress("mac");
        if (uuid != null) {
            return uuid;
        }

        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getDockerId() {
        String path = "/proc/self/cgroup";
        try {
            return readFileAsString(path);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    private static String readFileAsString(String path) throws IOException {
        InputStream inputStream = new FileInputStream(path);
        return readFromInputStream(inputStream);
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

}
