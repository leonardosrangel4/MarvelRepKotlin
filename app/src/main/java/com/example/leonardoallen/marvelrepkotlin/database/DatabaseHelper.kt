package com.example.leonardoallen.marvelrepkotlin.database

import com.example.leonardoallen.marvelrepkotlin.di.Injector
import com.example.leonardoallen.marvelrepkotlin.character.model.Character
import com.example.leonardoallen.marvelrepkotlin.comic.model.Comic

class DatabaseHelper {

    var marvelDatabase = Injector.provideDatabase()

    val allCharacters: MutableList<Character>
        get() = marvelDatabase.marvelDao().allCharacters

    fun insertCharacters(characters: MutableList<Character>) {
        marvelDatabase.marvelDao().insert(characters)
    }

    fun insertComics(comics: MutableList<Comic>) {
        marvelDatabase.comicDao().insert(comics)
    }

    fun isComicEmpty(characterId: Int): Boolean {
        return marvelDatabase.comicDao().getComics(characterId).isEmpty()
    }

    fun getComics(characterId: Int): MutableList<Comic> {
        return marvelDatabase.comicDao().getComics(characterId)
    }

}