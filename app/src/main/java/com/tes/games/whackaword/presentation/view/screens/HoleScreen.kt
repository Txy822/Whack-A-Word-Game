package com.tes.games.whackaword.presentation.view.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun HoleScreen() {
    val holeSize = 80.dp
    val holeCount = 5
    val minHorizontalDistance = 200.dp
    val minVerticalDistance = 100.dp
    val maxWidth = 800.dp
    val maxHeight = 600.dp

    val holes = generateRandomHoles(holeCount, minHorizontalDistance, minVerticalDistance, maxWidth, maxHeight)

    Box {
        for (hole in holes) {
            HoleCard(
                modifier = Modifier
                    .size(holeSize)
                    .offset(hole.x, hole.y)
            )
        }
    }
}

@Composable
fun HoleCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(Color.Black),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(2.dp),
        border = BorderStroke(
            width = 2.dp,
            color = Color.Blue,
        ),
    ){}
}

data class Hole2(val x: Dp, val y: Dp)

fun generateRandomHoles(
    holeCount: Int,
    minHorizontalDistance: Dp,
    minVerticalDistance: Dp,
    maxWidth: Dp,
    maxHeight: Dp
): List<Hole2> {
    val holes = mutableListOf<Hole2>()
//    var prevX = Random.nextInt(0, (maxWidth - minHorizontalDistance).value.toInt()).dp

    var prevX = Random.nextInt((maxWidth - minHorizontalDistance).value.toInt() + 1).dp
    var prevY = Random.nextInt((maxHeight - minVerticalDistance).value.toInt() + 1).dp

    println("prevX1:$prevX")
    println("prevX1.value.toInt():${prevX.value.toInt()}")
    println("prevXminHorizontalDistance:${minHorizontalDistance.value.toInt()}")
    println("prevX(maxWidth - minHorizontalDistance).value.toInt():${(maxWidth - minHorizontalDistance).value.toInt()}")
//    var prevY = Random.nextInt(0, (maxHeight - minVerticalDistance+1.dp).value.toInt()).dp
    println("prevY:$prevY")
    println("prevY:${prevY.value.toInt()}")
    holes.add(Hole2(prevX, prevY))

    for (i in 1 until holeCount) {
        val newX = Random.nextInt(prevX.value.toInt() + minHorizontalDistance.value.toInt(), (maxWidth - minHorizontalDistance).value.toInt()).dp
        val newY = Random.nextInt(prevY.value.toInt() + minVerticalDistance.value.toInt(), (maxHeight - minVerticalDistance + 1.dp).value.toInt()).dp

        holes.add(Hole2(newX, newY))

        prevX = newX
        prevY = newY
    }

    return holes
}