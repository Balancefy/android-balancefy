package com.balancefy.balancefyapp.adapter.pagers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.balancefy.balancefyapp.frames.ForumFragment
import com.balancefy.balancefyapp.frames.ForumGeralFragment
import com.balancefy.balancefyapp.frames.ForumRecomendadosFragment

class ForumPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ForumGeralFragment()
            1 -> ForumRecomendadosFragment()
            else -> ForumFragment()
        }
    }
}