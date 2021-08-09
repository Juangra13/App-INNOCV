package com.example.myapplication.view.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.business.model.User
import com.example.myapplication.databinding.FragmentUserListBinding
import com.example.myapplication.presenter.UserListIPresenter
import com.example.myapplication.presenter.UserListPresenter
import com.example.myapplication.utils.SnackbarUtils
import com.example.myapplication.view.adapter.OnClickItemListener
import com.example.myapplication.view.adapter.UserAdapter
import com.example.myapplication.view.custom.EditDataDialog
import com.roger.catloadinglibrary.CatLoadingView


class UserListFragment : Fragment(), UserIView, SearchView.OnQueryTextListener{

    private var _binding: FragmentUserListBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: UserAdapter
    private lateinit var presenter: UserListIPresenter
    private var loading: CatLoadingView? = null
    private var userList = mutableListOf<User>()
    private var listSave = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loading = CatLoadingView()
        presenter = UserListPresenter(this, this.activity as AppCompatActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        binding.svUser.setOnQueryTextListener(this)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeLayout.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.design_default_color_primary))
        binding.swipeLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(requireContext(), R.color.design_default_color_on_primary))
        binding.swipeLayout.setOnRefreshListener {
            showLoading()
            presenter.refreshUsers()
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.svUser.setOnCloseListener {
            userList.clear()
            userList.addAll(listSave)
            binding.rvUsers.adapter?.notifyDataSetChanged()
            listSave.clear()
            false
        }
    }

    override fun initRecyclerView(userList: ArrayList<User>) {
        this.userList = userList
        adapter = UserAdapter(this.userList as ArrayList<User>, object :OnClickItemListener{
            override fun onItemRemoved(user: User, position: Int) {
                presenter.deleteUser(user.id.toString(), position)
            }

            override fun onItemEdit(user: User, position: Int) {
                showEditUserDialog(user, position)
            }
        })
        binding.rvUsers.layoutManager = LinearLayoutManager(this.activity)
        binding.rvUsers.adapter = adapter
        if (binding.swipeLayout.isVisible) hideSwipeLayout()
        hideLoading()
    }

    private fun showEditUserDialog(user: User, position: Int) {
        user.name?.let {
            EditDataDialog.displayMessage(requireContext(), "Edita el nombre del usuario:", it,
                object : EditDataDialog.EditDataListener {
                    override fun onClosed(name: String?) {
                        if(!name.isNullOrEmpty() && !name.equals(user.name, true)) {
                            showLoading()
                            user.name = name
                            presenter.editUserName(user, position)
                        }
                    }
                })
        }
    }

    private fun hideSwipeLayout() {
        binding.swipeLayout.isRefreshing = false
    }

    override fun showLoading() {
        loading?.show(activity?.supportFragmentManager!!, "")
    }

    override fun hideLoading() {
        loading?.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()) {
            filterUserForName(query)
        }
        return true
    }

    private fun filterUserForName(name: String) {
        listSave.addAll(userList)
        userList.clear()
        val filterList = listSave.filter { it.name!!.contains(name, true) } as MutableList<User>
        userList.addAll(filterList)
        binding.rvUsers.adapter?.notifyDataSetChanged()
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun hideKeyboard() {
        binding.svUser.setQuery("", false)
        binding.svUser.clearFocus()
        val imm = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

    override fun removeUser(position: Int) {
        showMessageSnackbar("Usuario eliminado")
        userList.removeAt(position)
        binding.rvUsers.adapter?.notifyItemRemoved(position)
    }

    override fun actualizeAllList(userList: ArrayList<User>) {
        if (binding.swipeLayout.isVisible) hideSwipeLayout()
        this.userList.clear()
        this.userList.addAll(userList)
        binding.rvUsers.adapter?.notifyDataSetChanged()
        hideLoading()
    }

    override fun actualizeUserList(user: User, position: Int) {
        userList[position] = user
        binding.rvUsers.adapter?.notifyItemChanged(position)
        hideLoading()
    }

    override fun showMessageSnackbar(message: String) {
        SnackbarUtils.showSnackbar(binding.rvUsers, message)
    }

    override fun onResume() {
        super.onResume()
        showLoading()
        presenter.loadViewData()
    }
}