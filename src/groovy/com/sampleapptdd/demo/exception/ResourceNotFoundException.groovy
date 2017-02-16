package com.sampleapptdd.demo.exception

import com.sampleapptdd.demo.type.ApiError

class ResourceNotFoundException extends RuntimeException {

    ApiError apiError = ApiError.RESOURCE_NOT_FOUND
}
