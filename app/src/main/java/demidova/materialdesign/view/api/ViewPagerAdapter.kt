package demidova.materialdesign.view.api

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {

    private val fragments = arrayOf(QuestionFragment(),ThinkFragment(),DoneFragment())

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int)= fragments[position]

}