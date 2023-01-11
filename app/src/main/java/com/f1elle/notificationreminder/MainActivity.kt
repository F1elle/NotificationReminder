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
import androidx.compose.ui.focus.FocusManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.f1elle.notificationreminder.ui.theme.NotificationReminderTheme


class myModel: ViewModel(){
    val emptyCard = CardContent("", "")
    val isAddNoteClicked: MutableLiveData<Boolean> = MutableLiveData(true)
    var cardIndex = -1
    var cardTitle by mutableStateOf(emptyCard.title)
    var cardContent by mutableStateOf(emptyCard.content)


    val buttonContent: MutableLiveData<String> = MutableLiveData("Add note")
    fun setCard(card: CardContent){
        cardTitle = card.title
        cardContent = card.content
    }
    fun EditNote(cardToEdit: CardContent, index: Int){
        isAddNoteClicked.value = false
        cardIndex = index
        setCard(cardToEdit)
        buttonContent.value = "Edit note"
    }
    fun clearFields(){
        isAddNoteClicked.value = true
        cardIndex = -1
        setCard(emptyCard)
        buttonContent.value = "Add note"
    }
    fun onReveal(focusManager: FocusManager){
        focusManager.clearFocus()
    }
    fun noteButton(cardList: SnapshotStateList<CardContent>, title: String, content: String){
        if (isAddNoteClicked.value == true){
            cardList.add(CardContent(title, content))}
        else{
            cardList.set(cardIndex, CardContent(title, content))
        }
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