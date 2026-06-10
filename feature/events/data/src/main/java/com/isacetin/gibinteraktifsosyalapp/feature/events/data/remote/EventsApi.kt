package com.isacetin.gibinteraktifsosyalapp.feature.events.data.remote

import com.isacetin.gibinteraktifsosyalapp.feature.events.data.remote.dto.CreateEventRequest
import com.isacetin.gibinteraktifsosyalapp.feature.events.data.remote.dto.EventDto
import com.isacetin.gibinteraktifsosyalapp.feature.events.data.remote.dto.EventParticipantDto
import com.isacetin.gibinteraktifsosyalapp.feature.events.data.remote.dto.JoinEventRequest
import com.isacetin.gibinteraktifsosyalapp.feature.events.data.remote.dto.UserBalanceDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface EventsApi {
    @GET("events")
    suspend fun getEvents(
        @Query("order") order: String = "starts_at.asc",
    ): List<EventDto>

    @GET("event_participants")
    suspend fun getEventParticipants(
        @Query("select") select: String = "event_id,user_id",
    ): List<EventParticipantDto>

    @GET("users")
    suspend fun getUsers(@Query("id") id: String): List<UserBalanceDto>

    @Headers("Prefer: return=representation")
    @POST("events")
    suspend fun createEvent(@Body request: CreateEventRequest): List<EventDto>

    @Headers("Prefer: return=minimal")
    @POST("rpc/join_event")
    suspend fun joinEvent(@Body request: JoinEventRequest)

    @Headers("Prefer: return=minimal")
    @DELETE("event_participants")
    suspend fun leaveEvent(
        @Query("event_id") eventId: String,
        @Query("user_id") userId: String,
    )
}
