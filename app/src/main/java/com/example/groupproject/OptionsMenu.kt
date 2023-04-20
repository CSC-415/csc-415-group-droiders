import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.groupproject.R
import com.example.groupproject.R.layout.fragment_options_menu

class OptionsMenu : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(fragment_options_menu, container, false)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Sounds -> {
                // Handle menu item click for sound effects
                return true
            }
            R.id.Music -> {
                // Handle menu item click for music
                return true
            }
            R.id.Theme_Button -> {

                return true
            }
            R.id.Easy -> {
                // Handle menu item click setting difficulty to easy
                return true
            }
            R.id.Normal -> {
                // Handle menu item click setting difficulty to Normal
                return true
            }
            R.id.Nightmare -> {
                // Handle menu item click setting difficulty to Nightmare
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }
}
