package com.example.listmaker.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listmaker.MainActivity
import com.example.listmaker.TaskList
import com.example.listmaker.databinding.ListDetailFragmentBinding
import com.example.listmaker.ui.main.MainViewModel
import com.example.listmaker.ui.main.MainViewModelFactory


class ListDetailFragment : Fragment() {

    lateinit var binding: ListDetailFragmentBinding // variable to store the binding
    lateinit var viewModel: MainViewModel  // Variable to hold the ViewModel

    companion object {
        fun newInstance() = ListDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container:
        ViewGroup?, savedInstanceState: Bundle?
    ): View {

        // generates a binding class for you
        binding = ListDetailFragmentBinding.inflate(
            inflater,
            container, false
        )
        //  return the root of the View for your Fragment.
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(
            requireActivity(),
            MainViewModelFactory(
                PreferenceManager.getDefaultSharedPreferences
                    (requireActivity())
            )
        ).get(MainViewModel::class.java)

        val list: TaskList? = arguments?.getParcelable(MainActivity.INTENT_LIST_KEY)
        if (list != null) {
            viewModel.list = list
            requireActivity().title = list.name
        }
        val recyclerAdapter =
            ListItemsRecyclerViewAdapter(viewModel.list)
        binding.listItemsRecyclerview.adapter = recyclerAdapter
        binding.listItemsRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        viewModel.onTaskAdded = {
            recyclerAdapter.notifyDataSetChanged()

        }
    }

}