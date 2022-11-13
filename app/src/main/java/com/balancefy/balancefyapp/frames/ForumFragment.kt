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
import com.balancefy.balancefyapp.fragments.OnboardingFragment1
import com.balancefy.balancefyapp.fragments.OnboardingFragment2
import com.balancefy.balancefyapp.fragments.OnboardingFragment3
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ForumFragment : Fragment() {
    private lateinit var binding: FragmentForumBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                    tab.text = resources.getString(R.string.geral)
                    val badge = tab.orCreateBadge
                    badge.backgroundColor = ContextCompat.getColor(
                        requireContext(),
                        R.color.green_balancefy )
                    badge.number = 0
                    badge.isVisible = true
                }
                1 -> {
                    tab.text = resources.getString(R.string.recomendados)
                    val badge = tab.orCreateBadge
                    badge.backgroundColor = ContextCompat.getColor(
                        requireContext(),
                        R.color.green_balancefy )
                    badge.number = 0
                    badge.isVisible = true
                }
            }
        }}.attach()


        super.onViewCreated(view, savedInstanceState)
    }
}