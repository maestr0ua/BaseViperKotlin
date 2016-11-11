package com.example.akarpenko.basekotlin.other

import com.example.akarpenko.basekotlin.App
import com.example.akarpenko.basekotlin.BuildConfig
import com.example.akarpenko.basekotlin.UnitTestApp
import com.example.akarpenko.basekotlin.other.di.components.TestAppComponent
import org.junit.Before
import org.junit.Ignore
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by akarpenko on 08.11.16.
 */

@RunWith(RobolectricTestRunner::class)
@Config(application = UnitTestApp::class, constants = BuildConfig::class, sdk = intArrayOf(21))
@Ignore
open class BaseTest {

    lateinit var appComponent: TestAppComponent

    @Before
    open fun setUp() {
        appComponent = App.component as TestAppComponent
    }

}