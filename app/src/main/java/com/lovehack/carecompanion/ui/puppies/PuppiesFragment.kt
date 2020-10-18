package com.lovehack.carecompanion.ui.puppies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lovehack.carecompanion.R

class PuppiesFragment : Fragment() {

    private lateinit var puppiesViewModel: PuppiesViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        puppiesViewModel =
                ViewModelProviders.of(this).get(PuppiesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_puppies, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        puppiesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
