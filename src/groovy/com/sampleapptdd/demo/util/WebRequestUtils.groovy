package com.sampleapptdd.demo.util

import org.codehaus.groovy.grails.web.util.WebUtils
import org.apache.commons.validator.routines.InetAddressValidator

class WebRequestUtils {

    private static final List<String> POSSIBLE_IP_HEADERS = [
            'HTTP_CLIENT_IP',
            'HTTP_X_FORWARDED_FOR',
            'HTTP_X_FORWARDED',
            'HTTP_X_CLUSTER_CLIENT_IP',
            'HTTP_FORWARDED_FOR',
            'HTTP_VIA',
            'REMOTE_ADDR'
    ]

    public static String fetchIpAddress() {
        def validator = InetAddressValidator.getInstance()
        def request = WebUtils.retrieveGrailsWebRequest().currentRequest

        for(headerName in POSSIBLE_IP_HEADERS)  {
            String headerContent = request.getHeader(headerName)
            String ipAddress = extractIpAddress(headerName, headerContent)

            if(ipAddress && validator.isValid(ipAddress))	{
                return ipAddress
            }
        }

        return request?.getRemoteAddr()
    }

    private static String extractIpAddress(String header, String content)   {
        if(header == 'HTTP_X_FORWARDED_FOR')	{
            return content?.tokenize(', ')?.get(0)
        }

        return content
    }
}
