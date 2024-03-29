package com.thezayin.chatbottesting.domain.usecase

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.thezayin.chatbottesting.domain.model.Message
import com.thezayin.chatbottesting.domain.repo.BotRepository
import com.thezayin.chatbottesting.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class BotUseCase @Inject constructor(
    private val botRepository: BotRepository
) {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    operator fun invoke(message: String): Flow<Response<Message>> = flow {
        try {
            emit(Response.Loading)
            val response = botRepository.sendMessage(message)
            emit(Response.Success(response))
        } catch (e: HttpException) {
            emit(Response.Failure(e.localizedMessage ?: "Unexpected Error"))
        } catch (e: IOException) {
            emit(Response.Failure(e.localizedMessage ?: "Unexpected Error"))
        }
    }
}