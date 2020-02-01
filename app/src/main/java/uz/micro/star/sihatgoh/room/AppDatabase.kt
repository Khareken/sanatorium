package uz.xsoft.lesson4ict.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.micro.star.sihatgoh.models.SanatoriumFullData
import uz.micro.star.sihatgoh.models.SanatoriumTinyData

@Database(entities = [SanatoriumFullData::class,SanatoriumTinyData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getInputData(): InputDao

    companion object {
        var database: AppDatabase? = null
        fun init(context: Context) {
            database = Room.databaseBuilder(
                context.applicationContext, AppDatabase::
                class.java, "lesson"
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}