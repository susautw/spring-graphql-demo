package com.susautw.net.springdemo.interfaces.service

import com.susautw.net.springdemo.models.Author
import kotlinx.coroutines.Deferred

interface AuthorService{
    suspend fun getAllAuthor(): Deferred<List<Author>>

    suspend fun getAuthorByName(name: String): Deferred<Author?>

    suspend fun getAuthorById(id: Int): Deferred<Author?>
}