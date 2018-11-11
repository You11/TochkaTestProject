package ru.you11.tochkatestproject.model

import com.google.gson.annotations.SerializedName

class GithubUser(
    val id: Long,
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val score: Double)


class GithubUserList(
    @SerializedName("items")
    val users: ArrayList<GithubUser>
)