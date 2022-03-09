package com.geekbrains.tests

import com.geekbrains.tests.presenter.details.DetailsPresenter
import com.geekbrains.tests.view.details.ViewDetailsContract
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DetailsPresenterTest{

    private lateinit var presenter: DetailsPresenter

    @Mock
    private lateinit var viewContract: ViewDetailsContract

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        presenter = DetailsPresenter(0)
        presenter.onAttach(viewContract)
    }

    @Test
    fun detailsPresenter_RightInitCounter_Test() {
        assertEquals(0, presenter.count)
    }

    @Test
    fun setCounter_Completed_Test() {
        presenter.setCounter(5)
        assertEquals(5, presenter.count)
    }

    @Test
    fun onAttach_ViewInit_NotNull_Test() {
        assertNotNull(presenter.viewContract)
    }

    @Test
    fun onAttach_ViewInit_Completed_Test() {
        assertEquals(viewContract, presenter.viewContract)
    }

    @Test
    fun onDetach_Completed_Test() {
        presenter.onDetach()
        assertNull(presenter.viewContract)
    }

    @Test
    fun onIncrement_Completed_Test() {
        presenter.onAttach(viewContract)
        presenter.onIncrement()
        assertEquals(1, presenter.count)
        verify(viewContract).setCount(1)
    }

    @Test
    fun onDecrement_Completed_Test() {
        presenter.onAttach(viewContract)
        presenter.onDecrement()
        assertEquals(-1, presenter.count)
        verify(viewContract).setCount(-1)
    }
}