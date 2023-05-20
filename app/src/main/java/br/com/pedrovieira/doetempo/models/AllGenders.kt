package br.com.pedrovieira.doetempo.models

import android.os.Parcel
import android.os.Parcelable


data class AllGenders(
    var genders: List<Gender>
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(Gender)!!)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(genders)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AllGenders> {
        override fun createFromParcel(parcel: Parcel): AllGenders {
            return AllGenders(parcel)
        }

        override fun newArray(size: Int): Array<AllGenders?> {
            return arrayOfNulls(size)
        }
    }
}
