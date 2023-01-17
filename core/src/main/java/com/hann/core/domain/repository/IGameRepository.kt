package com.hann.core.domain.repository

import com.hann.core.data.Resource
import com.hann.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface IGameRepository {
    fun getAllGame(): Flow<Resource<List<Game>>>

    fun getGame(query: String): Flow<Resource<List<Game>>>

    fun getFavoriteGame(): Flow<List<Game>>

    fun setFavoriteGame(game: Game, state: Boolean)
}