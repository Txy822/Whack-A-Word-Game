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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tes.games.whackaword.R
import com.tes.games.whackaword.domain.model.VocabularyItem
import com.tes.games.whackaword.presentation.view.components.MediaPlayerComponent
import com.tes.games.whackaword.presentation.viewmodel.VocabularyGameViewModel
import com.tes.games.whackaword.presentation.viewmodel.VocabularyGameViewModel3
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun GameScreen2() {
    val context = LocalContext.current
    val holes = remember { generateRandomHoles(9) }
    //val holes2 = remember { mutableStateListOf<Hole>() }
    val createHolesState = remember { mutableStateOf(false) }
    val createEmptyHolesState = remember { mutableStateOf(false) }
    val viewModel3: VocabularyGameViewModel3 = viewModel()
    val viewModel: VocabularyGameViewModel = viewModel()
    val vocabularyItems by viewModel.vocabularyItems.collectAsState()
    val selectedVocabularyItems by viewModel.selectedVocabularyItems.collectAsState()
    val targetVocabularyItem by viewModel.targetVocabularyItem.collectAsState()
    val mediaPlayerState by viewModel.mediaPlayerState.collectAsState()
    val level by viewModel.level.collectAsState()
    val resetGame by viewModel.resetGame.collectAsState()
    val startGame by viewModel.startGame.collectAsState()
    //val selectedItems: List<VocabularyItem> = viewModel2.getSelectedVocabularyItems()
    val vocabularyItems2 = viewModel3.vocabularyItems
    //val vocabularyItems = viewModel2.getList()
    val coroutineScope = rememberCoroutineScope()

    val handler = Handler()


    if (resetGame || startGame) {
        println("start:$startGame and $resetGame")
        var cardCounter = 3
        Box(
            modifier = Modifier
                .padding(top = 200.dp)
                .fillMaxSize()
                .background(Color(0xFFCCBA85)) // Green ground color
        ) {


            LaunchedEffect(Unit) {
                while (true) {
                    // Create new holes
                    createEmptyHolesState.value= true
                    delay(2000)

                    createHolesState.value = true
                    // createHoles(holes,mediaPlayerState, selectedVocabularyItems, viewModel2, context, targetVocabularyItem )

                    // Wait for 10 seconds
                    delay(10000)
                    createHolesState.value = false

                }
            }
            if (createHolesState.value) {
                createHoles(
                    holes,
                    mediaPlayerState,
                    selectedVocabularyItems,
                    viewModel,
                    context,
                    targetVocabularyItem
                )
            }
            if (createEmptyHolesState.value) {
                createEmptyHoles(
                    holes,
                    mediaPlayerState,
                    selectedVocabularyItems,
                    viewModel,
                    context,
                    targetVocabularyItem
                )
            }
        }
    }
}

@Composable
fun createEmptyHoles(
    holes: List<Offset>,
    mediaPlayerState: Boolean,
    selectedVocabularyItems: List<VocabularyItem?>,
    viewModel: VocabularyGameViewModel,
    context: Context,
    targetVocabularyItem: VocabularyItem?
) {
    for (hole in holes) {
        Hole(
            position = hole,
            cardVisible = false,
            playAudioForSelectedCard = false,
            img = 0
        ) { }
    }
}



@Composable
fun createHoles(
    holes: List<Offset>,
    mediaPlayerState: Boolean,
    selectedVocabularyItems: List<VocabularyItem?>,
    viewModel: VocabularyGameViewModel,
    context: Context,
    targetVocabularyItem: VocabularyItem?
) {
    val numbers = listOf(0, 1, 2, 3, 4)

    val randomNumbers = numbers.shuffled().take(3)
    val restOfNumbers = numbers - randomNumbers.toSet()//0,1,3
    var j = 0
    var k = 0
    for ((i, hole) in holes.withIndex()) {
        if (randomNumbers.contains(i)) {
            Hole(
                position = hole,
                cardVisible = true,
                playAudioForSelectedCard = mediaPlayerState,
                img = selectedVocabularyItems[j]?.image ?: R.drawable.ic_launcher_background
            ) { viewModel.increaseLevel() }
            j++
        } else {
            Hole(
                position = hole,
                cardVisible = false,
                playAudioForSelectedCard = mediaPlayerState,
                img = selectedVocabularyItems[k]?.image ?: R.drawable.ic_launcher_background
            ) { viewModel.increaseLevel() }
            k++
        }
        MediaPlayerComponent(context, targetVocabularyItem, true)
    }
}

@Composable
fun Hole(
    position: Offset,
    cardVisible: Boolean,
    playAudioForSelectedCard: Boolean,
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
        ImageCard(position = position, holeSize = holeSize, img, clicked = clicked)
    }
    /*
    Image(
        painter = painterResource(id = R.drawable.fc_apple),
        contentDescription = null,
        modifier = Modifier
            .size(holeSize)
            .offset(position.x.dp + 100.dp, position.y.dp - 10.dp)
    )
*/
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

@Composable
fun ImageCard(position: Offset, holeSize: Dp, img: Int, clicked: () -> Unit) {
    Card(
        modifier = Modifier
            .size(holeSize, holeSize + 100.dp)
            .padding(16.dp)
            .offset(position.x.dp + 110.dp, position.y.dp - 110.dp)
            .clickable {
                clicked()
                //viewModel.increaseLevel()
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
        Box(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                painter = painterResource(id = img),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = colorResource(id = R.color.light_ray))
            )
        }
    }
}