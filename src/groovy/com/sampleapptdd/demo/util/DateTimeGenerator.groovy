package com.sampleapptdd.demo.util

class DateTimeGenerator {

    public String generateDateTime() {
        Date date = new Date()
        String newDate = date.format('M-d-yyyy-mm-ss.SSSSSS')
        return newDate
    }
}
