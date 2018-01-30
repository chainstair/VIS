package at.fhooe.mc.vis_ue4_krgd_hadodob_vers_1

import android.app.ListActivity
import android.os.Bundle
import android.app.Fragment

class MainActivity : ListActivity() {

    var bluetoothDeviceList : ArrayList<BluetoothModel>? = ArrayList<BluetoothModel>()
    var customAllListAdapter: ListAdapter? = null
    var listEntryFragmentForBLE: ListEntryFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bluetoothDeviceList?.add(BluetoothModel("Test", "Distance - 5m\nByte - 640 byte\nEnergy - low"))
        bluetoothDeviceList?.add(BluetoothModel("Test2", "Distance - 9m\nByte - 3928 byte\nEnergy - high"))
        bluetoothDeviceList?.add(BluetoothModel("Test3", "Distance - 11m\nByte - 2343 byte\nEnergy - mid"))
        bluetoothDeviceList?.add(BluetoothModel("Test4", "Distance - 0m\nByte - 6240 byte\nEnergy - low"))

        customAllListAdapter = ListAdapter(this, bluetoothDeviceList!!)
        listEntryFragmentForBLE = ListEntryFragment()
        listEntryFragmentForBLE?.listAdapter = customAllListAdapter
        customAllListAdapter?.notifyDataSetChanged()
    }
}