package com.example.data.mapper

import com.example.data.repository.film.api.FilmDto
import com.example.data.repository.film.api.FilmListDto
import com.example.domain.model.Film
import com.example.utils.extension.extractUrlId
import com.example.utils.extension.safe
import com.example.utils.extension.toDate
import java.util.*
import kotlin.collections.ArrayList

class FilmMapper {
    companion object {
        fun toFilm(filmDto: FilmDto): Film{
            return Film(
                id = filmDto.url.safe().extractUrlId() ?: -1,
                title = filmDto.title.safe(),
                openingCrawl = filmDto.openingCrawl.safe(),
                director = filmDto.director.safe(),
                producer = filmDto.producer.safe(),
                releaseDate = filmDto.releaseDate.safe().toDate() ?: Date(),
                characters = filmDto.characters ?: arrayListOf(),
                planets = filmDto.planets ?: arrayListOf(),
                starships = filmDto.starships ?: arrayListOf(),
                vehicles = filmDto.vehicles ?: arrayListOf(),
                species = filmDto.species ?: arrayListOf(),
            )
        }

        fun toFilmList(filmDtoList: List<FilmDto>): List<Film>{
            val arrayList = ArrayList<Film>()
            filmDtoList.forEach {
                arrayList.add(toFilm(it))
            }
            return arrayList
        }

        fun toFilmList(filmDtoList: FilmListDto): List<Film>{
            return filmDtoList.results?.let { toFilmList(it) } ?: listOf()
        }
    }
}