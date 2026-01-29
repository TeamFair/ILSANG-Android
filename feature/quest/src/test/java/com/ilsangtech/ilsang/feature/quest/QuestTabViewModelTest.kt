package com.ilsangtech.ilsang.feature.quest

import androidx.paging.PagingData
import app.cash.turbine.test
import com.ilsangtech.ilsang.core.domain.AreaRepository
import com.ilsangtech.ilsang.core.domain.QuestCompleteDateRepository
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.quest.QuestDetail
import com.ilsangtech.ilsang.core.model.quest.TypedQuest
import com.ilsangtech.ilsang.core.ui.quest.model.TypedQuestUiModel
import com.ilsangtech.ilsang.core.ui.quest.model.toUiModel
import com.ilsangtech.ilsang.feature.quest.model.QuestTabUiModel
import com.ilsangtech.ilsang.feature.quest.model.RepeatQuestTypeUiModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class QuestTabViewModelTest {

    private lateinit var viewModel: QuestTabViewModel

    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var areaRepository: AreaRepository

    @MockK
    private lateinit var questRepository: QuestRepository

    @MockK
    private lateinit var questCompleteDateRepository: QuestCompleteDateRepository

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(this.testDispatcher)
        MockKAnnotations.init(this, relaxed = true)

        // Common mocks
        every { userRepository.getMyInfo() } returns flowOf(
            mockk(relaxed = true)
        )
        every { questCompleteDateRepository.questCompleteDateMapFlow } returns MutableStateFlow(
            emptyMap()
        )
        every {
            questRepository.getTypedQuests(
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns flowOf(PagingData.from(listOf(mockk<TypedQuest>(relaxed = true))))

        viewModel = QuestTabViewModel(
            userRepository = userRepository,
            areaRepository = areaRepository,
            questRepository = questRepository,
            questCompleteDateRepository = questCompleteDateRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `초기 선택된 퀘스트 탭은 NORMAL 이어야 한다`() = runTest {
        // Then
        assertEquals(QuestTabUiModel.NORMAL, viewModel.selectedQuestTab.value)
    }

    @Test
    fun `selectQuestType을 호출하면 selectedQuestTab이 업데이트 되어야 한다`() = runTest {
        // When
        viewModel.selectQuestType(QuestTabUiModel.EVENT)

        // Then
        assertEquals(QuestTabUiModel.EVENT, viewModel.selectedQuestTab.value)
    }

    @Test
    fun `REPEAT 탭 선택 시 selectedRepeatType이 Daily로 설정되어야 한다`() = runTest {
        // When
        viewModel.selectQuestType(QuestTabUiModel.REPEAT)

        // Then
        assertEquals(QuestTabUiModel.REPEAT, viewModel.selectedQuestTab.value)
        assertEquals(RepeatQuestTypeUiModel.Daily, viewModel.selectedRepeatType.value)
    }

    @Test
    fun `selectRepeatPeriod를 호출하면 selectedRepeatType이 업데이트 되어야 한다`() = runTest {
        // Given
        viewModel.selectQuestType(QuestTabUiModel.REPEAT)

        // When
        viewModel.selectRepeatPeriod(RepeatQuestTypeUiModel.Weekly)

        // Then
        assertEquals(RepeatQuestTypeUiModel.Weekly, viewModel.selectedRepeatType.value)
    }

    @Test
    fun `selectQuest를 호출하면 selectedQuestDetail이 업데이트 되어야 한다`() = runTest {
        // Given
        val quest = mockk<TypedQuestUiModel>(relaxed = true) {
            every { questId } returns 1
            every { isIsZoneQuest } returns false
        }
        val questDetail = mockk<QuestDetail>(relaxed = true)
        coEvery { questRepository.getQuestDetail(1, false) } returns flowOf(questDetail)

        // When
        viewModel.selectQuest(quest)

        // Then
        viewModel.selectedQuestDetail.test {
            assertEquals(questDetail.toUiModel(quest.remainHours), awaitItem())
        }
    }

    @Test
    fun `unselectQuest를 호출하면 selectedQuestDetail이 null이 되어야 한다`() = runTest {
        // Given
        val quest = mockk<TypedQuestUiModel>(relaxed = true) {
            every { questId } returns 1
            every { isIsZoneQuest } returns false
        }
        val questDetail = mockk<QuestDetail>(relaxed = true)
        coEvery { questRepository.getQuestDetail(1, false) } returns flowOf(questDetail)
        viewModel.selectQuest(quest)
        viewModel.selectedQuestDetail.test {
            // Wait for initial selection
            awaitItem()

            // When
            viewModel.unselectQuest()

            // Then
            assertNull(awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `updateQuestFavoriteStatus를 호출하여 즐겨찾기를 등록하면 registerFavoriteQuest가 호출되어야 한다`() = runTest {
        // Given
        val questId = 123
        coEvery { questRepository.registerFavoriteQuest(questId) } returns Result.success(Unit)

        // When
        viewModel.updateQuestFavoriteStatus(questId, isFavorite = false)

        // Then
        coVerify(exactly = 1) { questRepository.registerFavoriteQuest(questId) }
        coVerify(exactly = 0) { questRepository.deleteFavoriteQuest(any()) }
    }

    @Test
    fun `updateQuestFavoriteStatus를 호출하여 즐겨찾기를 해제하면 deleteFavoriteQuest가 호출되어야 한다`() = runTest {
        // Given
        val questId = 123
        coEvery { questRepository.deleteFavoriteQuest(questId) } returns Result.success(Unit)

        // When
        viewModel.updateQuestFavoriteStatus(questId, isFavorite = true)

        // Then
        coVerify(exactly = 0) { questRepository.registerFavoriteQuest(any()) }
        coVerify(exactly = 1) { questRepository.deleteFavoriteQuest(questId) }
    }
}