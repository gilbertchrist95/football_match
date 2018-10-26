package com.treblig.footballmatch.pojo

import com.google.gson.annotations.SerializedName

data class League(@SerializedName("idLeague") val id: String, @SerializedName("strLeague") val name: String
) {
    override fun toString(): String {
        return name
    }
}