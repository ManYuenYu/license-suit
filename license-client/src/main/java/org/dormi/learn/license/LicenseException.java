/**
 * @author : dormi330
 * @date : 2020-03-06
 * description : 文件描述
 */

package org.dormi.learn.license;

public class LicenseException extends Exception {

    public static final int license_file_not_found = 10;
    public static final int license_file_invalid = 20;
    public static final int license_server_unreachable = 30;
    public static final int license_server_not_200 = 40;
    public static final int license_server_response_invalid = 50;
    public static final int license_decode_fail = 60;
    public static final int license_expired = 70;

    private int code;

    public LicenseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public LicenseException(int code) {
        this(code, null);
    }

    public int getCode() {
        return code;
    }
}
