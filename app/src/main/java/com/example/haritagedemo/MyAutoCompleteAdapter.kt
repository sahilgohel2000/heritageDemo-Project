package com.example.haritagedemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.example.haritagedemo.API.ServiceManager
import java.util.HashMap

class MyAutoCompleteAdapter(private val mContext: Context):BaseAdapter(),Filterable {
    private var resultList: ArrayList<FieldNearbySitesLocation?> = ArrayList()
    override fun getCount(): Int {
        return resultList.size
    }

    override fun getItem(index: Int): FieldNearbySitesLocation? {
        return resultList[index]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater =
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.item_nearby_sites, parent, false)
        }
        (convertView?.findViewById<View>(R.id.txtSiteName) as TextView).text =
            getItem(position)?.siteName
        (convertView?.findViewById<View>(R.id.txtSiteType) as TextView).text =
            getItem(position)?.category
        return convertView
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()

                if (!constraint.isNullOrEmpty()){
                    val pradictionList = findCities(constraint.toString())

                    filterResults.values = pradictionList
                    filterResults.count = pradictionList!!.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count>0){
                    resultList = results.values as ArrayList<FieldNearbySitesLocation?>
                    notifyDataSetChanged()
                }else{
                    notifyDataSetChanged()
                }
            }
        }
    }
    private fun findCities(input: String?): ArrayList<FieldNearbySitesLocation?>? {
        val syncPredictionList = getSyncPredictionList(input)
        return if (syncPredictionList != null) {
            ContentManager.getInstance().predictionDescriptionList
        } else null
    }

    private fun getSyncPredictionList(input: String?): ArrayList<FieldNearbySitesLocation?>? {
        val paramMap = HashMap<String, Any?>()
        paramMap["keyword"] = input.toString().trim()
        val predictions = ServiceManager(mContext).apiSearchItinerary(paramMap)
        ContentManager.getInstance().predictionList = predictions
        return predictions
    }
}