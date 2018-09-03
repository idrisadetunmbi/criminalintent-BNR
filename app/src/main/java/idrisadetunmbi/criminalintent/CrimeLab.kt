package idrisadetunmbi.criminalintent

import android.content.Context

import java.util.ArrayList
import java.util.UUID
import java.util.logging.Logger

class CrimeLab private constructor(context: Context) {

    private val mCrimes: MutableList<Crime>

    val crimes: List<Crime>
        get() = mCrimes

    init {
        mCrimes = ArrayList()
        for (i in 0..99) {
            val crime = Crime()
            crime.title = "Crime #$i"
            crime.isSolved = i % 2 == 0 // Every other one
            mCrimes.add(crime)
        }
    }

    fun getCrime(id: UUID): Crime? {
        for (crime in mCrimes) {
            if (id == crime.id) {
                return crime
            }
        }
        return null
    }

    fun addCrime(crime: Crime) {
        mCrimes.add(crime)
    }

    companion object {

        private var sCrimeLab: CrimeLab? = null

        operator fun get(context: Context): CrimeLab {
            if (sCrimeLab == null) {
                sCrimeLab = CrimeLab(context)
            }
            return sCrimeLab
        }
    }

}
