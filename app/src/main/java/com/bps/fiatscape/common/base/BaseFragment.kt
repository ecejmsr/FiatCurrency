package com.bps.fiatscape.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.bps.fiatscape.BR
import com.bps.fiatscape.common.navigation.Navigator
import javax.inject.Inject

abstract class BaseFragment<DataBinding : ViewDataBinding>: Fragment() {

    @Inject lateinit var navi: Navigator
    protected lateinit var binding: DataBinding
    protected abstract val layoutRes: Int
    protected abstract val viewModel: BaseViewModel?

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.apply {
            binding.lifecycleOwner = viewLifecycleOwner
            binding.setVariable(BR.viewModel, viewModel)
        }
        return binding.root
    }

}
