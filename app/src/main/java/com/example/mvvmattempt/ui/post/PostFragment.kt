package com.example.mvvmattempt.ui.post


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.mvvmattempt.R
import com.example.mvvmattempt.databinding.FragmentPostBinding
import com.example.mvvmattempt.ui.common.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment : Fragment() {

    private lateinit var binding: FragmentPostBinding
    private val adapter = CommentListAdapter()
    private val args: PostFragmentArgs by navArgs()
    private val mViewModel by viewModels<PostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(inflater).apply {
            post = args.post
            viewmodel = mViewModel
            commentList.adapter = adapter
            lifecycleOwner = viewLifecycleOwner
        }

        mViewModel.setPostIdAndGetComments(args.post.id)

        setHasOptionsMenu(true)
        subscribeUi()

        return binding.root
    }

    private fun subscribeUi() {
        mViewModel.commentList.observe(viewLifecycleOwner,
            Observer { adapter.submitList(it) })

        mViewModel.message.observe(viewLifecycleOwner,
            EventObserver { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() })
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
        mViewModel.retry()
    }
}
