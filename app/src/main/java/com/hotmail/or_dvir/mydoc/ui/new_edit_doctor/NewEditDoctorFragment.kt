package com.hotmail.or_dvir.mydoc.ui.new_edit_doctor

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.navigation.fragment.navArgs
import com.hotmail.or_dvir.mydoc.R
import com.hotmail.or_dvir.mydoc.ui.shared.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.UUID

class NewEditDoctorFragment : BaseFragment<NewEditDoctorViewModel>()
{
    private val fragArgs: NewEditDoctorFragmentArgs by navArgs()
    override val viewModel: NewEditDoctorViewModel by viewModel()
    override val fragmentId = R.id.newEditDoctorFragment

    @Composable
    override fun ScreenContent() = NewEditDoctorScreen(viewModel)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadDoctor(UUID.fromString(fragArgs.doctorId))
    }
}