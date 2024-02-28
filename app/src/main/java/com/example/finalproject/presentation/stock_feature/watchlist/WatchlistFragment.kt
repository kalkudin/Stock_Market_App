package com.example.finalproject.presentation.stock_feature.watchlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalproject.R

class WatchlistFragment : Fragment() {

    companion object {
        fun newInstance() = WatchlistFragment()
    }

    private lateinit var viewModel: WatchlistViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_watchlist, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WatchlistViewModel::class.java)
        // TODO: Use the ViewModel
    }

}