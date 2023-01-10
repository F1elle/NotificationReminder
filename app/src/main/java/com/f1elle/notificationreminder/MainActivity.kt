package com.f1elle.notificationreminder

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.f1elle.notificationreminder.ui.theme.NotificationReminderTheme


class myModel: ViewModel(){
    val emptyCard = CardContent("", "")
    val isInEditMode: MutableLiveData<Boolean> = MutableLiveData(false)
    //private val _cardTitle = MutableStateFlow(emptyCard.title)
    //private val _cardContent = MutableStateFlow(emptyCard.content)
    //val cardTitle = _cardTitle.asStateFlow()
    //val cardContent = _cardContent.asStateFlow()
    var cardTitle by mutableStateOf(emptyCard.title)
    var cardContent by mutableStateOf(emptyCard.content)

    val buttonContent: MutableLiveData<String> = MutableLiveData("Add note")
    fun setCard(card: CardContent){
        cardTitle = card.title
        cardContent = card.content
    }
/*
    fun setTitle(cardTitle: String){
        _cardTitle.value = cardTitle
    }
    fun setContent(cardContent: String){
        _cardContent.value = cardContent
    }
    fun setCard(card: CardContent){
        _cardTitle.value = card.title
        _cardContent.value = card.content
    }*/
    fun enableEditMode(cardToEdit: CardContent){
        setCard(cardToEdit)
        isInEditMode.value = true
        buttonContent.value = "Edit note"
    }
    fun disableEditMode(){
        isInEditMode.value = false
        setCard(emptyCard)
        buttonContent.value = "Add note"
    }
}

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = getSplashScreen()
        //splashScreen.setSplashScreenTheme()
        super.onCreate(savedInstanceState)
        setContent {
            NotificationReminderTheme {
                HomeScreen(myModel())
            }
        }
    }
}