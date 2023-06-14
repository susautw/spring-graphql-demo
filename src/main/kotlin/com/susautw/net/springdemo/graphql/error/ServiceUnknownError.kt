package com.susautw.net.springdemo.graphql.error

import org.springframework.graphql.execution.ErrorType

class ServiceUnknownError(message: String): ServiceException(ErrorType.INTERNAL_ERROR, message)