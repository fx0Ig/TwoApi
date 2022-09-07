package com.example.twoapi.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twoapi.data.UserDao
import com.example.twoapi.domain.User
import com.example.twoapi.network.DailyMotionApiService
import com.example.twoapi.network.GitHubApiService
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class UserViewModel(val dao: UserDao, val dailyMotionApiService: DailyMotionApiService, val gitHubApiService: GitHubApiService) :
    ViewModel() {

    private var _selectedUser: MutableLiveData<User> = MutableLiveData()
    val selectedUser: LiveData<User> = _selectedUser

    var users: LiveData<List<User>> = MutableLiveData()


        init {
            viewModelScope.launch { dao.deleteUsers() }
            users = dao.getUsers()
            viewModelScope.launch {
                val result: MutableList<User> = mutableListOf()
                val dailyResult = launch {
                    val response = dailyMotionApiService.getApiResponse()
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
                    val response = gitHubApiService.getApiResponse()
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

                dao.addUsers(result)
            }
        }

    fun setSelectedUser(userId: Int) {
        _selectedUser.value = users.value?.first { it.id == userId }
    }

}