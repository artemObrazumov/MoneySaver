package com.borsch_team.moneysaver.ui.profile

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.SharedMemory
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.borsch_team.moneysaver.MainActivity
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.data.PreferencesManager
import com.borsch_team.moneysaver.databinding.FragmentProfileBinding
import com.borsch_team.moneysaver.ui.auth.sign_in.AuthSignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding
    private lateinit var selectedColor: ColorStateList
    private lateinit var unselectedColor: ColorStateList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        //binding.name.text = FirebaseAuth.getInstance().currentUser?.email.toString()
        binding.name.text = PreferencesManager.getUsername(requireContext()).toString()

        if (FirebaseAuth.getInstance().currentUser == null) {
            binding.accountLastSync.visibility = View.GONE
        }

        binding.accountControl.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null) {
                startActivity(Intent(context, AuthSignInActivity::class.java))
            } else {
                Firebase.auth.signOut()
            }
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).launch {
            delay(100L)
            initializeThemeControls()
        }
    }

    private fun initializeThemeControls() {
        selectedColor = binding.themeLight.textColors
        unselectedColor = binding.themeDark.textColors

        when (PreferencesManager.getTheme(requireContext())) {
            PreferencesManager.THEME_DARK -> {
                binding.tabSelector.animate().x(binding.themeDark.x).duration = 100L
                binding.themeLight.setTextColor(unselectedColor)
                binding.themeDark.setTextColor(selectedColor)
            }
            PreferencesManager.THEME_OLED -> {
                binding.tabSelector.animate().x(binding.themeOled.x).duration = 100L
                binding.themeLight.setTextColor(unselectedColor)
                binding.themeOled.setTextColor(selectedColor)
            }
        }

        binding.themeLight.setOnClickListener {
            PreferencesManager.saveTheme(PreferencesManager.THEME_LIGHT, requireContext())
            recreateActivity()
        }
        binding.themeDark.setOnClickListener {
            PreferencesManager.saveTheme(PreferencesManager.THEME_DARK, requireContext())
            recreateActivity()
        }
        binding.themeOled.setOnClickListener {
            PreferencesManager.saveTheme(PreferencesManager.THEME_OLED, requireContext())
            recreateActivity()
        }
    }

    private fun recreateActivity() {
        requireActivity().finish()
        startActivity(Intent(requireActivity(), MainActivity::class.java).apply {
            putExtra("afterTheming", true)
        })
    }
}