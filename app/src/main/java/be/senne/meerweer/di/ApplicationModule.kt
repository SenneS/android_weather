package be.senne.meerweer.di

import android.content.Context
import androidx.room.Room
import be.senne.meerweer.data.local.WeatherDao
import be.senne.meerweer.data.local.WeatherDatabase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).build()

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao = weatherDatabase.weatherDao()
    @Singleton
    @Provides
    fun provideWeatherDatabase(@ApplicationContext appContext: Context): WeatherDatabase = Room.databaseBuilder(appContext, WeatherDatabase::class.java, "weatherdb").build()
}