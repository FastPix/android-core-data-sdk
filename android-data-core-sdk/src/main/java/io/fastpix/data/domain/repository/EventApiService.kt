package io.fastpix.data.domain.repository

import io.fastpix.data.domain.model.EventRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface EventApiService {
    
    @POST(".")
    suspend fun sendEvents(@Body eventRequest: EventRequest): Response<Unit>
}
