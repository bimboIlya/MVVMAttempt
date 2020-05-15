package com.example.mvvmattempt.ui.post


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController

import com.example.mvvmattempt.R
import com.example.mvvmattempt.databinding.FragmentPostBinding

class PostFragment : Fragment() {

    private lateinit var binding: FragmentPostBinding
    private val adapter = CommentListAdapter()
    private val args: PostFragmentArgs by navArgs()
    private val mViewModel: PostViewModel by lazy {
        ViewModelProvider(this).get(PostViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(inflater).also {
            it.post = args.post
            it.viewmodel = mViewModel
            it.commentList.adapter = adapter
            it.lifecycleOwner = viewLifecycleOwner
        }

        setHasOptionsMenu(true)
        subscribeUi()

        return binding.root
    }

    private fun subscribeUi() {
        mViewModel.postsList.observe(viewLifecycleOwner,
            Observer { adapter.submitList(it) })

        mViewModel.errorMessageId.observe(viewLifecycleOwner,
            Observer { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleToolbar()
        loadComments()
    }

    private fun handleToolbar() {
        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(navController.graph)
        binding.toolbar.run {
            setupWithNavController(navController, appBarConfig)
            setOnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.action_refresh -> {
                        loadComments()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun loadComments() {
        mViewModel.loadComments(args.post.id)
    }
}
