package be.senne.meerweer.domain.repository

import be.senne.meerweer.data.local.WeatherDao
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherDao: WeatherDao) : WeatherRepository {
}