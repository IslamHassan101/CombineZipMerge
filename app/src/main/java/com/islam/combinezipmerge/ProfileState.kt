package com.islam.combinezipmerge

data class ProfileState(
    val profilePicUri: String? = null,
    val userName: String? = null,
    val description: String? = null,
    val post: List<Post> = emptyList()
)
