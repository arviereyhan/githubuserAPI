package com.example.githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.FragmentFollowBinding


class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            val position = arguments?.getInt(ARG_POSITION, 0)
            val username = requireActivity().intent.getStringExtra(DetailUserActivity.EXTRA_USERNAME)
        binding.listFollow.layoutManager = LinearLayoutManager(activity)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailUserViewModel::class.java]

        if (position == 1){
            viewModel.setFollowers(username!!)
        } else {
            viewModel.setFollowing(username!!)
        }
        viewModel.follow.observe(viewLifecycleOwner){
            val adapter = UserAdapter(it)
            binding.listFollow.adapter = adapter
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }


    }

    companion object {
        const val ARG_POSITION = "arg_position"


    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}