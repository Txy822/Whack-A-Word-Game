package com.tes.games.whackaword.presentation.view.screens

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tes.games.whackaword.R
import com.tes.games.whackaword.domain.model.VocabularyItem
import com.tes.games.whackaword.presentation.view.components.MediaPlayerComponent
import com.tes.games.whackaword.presentation.view.navigation.Screen
import com.tes.games.whackaword.presentation.viewmodel.VocabularyGameViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun GameScreen(
    navController: NavController = rememberNavController(),
    viewModel: VocabularyGameViewModel = viewModel()
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenOrientation = configuration.orientation
    val holes = if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
        remember { generateRandomHolesForLANDSCAPE() }
    } else {
        remember { generateRandomHolesForPORTRAIT() }
    }
    val createHolesState = remember { mutableStateOf(false) }
    val createEmptyHolesState = remember { mutableStateOf(false) }
    val selectedVocabularyItems by viewModel.selectedVocabularyItems.collectAsState()
    val targetVocabularyItem by viewModel.targetVocabularyItem.collectAsState()
    val points by viewModel.point.collectAsState()
    val level by viewModel.level.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFCCBA85))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(colorResource(id = R.color.sky)),
            contentAlignment = Alignment.TopCenter
        ) {
            Row() {
                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = "Points: ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    color = Color.Black
                )
                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = "$points       ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    color = Color.Black
                )

                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = "Level: ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    color = Color.Black
                )
                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = "$level",
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(300.dp))
                Button(
                    modifier = Modifier.padding(top = 20.dp),
                    onClick = {
                        navController.navigate(Screen.MenuScreen.route)
                        navController.clearBackStack(Screen.SplashScreen.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color.Transparent
                    ),
                )

                {
                    Text(
                        modifier = Modifier
                            .background(Color.Transparent),
                        text = "Exit",
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp,
                        color = Color.Black
                    )
                }
            }
        }
        CreateBox(
            holes,
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
}

@Composable
fun CreateBox(
    holes: List<Offset>,
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
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 100.dp, end = 100.dp)
                .background(Color(0xFFCCBA85)) // Green ground color
        ) {
            LaunchedEffect(Unit) {
                while (true) {
                    // Create empty hole before new holes with images
                    createEmptyHolesState.value = true
                    // Wait for 2 seconds
                    delay(2000)
                    restartGame()
                    createHolesState.value = true
                    // Wait for 4 seconds
                    delay(4000)
                    createHolesState.value = false
                }
            }
            if (createHolesState.value) {
                CreateHoles(
                    holes,
                    selectedVocabularyItems,
                    viewModel,
                    context,
                    targetVocabularyItem
                )
            }
            if (createEmptyHolesState.value) {
                CreateEmptyHoles(
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
fun CreateEmptyHoles(
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
fun CreateHoles(
    holes: List<Offset>,
    selectedVocabularyItems: List<VocabularyItem?>,
    viewModel: VocabularyGameViewModel,
    context: Context,
    targetVocabularyItem: VocabularyItem?
) {

    // random numbers to keep track of selected its
    val numberOfHoles = listOf(0, 1, 2, 3, 4)
    val randomSelectedNumberOfHoles =
        numberOfHoles.shuffled().take(selectedVocabularyItems.size)////1,4,3
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
                    viewModel.increasePoint()

                }
            } else {
                Hole(
                    position = hole,
                    isTargetHole = false,
                    cardVisible = true,
                    img = selectedVocabularyItems[j]?.image ?: R.drawable.ic_launcher_background
                ) {
                    viewModel.decreasePoint()
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

    val configuration = LocalConfiguration.current
    val screenOrientation = configuration.orientation
    // Update UI based on orientation change
    if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
        // Handle landscape orientation
        val holeSize = 200.dp
//    OvalWithBorder()
        Canvas(
            modifier = Modifier
                .size(holeSize)
                .offset(position.x.dp, position.y.dp)

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
    } else {
        // Handle portrait orientation
        val holeSize = 150.dp
        Canvas(
            modifier = Modifier
                .size(holeSize)
                .offset(position.x.dp, position.y.dp)

        ) {
            drawOval(
                brush = Brush.horizontalGradient(listOf(Color.Black, Color.Black)),
                size = Size(width = 130.dp.toPx(), height = 20.dp.toPx()),
                topLeft = Offset(x = 120.dp.toPx(), y = 105.dp.toPx()),
                style = Stroke(width = 6.dp.toPx())
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
    val nonTargetClicked = remember { mutableStateOf(false) }

    var clickCounter by remember { mutableStateOf(0) }

    val context = LocalContext.current
    Card(
        modifier = Modifier
            .size(holeSize, holeSize + 100.dp)
            .padding(16.dp)
            .offset(position.x.dp + 110.dp, position.y.dp - 110.dp)
            .clickable {
                clickCounter += 1
                if (clickCounter == 1) {
                    clicked()
                    if (isTargetHole) {
                        targetClicked.value = true
                    } else {
                        nonTargetClicked.value = true
                    }
                }
            },

        colors = CardDefaults.cardColors(colorResource(id = R.color.light_ray)),
        elevation = CardDefaults.cardElevation(1.dp),
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
        } else if (nonTargetClicked.value) {
            val targetVocabularyItem = VocabularyItem("", 0, R.raw.sound__failure)
            MediaPlayerComponent(context, targetVocabularyItem)
        }

        Box(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            val currentImageId = if (targetClicked.value) {
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

fun generateRandomHolesForLANDSCAPE(): List<Offset> { // representing of point in cartesian space

    val holes = mutableListOf<Offset>()
    val randomX = Random.nextFloat() * 1.dp
    var randomY = Random.nextFloat() * 100.dp
    holes.add(Offset(randomX.value, randomY.value))

    randomY = Random.nextFloat() * 200.dp
    holes.add(Offset(randomX.value + (200), randomY.value + 100))

    randomY = Random.nextFloat() * 200.dp
    holes.add(Offset(randomX.value + (400), randomY.value + 200))
    randomY = Random.nextFloat() * 200.dp
    holes.add(Offset(randomX.value + (600), randomY.value))

    randomY = Random.nextFloat() * 200.dp
    holes.add(Offset(randomX.value + (800), randomY.value - 100))
    return holes
}

fun generateRandomHolesForPORTRAIT(): List<Offset> { // representing of point in cartesian space

    val holes = mutableListOf<Offset>()

    val randomX = Random.nextFloat() * 1.dp
    var randomY = Random.nextFloat() * 200.dp
    holes.add(Offset(randomX.value, randomY.value))

    randomY = Random.nextFloat() * 200.dp
    holes.add(Offset(randomX.value + (150), randomY.value + 100))

    randomY = Random.nextFloat() * 200.dp
    holes.add(Offset(randomX.value + (300), randomY.value + 200))

    randomY = Random.nextFloat() * 200.dp
    holes.add(Offset(randomX.value + (450), randomY.value))

    randomY = Random.nextFloat() * 200.dp
    holes.add(Offset(randomX.value + (600), randomY.value + 200))
    return holes
}


