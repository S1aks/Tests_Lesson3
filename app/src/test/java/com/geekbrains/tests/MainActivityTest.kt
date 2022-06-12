package com.geekbrains.tests

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.model.SearchResult
import com.geekbrains.tests.view.search.MainActivity
import com.geekbrains.tests.view.search.SearchResultAdapter
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLooper.idleMainLooper
import org.robolectric.shadows.ShadowToast

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var context: Context

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            assertNotNull(it)
        }
    }

    @Test
    fun activity_IsResumed() {
        assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun searchEditText_NotNull() {
        scenario.onActivity {
            val searchEditText = it.findViewById<TextView>(R.id.searchEditText)
            assertNotNull(searchEditText)
        }
    }

    @Test
    fun searchEditText_IsVisible() {
        scenario.onActivity {
            val searchEditText = it.findViewById<TextView>(R.id.searchEditText)
            assertEquals(View.VISIBLE, searchEditText.visibility)
        }
    }

    @Test
    fun toDetailsActivityButton_IsVisible() {
        scenario.onActivity {
            val toDetailsActivityButton = it.findViewById<Button>(R.id.toDetailsActivityButton)
            assertEquals(View.VISIBLE, toDetailsActivityButton.visibility)
        }
    }

    @Test
    fun searchEditText_Test() {
        scenario.onActivity {
            val editText = it.findViewById<EditText>(R.id.searchEditText)
            val text = "test_text"
            editText.setText(text, TextView.BufferType.EDITABLE)
            assertNotNull(editText.text)
            assertEquals(text, editText.text.toString())
        }

    }

    @Test
    fun displaySearchResults_DataSentToAdapterCompleted() {
        scenario.onActivity {
            val adapter = spy(SearchResultAdapter::class.java)
            val searchResults = listOf(SearchResult(
                id = 0,
                name = "",
                fullName = "",
                private = false,
                description = "",
                updatedAt = "",
                size = 1,
                stargazersCount = 1,
                language = "",
                hasWiki = false,
                archived = false,
                score = 0.0
            ))
            `when`(adapter.updateResults(searchResults)).thenCallRealMethod()
            it.displaySearchResults(searchResults, 1)
            verify(adapter).updateResults(searchResults)
            assertEquals(1, adapter.itemCount)
        }
    }

    @Test
    fun displayError_DefaultToastShowed_Test() {
        scenario.onActivity {
            val toastText = context.getText(R.string.undefined_error)
            it.displayError()
            idleMainLooper()
            assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo(toastText)
        }
    }

    @Test
    fun displayError_UserToastShowed_Test() {
        scenario.onActivity {
            val toastText = "user_error_text"
            it.displayError(toastText)
            idleMainLooper()
            assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo(toastText)
        }
    }

    @Test
    fun displayLoading_Show_Test() {
        scenario.onActivity {
            val progressBar = it.findViewById<ProgressBar>(R.id.progressBar)
            it.displayLoading(true)
            assertEquals(View.VISIBLE, progressBar.visibility)
        }
    }

    @Test
    fun displayLoading_Hide_Test() {
        scenario.onActivity {
            val progressBar = it.findViewById<ProgressBar>(R.id.progressBar)
            it.displayLoading(false)
            assertEquals(View.GONE, progressBar.visibility)
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}
