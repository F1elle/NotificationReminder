package com.f1elle.notificationreminder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.remember
import androidx.compose.material.*
import androidx.compose.material.FabPosition
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.BackdropScaffold
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedCardInterface(card: CardContent, index: Int, cardList: SnapshotStateList<CardContent>, backdropConceal: (CardContent) -> Unit){
    OutlinedCard(shape = MaterialTheme.shapes.large,
        onClick = { backdropConceal(cardList[index]) },
        modifier = Modifier
            .fillMaxWidth()
            .size(100.dp)) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = (15.dp), start = (15.dp), end = (15.dp))){
            Row(modifier = Modifier.fillMaxWidth()){
            Text(card.title, fontWeight = FontWeight.Bold)}
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = (5.dp), top = (7.5.dp))){
            Text(card.content)}
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                IconButton(onClick = { cardList.removeAt(index) }) {
                    Icon(Icons.Filled.Delete, null, tint = MaterialTheme.colorScheme.secondary)
                }
            }
        }
    }
}




@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun HomeScreen(viewModel: myModel){

    val cardList = remember { mutableStateListOf<CardContent>()}
    fun addNote(title: String, content: String){
        val card = CardContent(title, content)
        cardList.add(card)
    }
    val coroutineScope = rememberCoroutineScope()
    val backdropState = rememberBackdropScaffoldState(BackdropValue.Revealed)
    fun backDropConceal(getcard: CardContent){
        coroutineScope.launch{
            viewModel.enableEditMode(getcard)
            backdropState.conceal() }
    }
    BackdropScaffold(
        scaffoldState = backdropState,
        modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
        appBar = {},
        backLayerContent = {
        Scaffold(modifier = Modifier
            .padding()
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                ExtendedFloatingActionButton(onClick = {coroutineScope.launch{
                    backdropState.conceal() }}){
                    Icon(Icons.Filled.Add, null)
                    Text("Add")
                }
            }
        ){contentPadding ->
            if (cardList.size > 0){
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background), contentPadding = PaddingValues(15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp), reverseLayout = true
                ){
                    items(cardList.size) { index ->
                        OutlinedCardInterface(card = cardList.get(index), index, cardList, ::backDropConceal)

                    }
            }}else{
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(top = (30.dp)),
                contentAlignment = Alignment.TopCenter ){
                    Text("Here is nothing yet", color = MaterialTheme.colorScheme.onBackground)
                }
            }
        }
    }, frontLayerContent = {
            BoxWithConstraints (modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surfaceVariant)) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(15.dp),
                       verticalArrangement = Arrangement.SpaceEvenly,
                       horizontalAlignment = Alignment.End) {
                    //var Title by remember { mutableStateOf(TextFieldValue("")) }
                    /*if (backdropState.isRevealed){
                        keyboardController?.hide()
                        viewModel.disableEditMode()}*/

                    //var Title by rememberSaveable{ mutableStateOf(viewModel.card.value?.title.toString()) }
                    //var Note by rememberSaveable{ mutableStateOf(viewModel.card.value?.content.toString()) }
                    //val Title = viewModel.cardTitle.collectAsState()
                    //val Note = viewModel.cardContent.collectAsState()
                    OutlinedTextField(value = viewModel.cardTitle, onValueChange = { viewModel.cardTitle = it },
                    shape = MaterialTheme.shapes.large, modifier = Modifier.fillMaxWidth(),
                        placeholder = {Text("Title")})
                    OutlinedTextField(value = viewModel.cardContent, onValueChange = { viewModel.cardContent = it },
                    shape = MaterialTheme.shapes.large, modifier = Modifier.fillMaxWidth(),
                        placeholder = {Text("Note")})
                    val btn_state = (if(viewModel.cardTitle != "") true else false)
                    fun btn_click(){addNote(viewModel.cardTitle, viewModel.cardContent); viewModel.disableEditMode()}
                    Button(onClick = {btn_click()}, content = {Text((viewModel.buttonContent.value.toString()))},
                    enabled = btn_state)

                }
        }
    },  frontLayerScrimColor = MaterialTheme.colorScheme.surfaceVariant,
        frontLayerBackgroundColor = MaterialTheme.colorScheme.surfaceVariant,
        headerHeight = (0.dp)
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        if (backdropState.isRevealed){
            keyboardController?.hide()
            viewModel.disableEditMode()}
        }
    }





