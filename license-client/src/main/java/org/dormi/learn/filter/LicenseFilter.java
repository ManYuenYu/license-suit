/**
 * @author : 吴中勤
 * @date : 2019-08-06
 * description : TODO
 */

package org.dormi.learn.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Order(1)
@Component
public class LicenseFilter extends GenericFilterBean {

    private static final Logger log = LoggerFactory.getLogger(LicenseFilter.class);

    public static boolean pass = true;
    public static String error = null;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("doFilter, pass=" + pass + ", error=" + error);
        chain.doFilter(request, response);
    }
}