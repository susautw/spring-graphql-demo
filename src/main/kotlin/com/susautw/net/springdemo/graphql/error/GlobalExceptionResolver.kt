package com.susautw.net.springdemo.graphql.error

import graphql.GraphQLError
import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter
import org.springframework.stereotype.Component


@Component
class GlobalExceptionResolver : DataFetcherExceptionResolverAdapter() {
    override fun resolveToSingleError(exception: Throwable, env: DataFetchingEnvironment): GraphQLError? {
        val processException: ServiceException = if (exception is ServiceException) {
            exception
        } else {
            exception.printStackTrace()
            ServiceUnknownError(exception.message ?: "")
        }

        return GraphQLError
            .newError()
            .errorType(processException.errorType)
            .message(processException.message)
            .path(env.executionStepInfo.path)
            .location(env.field.sourceLocation)
            .build()
    }
}