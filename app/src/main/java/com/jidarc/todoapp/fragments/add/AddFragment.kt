package com.jidarc.todoapp.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jidarc.todoapp.R
import com.jidarc.todoapp.data.models.ToDoData
import com.jidarc.todoapp.data.viewmodel.ToDoViewModel
import com.jidarc.todoapp.fragments.SharedViewModel

class AddFragment : Fragment() {

    private lateinit var myView: View
    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_add, container, false)

        setHasOptionsMenu(true)

        myView.findViewById<Spinner>(R.id.priorities_spinner).onItemSelectedListener = mSharedViewModel.listener

        return myView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_add) {
            insertDataToDb()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val mTitle = myView.findViewById<EditText>(R.id.title_et).text.toString()
        val mPriority = myView.findViewById<Spinner>(R.id.priorities_spinner).selectedItem.toString()
        val mDescription = myView.findViewById<EditText>(R.id.description_et).text.toString()

        val validation = mSharedViewModel.verifyDataFromUser(mTitle, mDescription)

        if (validation) {
            val newData = ToDoData(0, mTitle, mSharedViewModel.parsePriority(mPriority), mDescription)
            mToDoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_LONG).show()
        }
    }

}