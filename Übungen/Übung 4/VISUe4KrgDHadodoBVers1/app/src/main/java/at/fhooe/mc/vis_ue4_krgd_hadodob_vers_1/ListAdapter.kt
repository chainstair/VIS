package at.fhooe.mc.vis_ue4_krgd_hadodob_vers_1

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * Created by chainstair on 29.01.2018.
 */
class ListAdapter(activity: Activity, bluetoothDeviceList: ArrayList<BluetoothModel>) : BaseAdapter() {

    var activity : Activity = activity
    var layoutInflater : LayoutInflater = (activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?)!!
    var bluetoothDeviceList : ArrayList<BluetoothModel> = bluetoothDeviceList

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var vi : View? = convertView
        var viewHolder : ViewHolder? = null
        // Check if a view can be reused, otherwise inflate a layout and set up the view holder
        if (vi == null){
            // Inflate view from layout file
            vi = layoutInflater.inflate(R.layout.fragment_list_entry,null)

            viewHolder = ViewHolder(activity.applicationContext)
            viewHolder.bluetoothTV = vi?.findViewById(R.id.fragment_listentryTV)

            // Set holder as tag for row for more efficient access.
            vi?.setTag(viewHolder)
        }
        else {
            // View has already been initialised, get its holder
            viewHolder = vi.getTag() as ViewHolder?
        }

        viewHolder?.bluetoothTV?.text = bluetoothDeviceList.get(position).name

        // SET ONCLICKLISTENER FOR WHOLE FRAGMENT!!
        vi?.setOnClickListener(View.OnClickListener { v ->
            var sensorTextView : TextView? = activity.findViewById((R.id.activity_main_sensorTV))
            sensorTextView?.setText(bluetoothDeviceList.get(position).sensordata)
        })

        return vi!!
    }

    override fun getItem(position: Int): Any {
        return bluetoothDeviceList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return bluetoothDeviceList.size
    }
}