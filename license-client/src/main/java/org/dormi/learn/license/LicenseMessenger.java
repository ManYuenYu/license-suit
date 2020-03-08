/**
 * @author : dormi330
 * @date : 2020-03-06
 * description : 与服务端进行通讯并得到结果
 */

package org.dormi.learn.license;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.codec.binary.Base64;
import org.dormi.learn.utils.crypt.CustomCryptor;
import org.dormi.learn.utils.crypt.JsonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.UTF_8;

public class LicenseMessenger {

    private static Logger logger = LoggerFactory.getLogger(LicenseMessenger.class);

    private static String getResponse(String serverAddr, String data) throws LicenseException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(serverAddr)
                .post(RequestBody.create(data.getBytes(UTF_8)))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() != 200) {
                throw new LicenseException(LicenseException.license_server_not_200, "license_server_not_200 response code:" + response.code() + ", body:" + response.body().toString());
            }
            return response.body().string();
        } catch (Exception ex) {
            throw new LicenseException(LicenseException.license_server_unreachable, "license_server_unreachable fail to connect to server: " + serverAddr);
        }
    }

    public static LicenseCheckResponseDTO checkToServer(String serverAddr, LicenseCheckRequestDTO reqestData) throws LicenseException {

        String base64String = Base64.encodeBase64String(reqestData.toString().getBytes(UTF_8));
        String dataEncrypt = CustomCryptor.encode(base64String);
        String resp = getResponse(serverAddr, dataEncrypt);

        if (logger.isDebugEnabled()) {
            logger.debug("resp=" + resp);
        }

        if (resp == null) {
            throw new LicenseException(LicenseException.license_server_response_invalid, "license_server_response_invalid, response null, serverAddr=" + serverAddr);
        }

        String crypt1 = CustomCryptor.decode(resp);
        if (crypt1 == null) {
            throw new LicenseException(LicenseException.license_decode_fail, "license_decode_fail, custom decode fail, serverAddr=" + serverAddr + ", src string=" + resp);
        }

        String plain = null;
        try {
            plain = new String(Base64.decodeBase64(crypt1));
        } catch (Exception ex) {
            throw new LicenseException(LicenseException.license_decode_fail, "license_decode_fail, base64 decode fail, serverAddr=" + serverAddr + ", src string=" + crypt1);
        }

        try {
            return JsonSerializer.jsonToObj(plain, LicenseCheckResponseDTO.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new LicenseException(LicenseException.license_server_response_invalid, "license_server_response_invalid, after decode cannot map to LicenseCheckResponseDTO, serverAddr=" + serverAddr + ", plain string=" + plain);
        }
    }

}

