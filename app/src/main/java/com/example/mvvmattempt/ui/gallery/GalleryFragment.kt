package com.example.mvvmattempt.ui.gallery

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mvvmattempt.R
import com.example.mvvmattempt.databinding.FragmentGalleryBinding
import com.example.mvvmattempt.ui.common.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private lateinit var binding: FragmentGalleryBinding

    private val viewModel by viewModels<GalleryViewModel>()

    private val adapter = GalleryAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryBinding.inflate(inflater).apply {
            viewmodel = viewModel
            picList.adapter = adapter
            lifecycleOwner = viewLifecycleOwner
        }
        setHasOptionsMenu(true)
        subscribeUi()

        // Without this, when Back Button is pressed, navigates back to PostsListFragment
        requireActivity().onBackPressedDispatcher.addCallback(this, true) {
            requireActivity().finish()
        }

        return binding.root
    }

    private fun subscribeUi() {
        viewModel.picList.observe(viewLifecycleOwner,
            Observer { adapter.submitList(it) })

        viewModel.message.observe(viewLifecycleOwner,
            EventObserver { Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show() })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_refresh -> {
                retry()
                true
            }
            else -> false
        }

    private fun retry() {
        viewModel.retry()
    }
}