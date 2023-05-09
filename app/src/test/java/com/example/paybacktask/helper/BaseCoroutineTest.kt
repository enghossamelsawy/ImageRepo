package com.example.paybacktask.helper

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import org.junit.Rule

@Suppress("UnnecessaryAbstractClass")
@OptIn(ExperimentalCoroutinesApi::class)
open class BaseCoroutineTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    protected val coTestScope: TestScope get() = coroutinesTestRule.scope

    protected val coTestScheduler: TestCoroutineScheduler get() = coroutinesTestRule.scheduler

    protected val coTestDispatcher: TestDispatcher get() = coroutinesTestRule.dispatcher
}