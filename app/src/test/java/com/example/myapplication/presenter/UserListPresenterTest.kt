package com.example.myapplication.presenter

import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.business.model.User
import com.example.myapplication.interactor.UserInteractor
import com.example.myapplication.view.fragment.UserIView
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test


class UserListPresenterTest {

    private lateinit var presenter: UserListPresenter
    private val interactor: UserInteractor = mockk(relaxed = true)
    open var context: AppCompatActivity = mockk(relaxed = true)
    private lateinit var view: UserIView

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        view = mockk(relaxed = true)
        presenter = UserListPresenter(view, context = context)
        setPrivateField("interactor", interactor)
    }

    private fun setPrivateField(name: String, item: Any) {
        val field = presenter.javaClass.getDeclaredField(name)
        field.isAccessible = true
        field.set(presenter, item)
    }

    @Test
    fun loadViewData() {
        /* When */
        presenter.loadViewData()
        every { interactor.getAllUser(false) } answers {}

        /* Then*/
        verify { presenter["loadAllUser"](false) }
    }

    @Test
    fun `Test loadUsers isRefresh true`() {
        /* Given */
        val list: ArrayList<User> = mockk()

        /* When */
        presenter.loadUsers(list, true)

        /* Then */
        verify { view.actualizeAllList(list) }
        verify { view.hideKeyboard() }
    }

    @Test
    fun `Test loadUsers isRefresh false`() {
        /* Given */
        val list: ArrayList<User> = mockk()

        /* When */
        presenter.loadUsers(list, false)

        /* Then */
        verify { view.initRecyclerView(list) }
        verify { view.hideKeyboard() }
    }

    @After
    fun tearDown() {
        unmockkAll()
        clearAllMocks()
    }
}