package com.susautw.net.springdemo.graphql.controllers

import com.susautw.net.springdemo.graphql.error.ResourceNotFound
import com.susautw.net.springdemo.interfaces.service.AuthorService
import com.susautw.net.springdemo.interfaces.service.BookService
import com.susautw.net.springdemo.models.Author
import com.susautw.net.springdemo.models.Book
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class BookController(val bookService: BookService, val authorService: AuthorService) {
    @QueryMapping
    fun books(): List<Book> {
        return bookService.getAllBooks()
    }

    @QueryMapping
    fun book(@Argument id: Int?, @Argument name: String?): Book? {
        if (id != null) {
            return bookService.getBookById(id)
        }
        if (name != null) {
            return bookService.getBookByName(name)
        }
        return null
    }

    @MutationMapping
    suspend fun addBook(@Argument name: String, @Argument authorId: Int): Book {
        return bookService.addBook(
            name,
            authorService.getAuthorById(authorId).await() ?: throw ResourceNotFound(Author::class, "id: $authorId")
        )
    }

    @MutationMapping(name = "delBook")
    fun deleteBook(@Argument id: Int): Book {
        val book = bookService.getBookById(id) ?: throw ResourceNotFound(Book::class, "id: $id")
        bookService.deleteBook(book)
        return book
    }
}
