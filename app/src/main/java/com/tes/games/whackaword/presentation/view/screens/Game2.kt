package com.tes.games.whackaword.presentation.view.screens

import android.content.Context
import android.os.Handler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tes.games.whackaword.R
import com.tes.games.whackaword.domain.model.VocabularyItem
import com.tes.games.whackaword.presentation.view.components.MediaPlayerComponent
import com.tes.games.whackaword.presentation.viewmodel.VocabularyGameViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun GameScreen2() {
    val context = LocalContext.current
    val holes = remember { generateRandomHoles(9) }
    //val holes2 = remember { mutableStateListOf<Hole>() }
    val targetClicked = remember { mutableStateOf(false) }

    val createHolesState = remember { mutableStateOf(false) }
    val createEmptyHolesState = remember { mutableStateOf(false) }
    val restartGame = remember { mutableStateOf(false) }
   // val viewModel3: VocabularyGameViewModel3 = viewModel()
    val viewModel: VocabularyGameViewModel = viewModel()
    val vocabularyItems by viewModel.vocabularyItems.collectAsState()
    val selectedVocabularyItems by viewModel.selectedVocabularyItems.collectAsState()
    val targetVocabularyItem by viewModel.targetVocabularyItem.collectAsState()
    val mediaPlayerState by viewModel.mediaPlayerState.collectAsState()
    val level by viewModel.level.collectAsState()
    val resetGame by viewModel.resetGame.collectAsState()
    val startGame by viewModel.startGame.collectAsState()
    //val selectedItems: List<VocabularyItem> = viewModel2.getSelectedVocabularyItems()
    //val vocabularyItems2 = viewModel3.vocabularyItems
    //val vocabularyItems = viewModel2.getList()
    val coroutineScope = rememberCoroutineScope()

    val handler = Handler()

    println("Selected  size ${selectedVocabularyItems.size} ")

    for(i in selectedVocabularyItems.indices){
        println("Selected $i: ${selectedVocabularyItems[i]!!.image} ")
    }
    println("Selected Target: ${targetVocabularyItem!!.image} ")


    println("Level: $level")
    var cardCounter = 3
    createBox(
        holes,
        mediaPlayerState,
        selectedVocabularyItems,
        viewModel,
        context,
        targetVocabularyItem,
        createEmptyHolesState,
        createHolesState,
    ) {
        viewModel.restartGame()
    }
}

@Composable
fun createBox(
    holes: List<Offset>,
    mediaPlayerState: Boolean,
    selectedVocabularyItems: List<VocabularyItem?>,
    viewModel: VocabularyGameViewModel,
    context: Context,
    targetVocabularyItem: VocabularyItem?,
    createEmptyHolesState: MutableState<Boolean>,
    createHolesState: MutableState<Boolean>,
    restartGame: () -> Unit
) {
    if (selectedVocabularyItems.isNotEmpty()) {
        Box(
            modifier = Modifier
                .padding(top = 200.dp)
                .fillMaxSize()
                .background(Color(0xFFCCBA85)) // Green ground color
        ) {


            LaunchedEffect(Unit) {
                while (true) {
                    // Create new holes
                    createEmptyHolesState.value = true
                    delay(2000)
                    restartGame()
                    createHolesState.value = true
                    // Wait for 4 seconds
                    delay(4000)
                    createHolesState.value = false

                }
            }
            if (createHolesState.value) {
                createHoles(
                    holes,
                    selectedVocabularyItems,
                    viewModel,
                    context,
                    targetVocabularyItem
                )
            }
            if (createEmptyHolesState.value) {
                createEmptyHoles(
                    holes,
                )
            }
        }
    } else {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFCCBA85)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Game Doest Started! " +
                        "Try again later",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black

            )
        }
    }
}


@Composable
fun createEmptyHoles(
    holes: List<Offset>,
) {
    for (hole in holes) {
        Hole(
            position = hole,
            isTargetHole = false,
            cardVisible = false,
            img = 0
        ) { }
    }
}


