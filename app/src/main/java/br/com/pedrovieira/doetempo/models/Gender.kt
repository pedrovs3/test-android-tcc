package br.com.pedrovieira.doetempo.models

import android.os.Parcel
import android.os.Parcelable

data class Gender(
    var id: String? = null,
    var name: String? = null,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Gender> {
        override fun createFromParcel(parcel: Parcel): Gender {
            return Gender(parcel)
        }

        override fun newArray(size: Int): Array<Gender?> {
            return arrayOfNulls(size)
        }
    }
}
