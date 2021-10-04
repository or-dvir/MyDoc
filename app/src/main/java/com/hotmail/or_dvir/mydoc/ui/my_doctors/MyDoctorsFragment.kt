package com.hotmail.or_dvir.mydoc.ui.my_doctors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.hotmail.or_dvir.mydoc.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyDoctorsFragment : Fragment()
{
    private val viewModel: MyDoctorsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        return ComposeView(requireContext()).apply {
            // In order for savedState to work, the same ID needs to be used for all instances.
            id = R.id.myDoctorsFragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

//            viewModel.apply {
//                onEmailInputChanged(fragArgs.email)
//                onPasswordInputChanged(fragArgs.password)
//            }

            collectRegisterEvents()
            setContent { MyDoctorsScreen(viewModel) }
        }
    }

    private fun collectRegisterEvents()
    {
        //todo
//        lifecycleScope.launchWhenStarted {
//            viewModel.registerEventsFlow.collect {
//                when (it)
//                {
//                    is RegisterResponse.Success ->
//                    {
//                        //todo temporary! navigate to appropriate screen
//                        AlertDialog.Builder(requireContext()).apply {
//                            setMessage("success")
//                            setPositiveButton(R.string.ok) { _, _ -> /*do nothing*/ }
//                        }.show()
//                    }
//                    is RegisterResponse.Error ->
//                    {
//                        AlertDialog.Builder(requireContext()).apply {
//                            setMessage(it.error)
//                            setPositiveButton(R.string.ok) { _, _ -> /*do nothing*/ }
//                        }.show()
//                    }
//                }
//            }
//        }
    }
}