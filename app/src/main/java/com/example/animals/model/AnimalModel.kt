package com.example.animals.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/* In this file, some classes are extending from Parcelable, this is to be able to pass content
    in the Navigation method, it used to be via Intents, now it's done via Navigation, which basically
    use Intent behind the scene
*/

data class ApiKey(
    val message: String?,
    val key: String
)

data class AnimalModel(
    val name: String?,
    val location: String?,
    val taxonomy: Taxonomy?,
    val speed: Speed?,
    val diet: String?,

    @SerializedName("lifespan")
    val lifeSpan: String?,

    @SerializedName("image")
    val imageUrl: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Taxonomy::class.java.classLoader),
        parcel.readParcelable(Speed::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(location)
        parcel.writeParcelable(taxonomy, flags)
        parcel.writeParcelable(speed, flags)
        parcel.writeString(diet)
        parcel.writeString(lifeSpan)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AnimalModel> {
        override fun createFromParcel(parcel: Parcel): AnimalModel {
            return AnimalModel(parcel)
        }

        override fun newArray(size: Int): Array<AnimalModel?> {
            return arrayOfNulls(size)
        }
    }
}

data class Taxonomy(
    val kingdom: String?,
    val order: String?,
    val family: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(kingdom)
        parcel.writeString(order)
        parcel.writeString(family)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Taxonomy> {
        override fun createFromParcel(parcel: Parcel): Taxonomy {
            return Taxonomy(parcel)
        }

        override fun newArray(size: Int): Array<Taxonomy?> {
            return arrayOfNulls(size)
        }
    }
}

data class Speed(
    val metric: String?,
    val imperial: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(metric)
        parcel.writeString(imperial)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Speed> {
        override fun createFromParcel(parcel: Parcel): Speed {
            return Speed(parcel)
        }

        override fun newArray(size: Int): Array<Speed?> {
            return arrayOfNulls(size)
        }
    }
}

/*this var color, will be referenced in LinearLayout (fragment_detail) as value to the background
    this is to use with data binding lib, the reference was created in <variable name="palette" />
*/
data class AnimalPalette(var color: Int)