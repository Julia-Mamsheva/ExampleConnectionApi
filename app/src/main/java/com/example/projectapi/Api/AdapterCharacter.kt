package com.example.projectapi.Api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectapi.databinding.ItemobjectBinding
import com.example.projectapi.Model.Result
import com.example.projectapi.R

// Класс для отображения объектов в view
class AdapterCharacter(private val listener: Listener) :
// RecyclerView.Adapter универсальный элемент управления с большими возможностями
    RecyclerView.Adapter<AdapterCharacter.CharacterHolder>() {
    private val characterList = ArrayList<Result>()
    // ViewHolder описывает вид элемента и метаданные о его месте в RecyclerView
    class CharacterHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ItemobjectBinding.bind(item) // Находим необходимый layout (шаблон)
        /**
         @param result - Один объект их списка
         @param listener - Слушатель объекта (дальше мы повесим на него клик)
         */
        fun bind(result: Result, listener: Listener) = with(binding)
        {
            // Расскладываем данные по объектам на странице Itemobject
            nameCharacter.text = result.name
            speciesCharacter.text = result.species
            objectItem.setOnClickListener()
            {
                listener.OnClick(result)
            }
        }
    }

    //Вызывается, когда RecyclerView требуется новый элемент ViewHolder данного типа для представления элемента
    /**
     @param parent- Группа просмотра, в которую будет добавлено новое представление после привязки к позиции адаптера
     @param viewType- Тип представления нового представления*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemobject, parent, false)
        /* В первом параметре указывается идентификатор ресурса разметки, в который мы собираемся добавлять объекты.
         Во втором параметре указывается корневой компонент, к которому нужно присоединить объекты.
         В третьем параметре (если он используется) указывается, нужно ли присоединять объекты к корневому элементу.*/
        return CharacterHolder(view)
    }
    //Вызывается RecyclerView для отображения данных в указанной позиции
    /**
     @param holder - ViewHolder, который должен быть обновлен для представления
     содержимого элемента в заданной позиции в наборе данных
     @param position - Положение элемента в наборе данных адаптера.
     */
    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        holder.bind(characterList[position], listener)
    }
    //Возвращает стабильный идентификатор для элемента по адресу position.
    override fun getItemCount(): Int {
        return characterList.size
    }
    //Добавляем объект в лист и сохраняем изменения
    fun addResult(character: Result) {
        characterList.add(character)
        notifyDataSetChanged()
    }

    interface Listener {
        fun OnClick(result: Result)
    }
}