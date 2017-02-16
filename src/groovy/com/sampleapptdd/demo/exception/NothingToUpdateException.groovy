package com.sampleapptdd.demo.exception

import com.sampleapptdd.demo.type.ApiError

class NothingToUpdateException extends RuntimeException {
    ApiError apiError = ApiError.NOTHING_TO_UPDATE
}
