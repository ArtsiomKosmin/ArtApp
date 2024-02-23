package com.example.domain.useCase.base

suspend fun <P, R> UseCase<in P, R>.executeSafely(params: P) = runCatching { execute(params) }

interface UseCase<P, R> {
    suspend fun execute(params: P): R
}