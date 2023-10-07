package com.example.projectapi.Fragments

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectapi.databinding.FragmentItemInformationDialogListDialogBinding
import com.example.projectapi.Model.Result

class ItemInformationDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentItemInformationDialogListDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =
            FragmentItemInformationDialogListDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var result =
            arguments?.getSerializable("iteminformation") as Result //Принимаем объект, на который нажали
        //Отображем данные
        with(binding) {
            nameCharacter.text = result.name
            speciesCharacter.text = result.species
            dateCrateCharacter.text = result.created
            genderCharacter.text = result.gender
            originCharacter.text = result.origin.name
            statusCharacter.text = result.status
        }
    }
}