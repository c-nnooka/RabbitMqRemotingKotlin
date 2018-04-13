package com.example.rabbiitmqremoting.response

import java.io.Serializable

abstract class AbstractResponse:Serializable {

    enum class ResponseStatus {
        SUCCESS,
        FAIL
    }

    var response: Any? = null
    var errorCode = SpiResponseConstants.DEFAULT_RESPONSE_CODE
    var errorMessage = SpiResponseConstants.DEFAULT_ERROR_MESSAGE
    var status : ResponseStatus = ResponseStatus.FAIL
}

