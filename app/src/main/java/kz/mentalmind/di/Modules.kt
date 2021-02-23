package kz.mentalmind.di

import android.preference.PreferenceManager
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.google.gson.Gson
import kz.mentalmind.data.api.ApiService
import kz.mentalmind.data.repository.AuthRepository
import kz.mentalmind.data.repository.MainRepository
import kz.mentalmind.data.repository.MeditationRepository
import kz.mentalmind.data.repository.UserRepository
import kz.mentalmind.ui.authorization.AuthViewModel
import kz.mentalmind.ui.creative.CreativeViewModel
import kz.mentalmind.ui.instruments.InstrumentsViewModel
import kz.mentalmind.ui.main.MainViewModel
import kz.mentalmind.ui.meditations.MeditationsViewModel
import kz.mentalmind.ui.player.PlayerViewModel
import kz.mentalmind.ui.profile.ProfileViewModel
import kz.mentalmind.ui.purchase.TariffsViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        AuthRepository(apiService = get(), sPrefs = get(), gson = get())
    }
    single {
        MainRepository(get(), get(), get())
    }
    single {
        UserRepository(get(), get(), get())
    }
    single {
        PreferenceManager.getDefaultSharedPreferences(androidContext())
    }
    single {
        MeditationRepository(get(), get())
    }
}

val networkModule = module {
    single {
        Gson()
    }

    single {
        OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .followRedirects(false)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://server.mentalmind.kz/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    single {
        get<Retrofit>(Retrofit::class.java).create(ApiService::class.java)
    }
}

val viewModelsModule = module {
    viewModel {
        AuthViewModel(authRepository = get())
    }
    viewModel {
        CreativeViewModel(mainRepository = get())
    }
    viewModel {
        InstrumentsViewModel(mainRepository = get())
    }
    viewModel {
        MainViewModel(mainRepository = get())
    }
    viewModel {
        MeditationsViewModel(meditationRepository = get())
    }
    viewModel {
        ProfileViewModel(mainRepository = get())
    }
    viewModel {
        PlayerViewModel(mainRepository = get())
    }
    viewModel {
        TariffsViewModel(mainRepository = get())
    }
}

val navigationModule = module {
    single {
        Cicerone.create()
    }

    single {
        get<Cicerone<Router>>(Cicerone::class.java).router
    }

    single {
        get<Cicerone<Router>>(Cicerone::class.java).getNavigatorHolder()
    }
}