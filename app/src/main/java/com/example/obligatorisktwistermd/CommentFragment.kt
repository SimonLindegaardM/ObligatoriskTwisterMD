package com.example.obligatorisktwistermd

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.obligatorisktwistermd.databinding.FragmentCommentsBinding
import com.google.android.material.snackbar.Snackbar
import models.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CommentFragment : Fragment() {

    private var _binding: FragmentCommentsBinding? = null
    private val binding get() = _binding!!

    val messageViewModel: MessageViewModel by activityViewModels()
    val authViewModel: AuthViewModel by activityViewModels()
    val message: MutableLiveData<Message> = MutableLiveData<Message>()
    val comment: MutableLiveData<Comment> = MutableLiveData<Comment>()
    private val args: CommentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCommentsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val info = CommentFragmentArgs.fromBundle(requireArguments())
        message.value = info.message

        message.observe(viewLifecycleOwner,{
            binding.messageText.text = message.value?.content
            messageViewModel.getComments(args.message.id)
        })

        binding.buttonDelete.setOnClickListener{
            val id : Int = message.value?.id ?: return@setOnClickListener
            if (message.value?.user == authViewModel.userInfoData.value?.email){
                messageViewModel.delete(id)
                findNavController().popBackStack()
            } else {
                return@setOnClickListener
            }

        }

        messageViewModel.commentsLiveData.observe(viewLifecycleOwner,{
            binding.commentView.adapter = CommentAdapter<Comment>(it){
                val builder : AlertDialog.Builder = AlertDialog.Builder(requireContext())

                builder.setMessage("Delete Comment?").setPositiveButton("Yes", DialogInterface.OnClickListener{ dialog, id ->
                    messageViewModel.deleteComments(message.value?.id!!, it.id)
                }).setNegativeButton("Cancel", DialogInterface.OnClickListener {dialog, id ->
                    dialog.cancel()
                })
                if(authViewModel.userInfoData.value?.email == it.user) {
                    builder.show()
                }
            }
        })

        binding.fabComment.setOnClickListener{ view ->
            showDialog()
        }
        binding.swipeRefresh.setOnRefreshListener{
            messageViewModel.getComments(message.value?.id!!)
            binding.swipeRefresh.isRefreshing = false
        }

    }
    private fun showDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Skriv en kommentar")
        val inputField = EditText(requireContext())
        inputField.hint = "Indtast her"
        inputField.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(inputField)
        builder.setPositiveButton("Send") {dialog, id->
            val comment = Comment(1,message.value?.id!!,inputField.text.toString(),authViewModel.userInfoData.value?.email.toString())
            if (comment.content.isNullOrEmpty()) {
                Snackbar.make(binding.root, "Mangler en kommentar", Snackbar.LENGTH_LONG).show()
            } else  {
                messageViewModel.addComment(message.value?.id!!,comment)
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