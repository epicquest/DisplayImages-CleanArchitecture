import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.epicqueststudios.displayimages.presentation.viewmodels.MainViewModel
import com.epicqueststudios.displayimages.data.models.ImageItem
import com.epicqueststudios.displayimages.data.models.ImageItemAttributes
import com.epicqueststudios.displayimages.data.models.ImageItemData
import com.epicqueststudios.displayimages.data.models.ImageItemInfo
import com.epicqueststudios.displayimages.domain.Resource
import com.epicqueststudios.displayimages.domain.DownloadImagesUseCase
import com.epicqueststudios.displayimages.presentation.models.ImageUIItem
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
        val imageData = listOf(
            ImageItemData(ImageItem(1, ImageItemAttributes("name1", "desc1", ImageItemInfo("url1")))), ImageItemData(
                ImageItem(2, ImageItemAttributes("name2", "desc2", ImageItemInfo("url2")))
            )
        )
        val successResource = Resource.Success(imageData.map {
            ImageUIItem(it.item.id, it.item.attributes.name, it.item.attributes.description, it.item.attributes.imageInfo.imageUrl)
        })
        coEvery { downloadImagesUseCase.downloadImages(savedStateHandle, true) } returns successResource

        val observer = mockk<Observer<Resource<List<ImageUIItem>>>>()
        every { observer.onChanged(any()) } answers {}
        viewModel.images.observeForever(observer)

        viewModel.downloadImages(forced = true)
        verify { observer.onChanged(any()) }
        verify { observer.onChanged(any()) }
        coVerify(exactly = 1) { downloadImagesUseCase.downloadImages(savedStateHandle, true) }
    }

    @Test
    fun `downloadImages error`() = testScope.runBlockingTest {
        val errorMessage = "Failed to download images"
        val errorResource: Resource.Error<List<ImageUIItem>> = Resource.Error(errorMessage)
        coEvery { downloadImagesUseCase.downloadImages(savedStateHandle, true) } returns errorResource

        val observer = mockk<Observer<Resource<List<ImageUIItem>>>>()
        every { observer.onChanged(any()) } answers {}
        viewModel.images.observeForever(observer)

        viewModel.downloadImages(forced = true)
        verify { observer.onChanged(any()) }
        verify { observer.onChanged(any()) }
        coVerify(exactly = 1) { downloadImagesUseCase.downloadImages(savedStateHandle, true) }
    }

    @Test
    fun `downloadImages cache hit`() = testScope.runBlockingTest {
        val imageData = listOf(
            ImageItemData(ImageItem(1, ImageItemAttributes("name1", "desc1", ImageItemInfo("url1")))), ImageItemData(
                ImageItem(2, ImageItemAttributes("name2", "desc2", ImageItemInfo("url2")))
            )
        )
        val successResource = Resource.Success(listOf<ImageUIItem>())
        coEvery { downloadImagesUseCase.downloadImages(savedStateHandle, false) } returns successResource

        every { savedStateHandle.get<List<ImageItemData>>(MainViewModel.KEY_IMAGES) } returns imageData

        val observer = mockk<Observer<Resource<List<ImageUIItem>>>>()
        viewModel.images.observeForever(observer)

        viewModel.downloadImages(forced = false)
        verify { observer.onChanged(any()) }
        verify { observer.onChanged(any()) }

        coVerify(exactly = 0) { downloadImagesUseCase.downloadImages(savedStateHandle, false) }
    }

    @Test
    fun `downloadImages exception`() = testScope.runBlockingTest {
        val exceptionMessage = "Failed to download images"
        coEvery { downloadImagesUseCase.downloadImages(savedStateHandle, true) } throws Exception(exceptionMessage)

        val observer = mockk<Observer<Resource<List<ImageUIItem>>>>()
        viewModel.images.observeForever(observer)

        viewModel.downloadImages(forced = true)

        verify { observer.onChanged(any()) }
        verify { observer.onChanged(any()) }
    }
}