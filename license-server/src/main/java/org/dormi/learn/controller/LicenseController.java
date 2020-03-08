/**
 * @author : dormi330
 * @date : 2020-03-05
 * description : 文件描述
 */

package org.dormi.learn.controller;


import org.dormi.learn.pojo.dto.CreateLicenseDTO;
import org.dormi.learn.service.LicenseCheckService;
import org.dormi.learn.service.LicenseFileService;
import org.dormi.learn.utils.license.LicenseFile;
import org.dormi.learn.utils.license.LicenseFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;


@Controller
@RequestMapping("/api/v1")
public class LicenseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private LicenseFileService licenseFileService;

    @Autowired
    LicenseCheckService licenseCheckService;

    @ResponseBody
    @RequestMapping(value = {"/debug/licenseFile"}, method = {RequestMethod.POST})
    public String createNewLicenseDebug(HttpServletRequest request, HttpServletResponse response, @RequestBody CreateLicenseDTO dto) {
        LicenseFile licenseFile = licenseFileService.getLicenseFile(dto);

        return LicenseFileUtils.toStringContent(licenseFile);
    }

    @RequestMapping(value = {"/licenseFile"}, method = {RequestMethod.POST})
    public void createNewLicense(HttpServletRequest request, HttpServletResponse response, @RequestBody CreateLicenseDTO dto) {
        LicenseFile licenseFile = licenseFileService.getLicenseFile(dto);

        String fileName = LicenseFileUtils.getFileName(licenseFile);
        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        try {
            response.getOutputStream().write(LicenseFileUtils.toStringContent(licenseFile).getBytes(UTF_8));
            response.getOutputStream().flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = {"/check"}, method = {RequestMethod.POST})
    public String checkLicense(@RequestBody String str) {
        return licenseCheckService.check(str);
    }
}