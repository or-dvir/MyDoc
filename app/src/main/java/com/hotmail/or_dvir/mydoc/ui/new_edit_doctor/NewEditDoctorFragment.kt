package com.hotmail.or_dvir.mydoc.ui.new_edit_doctor

import androidx.compose.runtime.Composable
import com.hotmail.or_dvir.mydoc.R
import com.hotmail.or_dvir.mydoc.ui.shared.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewEditDoctorFragment : BaseFragment<NewEditDoctorViewModel>()
{
    override val viewModel: NewEditDoctorViewModel by viewModel()

    override val fragmentId = R.id.newEditDoctorFragment

    @Composable
    override fun ScreenContent() = NewEditDoctorScreen(viewModel)
}