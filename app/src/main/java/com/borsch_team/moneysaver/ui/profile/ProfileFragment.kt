package com.borsch_team.moneysaver.ui.profile

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.Constants
import com.borsch_team.moneysaver.MainActivity
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
import java.text.SimpleDateFormat
import java.util.*


class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding
    private lateinit var selectedColor: ColorStateList
    private lateinit var unselectedColor: ColorStateList

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        binding.name.text = FirebaseAuth.getInstance().currentUser?.email.toString()

        //binding.name.text = FirebaseAuth.getInstance().currentUser?.email.toString()
        binding.name.text = App.preferencesManager.getUsername()

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
        binding.currentTheme.text = "Тема: " + when(App.preferencesManager.getTheme()) {
            PreferencesManager.THEME_LIGHT -> {"светлая"}
            PreferencesManager.THEME_DARK -> {"темная"}
            PreferencesManager.THEME_OLED -> {"OLED"}
            else -> {""}
        }
        binding.changeName.setOnClickListener {
            ChangeNameUserFragment(App.preferencesManager.getUsername()!!) {
                binding.name.text = it
            }.show(childFragmentManager, "tag")
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        updateProfileState()
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).launch {
            delay(100L)
            initializeThemeControls()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateProfileState() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            binding.accountLastSync.visibility = View.GONE
            binding.accountControl.text = "Войти в аккаунт"
        } else {
            binding.accountStatus.text = "Вход в аккаунт выполнен"
            binding.accountLastSync.visibility = View.VISIBLE
            binding.accountControl.text = "Выйти из аккаунта"
            if (App.preferencesManager.getLastTimeUpdate() == -1L) {
                binding.accountLastSync.text = "Синхронизация не выполнена"
            } else {
                binding.accountLastSync.text = "Последняя синхронизация:\n" +
                SimpleDateFormat(Constants.TIME_FORMAT_PATTERN_EXTENDED, Locale("ru"))
                    .format(App.preferencesManager.getLastTimeUpdate())
            }
        }
    }

    private fun initializeThemeControls() {
        selectedColor = binding.themeLight.textColors
        unselectedColor = binding.themeDark.textColors

        when (App.preferencesManager.getTheme()) {
            PreferencesManager.THEME_DARK -> {
                binding.tabSelector.animate().x(binding.themeDark.x).duration = 1L
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
            App.preferencesManager.saveTheme(PreferencesManager.THEME_LIGHT)
            recreateActivity()
        }
        binding.themeDark.setOnClickListener {
            App.preferencesManager.saveTheme(PreferencesManager.THEME_DARK)
            recreateActivity()
        }
        binding.themeOled.setOnClickListener {
            App.preferencesManager.saveTheme(PreferencesManager.THEME_OLED)
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