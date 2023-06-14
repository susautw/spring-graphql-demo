package com.susautw.net.springdemo.graphql.error

import org.springframework.graphql.execution.ErrorType
import kotlin.reflect.KClass

class ResourceNotFound(resourceName: String, resourceQuery: String? = null) :
    ServiceException(ErrorType.NOT_FOUND, "`$resourceName($resourceQuery)` was not found") {
    constructor(resource: KClass<*>, resourceQuery: String? = null) : this(resource.simpleName!!, resourceQuery)
}