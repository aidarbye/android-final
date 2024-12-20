import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androidfinalxml.activity.AuthActivity
import com.example.androidfinalxml.fragments.LoginFragment
import com.example.androidfinalxml.fragments.RegisterFragment

class ViewPagerAdapter(fragment: AuthActivity) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LoginFragment()
            1 -> RegisterFragment()
            else -> throw IllegalStateException("Invalid position: $position")
        }
    }
}
