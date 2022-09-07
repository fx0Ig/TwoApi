package com.example.twoapi.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twoapi.AppComponent
import com.example.twoapi.domain.User
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private var _selectedUser: MutableLiveData<User> = MutableLiveData()
    val selectedUser: LiveData<User> = _selectedUser

    var users: LiveData<List<User>> = MutableLiveData()
    private val dao = AppComponent.userDatabase.userDao()

    init {
        viewModelScope.launch { dao.deleteUsers() }
        users = dao.getUsers()
        viewModelScope.launch {
            val result: MutableList<User> = mutableListOf()
            val dailyResult = launch {
                val response = AppComponent.dailymotionApiService.getApiResponse()
                result.addAll(
                    response.list.map {
                        User(
                            it.screenname,
                            "",
                            "dailymotion"
                        )
                    })
            }
            val githubResult = launch {
                val response = AppComponent.githubApiService.getApiResponse()
                result.addAll(
                    response.map {
                        User(
                            it.login,
                            it.avatarUrl,
                            "github"
                        )
                    })
            }
            joinAll(dailyResult, githubResult)

            AppComponent.userDatabase.userDao().addUsers(result)
        }
    }

    fun setSelectedUser(userId: Int) {
        _selectedUser.value = users.value?.first { it.id == userId }
    }

}