@Composable
fun createHoles(
    holes: List<Offset>,
    selectedVocabularyItems: List<VocabularyItem?>,
    viewModel: VocabularyGameViewModel,
    context: Context,
    targetVocabularyItem: VocabularyItem?
) {

    val numberOfHoles = listOf(0, 1, 2, 3, 4)
    val randomSelectedNumberOfHoles = numberOfHoles.shuffled().take(selectedVocabularyItems.size)////1,4,3
    var j = 0
    for ((i, hole) in holes.withIndex()) {
        if (randomSelectedNumberOfHoles.contains(i)) {
            if (selectedVocabularyItems[j] == targetVocabularyItem) {
                Hole(
                    position = hole,
                    isTargetHole = true,
                    cardVisible = true,
                    img = selectedVocabularyItems[j]?.image ?: R.drawable.ic_launcher_background
                ) {
                    viewModel.increaseLevel()
                }
            } else {
                Hole(
                    position = hole,
                    isTargetHole = false,
                    cardVisible = true,
                    img = selectedVocabularyItems[j]?.image ?: R.drawable.ic_launcher_background
                ) {
                    viewModel.increaseLevel()
                }
            }
            j++
        } else {
            Hole(
                position = hole,
                isTargetHole = false,
                cardVisible = false,
                img = 0
            ) { }
        }
        MediaPlayerComponent(context, targetVocabularyItem)
    }
}

@Composable
fun Hole(
    position: Offset,
    cardVisible: Boolean,
    isTargetHole: Boolean,
    img: Int,
    clicked: () -> Unit
) {
    val holeSize = 200.dp
//    OvalWithBorder()
    Canvas(
        modifier = Modifier
            .size(holeSize)
            .offset(position.x.dp, position.y.dp)
//            .border(width = 2.dp, color = Color.Magenta)

    ) {
        drawOval(
            brush = Brush.horizontalGradient(listOf(Color.Black, Color.Black)),
            size = Size(width = 210.dp.toPx(), height = 60.dp.toPx()),
            topLeft = Offset(x = 105.dp.toPx(), y = 120.dp.toPx()),
            style = Stroke(width = 8.dp.toPx())
        )
    }
    if (cardVisible) {
        ImageCard(
            position = position,
            holeSize = holeSize,
            isTargetHole = isTargetHole,
            img,
            clicked = clicked
        )
    }
}

@Composable
fun ImageCard(
    position: Offset,
    holeSize: Dp,
    isTargetHole: Boolean,
    img: Int,
    clicked: () -> Unit
) {
    val targetClicked = remember { mutableStateOf(false) }
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .size(holeSize, holeSize + 100.dp)
            .padding(16.dp)
            .offset(position.x.dp + 110.dp, position.y.dp - 110.dp)
            .clickable {
                if (isTargetHole) {
                    clicked()
                    targetClicked.value = true
                }
            },

        colors = CardDefaults.cardColors(colorResource(id = R.color.light_ray)),
        elevation = CardDefaults.cardElevation(1.dp),
//        shape = RoundedCornerShape(16.dp),
        shape = MaterialTheme.shapes.medium.copy(
            topStart = CornerSize(16.dp),
            topEnd = CornerSize(16.dp),
            bottomStart = CornerSize(5.dp),
            bottomEnd = CornerSize(5.dp)
        ),
        border = BorderStroke(
            width = 6.dp,
            color = Color.Black,
        ),
    ) {
        if (targetClicked.value) {
            val targetVocabularyItem = VocabularyItem("", 0, R.raw.correct)
            MediaPlayerComponent(context, targetVocabularyItem)
        }

        Box(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            val currentImageId  = if (targetClicked.value) {
                R.drawable.tick
            } else {
                img
            }
                Image(
                    painter = painterResource(id = currentImageId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = colorResource(id = R.color.light_ray))
                )
        }
    }
}

fun generateRandomHoles(count: Int): List<Offset> {
    val holes = mutableListOf<Offset>()
    val secondOffset = 0
//    repeat(count) {
    val randomX = Random.nextFloat() * 200.dp
    var randomY = Random.nextFloat() * 200.dp
    holes.add(Offset(randomX.value, randomY.value))

    // randomX = Random.nextFloat() * 200.dp
    randomY = Random.nextFloat() * 200.dp
    holes.add(Offset(randomX.value + (200), randomY.value + 100))

    // randomX = Random.nextFloat() * 200.dp
    randomY = Random.nextFloat() * 200.dp
    holes.add(Offset(randomX.value + (400), randomY.value + 200))


    // randomX = Random.nextFloat() * 200.dp
    randomY = Random.nextFloat() * 200.dp
    holes.add(Offset(randomX.value + (600), randomY.value))

    // randomX = Random.nextFloat() * 200.dp
    randomY = Random.nextFloat() * 200.dp
    holes.add(Offset(randomX.value + (800), randomY.value - 100))

//    }
    return holes
}

