package uz.xsoft.lesson4ict.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.micro.star.sihatgoh.models.SanatoriumFullData
import uz.micro.star.sihatgoh.models.SanatoriumTinyData

@Dao
interface InputDao {

    @Query("SELECT * FROM SanatoriumFullData")
    fun get(): LiveData<List<SanatoriumFullData>>

    @Insert
        (onConflict = OnConflictStrategy.REPLACE)
    fun save(inputData: SanatoriumFullData)

    @Query("SELECT * FROM SanatoriumTinyData")
    fun getTiny(): LiveData<List<SanatoriumTinyData>>

    @Insert
        (onConflict = OnConflictStrategy.REPLACE)
    fun saveTiny(inputData: SanatoriumTinyData)

    @Query("SELECT * FROM SanatoriumTinyData")
    fun getTinySanatoryOffline(): List<SanatoriumTinyData>

    @Query("SELECT * FROM SanatoriumFullData")
    fun getSanatoryOffline(): List<SanatoriumFullData>

    @Query("SELECT * FROM SanatoriumFullData where id=:id")
    fun getSanatoryOfflineById(id: Int): SanatoriumFullData
//
//    @Delete
//    fun delete(model: TrainerData)
//
//    @Update
//    fun update(entity: TrainerData)
}