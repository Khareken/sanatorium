package uz.micro.star.sihatgoh

import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_internet_check.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import uz.micro.star.sihatgoh.broadcasts.NetworkStateChanged
import uz.micro.star.sihatgoh.broadcasts.NetworkStateReceiver


class InternetCheckActivity : AppCompatActivity() {
    var networkStateReceiver: NetworkStateReceiver? = null
    private var snackbar: Snackbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internet_check)

        val filter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        networkStateReceiver = NetworkStateReceiver()
        this.registerReceiver(networkStateReceiver, filter)

    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            EventBus.getDefault().unregister(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        if (networkStateReceiver != null) {
            try {
                this.unregisterReceiver(networkStateReceiver)
                networkStateReceiver = null
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: NetworkStateChanged) {
        if (event.isInternetConnected) {
            if (snackbar != null) {
                snackbar!!.dismiss()
            }
        } else {
            if (snackbar == null) {
                snackbar = Snackbar.make(
                    check,
                    "No internet connection",
                    Snackbar.LENGTH_INDEFINITE
                )
            }
            snackbar!!.show()
        }
    }
}