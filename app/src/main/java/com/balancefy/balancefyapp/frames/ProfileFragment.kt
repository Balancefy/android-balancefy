package com.balancefy.balancefyapp.frames

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.adapter.TopicPostsProfileAdapter
import com.balancefy.balancefyapp.databinding.FragmentProfileBinding
import com.balancefy.balancefyapp.models.request.UserEdit
import com.balancefy.balancefyapp.models.response.FeedTopicoResponseDto
import com.balancefy.balancefyapp.models.response.ListaFeedTopicoResponse
import com.balancefy.balancefyapp.rest.Rest
import com.balancefy.balancefyapp.utils.getFileName
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var token: String
    private lateinit var avatarIMG: String
    private lateinit var avatarBackgroundIMG: String
    private var selectedImage: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    private val pegarFoto = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        binding.avatarProfile.setImageURI(uri)
        val file = uri!!.path?.let { File(it) }
        val multipart = MultipartBody.Part.createFormData(
            "file",
            file?.name,
            RequestBody.create(
                MediaType.parse("image/*"),
                file!!
            )
        )
        Rest.getUploadInstance().uploadAvatar("Bearer ${arguments?.getString("token")}", multipart)
            .enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    when (response.code()) {
                        200 -> {
                            Toast.makeText(context, "Upload bem sucedido", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    println(t.message)
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private val background = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        binding.backgroundProfile.setImageURI(uri)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        preferences = requireContext().getSharedPreferences("Auth", AppCompatActivity.MODE_PRIVATE)

        token = preferences.getString("token", "")!!

        avatarIMG = preferences.getString("avatar", "")!!

        avatarBackgroundIMG = preferences.getString("banner", "")!!

        binding.nameProfile.text = preferences.getString("nameUser", "Ze ninguem")

        if (avatarIMG != "") {
            Picasso.get().load(avatarIMG).into(binding.avatarProfile)
        } else {
            binding.avatarProfile.setImageResource(R.drawable.ic_account)
        }

        if (avatarBackgroundIMG != "") {
            Picasso.get().load(avatarBackgroundIMG).into(binding.backgroundProfile)
        } else {
            binding.backgroundProfile.setImageResource(R.drawable.fundo_perfil)
        }

        binding.avatarProfile.setOnClickListener {
            openImagePicker()

        }

        binding.backgroundProfile.setOnClickListener {
            background.launch("image/*")
        }

        binding.icEditProfile.setOnClickListener {
            binding.icForumProfile.visibility = View.VISIBLE
            binding.editProfileComponent.visibility = View.VISIBLE

            binding.icEditProfile.visibility = View.GONE
            binding.recyclerContainer.visibility = View.GONE
        }

        binding.icForumProfile.setOnClickListener {
            binding.icForumProfile.visibility = View.GONE
            binding.editProfileComponent.visibility = View.GONE

            binding.icEditProfile.visibility = View.VISIBLE
            binding.recyclerContainer.visibility = View.VISIBLE
        }

        binding.btnProfileEditConfirm.setOnClickListener {
            if (validateFields()) {
                binding.icForumProfile.visibility = View.GONE
                binding.editProfileComponent.visibility = View.GONE

                binding.icEditProfile.visibility = View.VISIBLE
                binding.recyclerContainer.visibility = View.VISIBLE
            }
        }

        recyclerViewConfiguration()
    }

    fun openImagePicker() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, 101)
        }
    }

    private fun uploadImage() {
        if (selectedImage == null) {
            println("IMAGE IS NULL")
            return
        }
        val parcelFileDescriptor = requireContext().contentResolver.openFileDescriptor(
            selectedImage!!,
            "r",
            null
        ) ?: return
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(
            requireContext().cacheDir,
            requireContext().contentResolver.getFileName(selectedImage!!)
        )
        val outputStream = FileOutputStream(file)

        inputStream.copyTo(outputStream)

        val multipart = MultipartBody.Part.createFormData(
            "image",
            file.name,
            RequestBody.create(
                MediaType.parse("image/*"),
                file
            )
        )

        Rest.getUploadInstance().uploadAvatar("Bearer ${arguments?.getString("token")}", multipart)
            .enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    when (response.code()) {
                        200 -> {
                            Toast.makeText(context, "Upload bem sucedido", Toast.LENGTH_SHORT)
                                .show()
                        }
                        else -> {
                            println(response.code())
                        }
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    println(t.message)
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }
            })


    }


    private fun validateFields(): Boolean {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()

        return when {
            email.isEmpty() -> {
                binding.etEmail.error = getString(R.string.error_empty_field)
                false
            }

            validateEmail(email) -> {
                binding.etEmail.error = getString(R.string.error_invalid_field)
                false
            }

            name.isEmpty() -> {
                binding.etName.error = getString(R.string.error_empty_field)
                false
            }
            name.length < 3 -> {
                binding.etName.error = getString(R.string.error_invalid_field)
                false
            }

            else -> {
                val userData = UserEdit(email, name)

                Rest.getUserInstance().editProfile(
                    "Bearer $token",
                    userData
                ).enqueue(object : Callback<UserEdit> {
                    override fun onResponse(call: Call<UserEdit>, response: Response<UserEdit>) {
                        val data = response.body()
                        val editor = preferences.edit()

                        when (response.code()) {
                            200 -> {
                                Toast.makeText(
                                    context,
                                    R.string.email_name_changed,
                                    Toast.LENGTH_SHORT
                                ).show()
                                editor.putString("nameUser", userData.name)
                                editor.apply()
                            }
                        }
                    }

                    override fun onFailure(call: Call<UserEdit>, t: Throwable) {
                        binding.tvError.text = context?.getString(R.string.connection_error)
                    }
                })
                true
            }
        }
    }

    private fun recyclerViewConfiguration() {
        Rest.getForumInstance().getListTopicById(
            "Bearer $token",
            requireArguments().getInt("accountId")
        ).enqueue(object : Callback<ListaFeedTopicoResponse> {
            override fun onResponse(
                call: Call<ListaFeedTopicoResponse>,
                response: Response<ListaFeedTopicoResponse>
            ) {
                val data = response.body()?.listTopic

                when (response.code()) {
                    200 -> {
                        configRecycleView(data)
                    }
                    else -> {
                        println(response.code())
                        Toast.makeText(
                            context,
                            getString(R.string.connection_error),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call<ListaFeedTopicoResponse>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                binding.emptyListOfTopics.text = getString(R.string.no_posts)
            }
        })
    }

    private fun showMessageTest(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun configRecycleView(posts: List<FeedTopicoResponseDto>?) {
        if (posts!!.isEmpty()) {
            binding.emptyListOfTopics.visibility = View.VISIBLE

            binding.recyclerContainer.visibility = View.GONE
        } else {
            binding.emptyListOfTopics.visibility = View.GONE

            binding.recyclerContainer.visibility = View.VISIBLE

            val recyclerContainer = binding.recyclerContainer

            recyclerContainer.layoutManager = LinearLayoutManager(context)

            recyclerContainer.adapter = TopicPostsProfileAdapter(
                posts,
                token
            ) { mensagem -> showMessageTest(mensagem) }
        }
    }

    private fun validateEmail(email: String) =
        email.length < 3 || !email.contains("@") || !email.contains(".com")


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                101 -> {
                    selectedImage = data?.data
                    binding.avatarProfile.setImageURI(selectedImage)
                    uploadImage()
                }
            }
        }
    }
//    private fun validatePass(pass: String) =
//        !pass.contains(regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])[0-9a-zA-Z$*&@#]{8,}$".toRegex())

}