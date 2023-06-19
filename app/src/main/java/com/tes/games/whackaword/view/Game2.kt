package com.tes.games.whackaword.view

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
import com.tes.games.whackaword.model.VocabularyItem
import com.tes.games.whackaword.viewmodel.VocabularyGameViewModel
import com.tes.games.whackaword.viewmodel.VocabularyGameViewModel2
import kotlin.random.Random

@Composable
fun GameScreen2() {
    val holes = remember { generateRandomHoles(9) }
    var clicked = remember { false }
    val viewModel: VocabularyGameViewModel = viewModel()
    val viewModel2: VocabularyGameViewModel2 = viewModel()
    val vocabularyItems2 = viewModel.vocabularyItems
    val vocabularyItems = viewModel2.getList()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val handler = Handler()


    val selectedList = mutableListOf<VocabularyItem>()
    for (i in 0..4) {
        var selectedItem = selectRandomVocabularyItem(vocabularyItems)
        while (selectedList.contains(selectedItem)) {
            selectedItem = selectRandomVocabularyItem(vocabularyItems)
        }
        selectedList.add(selectedItem)
    }



    var cardCounter = 5
    Box(
        modifier = Modifier
            .padding(top = 200.dp)
            .fillMaxSize()
            .background(Color(0xFFCCBA85)) // Green ground color
    ) {
        for (hole in holes) {

            if (cardCounter % 2 == 0) {
                Hole(hole, visible = false, selectedList[cardCounter - 1].image) {}

            } else {
                if (cardCounter == 3) {
                    var a = Hole(hole, visible = true, selectedList[cardCounter - 1].image) {}
                    MediaPlayerComponent(context, selectedList[cardCounter - 1], true)
//                    LaunchedEffect(Unit) {
//                        delay(10000L) // Delay for 1 second
//                    }
                    a = Hole(hole, visible = true, R.drawable.tick) {}
                    MediaPlayerComponent(context, VocabularyItem("",0, R.raw.correct), true)


                } else {
                    Hole(hole, visible = true, selectedList[cardCounter - 1].image) {}
                }
            }
            cardCounter--
        }
    }
}

@Composable
fun Hole(position: Offset, visible: Boolean, img: Int, clicked: () -> Unit) {
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
    if (visible) {
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
    holes.add(Offset(randomX.value + (800), randomY.value))

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
            .clickable { clicked },
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


@Composable
fun OvalWithBorder() {
    val canvasColor = Color.Transparent
    val ovalColor = Color.Blue
    val borderColor = Color.Red
    val borderWidth = 4.dp
    val ovalSize = Size(width = 250.dp.value, height = 120.dp.value)
    val canvasModifier = Modifier
        .size(ovalSize.width.dp, ovalSize.height.dp)

    Box(
        modifier = Modifier.size(width = 250.dp, 120.dp)
    ) {
        Canvas(modifier = canvasModifier) {
            drawRect(color = Color.Transparent)
            drawOval(
                brush = Brush.horizontalGradient(listOf(borderColor, borderColor)),
                size = ovalSize,
                topLeft = Offset(x = 0f, y = 0f),
                style = Stroke(width = borderWidth.toPx()),
            )
        }
    }
}

fun selectRandomVocabularyItem(
    vocabularyItems: List<VocabularyItem>,
): VocabularyItem {
    if (vocabularyItems.isEmpty()) {
        return VocabularyItem("", 0, 0)
    }
    val randomIndex = Random.nextInt(vocabularyItems.size)

//    val updatedList = vocabularyItems.toMutableList()
//    updatedList.removeAt(randomIndex)

    // Update the list of vocabulary items
    // You can use viewModel or mutableStateOf to handle the list
    return vocabularyItems[randomIndex]
}

