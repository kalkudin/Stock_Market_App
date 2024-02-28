package com.example.finalproject.presentation.stock_feature.stock_news

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalproject.R

class StockNewsFragment : Fragment() {

    companion object {
        fun newInstance() = StockNewsFragment()
    }

    private lateinit var viewModel: StockNewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stock_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StockNewsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}