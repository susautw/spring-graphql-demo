package com.susautw.net.springdemo.graphql.controllers

import com.susautw.net.springdemo.core.registerSuspendMappedBatchLoader
import com.susautw.net.springdemo.graphql.error.BadRequest
import com.susautw.net.springdemo.interfaces.service.AuthorService
import com.susautw.net.springdemo.interfaces.service.BookService
import com.susautw.net.springdemo.models.Author
import com.susautw.net.springdemo.models.Book
import org.dataloader.DataLoader
import org.springframework.graphql.data.method.annotation.*
import org.springframework.graphql.execution.BatchLoaderRegistry
import org.springframework.stereotype.Controller
import java.util.concurrent.CompletableFuture


@Controller
class AuthorController(val bookService: BookService, val authorService: AuthorService, registry: BatchLoaderRegistry) {

    init {
        registry.forName<Pair<Author, Int?>, List<Book>>("booksLoader")
            .registerSuspendMappedBatchLoader { authorsRequest, _ ->
                val authorsLimitMap = authorsRequest.toMap()
                bookService
                    .getAllBooks()
                    .filter { authorsLimitMap.containsKey(it.author) }
                    .groupBy { it.author to authorsLimitMap[it.author] }
                    .mapValues { entry ->
                        val limit = entry.key.second
                        val books = entry.value
                        limit?.let { books.take(it) } ?: books
                    }

            }
    }

    @QueryMapping
    suspend fun authors(): List<Author> = authorService.getAllAuthor().await()


    @QueryMapping
    suspend fun author(@Argument id: Int?, @Argument name: String?): Author? {
        if (id != null) {
            return authorService.getAuthorById(id).await()
        }
        if (name != null) {
            return authorService.getAuthorByName(name).await()
        }
        return null
    }

    @SchemaMapping
    fun books(
        author: Author,
        @Argument limit: Int?,
        booksLoader: DataLoader<Pair<Author, Int?>, List<Book>>
    ): CompletableFuture<List<Book>>? {
        if ((limit ?: 0) < 0) {
            throw BadRequest("Argument `limit` can't less than zero")
        }

        return booksLoader.load(author to limit)
    }
}