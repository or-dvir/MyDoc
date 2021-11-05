package com.hotmail.or_dvir.mydoc.ui.shared

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hotmail.or_dvir.mydoc.NavGraphDirections
import com.hotmail.or_dvir.mydoc.navigation.NavigationDestination.DoctorDetailsScreen
import com.hotmail.or_dvir.mydoc.navigation.NavigationDestination.NewEditDoctorScreen
import com.hotmail.or_dvir.mydoc.navigation.NavigationDestination.PopStack
import com.hotmail.or_dvir.mydoc.ui.my_doctors.MyDoctorsFragmentDirections
import kotlinx.coroutines.flow.collect

abstract class BaseFragment<out VM : BaseViewModel<out Any>> : Fragment()
{
    @get:IdRes
    internal abstract val fragmentId: Int
    internal abstract val viewModel: VM

    @Composable
    abstract fun ScreenContent()

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        return ComposeView(requireContext()).apply {
            //todo what does this note mean? must have been copied from a sample...
            //In order for savedState to work, the same ID needs to be used for all instances.
            id = fragmentId

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            collectNavigationEvents()
            setContent { ScreenContent() }
        }
    }

    private fun collectNavigationEvents()
    {
        //todo probably better for each fragment to handle their own navigation...
        // then you can get rid of the viewModel abstract variable

        lifecycleScope.launchWhenStarted {
            viewModel.navDestinationFlow.collect {
                findNavController().apply {
                    when (it)
                    {
                        is PopStack -> popBackStack()
                        is DoctorDetailsScreen ->
                        {
                            navigate(
                                MyDoctorsFragmentDirections.toDoctorDetailsFragment(
                                    it.doctorId.toString()
                                )
                            )
                        }
                        is NewEditDoctorScreen ->
                        {
                            navigate(
                                NavGraphDirections.toNewEditDoctorFragment(
                                    //do NOT use extension function toString().
                                    //for null values it returns the string "null"
                                    it.doctorId?.toString()
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}