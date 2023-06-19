package com.tes.games.whackaword.presentation.view.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tes.games.whackaword.domain.model.VocabularyItem

@Composable
fun VocabularyCard(
    vocabularyItem: VocabularyItem?,
    onClick: (VocabularyItem) -> Unit
) {
    val scaleAnimation = remember { androidx.compose.animation.core.Animatable(0f) }
    val cardSize = 200.dp

    Box(
        modifier = Modifier
            .size(cardSize)
            .clickable { vocabularyItem?.let { onClick(it) } }
            .animateContentSize(
                animationSpec = tween(durationMillis = 300)
            )
            .scale(scaleAnimation.value)
            .padding(8.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(4.dp)),
        contentAlignment = Alignment.Center
    ) {
        vocabularyItem?.let { item ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = item.image),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.word,
                    fontSize = 16.sp
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        scaleAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 300)
        )
    }
}
