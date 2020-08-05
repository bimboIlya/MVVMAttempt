package com.example.mvvmattempt.ui.posts


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmattempt.R
import com.example.mvvmattempt.databinding.FragmentPostsListBinding
import com.example.mvvmattempt.ui.common.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsListFragment : Fragment() {

    private lateinit var binding: FragmentPostsListBinding
    private val adapter = PostsListAdapter()
    private val postsViewModel by viewModels<PostsListViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostsListBinding.inflate(inflater).apply {
            viewmodel = postsViewModel
            postList.adapter = adapter
            lifecycleOwner = viewLifecycleOwner
        }

        setHasOptionsMenu(true)
        setRecyclerViewDivider()
        subscribeUi()

        return binding.root
    }


    private fun setRecyclerViewDivider() {
        val recyclerView = binding.postList
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager

        val divider = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView.addItemDecoration(divider)
    }


    private fun subscribeUi() {
        postsViewModel.postsList.observe(viewLifecycleOwner,
            Observer {
                adapter.submitList(it)
                binding.postList.scrollToPosition(START)
            })

        postsViewModel.message.observe(viewLifecycleOwner,
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
        postsViewModel.retry()
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        handleToolbar()
//    }
//
//    private fun handleToolbar() {
//        val navController = findNavController()
//        val appBarConfig = AppBarConfiguration(navController.graph)
//        binding.toolbar.run {
//            setupWithNavController(navController, appBarConfig)
//            setOnMenuItemClickListener { item ->
//                when (item.itemId) {
//                    R.id.action_refresh -> {
//                        postsViewModel.retry()
//                        true
//                    }
//                    else -> false
//                }
//            }
//        }
//    }

    companion object {
        const val START = 0
    }
}