package com.hotmail.or_dvir.mydoc.ui.my_doctors

import androidx.compose.runtime.Composable
import com.hotmail.or_dvir.mydoc.R
import com.hotmail.or_dvir.mydoc.ui.shared.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyDoctorsFragment : BaseFragment<MyDoctorsViewModel>()
{
    override val viewModel: MyDoctorsViewModel by viewModel()

    override val fragmentId = R.id.myDoctorsFragment

    @Composable
    override fun ScreenContent() = MyDoctorsScreen(viewModel)
}