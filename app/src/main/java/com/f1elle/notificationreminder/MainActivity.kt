package com.f1elle.notificationreminder

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.f1elle.notificationreminder.ui.theme.NotificationReminderTheme


class myModel: ViewModel(){
    val emptyCard = CardContent("", "")
    val isInEditMode: MutableLiveData<Boolean> = MutableLiveData(false)
    var cardTitle by mutableStateOf(emptyCard.title)
    var cardContent by mutableStateOf(emptyCard.content)

    val buttonContent: MutableLiveData<String> = MutableLiveData("Add note")
    fun setCard(card: CardContent){
        cardTitle = card.title
        cardContent = card.content
    }
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
    fun noteButton(cardList: SnapshotStateList<CardContent>, title: String, content: String){
        cardList.add(CardContent(title, content))
    }
    fun noteButton(cardList: SnapshotStateList<CardContent>, title: String, content: String, index: Int){
        cardList.set(index, CardContent(title, content))
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