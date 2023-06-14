package com.susautw.net.springdemo.impl.service

import com.susautw.net.springdemo.interfaces.service.BookService
import com.susautw.net.springdemo.models.Author
import com.susautw.net.springdemo.models.Book
import org.springframework.stereotype.Service

@Service
class BookServiceImpl : BookService {
    companion object {
        @JvmStatic
        val data = mutableListOf(  // assume the list is always sorted
            Book(id = 1, name = "Book1", author = AuthorServiceImpl.data[0]),
            Book(id = 2, name = "Book2", author = AuthorServiceImpl.data[0]),
            Book(id = 3, name = "Book3", author = AuthorServiceImpl.data[0]),
            Book(id = 4, name = "Book4", author = AuthorServiceImpl.data[0]),
            Book(id = 5, name = "Book5", author = AuthorServiceImpl.data[0]),
            Book(id = 6, name = "Book6", author = AuthorServiceImpl.data[1]),
            Book(id = 7, name = "Book7", author = AuthorServiceImpl.data[1]),
            Book(id = 8, name = "Book8", author = AuthorServiceImpl.data[1]),
            Book(id = 9, name = "Book9", author = AuthorServiceImpl.data[2]),
            Book(id = 10, name = "Book10", author = AuthorServiceImpl.data[2]),
        )
    }

    override fun getAllBooks(): List<Book> {
        return data
    }

    override fun getBookById(id: Int): Book? {
        return data.find { it.id == id }
    }

    override fun getBookByName(name: String): Book? {
        return data.find { it.name == name }
    }

    override fun addBook(name: String, author: Author): Book {
        val id = data.last().id + 1
        val book = Book(id, name, author)
        data.add(book)
        return book
    }

    override fun deleteBook(book: Book) {
        data.remove(book)
    }

}