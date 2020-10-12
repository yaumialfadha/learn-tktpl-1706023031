package id.ac.ui.cs.mobileprogramming.yaumialfadha.lab_4.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ac.ui.cs.mobileprogramming.yaumialfadha.lab_4.R
import kotlinx.android.synthetic.main.fragment_b.view.*


class FragmentB : Fragment() {
    // TODO: Rename and change types of parameters

    var displayMessage: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_b, container, false)

        displayMessage = arguments?.getString("message")

        view.displayMessage.text = displayMessage

        return view
    }


}