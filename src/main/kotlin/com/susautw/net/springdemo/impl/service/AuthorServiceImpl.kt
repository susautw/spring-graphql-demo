package com.susautw.net.springdemo.impl.service

import com.susautw.net.springdemo.interfaces.service.AuthorService
import com.susautw.net.springdemo.models.Author
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service

@Service
class AuthorServiceImpl : AuthorService {
    companion object {
        @JvmStatic
        val data = listOf(
            Author(id = 1, name = "Author1"),
            Author(id = 2, name = "Author2"),
            Author(id = 3, name = "Author3"),
        )
    }

    override suspend fun getAllAuthor(): Deferred<List<Author>> = coroutineScope {
        async { data }
    }

    override suspend fun getAuthorByName(name: String): Deferred<Author?> = coroutineScope {
        async { data.find { it.name == name } }
    }

    override suspend fun getAuthorById(id: Int): Deferred<Author?> = coroutineScope {
        async { data.find { it.id == id } }
    }
}