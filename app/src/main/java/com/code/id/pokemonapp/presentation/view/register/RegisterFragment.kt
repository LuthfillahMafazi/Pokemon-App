package com.code.id.pokemonapp.presentation.view.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.code.id.pokemonapp.databinding.FragmentRegisterBinding
import com.code.id.pokemonapp.domain.model.UserEntity
import com.code.id.pokemonapp.presentation.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        observeRegister()

    }

    private fun observeRegister() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerResult.collect {
                    if (it == true) {
                        Toast.makeText(requireContext(), "Pendaftaran berhasil", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    private fun initListener() {
        binding?.apply {
            tvToLogin.setOnClickListener {
                findNavController().popBackStack()
            }

            btnRegister.setOnClickListener {
                if (etUsername.text.isNullOrEmpty() || etPassword.text.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), "Semua field harus diisi", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val userEntity = UserEntity(
                        userName = etUsername.text.toString(),
                        password = etPassword.text.toString()
                    )
                    viewModel.registerUser(userEntity)
                }
            }
        }
    }
}