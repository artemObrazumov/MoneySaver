package com.borsch_team.moneysaver.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.databinding.FragmentProfileBinding
import com.borsch_team.moneysaver.ui.auth.sign_in.AuthSignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        binding.name.text = FirebaseAuth.getInstance().currentUser?.email.toString()

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
        initializeThemeControls()
        return binding.root
    }

    private fun initializeThemeControls() {
        requireActivity().setTheme(R.style.Theme_MoneySaver_Dark)
        //requireActivity().recreate()
    }
}