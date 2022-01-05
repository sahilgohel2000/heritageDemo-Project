package com.example.haritagedemo.API

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HeritageSiteDetailModel(

    @SerializedName("amenities")
    var amenities: ArrayList<String> = ArrayList(),
    @SerializedName("nid")
    var nid: String = "", // 59
    @SerializedName("description")
    var description: String = "", // <p class="rtejustify">The architecture of the complex is credited to Azam and Muazzam Khan; two Persian brothers who are buried in the <a class="mw-redirect" href="https://en.wikipedia.org/wiki/Azam_and_Mauzzam_Khan%27s_Tomb" title="Azam and Mauzzam Khan's Tomb">tomb near Vasna, Ahmedabad</a>. The complex was originally spread over 72 acres, surrounded by elaborate gardens on all sides. Over time, human settlements came around it, eating into the gardens and reducing the area to 34 acres.</p><p class="rtejustify">Shaikh Ahmed Khattu Ganj Bakhsh of <a class="mw-redirect" href="https://en.wikipedia.org/wiki/Anhilwad_Patan" title="Anhilwad Patan">Anhilwad Patan</a>, the friend and adviser of <a href="https://en.wikipedia.org/wiki/Ahmad_Shah_I" title="Ahmad Shah I">Ahmad Shah I</a>, retired to Sarkhej in his later life and died here in 1445. In his honour a tomb, begun in 1445 by <a href="https://en.wikipedia.org/wiki/Muhammad_Shah_II" title="Muhammad Shah II">Muhammad Shah II</a>, was, in 1451, finished by his son Qutbuddin <a href="https://en.wikipedia.org/wiki/Ahmad_Shah_II" title="Ahmad Shah II">Ahmad Shah II</a>. The next Sultan <a href="https://en.wikipedia.org/wiki/Mahmud_Begada" title="Mahmud Begada">Mahmud Begada</a> was fond of the place and expanded the complex greatly. He dug a large Sarkhej lake, surrounded it with cut stone steps, built on its south-west corner a splendid palace, and finally, opposite to the Ganj Baksh's tomb, raised a mausoleum for himself and his family, where he, his son <a href="https://en.wikipedia.org/wiki/Muzaffar_Shah_II" title="Muzaffar Shah II">Muzaffar Shah II</a> and his queen Rajbai are buried.</p>
    @SerializedName("field_heritage_sites_category")
    var fieldHeritageSitesCategory: ArrayList<String> = ArrayList(),
    @SerializedName("heritage_site_name")
    var heritageSiteName: String = "", // Indian Institute of Mangement (Louis Khan)
    @SerializedName("language")
    var language: String = "", // en
    @SerializedName("latitude")
    var latitude: Double = 0.0, // 23.0329473
    @SerializedName("longitude")
    var longitude: Double = 0.0, // 72.5327703
    @SerializedName("type")
    var type: String = "", // heritage_site
    @SerializedName("field_upload_url")
    var fieldUploadUrl: ArrayList<String> = ArrayList()
):Serializable
