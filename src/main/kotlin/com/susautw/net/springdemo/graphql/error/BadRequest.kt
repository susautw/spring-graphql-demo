package com.susautw.net.springdemo.graphql.error

import org.springframework.graphql.execution.ErrorType

class BadRequest(override val message: String?): ServiceException(ErrorType.BAD_REQUEST, message)