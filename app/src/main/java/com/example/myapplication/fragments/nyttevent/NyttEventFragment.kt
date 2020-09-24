package com.example.myapplication.fragments.nyttevent

import RecyclerView.RecyclerView.EventRecyclerAdapter
import RecyclerView.RecyclerView.Moduls.Event
import RecyclerView.RecyclerView.OnEventItemClickListener
import RecyclerView.RecyclerView.TopSpacingItemDecoration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.fragments.Communicator
import com.example.myapplication.midlertidig
import kotlinx.android.synthetic.main.fragment_mine_event.*
import kotlinx.android.synthetic.main.fragment_nytt_event.*
import kotlinx.android.synthetic.main.fragment_nytt_event.view.*

/**
 * A simple [Fragment] subclass.
 */
class NyttEventFragment : Fragment(), OnEventItemClickListener {

    private lateinit var eventAdapter: EventRecyclerAdapter
    private lateinit var skjemamidlertidig: midlertidig

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_nytt_event, container, false)

        view.floating_action_button.setOnClickListener {

            skjemamidlertidig = activity as midlertidig
            skjemamidlertidig.midlertidigSkjema()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initRecyclerView()
        addDataSet()
    }

    //DUMMY DATA
    private fun addDataSet() {
        // val data = DataSource.createDataset()
        var liste: ArrayList<Event> = ArrayList()
        liste.add(
            Event(
                "Konsert med Rockeband",
                "Let's rock'n'roll",
                "data:image/jpeg;base64,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",
                "03-02-21",
                "Haugesund",
                "57",
                "16",
                EventRecyclerAdapter.VIEW_TYPE_ADMINLISTE
            )
        )
        liste.add(
            Event(
                "Tennis",
                "Slå ut all sinnet i ballen",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSCzmPN0vFuCHa7HP6KiM4FOChJ5NvQgD89Yg&usqp=CAU",
                "08-06-20",
                "Tønsberg",
                "7",
                "2",
                EventRecyclerAdapter.VIEW_TYPE_ADMINLISTE
            )
        )

        eventAdapter.submitList(liste);
    }

    //Initierer og kobler recycleView til activityMain
    private fun initRecyclerView() {
        //Apply skjønner contexten selv.
        recycler_view_nyttEvent.apply {
            layoutManager = LinearLayoutManager(context)
            val topSpacingDecoration = TopSpacingItemDecoration(20)
            addItemDecoration(topSpacingDecoration)
            eventAdapter = EventRecyclerAdapter(this@NyttEventFragment)
            adapter = eventAdapter
        }

    }

    override fun onItemClick(item: Event, position: Int) {
       //Fyll ut
    }
}