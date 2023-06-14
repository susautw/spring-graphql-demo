package com.susautw.net.springdemo.interfaces.service

import com.susautw.net.springdemo.models.Author
import com.susautw.net.springdemo.models.Book

interface BookService {
    fun getAllBooks(): List<Book>
    fun getBookById(id: Int): Book?
    fun getBookByName(name: String): Book?
    fun addBook(name: String, author: Author): Book
    fun deleteBook(book: Book)
}