package com.example.carebout.view.medical

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.carebout.R

var weight: Float = 3.3f
var paperCupFeed: Float = 0.0f
var paperCup: Float = 80.0f
var feedGram: Float = 0.0f

fun getAmount(weight: Float) : Float {
    feedGram = weight*20
    paperCupFeed = feedGram / paperCup

    return paperCupFeed
}

class CurrentWeight : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val weightView: View = inflater.inflate(R.layout.currentweight, container, false)
        val feedAmount: TextView = weightView.findViewById(R.id.feedAmount)
        val cWeight: TextView = weightView.findViewById(R.id.weight)

        feedAmount.text = "종이컵\n%.1f".format(getAmount(weight))
        cWeight.text = weight.toString()

        return weightView
    }
}