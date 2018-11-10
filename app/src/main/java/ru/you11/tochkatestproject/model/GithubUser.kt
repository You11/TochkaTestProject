package ru.you11.tochkatestproject.model

class GithubUser(
    val id: Long,
    val login: String,
    val avatarUrl: String,
    val email: String,
    val bio: String,
    val followers: Int,
    val publicRepositories: Int) {

}