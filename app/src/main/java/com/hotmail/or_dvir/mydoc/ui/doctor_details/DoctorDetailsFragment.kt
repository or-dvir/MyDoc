package com.hotmail.or_dvir.mydoc.ui.doctor_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.hotmail.or_dvir.mydoc.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.UUID

class DoctorDetailsFragment : Fragment()
{
    private val viewModel: DoctorDetailsViewModel by viewModel()
    private val fragArgs: DoctorDetailsFragmentArgs by navArgs()

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

//            collectRegisterEvents()
            setContent { DoctorsDetailsScreen(viewModel) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadDoctor(UUID.fromString(fragArgs.doctorId))
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