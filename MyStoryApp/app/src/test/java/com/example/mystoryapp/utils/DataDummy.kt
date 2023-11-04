package com.example.mystoryapp.utils

import com.example.mystoryapp.database.ListStoryDetail
import com.example.mystoryapp.dataclass.*

object DataDummy {

    fun generateDummyNewsEntity(): List<ListStoryDetail> {
        val newsList = ArrayList<ListStoryDetail>()
        for (i in 0..5) {
            val stories = ListStoryDetail(
                "Title $i",
                "this is name",
                "This is description",
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                "2023-10-22",
                null,
                null,
            )
            newsList.add(stories)
        }
        return newsList
    }

    fun generateDummyNewStories(): List<ListStoryDetail> {
        val newsList = ArrayList<ListStoryDetail>()
        for (i in 0..5) {
            val stories = ListStoryDetail(
                "Title $i",
                "this is name",
                "This is description",
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                "2023-10-22",
                null,
                null,
            )
            newsList.add(stories)
        }
        return newsList
    }


    fun generateDummyRequestLogin(): LoginDataAccount {
        return LoginDataAccount("ajigilangrahmanda046@gmail.com", "12345678")
    }

    fun generateDummyResponseLogin(): ResponseLogin {
        val newLoginResult = LoginResult("qwerty", "aji", "ini-token")
        return ResponseLogin(false, "Login successfully", newLoginResult)
    }

    fun generateDummyRequestRegister(): RegisterDataAccount {
        return RegisterDataAccount("aji", "aji@gmail.com", "12345678")
    }

}