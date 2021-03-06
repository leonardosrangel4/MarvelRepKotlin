package com.example.leonardoallen.marvelrepkotlin.di

import android.arch.persistence.room.Room
import com.example.leonardoallen.marvelrepkotlin.MarvelApplication
import com.example.leonardoallen.marvelrepkotlin.api.MarvelApi
import com.example.leonardoallen.marvelrepkotlin.character.api.CharacterRepository
import com.example.leonardoallen.marvelrepkotlin.comic.api.ComicRepository
import com.example.leonardoallen.marvelrepkotlin.database.DatabaseHelper
import com.example.leonardoallen.marvelrepkotlin.database.MarvelDatabase
import com.example.leonardoallen.marvelrepkotlin.util.NetworkUtils
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Injector {

    companion object {
        private var INSTANCE: MarvelDatabase? = null

        fun provideDatabase(): MarvelDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(MarvelApplication.context.get()!!, MarvelDatabase::class.java, "production")
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE!!
        }

        private fun provideRetrofit(): Retrofit {
            val BASE_URL = "https://gateway.marvel.com:443/v1/public/"
            return Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()
        }

        private fun provideMarvelApi(retrofit: Retrofit): MarvelApi {
            return retrofit.create(MarvelApi::class.java)
        }

        fun marvelApi(): MarvelApi {
            return provideMarvelApi(provideRetrofit())
        }

        fun provideCharacterRepository(): CharacterRepository {
            return CharacterRepository()
        }

        fun provideComicRepository(): ComicRepository {
            return ComicRepository()
        }

        fun provideNetworkUtil(): NetworkUtils {
            return NetworkUtils.getInstance()
        }

        fun provideDatabaseHelper(): DatabaseHelper {
            return DatabaseHelper()
        }

    }

}