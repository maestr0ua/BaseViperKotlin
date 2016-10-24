package com.example.akarpenko.basekotlin.base

import android.content.Context
import android.graphics.Point
import android.os.Bundle


interface BaseView {

    val viewArguments: Bundle

    val viewContext: Context

    val displaySize: Point

}
