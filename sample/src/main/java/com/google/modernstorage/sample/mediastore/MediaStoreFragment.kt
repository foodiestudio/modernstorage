package com.google.modernstorage.sample.mediastore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.modernstorage.sample.R
import com.google.modernstorage.sample.databinding.FragmentMediastoreBinding

class MediaStoreFragment : Fragment() {
    private var _binding: FragmentMediastoreBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MediaStoreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMediastoreBinding.inflate(inflater, container, false)

        setupButtonGroups()

        binding.addMedia.setOnClickListener {
            val type = when (binding.mediastoreType.checkedButtonId) {
                R.id.type_image -> MediaType.IMAGE
                R.id.type_video -> MediaType.VIDEO
                else -> throw Exception("This is not supposed to happen")
            }

            when (binding.mediastoreSource.checkedButtonId) {
                R.id.source_internet -> downloadMedia(type)
                R.id.source_camera -> captureMedia(type)
                else -> throw Exception("This is not supposed to happen")
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupButtonGroups() {
        binding.mediastoreType.clearChecked()
        binding.mediastoreSource.clearChecked()

        binding.mediastoreType.isSelectionRequired = true
        binding.mediastoreSource.isSelectionRequired = true

        binding.mediastoreType.check(R.id.type_image)
        binding.mediastoreSource.check(R.id.source_internet)
    }

    private fun downloadMedia(type: MediaType) {
        binding.addMedia.isEnabled = false

        when (type) {
            MediaType.IMAGE -> viewModel.saveRandomImageFromInternet {
                binding.addMedia.isEnabled = true
            }
            MediaType.VIDEO -> viewModel.saveRandomVideoFromInternet {
                binding.addMedia.isEnabled = true
            }
        }
    }

    private fun captureMedia(type: MediaType) {

    }
}