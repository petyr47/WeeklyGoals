package com.inits.ng.weeklygoals.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Goal::class ], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dao(): GoalDao

    companion object {
        private const val databaseName = "goal-db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }


        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                databaseName
            )
                /*       .addCallback(object : RoomDatabase.Callback() {
                           override fun onCreate(db: SupportSQLiteDatabase) {
                               super.onCreate(db)
                               GlobalScope.launch(Dispatchers.IO) {
                                   getInstance(context).voterDao().insertVoterList(Voter.giveDummyVoters())
                               }
                           }
                       }

                       )*/
                .fallbackToDestructiveMigration()
                .build()
        }
    }


}