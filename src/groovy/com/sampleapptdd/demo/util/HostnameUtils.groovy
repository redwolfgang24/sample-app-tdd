package com.sampleapptdd.demo.util

import org.codehaus.groovy.grails.web.util.WebUtils

class HostnameUtils {

    public static String fetchHostname() {
        def webUtils = WebUtils.retrieveGrailsWebRequest()
        def request = webUtils.currentRequest

        def host = new StringBuilder(request.serverName)
        def port = request.serverPort

        if (!(port in [80, 443])) {
            host << ":" << port
        }

        return host
    }
}
