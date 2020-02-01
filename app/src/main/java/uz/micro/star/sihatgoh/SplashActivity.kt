package uz.micro.star.sihatgoh

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import uz.micro.star.sihatgoh.dialogs.ConnectionDialog
import uz.micro.star.sihatgoh.utils.Network
import uz.xsoft.lesson4ict.room.AppDatabase

class   SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                checking()
            }
        }.start()
    }

    fun checking() {
        if (AppDatabase.database!!.getInputData().getTinySanatoryOffline().size > 0 &&
            AppDatabase.database!!.getInputData().getTinySanatoryOffline().size > 0
        ) {
            finish()
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        } else if (Network.connectionAllowed(this)) {
            finish()
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        } else {
            var connect = ConnectionDialog(this)
            connect.setOnDialogClickListener {
                checking()
                connect.dismiss()
            }
            connect.show()
        }

    }
}
