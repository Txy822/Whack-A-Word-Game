package com.tes.games.whackaword.viewmodeltest

import android.content.Context
import android.media.MediaPlayer
import com.tes.games.whackaword.R
import com.tes.games.whackaword.domain.model.VocabularyItem
import com.tes.games.whackaword.domain.repository.VocabularyGameRepository
import com.tes.games.whackaword.presentation.viewmodel.VocabularyGameViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class VocabularyGameViewModelTest {

    // Mock dependencies
    @Mock
    private lateinit var repository: VocabularyGameRepository

    @Mock
    private lateinit var context: Context

    val mediaPlayerMock: MediaPlayer = mock()

    // Create an instance of the ViewModel
    private lateinit var viewModel: VocabularyGameViewModel

    @Before
    fun setup() {
        // Initialize the mocks
        MockitoAnnotations.initMocks(this)

        // Create the ViewModel instance
        viewModel = VocabularyGameViewModel(repository, context)
    }

    @Test
    fun increasePoint_increasesPointAndUpdatesLevel() {
        // Initialize the initial point value
        viewModel.increasePoint()

        // Verify that the point value has increased
        assertEquals(1, viewModel.point.value)

        // Verify that the level has been updated
        assertEquals(1, viewModel.level.value)
    }

    @Test
    fun decreasePoint_decreasesPoint() {

        // Call the method to decrease the point
        viewModel.decreasePoint()

        // Verify that the point value has decreased
        assertEquals(-1, viewModel.point.value)
    }

    @Test
    fun startGame_initializesDataAndPlaysMedia() {
        // Mock the repository to return some vocabulary items
        val vocabularyItems = listOf(
            VocabularyItem("Apple",R.drawable.fc_apple, R.raw.fc_apple),
            VocabularyItem("Cake", R.drawable.fc_cake, R.raw.fc_cake),
            VocabularyItem("Tomato",R.drawable.fc_tomato, R.raw.fc_tomato)
        )
        //this list is used as twice shuffling makes list different
        val vocabularyItems2 = listOf(
            VocabularyItem("Apple",R.drawable.fc_apple, R.raw.fc_apple),
        )
        `when`(repository.getVocabularyItems()).thenReturn(vocabularyItems2)

        // Mock the shuffled selected vocabulary items
        val shuffledVocabularyItems = vocabularyItems2.shuffled()
        val selectedVocabularyItems = shuffledVocabularyItems.take(1)
        `when`(repository.getVocabularyItems()).thenReturn(vocabularyItems2)


        // Mock the shuffled target vocabulary item
       val shuffledSelectedItems= selectedVocabularyItems.shuffled()
        val targetVocabularyItem = shuffledSelectedItems.first()

        // Call the method to start the game
        viewModel.startGame()

        // Verify that the vocabulary items have been set
        assertEquals(vocabularyItems2, viewModel.vocabularyItems.value)

        // Verify that the selected vocabulary items have been set
        assertEquals(selectedVocabularyItems, viewModel.selectedVocabularyItems.value)

        // Verify that the target vocabulary item has been set
        assertEquals(targetVocabularyItem, viewModel.targetVocabularyItem.value)

       // viewModel.mediaPlayer = mediaPlayerMock
        // Verify that the media player has been started
        //verify(mediaPlayerMock).start()

        // Verify that the media player state has been updated
       // assertTrue(viewModel.mediaPlayerState.value)
    }

    @Test
    fun restartGame_resetsGameData() {
        // Call the method to restart the game
        viewModel.restartGame()

        // Verify that the reset game flag has been set
        assertTrue(viewModel.resetGame.value)

        // Verify that the vocabulary items, selected vocabulary items, and target vocabulary item have been reset
        assertEquals(emptyList<VocabularyItem>(), viewModel.vocabularyItems.value)
        assertEquals(emptyList<VocabularyItem>(), viewModel.selectedVocabularyItems.value)
        assertNull(viewModel.targetVocabularyItem.value)
    }

    // Add more test cases as needed for other methods and scenarios

}