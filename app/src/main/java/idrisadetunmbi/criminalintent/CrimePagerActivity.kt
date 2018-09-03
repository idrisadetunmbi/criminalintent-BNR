package idrisadetunmbi.criminalintent

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import java.util.*

class CrimePagerActivity : AppCompatActivity() {

    private var mViewPager: ViewPager? = null
    private var mCrimes: List<Crime>? = null
    private val mSelectedItem: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crime_pager)

        mCrimes = CrimeLab[this].crimes

        mViewPager = findViewById(R.id.crime_view_pager)
        mViewPager!!.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                val crime = mCrimes!![position]
                return CrimeFragment.newInstance(crime.id)
            }

            override fun getCount(): Int {
                return mCrimes!!.size
            }
        }
        val startCrimeId = intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID
        val startCrime = CrimeLab[this].getCrime(startCrimeId)
        mViewPager!!.currentItem = mCrimes!!.indexOf(startCrime)
    }

    companion object {
        private const val EXTRA_CRIME_ID = "idrisadetunmbi.criminalintent.crime_id"

        fun newIntent(packageContext: Context, crimeId: UUID): Intent {
            val intent = Intent(packageContext, CrimePagerActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID, crimeId)
            return intent
        }
    }
}
