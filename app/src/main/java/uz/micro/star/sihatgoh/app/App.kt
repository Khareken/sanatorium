package uz.micro.star.sihatgoh.app

import android.app.Application
import com.example.e_lesson_1.retrofit.ApiCLient
import uz.micro.star.sihatgoh.libs.Database
import uz.xsoft.lesson4ict.room.AppDatabase

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Database.init(this)
        Database.getDatabase().createDatabase().open()
        //////////////////////////////
        AppDatabase.init(this)
        ApiCLient.refreshToken()
    }
}