package com.inovex.main.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * InovexideaApiInterceptor class
 *
 * @author rabiul
 *
 */
@Component
public class InovexideaApiInterceptor implements HandlerInterceptor {

    public static final Logger logger = LoggerFactory.getLogger(InovexideaApiInterceptor.class);

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.
     * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * java.lang.Object, java.lang.Exception)
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        logger.info("InovexideaApiInterceptor:afterCompletion : Request Completed!");
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.
     * http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * java.lang.Object, org.springframework.web.servlet.ModelAndView)
     */
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        logger.info("InovexideaApiInterceptor:afterCompletion : Method executed");
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.
     * http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        logger.info("InovexideaApiInterceptorr:preHandle : Start");

        // surveyResultService.checkSecretKey(request.getHeader(JasmyConstant.SECRET_KEY));

        logger.info("InovexideaApiInterceptorr:preHandle : End");
        return true;
    }
}
