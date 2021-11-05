package com.hotmail.or_dvir.mydoc.ui.my_doctors

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import com.hotmail.or_dvir.mydoc.R
import com.hotmail.or_dvir.mydoc.ui.shared.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyDoctorsFragment : BaseFragment<MyDoctorsViewModel>()
{
    override val viewModel: MyDoctorsViewModel by viewModel()

    override val fragmentId = R.id.myDoctorsFragment

    @Composable
    override fun ScreenContent() = MyDoctorsScreen()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        //todo used for experiments. just leave it here until you dont need it anymore
        super.onViewCreated(view, savedInstanceState)
    }
}