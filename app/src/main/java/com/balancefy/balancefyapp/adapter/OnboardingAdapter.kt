package com.balancefy.balancefyapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingAdapter
    (
    fm: FragmentManager,
    lifecycle: Lifecycle,
    private val list: List<Fragment>
) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}