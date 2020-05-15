package com.example.mvvmattempt.ui.posts


import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.mvvmattempt.R
import com.example.mvvmattempt.databinding.FragmentPostListBinding

class PostsListFragment : Fragment() {

    private lateinit var binding: FragmentPostListBinding
    private val adapter = PostsListAdapter()
    private val mViewModel by lazy {
        ViewModelProvider(this).get(PostsListViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostListBinding.inflate(inflater).also {
            it.viewmodel = mViewModel
            it.postList.adapter = adapter
            it.lifecycleOwner = viewLifecycleOwner
        }

        setHasOptionsMenu(true)
        subscribeUi()

        return binding.root
    }


    private fun subscribeUi() {
        mViewModel.postsList.observe(viewLifecycleOwner,
            Observer {
                adapter.submitList(it)
                binding.postList.scrollToPosition(START)
            })

        mViewModel.errorMessageId.observe(viewLifecycleOwner,
            Observer { Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show() })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleToolbar()
    }

    private fun handleToolbar() {
        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(navController.graph)
        binding.toolbar.run {
            setupWithNavController(navController, appBarConfig)
            setOnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.action_refresh -> {
                        mViewModel.loadPosts()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    companion object {
        const val START = 0
    }
}