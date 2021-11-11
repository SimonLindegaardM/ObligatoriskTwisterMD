package com.example.obligatorisktwistermd

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import models.AuthViewModel
import models.Message
import models.MessageAdapter
import models.MessageViewModel
import com.example.obligatorisktwistermd.databinding.FragmentMessagesBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MessageFragment : Fragment() {

    private var _binding: FragmentMessagesBinding? = null

    private val authViewModel: AuthViewModel by activityViewModels()
    private val messageViewModel: MessageViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        _binding = FragmentMessagesBinding.inflate(inflater, container, false)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messageViewModel.messageLiveData.observe(viewLifecycleOwner) { messages ->
            binding.messageview.adapter = MessageAdapter<Message>(messages){
                    val action = MessageFragmentDirections.actionMessageFragmentToCommentFragment(it)
                    findNavController().navigate(action)
            }
        }
        messageViewModel.reload()
        binding.fab.setOnClickListener{ view ->
            showDialog()
        }
        binding.swipeRefresh.setOnRefreshListener{
            messageViewModel.reload()
            binding.swipeRefresh.isRefreshing = false
        }

    }
    private fun showDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Skriv en besked")
        val inputField = EditText(context)
        inputField.hint = "Indtast her"
        inputField.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(inputField)
        builder.setPositiveButton("Send") {dialog, which->
            val messageInput = inputField.text.toString().trim()
            if (messageInput.isEmpty()) {
                Snackbar.make(binding.root, "Mangler en besked", Snackbar.LENGTH_LONG).show()
            } else  {
                val message = Message(messageInput, authViewModel.userInfoData.value?.email.toString(), 0)
                messageViewModel.add(message)
            }
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}