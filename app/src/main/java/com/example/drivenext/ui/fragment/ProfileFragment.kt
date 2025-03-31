package com.example.drivenext.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.drivenext.R
import com.example.drivenext.viewmodel.UserViewModel
import android.widget.LinearLayout
import android.widget.Toast
import com.example.drivenext.ui.activity.LoginActivity

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val userViewModel: UserViewModel by viewModels()
    private val pickImageRequestCode = 1001
    private var userEmail: String? = null
    private var avatarButton: ImageButton? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        userEmail = prefs.getString("email", null)

        avatarButton = view.findViewById(R.id.btn_avatar)

        val mailText: TextView = view.findViewById(R.id.mail_text)
        val nameText: TextView = view.findViewById(R.id.name)
        val logoutBtn: LinearLayout = view.findViewById(R.id.login_out)

        userEmail?.let { email ->
            userViewModel.getUserByEmail(email).observe(viewLifecycleOwner) { user ->
                user?.let {
                    nameText.text = "${it.firstName} ${it.lastName}"
                    mailText.text = it.email

                    if (!it.profilePic.isNullOrEmpty()) {
                        Glide.with(this)
                            .load(Uri.parse(it.profilePic))
                            .circleCrop()
                            .into(avatarButton!!)
                    }

                    avatarButton?.setOnClickListener {
                        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                            addCategory(Intent.CATEGORY_OPENABLE)
                            type = "image/*"
                        }
                        startActivityForResult(intent, pickImageRequestCode)
                    }
                }
            }
        }

        logoutBtn.setOnClickListener {
            prefs.edit().clear().apply()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == pickImageRequestCode && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            imageUri?.let {
                requireContext().contentResolver.takePersistableUriPermission(
                    it, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                // Обновляем в БД
                userEmail?.let { email ->
                    userViewModel.getUserByEmail(email).observe(viewLifecycleOwner) { user ->
                        user?.let { currentUser ->
                            currentUser.profilePic = it.toString()
                            userViewModel.update(currentUser)

                            Glide.with(this)
                                .load(it)
                                .circleCrop()
                                .into(avatarButton!!)
                        }
                    }
                }
            }
        }
    }
}
