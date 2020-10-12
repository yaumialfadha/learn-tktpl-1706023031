package id.ac.ui.cs.mobileprogramming.yaumialfadha.lab_4.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ac.ui.cs.mobileprogramming.yaumialfadha.lab_4.Communicator
import id.ac.ui.cs.mobileprogramming.yaumialfadha.lab_4.R
import kotlinx.android.synthetic.main.fragment_a.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentA.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentA : Fragment() {

    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_a, container, false)

        communicator = activity as Communicator

        view.sendBtn.setOnClickListener {
            communicator.passDataCom(view.messageInput.text.toString())
        }
        return view
    }

}