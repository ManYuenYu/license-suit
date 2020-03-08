/**
 * @author : dormi330
 * @date : 2020-03-05
 * description : 文件描述
 */

package org.dormi.learn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * debug controller
 */
@Controller
@RequestMapping("/debug")
public class DebugController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /** curl http://localhost:8080/license/debug */
    @ResponseBody
    @RequestMapping(value = {"", "/"}, method = {RequestMethod.GET})
    public String ruok() {
        return "imok";
    }

}
