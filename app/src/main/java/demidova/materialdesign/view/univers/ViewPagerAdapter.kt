package demidova.materialdesign.view.univers

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter



class ViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {

    private val fragments = arrayOf(EarthFragment(), MarsFragment(), SystemFragment())

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int)= fragments[position]

}