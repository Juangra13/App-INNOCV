package com.example.myapplication.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.constants.GeneralConstants
import com.example.myapplication.databinding.FragmentHighUserBinding
import com.example.myapplication.presenter.HighUserIPresenter
import com.example.myapplication.presenter.HighUserPresenter
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.Locale
import java.util.Calendar

class HighUserFragment : Fragment(), HighUserIView, DatePickerDialog.OnDateSetListener {

    private var _binding: FragmentHighUserBinding? = null

    private val binding get() = _binding!!

    private lateinit var presenter: HighUserIPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = HighUserPresenter(this, this.activity as AppCompatActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHighUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etBirthday.setOnClickListener {
            showDatePicker()
        }

        binding.btAddUser.setOnClickListener {
            checkDataUser()
        }
    }

    private fun checkDataUser() {
        val name = binding.etName.text.toString()
        val birthday = binding.etBirthday.text.toString()
        presenter.addNewUser(name = name, birthday = birthday)
    }

    private fun showDatePicker() {
        val now: Calendar = Calendar.getInstance()
        val dpd = DatePickerDialog.newInstance(
            this,
            now.get(Calendar.YEAR),
            now.get(Calendar.MONTH),
            now.get(Calendar.DAY_OF_MONTH)
        )
        dpd.locale =
            Locale(GeneralConstants.LOCALE_SPAIN_LOWWER, GeneralConstants.LOCALE_SPAIN_UPPER)
        activity?.let { dpd.show(it.supportFragmentManager, GeneralConstants.TAG_DATEPICKER) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date = dayOfMonth.toString() + "/" + (monthOfYear + 1).toString() + "/" + year
        binding.etBirthday.setText(date)
    }

    override fun showDialogOK() {
        showDialogWidthMessage("Se ha creado el usuario con éxito.", true)
    }

    override fun showDialogKO() {
        showDialogWidthMessage(message = "Ups!.\nNo se ha podido crear el usuario.", isOK = false)
    }

    private fun showDialogWidthMessage(message: String, isOK: Boolean) {
        AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setTitle("Alta usuario")
            .setMessage(message)
            .setPositiveButton(
                "OK"
            ) { dialog, _ -> if (isOK) findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment) else dialog.dismiss() }
            .show()
    }
}