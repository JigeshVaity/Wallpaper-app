package com.example.wallpapermy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wallpapermy.adapter.BomAdapter
import com.example.wallpapermy.adapter.PopularAdapter
import com.example.wallpapermy.model.BomModel
import com.example.wallpapermy.model.PopularModel
import com.google.firebase.firestore.FirebaseFirestore

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    //
    private lateinit var db: FirebaseFirestore


    //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        var rec = rootView.findViewById<RecyclerView>(R.id.rcv_bom)
        db = FirebaseFirestore.getInstance()
        db.collection("bestofmonth").addSnapshotListener { value, error ->
            val listBestOfMonth = arrayListOf<BomModel>()
            val data = value?.toObjects(BomModel::class.java)
            listBestOfMonth.addAll(data!!)
            rec.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rec.adapter = BomAdapter(requireContext(),listBestOfMonth)

        }


        var rec1 = rootView.findViewById<RecyclerView>(R.id.rcv_popular)
        db = FirebaseFirestore.getInstance()
        db.collection("popular").addSnapshotListener { value, error ->
            val listPopular = arrayListOf<PopularModel>()
            val data = value?.toObjects(PopularModel::class.java)
            listPopular.addAll(data!!)
            rec1.layoutManager = GridLayoutManager(context,2)

            rec1.adapter = PopularAdapter(requireContext(),listPopular)

        }

        return rootView
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
