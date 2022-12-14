package com.balancefy.balancefyapp.frames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.adapter.pagers.ForumPagerAdapter
import com.balancefy.balancefyapp.databinding.FragmentForumBinding
import com.google.android.material.tabs.TabLayoutMediator

class ForumFragment : Fragment() {
    private lateinit var binding: FragmentForumBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForumBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewPager2: ViewPager2 = binding.viewPagerForum
        viewPager2.adapter = ForumPagerAdapter( this)
        val tabLayout = binding.tabLayout

        TabLayoutMediator(tabLayout, viewPager2
        ) { tab, position -> run {
            when(position) {
                0 -> {
                    tab.text = resources.getString(R.string.general)
                    val badge = tab.orCreateBadge
                    badge.backgroundColor = ContextCompat.getColor(
                        requireContext(),
                        R.color.green_balancefy )
                    badge.number = 0
                    badge.isVisible = true
                }
                1 -> {
                    tab.text = resources.getString(R.string.for_you)
                    val badge = tab.orCreateBadge
                    badge.backgroundColor = ContextCompat.getColor(
                        requireContext(),
                        R.color.green_balancefy )
                    badge.number = 75
                    badge.isVisible = true
                }
            }
        }}.attach()

        viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val badge = tabLayout.getTabAt(position)?.orCreateBadge
                badge?.isVisible = false
                badge?.number = 0
            }
        })
        super.onViewCreated(view, savedInstanceState)
    }
}