package com.example.mathgame

import android.app.Application
import android.content.Context
import com.example.mathgame.di.AppComponent
import com.example.mathgame.di.AppComponentProvider
import com.example.mathgame.di.DaggerAppComponent


class MainApplication : Application(), AppComponentProvider {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this).also { it.inject(this) }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    override fun provideAppComponent(): AppComponent = appComponent
}










//class App: Application() {
//
//    lateinit var applicationComponent: ApplicationComponent
//
//    override fun onCreate() {
//        super.onCreate()
//        injectDependencies()
//    }
//
//    private fun injectDependencies() {
//        applicationComponent = DaggerApplicationComponent.create()
//        applicationComponent.inject(ApplicationModule(ApplicationModule))
//    }
//
//}