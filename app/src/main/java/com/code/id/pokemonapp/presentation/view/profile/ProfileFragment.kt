package com.code.id.pokemonapp.presentation.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.code.id.pokemonapp.data.local.PreferenceManager
import com.code.id.pokemonapp.databinding.FragmentProfileBinding
import com.code.id.pokemonapp.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding

    private val viewModel: HomeViewModel by activityViewModels()

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkUserLogin()
        initListener()

    }
    private fun checkUserLogin() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLogin.collect {
                    if (it == true) {
                        initView()
                    }
                }
            }
        }
    }

    private fun initListener() {
        binding?.btnLogout?.setOnClickListener {
            preferenceManager.logout()
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToNavAuth())
        }
    }

    private fun initView() {
        binding?.apply {
            tvUsername.text = preferenceManager.getUsername()
            tvUserId.text = preferenceManager.getUserId().toString()
        }
    }
}