package com.sampleapptdd.demo.util

import groovy.text.SimpleTemplateEngine

class EmailTemplateUtils {

    private static SimpleTemplateEngine simpleTemplateEngine = null

    protected EmailTemplateUtils() {}

    public static SimpleTemplateEngine getTemplateEngineInstance() {
        if(simpleTemplateEngine == null) {
            simpleTemplateEngine = new SimpleTemplateEngine()
        }

        return simpleTemplateEngine
    }
}
