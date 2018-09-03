package idrisadetunmbi.criminalintent

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.TextView

class CrimeListFragment : Fragment() {

    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: CrimeAdapter? = null
    private var mClickedCrimeItemPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_crime_list, container, false)
        mRecyclerView = v.findViewById(R.id.crime_recycler_view)
        mRecyclerView!!.layoutManager = LinearLayoutManager(activity)

        return v
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.fragment_crime_list, menu)
    }

    private fun updateUI() {
        val crimeLab = CrimeLab[activity!!]
        val crimes = crimeLab.crimes

        if (mAdapter == null) {
            mAdapter = CrimeAdapter(crimes)
            mRecyclerView!!.adapter = mAdapter
        } else {
            mAdapter!!.notifyItemChanged(mClickedCrimeItemPosition)
        }
    }

    private inner class CrimeHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_crime, parent, false)), View.OnClickListener {

        private val mTitleTextView: TextView
        private val mDateTextView: TextView
        private val mSolvedImageView: ImageView
        private var mCrime: Crime? = null

        init {
            itemView.setOnClickListener(this)

            mTitleTextView = itemView.findViewById<View>(R.id.crime_title) as TextView
            mDateTextView = itemView.findViewById<View>(R.id.crime_date) as TextView
            mSolvedImageView = itemView.findViewById<View>(R.id.crime_solved) as ImageView
        }

        override fun onClick(v: View) {
            val intent = CrimePagerActivity.newIntent(activity, mCrime!!.id)
            setClickedItemPosition(adapterPosition)
            startActivity(intent)
        }

        fun bind(crime: Crime) {
            mCrime = crime
            mTitleTextView.text = mCrime!!.title
            mDateTextView.text = mCrime!!.date!!.toString()
            mSolvedImageView.visibility = if (mCrime!!.isSolved) View.VISIBLE else View.GONE
        }

        private fun setClickedItemPosition(position: Int) {
            mClickedCrimeItemPosition = position
        }

    }

    private inner class CrimeAdapter(private val mCrimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val layoutInflater = LayoutInflater.from(activity)
            return CrimeHolder(layoutInflater, parent)
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = mCrimes[position]
            holder.bind(crime)
        }

        override fun getItemCount(): Int {
            return mCrimes.size
        }
    }
}
