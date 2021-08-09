package com.example.myapplication.view.custom

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.databinding.EditCustomViewBinding


object EditDataDialog {
    fun displayMessage(
        context: Context,
        title: String?,
        name: String,
        listener: EditDataListener?
    ): AlertDialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val binding = EditCustomViewBinding.inflate(inflater)
        builder.setTitle(title)
        val layoutView: View = binding.root
        builder.setView(layoutView)
        binding.nameEditText.setText(name)
        binding.nameEditText.requestFocus()
        builder.setPositiveButton("Ok"
        ) { dialog, _ ->
            listener?.onClosed(
                binding.nameEditText.text.toString()
            )
            dialog.cancel()
        }
        builder.show()
        return builder.create()
    }

    interface EditDataListener {
        fun onClosed(
            name: String?
        )
    }
}