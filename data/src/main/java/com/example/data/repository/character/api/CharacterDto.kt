package com.example.data.repository.character.api

import com.google.gson.annotations.SerializedName

data class CharacterDto (
    @SerializedName("name")
    val name: String?
        )