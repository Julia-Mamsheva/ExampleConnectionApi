package com.example.projectapi.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projectapi.Api.AdapterCharacter
import com.example.projectapi.Api.ApiRequest
import com.example.projectapi.Fragments.ItemInformationDialogFragment
import com.example.projectapi.Model.Result
import com.example.projectapi.Model.RickAndMorty
import com.example.projectapi.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), AdapterCharacter.Listener {
    lateinit var binding: ActivityMainBinding
    private val adapterCharacter = AdapterCharacter(this) // Объект нашего адаптера

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
        listener()
    }

    fun listener()
    {
        binding.nextPageEmail.setOnClickListener()
        {
            startActivity(Intent(this, Email::class.java))
        }
    }
    private fun getData() {
        val api = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/") // Url - подключения к api (Базовый URL всегда заканчивается слешем)
            .addConverterFactory(GsonConverterFactory.create()) //Добавляем json конвертор
            .build()
            .create(ApiRequest::class.java) // Добавление класса ответа
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.getCharacter().awaitResponse() // Получение данных с помощью метода в интерфейсе
                if (response.isSuccessful) {
                    val data = response.body()!! // Получаем тело ответа
                    runOnUiThread { init(data) } // Отображем View  в пользовательском потоке
                    Log.d("MainActivity", data.toString()) // Вывод в консоль для отладки
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("MainActivity", e.toString()) // Вывод в консоль для отладки
                }
            }
        }
    }
    fun init(data:RickAndMorty)
    {
        with(binding) {
            listViewCharacter.layoutManager = GridLayoutManager(this@MainActivity, 1) // Настраиваем наш list
            listViewCharacter.adapter = adapterCharacter // Добавляем к листу адаптер

            val listCharacter: List<Result> = data.results // Получаем лист объектов
            if (listCharacter.isNotEmpty()) {
                for (element in listCharacter) {
                    adapterCharacter.addResult(element) // Добавляем по очередно в адаптер
                }
            }
        }
    }

    override fun OnClick(result: Result) {
      // Здесь будут обрабатываться действия при клике на объект
        val itemListDialogFragment = ItemInformationDialogFragment() //Создаем объект фрагмента
        val bundle = Bundle() //Bundle - представляет собой контейнер, который может содержать массивы или значения любого типа данных
        bundle.putSerializable("iteminformation", result)  // 1 параметр - уникальный ключ передачи данных, 2 параметр - данные, которые передаем
        itemListDialogFragment.arguments = bundle // Передаем данные
        itemListDialogFragment.show(supportFragmentManager, "pop") // Запускаем наш фрагмент
    }
}