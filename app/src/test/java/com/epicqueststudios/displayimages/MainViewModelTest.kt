import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.epicqueststudios.displayimages.presentation.viewmodels.MainViewModel
import com.epicqueststudios.displayimages.data.DownloadImagesResponse
import com.epicqueststudios.displayimages.data.ImageItem
import com.epicqueststudios.displayimages.data.ImageItemAttributes
import com.epicqueststudios.displayimages.data.ImageItemData
import com.epicqueststudios.displayimages.data.ImageItemInfo
import com.epicqueststudios.displayimages.data.Resource
import com.epicqueststudios.displayimages.domain.DownloadImagesUseCase
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class TestApplication : Application()
@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var downloadImagesUseCase: DownloadImagesUseCase
    private lateinit var savedStateHandle: SavedStateHandle
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        downloadImagesUseCase = mockk()
        savedStateHandle = mockk(relaxed = true)
        viewModel = MainViewModel(
            TestApplication(),
            testDispatcher,
            savedStateHandle,
            downloadImagesUseCase
        )
    }
    @After
    fun tearDown() {
        // Reset the Main dispatcher after testing
        Dispatchers.resetMain()
    }
    @Test
    fun `downloadImages success`() = testScope.runBlockingTest {
        val imageData = listOf(ImageItemData(ImageItem(1, ImageItemAttributes("name1", "desc1", ImageItemInfo("url1")))), ImageItemData(ImageItem(2, ImageItemAttributes("name2", "desc2", ImageItemInfo("url2")))))
        val successResource = Resource.Success(DownloadImagesResponse(imageData))
        coEvery { downloadImagesUseCase.downloadImages() } returns successResource
        coEvery { viewModel.retrieveData() } returns null

        val observer = mockk<Observer<Resource<List<ImageItemData>>>>()
        every { observer.onChanged(any()) } answers {}
        viewModel.images.observeForever(observer)

        viewModel.downloadImages(forced = true)
        verify { observer.onChanged(any()) }
        verify { observer.onChanged(any()) }
        coVerify { savedStateHandle[MainViewModel.KEY_IMAGES] = imageData }
        coVerify(exactly = 1) { downloadImagesUseCase.downloadImages() }
    }

    @Test
    fun `downloadImages error`() = testScope.runBlockingTest {
        val errorMessage = "Failed to download images"
        val errorResource:Resource.Error<DownloadImagesResponse> = Resource.Error(errorMessage)
        coEvery { downloadImagesUseCase.downloadImages() } returns errorResource
        coEvery { viewModel.retrieveData() } returns null

        val observer = mockk<Observer<Resource<List<ImageItemData>>>>()
        every { observer.onChanged(any()) } answers {}
        viewModel.images.observeForever(observer)

        viewModel.downloadImages(forced = true)
        verify { observer.onChanged(any()) }
        verify { observer.onChanged(any()) }
        coVerify(exactly = 1) { downloadImagesUseCase.downloadImages() }
    }

    @Test
    fun `downloadImages cache hit`() = testScope.runBlockingTest {
        val imageData = listOf(ImageItemData(ImageItem(1, ImageItemAttributes("name1", "desc1", ImageItemInfo("url1")))), ImageItemData(ImageItem(2, ImageItemAttributes("name2", "desc2", ImageItemInfo("url2")))))
        val successResource = Resource.Success(DownloadImagesResponse(listOf()))
        coEvery { downloadImagesUseCase.downloadImages() } returns successResource
        coEvery { viewModel.retrieveData() } returns imageData

        every { savedStateHandle.get<List<ImageItemData>>(MainViewModel.KEY_IMAGES) } returns imageData

        val observer = mockk<Observer<Resource<List<ImageItemData>>>>()
        viewModel.images.observeForever(observer)

        viewModel.downloadImages(forced = false)
        verify { observer.onChanged(any()) }
        verify { observer.onChanged(any()) }

        coVerify(exactly = 0) { downloadImagesUseCase.downloadImages() }
    }

    @Test
    fun `downloadImages exception`() = testScope.runBlockingTest {
        val exceptionMessage = "Failed to download images"
        coEvery { downloadImagesUseCase.downloadImages() } throws Exception(exceptionMessage)

        val observer = mockk<Observer<Resource<List<ImageItemData>>>>()
        viewModel.images.observeForever(observer)

        viewModel.downloadImages(forced = true)

        verify { observer.onChanged(any()) }
        verify { observer.onChanged(any()) }
    }
}