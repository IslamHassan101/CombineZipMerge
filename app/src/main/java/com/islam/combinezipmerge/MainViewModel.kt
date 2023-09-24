package com.islam.combinezipmerge

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip

class MainViewModel : ViewModel() {


    private val user = MutableStateFlow<User?>(null)
    private val posts = MutableStateFlow(emptyList<Post>())

    private val _profileState = MutableStateFlow<ProfileState?>(null)

    val profileState = _profileState.asStateFlow()

    private val flow1 = (1..10).asFlow().onEach { delay(1000L) }
    private val flow2 = (10..20).asFlow().onEach { delay(300L) }
    var numberString by mutableStateOf("")
        private set

    init {

        user.combine(posts) { user, posts ->
            _profileState.value = profileState.value?.copy(
                profilePicUri = user?.profilePicUrl,
                userName = user?.profilePicUrl,
                description = user?.description,
                post = posts
            )
        }.launchIn(viewModelScope)


        //Zip
        flow1.zip(flow2) { number1, number2 ->

            numberString += "($number1 , $number2)/n"
        }.launchIn(viewModelScope)

        //merge

        merge(flow1, flow2).onEach {
            numberString += "$it /n"
        }.launchIn(viewModelScope)


    }

}