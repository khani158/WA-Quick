package com.example.data

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "recent_numbers")
data class RecentNumber(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val number: String,
    val countryCode: String,
    val lastUsedTimestamp: Long = System.currentTimeMillis()
)

@Dao
interface RecentNumberDao {
    @Query("SELECT * FROM recent_numbers ORDER BY lastUsedTimestamp DESC LIMIT 5")
    fun getRecentNumbers(): Flow<List<RecentNumber>>

    @Insert
    suspend fun insert(recentNumber: RecentNumber)

    @Update
    suspend fun update(recentNumber: RecentNumber)

    @Query("SELECT * FROM recent_numbers WHERE number = :number AND countryCode = :countryCode LIMIT 1")
    suspend fun getByNumber(number: String, countryCode: String): RecentNumber?

    @Query("DELETE FROM recent_numbers WHERE id = :id")
    suspend fun deleteById(id: Int)
}

@Database(entities = [RecentNumber::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recentNumberDao(): RecentNumberDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "wa_quick_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
