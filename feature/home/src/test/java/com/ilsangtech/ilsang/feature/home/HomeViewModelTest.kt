package com.ilsangtech.ilsang.feature.home

import app.cash.turbine.test
import com.ilsangtech.ilsang.core.domain.AreaRepository
import com.ilsangtech.ilsang.core.domain.BannerRepository
import com.ilsangtech.ilsang.core.domain.QuestCompleteDateRepository
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.domain.RankRepository
import com.ilsangtech.ilsang.core.domain.SeasonRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.banner.Banner
import com.ilsangtech.ilsang.core.model.quest.LargeRewardQuest
import com.ilsangtech.ilsang.core.model.quest.PopularQuest
import com.ilsangtech.ilsang.core.model.quest.RecommendedQuest
import com.ilsangtech.ilsang.core.model.rank.UserRank
import com.ilsangtech.ilsang.core.model.season.Season
import com.ilsangtech.ilsang.feature.home.model.HomeTabUiState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: HomeViewModel

    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var seasonRepository: SeasonRepository

    @MockK
    private lateinit var areaRepository: AreaRepository

    @MockK
    private lateinit var bannerRepository: BannerRepository

    @MockK
    private lateinit var questRepository: QuestRepository

    @MockK
    private lateinit var rankRepository: RankRepository

    @MockK
    private lateinit var questCompleteDateRepository: QuestCompleteDateRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun `정상적인 데이터가 emit되면 Success 상태를 반환해야 한다`() = runTest {
        // given
        every { userRepository.getMyInfo() } returns flowOf(mockk(relaxed = true))
        coEvery { areaRepository.getCommercialArea(any()) } returns mockk(relaxed = true) {
            every { areaName } returns "강남역 상권"
        }
        every { bannerRepository.getBanners() } returns flowOf(listOf(mockk<Banner>(relaxed = true)))
        every { questRepository.getPopularQuests(any()) } returns flowOf(
            listOf(mockk<PopularQuest>(relaxed = true))
        )
        every { questRepository.getRecommendedQuests(any()) } returns flowOf(
            listOf(mockk<RecommendedQuest>(relaxed = true))
        )
        every {
            questRepository.getLargeRewardQuests(any(), any())
        } returns flowOf(listOf(mockk<LargeRewardQuest>(relaxed = true)))
        every { rankRepository.getTotalTopRankUsers(any()) } returns flowOf(
            listOf(mockk<UserRank>(relaxed = true))
        )
        coEvery { seasonRepository.getCurrentSeason() } returns mockk<Season>(relaxed = true)

        coEvery { questCompleteDateRepository.questCompleteDateMapFlow } returns MutableStateFlow(
            emptyMap()
        )

        // when
        viewModel = HomeViewModel(
            userRepository,
            seasonRepository,
            areaRepository,
            bannerRepository,
            questRepository,
            rankRepository,
            questCompleteDateRepository
        )

        // then
        viewModel.homeTabUiState.test {
            skipItems(1)
            assertTrue(awaitItem() is HomeTabUiState.Success)
            assertEquals(
                "강남역 상권",
                (viewModel.homeTabUiState.value as HomeTabUiState.Success).data.myInfo.isCommericalAreaName
            )
        }
    }

    @Test
    fun `Repository 에서 예외가 발생하면 Error 상태를 반환해야 한다`() = runTest {
        // given
        every { userRepository.getMyInfo() } returns flow {
            throw IllegalStateException("Repository Error")
        }
        // when
        viewModel = HomeViewModel(
            userRepository,
            seasonRepository,
            areaRepository,
            bannerRepository,
            questRepository,
            rankRepository,
            questCompleteDateRepository
        )

        // then
        viewModel.homeTabUiState.test {
            skipItems(1)
            val item = awaitItem()
            assertTrue(item is HomeTabUiState.Error)
            assertTrue((item as HomeTabUiState.Error).throwable is IllegalStateException)
            assertEquals("Repository Error", item.throwable.message)
        }
    }

    @Test
    fun `퀘스트를 선택했을 때 SelectedQuest가 업데이트 되어야 한다`() = runTest {
        // given
        every { userRepository.getMyInfo() } returns flowOf(mockk(relaxed = true))

        every {
            questRepository.getQuestDetail(any(), any())
        } returns flowOf(mockk(relaxed = true) {
            every { title } returns "Test Quest"
        })

        viewModel = HomeViewModel(
            userRepository,
            seasonRepository,
            areaRepository,
            bannerRepository,
            questRepository,
            rankRepository,
            questCompleteDateRepository
        )

        // when
        viewModel.selectQuest(1)

        viewModel.selectedQuest.test {
            // then
            skipItems(1)
            val item = awaitItem()
            assertTrue(item != null)
            assertEquals("Test Quest", item?.title)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `즐겨찾기 등록 시 퀘스트 상세 재조회가 트리거된다 - turbine`() = runTest {
        // given
        every { userRepository.getMyInfo() } returns flowOf(mockk(relaxed = true))
        every {
            questRepository.getQuestDetail(any(), any())
        } returns flowOf(
            mockk(relaxed = true) {
                every { id } returns 1
                every { favoriteYn } returns false
            }
        )

        coEvery {
            questRepository.registerFavoriteQuest(any())
        } returns Result.success(Unit)

        viewModel = HomeViewModel(
            userRepository,
            seasonRepository,
            areaRepository,
            bannerRepository,
            questRepository,
            rankRepository,
            questCompleteDateRepository
        )

        viewModel.selectedQuest.test {
            skipItems(1)
            // when
            viewModel.selectQuest(1)
            val first = awaitItem()
            assertNotNull(first)

            viewModel.updateQuestFavoriteStatus()
            advanceUntilIdle()

            // then
            coVerify(exactly = 1) {
                questRepository.registerFavoriteQuest(1)
            }
            coVerify(atLeast = 2) {
                questRepository.getQuestDetail(1, any())
            }
        }
    }

    @Test
    fun `시즌 오픈 다이얼로그 읽음 처리 시 읽음 상태가 업데이트 되어야 한다`() = runTest {
        // given
        viewModel = HomeViewModel(
            userRepository,
            seasonRepository,
            areaRepository,
            bannerRepository,
            questRepository,
            rankRepository,
            questCompleteDateRepository
        )
        // when
        viewModel.seasonOpenDialogShown(true)
        advanceUntilIdle()
        // then
        coVerify { userRepository.updateSeasonOpenDialogRejected(true) }
        assertEquals(viewModel.shouldShowSeasonOpenDialog.value, false)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }
}
