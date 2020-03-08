/**
 * @author : dormi330
 * @date : 2020-03-06
 * description : 文件描述
 */

package org.dormi.learn.license;

import org.dormi.learn.utils.license.LicenseFilePlain;
import org.dormi.learn.utils.license.LicenseFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LicenseFileReader {
    private static Logger logger = LoggerFactory.getLogger(LicenseFileReader.class);

    private final String product;

    private String filePathPattern = "/Users/dormi330/tmp/{product}.license.txt";

    public LicenseFileReader(String product) {
        this.product = product;
    }

    public LicenseFilePlain get() throws LicenseException {
        String filePath = filePathPattern.replace("{product}", product);
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new LicenseException(LicenseException.license_file_not_found, "license_file_not_found, path=" + filePath);
        }

        LicenseFilePlain plainFile = null;
        try {
            plainFile = LicenseFileUtils.fromStringContent(content);
        } catch (Exception ex) {
            throw new LicenseException(LicenseException.license_file_invalid, "license_file_invalid, path=" + filePath);
        }

        if (plainFile == null) {
            throw new LicenseException(LicenseException.license_file_invalid, "license_file_invalid, path=" + filePath);
        }

        return plainFile;
    }
}

