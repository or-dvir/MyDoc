package com.hotmail.or_dvir.mydoc.ui.doctor_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.hotmail.or_dvir.mydoc.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class DoctorDetailsFragment : Fragment()
{
    private val viewModel: DoctorDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        return ComposeView(requireContext()).apply {
            // In order for savedState to work, the same ID needs to be used for all instances.
            id = R.id.doctorDetailsFragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

//            viewModel.apply {
//                onEmailInputChanged(fragArgs.email)
//                onPasswordInputChanged(fragArgs.password)
//            }

//            collectRegisterEvents()
            setContent { DoctorsDetailsScreen(viewModel) }
        }
    }

//    private fun collectRegisterEvents()
//    {
//        lifecycleScope.launchWhenStarted {
//            viewModel.navDestinationFlow.collect { destination ->
//                when (destination)
//                {
//                    is NewDoctorScreen ->
//                    {
//                        //todo
//                    }
//
//                    is DoctorDetailsScreen ->
//                    {
//                        //todo
//                    }
//                }
//            }
//        }
//    }
}