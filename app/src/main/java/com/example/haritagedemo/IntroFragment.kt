package com.example.haritagedemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.haritagedemo.Model.IntroModel
import kotlinx.android.synthetic.main.fragment_intro.*
import java.io.Serializable


class IntroFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun bindViews(view: View) {
        val item = arguments?.getSerializable(MY_ITEM) as IntroModel

        txtHeader.text = getString(item.introText)
        txtTagLine.text = getString(item.iconTagLine)

        imgIcons.setImageResource(item.icon)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }

    companion object{
        private const val MY_ITEM = "my_item"

        fun newInstance(item:IntroModel) = IntroFragment().apply {
            arguments = Bundle(1).apply {
                putSerializable(MY_ITEM, item as Serializable)
            }
        }
    }
}