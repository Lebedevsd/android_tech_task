package com.nordpass.task.ui.list

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nordpass.task.R
import com.nordpass.task.ui.base.BaseFragment
import com.nordpass.tt.usecase.Todo
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TodoListFragment : BaseFragment(R.layout.fragment_list) {
    private val viewModel: TodoListViewModel by viewModels()
    private var adapter: TodoListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Toolbar>(R.id.toolbar)?.let {
            (requireActivity() as AppCompatActivity).apply {
                setSupportActionBar(it)
            }
        }
        adapter = TodoListAdapter(viewModel::onItemClicked)
        view.findViewById<RecyclerView>(R.id.todoRecycler)?.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner) {
            adapter?.submitList(it)
        }
        viewModel.showItem.observe(viewLifecycleOwner) {
            showTodoDetails(it)
        }

    }

    private fun showTodoDetails(todo: Todo) {
        findNavController().navigate(TodoListFragmentDirections.actionTodoDetails(todo))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.theme_switch, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val checkable: MenuItem = menu.findItem(R.id.dark_theme_item)
        val isNightTheme = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        checkable.isChecked = isNightTheme == Configuration.UI_MODE_NIGHT_YES
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val isNightTheme = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return when (item.itemId) {
            R.id.dark_theme_item -> {
                when (isNightTheme) {
                    Configuration.UI_MODE_NIGHT_YES ->
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    Configuration.UI_MODE_NIGHT_NO ->
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                item.isChecked = !item.isChecked
                true
            }
            else -> false
        }
    }
}
