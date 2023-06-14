package com.susautw.net.springdemo.graphql.error

import graphql.ErrorClassification

open class ServiceException(val errorType: ErrorClassification, override val message: String?) :
    Exception("$errorType: $message")

