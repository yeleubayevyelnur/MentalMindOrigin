package kz.mentalmind

import android.app.Application
import com.facebook.stetho.Stetho
import com.pusher.pushnotifications.PushNotifications
import kz.mentalmind.di.appModule
import kz.mentalmind.di.navigationModule
import kz.mentalmind.di.networkModule
import kz.mentalmind.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MentalMindApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        PushNotifications.start(applicationContext, getString(R.string.pusher_beam_instance_id))
        // Todo move this to language selection place
        PushNotifications.addDeviceInterest(getString(R.string.pusher_beam_interest_ru))
        Stetho.initializeWithDefaults(this)
        startKoin {
            androidContext(this@MentalMindApplication)
            androidLogger()
            modules(
                listOf(
                    appModule,
                    networkModule,
                    viewModelsModule,
                    navigationModule
                )
            )
        }
    }
}