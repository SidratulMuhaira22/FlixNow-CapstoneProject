package hera.flixnow.made.submission

import android.app.Application
import hera.flixnow.made.submission.di.MyAppComponent
import hera.flixnow.made.submission.di.DaggerMyAppComponent
import hera.flixnow.made.core.di.ComponentCore
import hera.flixnow.made.core.di.DaggerComponentCore

open class MyApplication : Application() {

  private val componentCore: ComponentCore by lazy {
    DaggerComponentCore.factory().create(applicationContext)
  }

  val myAppComponent: MyAppComponent by lazy {
    DaggerMyAppComponent.factory().create(componentCore)
  }
}