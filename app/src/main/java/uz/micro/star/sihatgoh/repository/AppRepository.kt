package uz.xsoft.lesson4ict.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.e_lesson_1.retrofit.ApiCLient
import com.example.e_lesson_1.retrofit.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.micro.star.sihatgoh.models.SanatoriumFullData
import uz.micro.star.sihatgoh.models.SanatoriumTinyData
import uz.xsoft.lesson4ict.room.AppDatabase

class AppRepository {
    private var sanatoriumListLiveData: LiveData<List<SanatoriumFullData>>? = null
    private var sanatoriumTinyListLiveData: LiveData<List<SanatoriumTinyData>>? = null

    private var apiInterface: ApiInterface? = ApiCLient.retrofit!!.create(ApiInterface::class.java)

    fun getSanatoriumTinyList(): LiveData<List<SanatoriumTinyData>> {
        if (sanatoriumTinyListLiveData == null) {
            sanatoriumTinyListLiveData = AppDatabase.database?.getInputData()?.getTiny()
        }
        apiInterface!!.allSanatoriesTiny().enqueue((object : Callback<List<SanatoriumTinyData>> {
            override fun onFailure(call: Call<List<SanatoriumTinyData>>, t: Throwable) {
//                Toast.makeText(, t.toString(), Toast.LENGTH_SHORT).show();
                Log.d("YYY",t.toString())
            }

            override fun onResponse(
                call: Call<List<SanatoriumTinyData>>,
                response: Response<List<SanatoriumTinyData>>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {

                        if (response.body() != null) {
                            addTrainersTiny(response.body()!!)
                        }
                    }
                }
            }
        }))
        return sanatoriumTinyListLiveData!!
    }

    private fun addTrainersTiny(body: List<SanatoriumTinyData>) {
        body.forEach { trainer -> AppDatabase.database?.getInputData()?.saveTiny(trainer) }
        sanatoriumTinyListLiveData = AppDatabase.database?.getInputData()?.getTiny()
    }

    fun getSanatoriumList(): LiveData<List<SanatoriumFullData>> {
        if (sanatoriumListLiveData == null) {
            sanatoriumListLiveData = AppDatabase.database?.getInputData()?.get()
        }
        apiInterface!!.allSanatories().enqueue(object : Callback<List<SanatoriumFullData>> {
            override fun onFailure(call: Call<List<SanatoriumFullData>>, t: Throwable) {
//                Toast.makeText(, t.toString(), Toast.LENGTH_SHORT).show();
            }

            override fun onResponse(
                call: Call<List<SanatoriumFullData>>,
                response: Response<List<SanatoriumFullData>>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                            addSanatories(response.body()!!)
                    }
                }
            }
        })
        return sanatoriumListLiveData!!
    }

    fun addSanatories(trainer: List<SanatoriumFullData>) {
        trainer.forEach { trainer -> AppDatabase.database?.getInputData()?.save(trainer) }
        sanatoriumListLiveData = AppDatabase.database?.getInputData()?.get()
    }

    fun getTinySanatoryOffline(): ArrayList<SanatoriumTinyData> {
        return AppDatabase.database?.getInputData()?.getTinySanatoryOffline() as ArrayList<SanatoriumTinyData>
    }

    fun getSanatoryOffline(id:Int): SanatoriumFullData {
        return AppDatabase.database?.getInputData()?.getSanatoryOfflineById(id)!!
    }


//
//    fun addTriner(trainer: TrainerData) {
//        apiInterface!!.addTriner(trainer).enqueue(object : Callback<TrainerData> {
//            override fun onFailure(call: Call<TrainerData>, t: Throwable) {
//
//            }
//
//            override fun onResponse(call: Call<TrainerData>, response: Response<TrainerData>) {
//                AppDatabase.database?.getInputData()?.save(response.body()!!)
//                sanatoriumListLiveData = AppDatabase.database?.getInputData()?.get()
//            }
//        })
//
//    }
//
//    fun deleteById(trainer: TrainerData) {
//        apiInterface!!.deteteTrainerById(trainer.id).enqueue(object : Callback<TrainerData> {
//            override fun onFailure(call: Call<TrainerData>, t: Throwable) {
//
//            }
//
//            override fun onResponse(call: Call<TrainerData>, response: Response<TrainerData>) {
//                AppDatabase.database?.getInputData()?.delete(trainer)
//                sanatoriumListLiveData = AppDatabase.database?.getInputData()?.get()
//            }
//        })
//
//    }
//
//    fun updateById(trainer: TrainerData) {
//        apiInterface!!.updateTrainer(trainer.id, trainer).enqueue(object : Callback<TrainerData> {
//            override fun onFailure(call: Call<TrainerData>, t: Throwable) {
//
//            }
//
//            override fun onResponse(call: Call<TrainerData>, response: Response<TrainerData>) {
//                AppDatabase.database?.getInputData()?.update(trainer)
//                sanatoriumListLiveData = AppDatabase.database?.getInputData()?.get()
//            }
//        })
//    }
}