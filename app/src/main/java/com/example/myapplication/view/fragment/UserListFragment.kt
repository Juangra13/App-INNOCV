package com.example.myapplication.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.business.model.User
import com.example.myapplication.databinding.FragmentUserListBinding
import com.example.myapplication.presenter.UserListIPresenter
import com.example.myapplication.presenter.UserListPresenter
import com.example.myapplication.view.adapter.UserAdapter


class UserListFragment : Fragment(), UserIView{

    private var _binding: FragmentUserListBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: UserAdapter
    private lateinit var presenter: UserListIPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = UserListPresenter(this, this.activity as AppCompatActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        presenter.loadViewData()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    override fun initRecyclerView(userList: ArrayList<User>) {
        adapter = UserAdapter(userList)
        binding.rvUsers.layoutManager = LinearLayoutManager(this.activity)
        binding.rvUsers.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}