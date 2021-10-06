package com.hotmail.or_dvir.mydoc.ui.doctor_details

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.navigation.fragment.navArgs
import com.hotmail.or_dvir.mydoc.R
import com.hotmail.or_dvir.mydoc.ui.shared.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.UUID

class DoctorDetailsFragment : BaseFragment<DoctorDetailsViewModel>()
{
    private val fragArgs: DoctorDetailsFragmentArgs by navArgs()
    override val viewModel: DoctorDetailsViewModel by viewModel()
    override val fragmentId = R.id.doctorDetailsFragment

    @Composable
    override fun ScreenContent() = DoctorsDetailsScreen(viewModel)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadDoctor(UUID.fromString(fragArgs.doctorId))
    }
}