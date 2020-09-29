package kz.mentalmind

import android.app.Application
import com.facebook.stetho.Stetho
import kz.mentalmind.di.appModule
import kz.mentalmind.di.networkModule
import kz.mentalmind.di.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MentalMindApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        startKoin {
            androidContext(this@MentalMindApplication)
            androidLogger()
            modules(
                listOf(
                    appModule,
                    networkModule,
                    viewModel
                )
            )
        }
    }
}