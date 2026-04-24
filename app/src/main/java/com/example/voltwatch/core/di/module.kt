package com.example.voltwatch.core.di

import android.content.Context
import androidx.room.Room
import com.example.voltwatch.core.data.repository.BatteryRopoImp
import com.example.voltwatch.core.data.roomdatabase.dao.BatteryDao
import com.example.voltwatch.core.data.roomdatabase.db.VoltWatchDatabase
import com.example.voltwatch.core.domain.BatteryRepository
import com.example.voltwatch.core.useCase.addDataUseCase
import com.example.voltwatch.core.useCase.getBattery_dataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object module {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): VoltWatchDatabase {
        return Room.databaseBuilder(
            context,
            VoltWatchDatabase::class.java,
            "voltwatch_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(db: VoltWatchDatabase): BatteryDao {
        return db.batteryDao()
    }

    @Provides
    @Singleton
    fun provideRepository(dao: BatteryDao): BatteryRepository {
        return BatteryRopoImp(dao)
    }

    @Provides
    @Singleton
    fun useCaseProvider(batteryRopoImp: BatteryRopoImp): getBattery_dataUseCase {
        return getBattery_dataUseCase(batteryRopoImp)
    }

    @Provides
    @Singleton
    fun useCasesaveDataProvider(batteryRopoImp: BatteryRopoImp): addDataUseCase {
        return addDataUseCase(batteryRopoImp)
    }


}