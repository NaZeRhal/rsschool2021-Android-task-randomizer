package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var onGenerateClickListener: OnGenerateClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            onGenerateClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        generateButton?.setOnClickListener {
            val minText = view.findViewById<EditText>(R.id.min_value).text.toString()
            val maxText = view.findViewById<EditText>(R.id.max_value).text.toString()
            if (validateInput(minText, maxText)) {
                onGenerateClickListener?.onGenerateClick(minText.toInt(), maxText.toInt())
            }
        }
    }

    private fun validateInput(minText: String, maxText: String): Boolean {
        return if (minText.isEmpty() || maxText.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill the fields", Toast.LENGTH_SHORT).show()
            false
        } else if (minText.toInt() > maxText.toInt()) {
            Toast.makeText(requireContext(), "Max must be greater than min", Toast.LENGTH_SHORT)
                .show()
            false
        } else true
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

    interface OnGenerateClickListener {
        fun onGenerateClick(min: Int, max: Int)
    }
}