package com.borsch_team.moneysaver.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.databinding.FragmentChangeNameUserBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class ChangeNameUserFragment(private val currentName: String, private val newName:(name: String) -> Unit): BottomSheetDialogFragment() {

    private lateinit var binding: FragmentChangeNameUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeNameUserBinding.inflate(layoutInflater)

        binding.etName.setText(currentName)

        binding.btnSave1.setOnClickListener {
            val newName = binding.etName.text.toString()
            if(newName != ""){
                App.preferencesManager.saveUsername(newName)
                newName(newName)
                dismiss()
            }else{
                Snackbar
                    .make(binding.root, "Введите новое имя!", Snackbar.LENGTH_LONG).show()
            }
        }

        return binding.root
    }
}