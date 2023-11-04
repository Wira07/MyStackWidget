package com.yudawahfiudin.storyapp.utils

import com.yudawahfiudin.storyapp.remote.*

object DataDummy {

    fun generateDummyLoginResponse(): LoginResponse {
        return LoginResponse(
            false,
            "token",
            UserResponse(
                "id",
                "name",
                "token"
            )
        )
    }

    fun generateDummyRegisterResponse(): RegisterResponse{
        return RegisterResponse(
            false,
            "success"
        )
    }

    fun generateDummyStory(): List<ListStoryItem>{
        val item = arrayListOf<ListStoryItem>()

        for (i in 0 until 10){
            val story = ListStoryItem(
                "story-c5qhn5AzIeZ3otQ2",
                "hilman",
                "Istriku Mina",
                "https://story-api.dicoding.dev/images/stories/photos-1670510666175_t7vuFpdb.jpg",
                "2022-12-08T14:44:26.176Z",
                -6.8676653,
                107.5915846
            )
            item.add(story)
        }
        return item
    }

    fun generateDummyStoryLocation(): StoryResponse {
        val item: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100){
            val story = ListStoryItem(
                "story-c5qhn5AzIeZ3otQ2",
                "hilman",
                "Istriku Mina",
                "https://story-api.dicoding.dev/images/stories/photos-1670510666175_t7vuFpdb.jpg",
                "2022-12-08T14:44:26.176Z",
                -6.8676653,
                107.5915846
            )
            item.add(story)
        }
        return StoryResponse(
            false,
            "success",
            item
        )
    }

    fun generateDummyAddStoryResponse(): RegisterResponse{
        return RegisterResponse(
            false,
            "success"
        )
    }

    fun generateDummyGetUserResponse(): RegisterResponse{
        return RegisterResponse(
            false,
            "success"
        )
    }
}