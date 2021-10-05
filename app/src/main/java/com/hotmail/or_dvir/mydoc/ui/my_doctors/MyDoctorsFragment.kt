package com.hotmail.or_dvir.mydoc.ui.my_doctors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hotmail.or_dvir.mydoc.R
import com.hotmail.or_dvir.mydoc.ui.shared.NavigationDestination.DoctorDetailsScreen
import com.hotmail.or_dvir.mydoc.ui.shared.NavigationDestination.NewDoctorScreen
import kotlinx.coroutines.flow.collect
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

            collectNavigationEvents()
            setContent { MyDoctorsScreen(viewModel) }
        }
    }

    private fun collectNavigationEvents()
    {
        lifecycleScope.launchWhenStarted {
            viewModel.navDestinationFlow.collect { destination ->
                when (destination)
                {
                    is NewDoctorScreen ->
                    {
                        //todo
                    }

                    is DoctorDetailsScreen ->
                    {
                        findNavController().navigate(
                            MyDoctorsFragmentDirections.toDoctorDetailsFragment(
                                destination.doctorId.toString()
                            )
                        )
                    }
                }
            }
        }
    }
}