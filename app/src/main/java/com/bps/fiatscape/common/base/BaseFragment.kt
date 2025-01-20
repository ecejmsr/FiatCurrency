package com.bps.fiatscape.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavDirections
import com.bps.fiatscape.BR
import com.bps.fiatscape.MainActivity
import com.bps.fiatscape.MainActivity.Companion.backPressedFunction
import com.bps.fiatscape.common.navigation.Navigator
import javax.inject.Inject

abstract class BaseFragment<DataBinding : ViewDataBinding>: Fragment() {

    @Inject lateinit var navi: Navigator
    protected lateinit var binding: DataBinding
    protected abstract val layoutRes: Int
    protected abstract val viewModel: BaseViewModel?
    protected open var appBarBackButtonVisibility = View.GONE
    protected open var appBarNotificationsVisibility = View.VISIBLE
    protected open var appBarBackButtonFunction: (() -> Unit)? = null

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
        registerToNavigationStream()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setAppBar()
    }

    private fun registerToNavigationStream() {
        viewModel?.navigationCommand?.observe(viewLifecycleOwner) { command ->
            if (command == null) return@observe

            navi.navigateAction(command)
            viewModel?.navigationCommand?.value = null
        }
    }

    private fun setAppBar() {
        val mainActivity = activity as MainActivity
        mainActivity.setAppBar(
            backBtn = appBarBackButtonVisibility,
            notificationBtn = appBarNotificationsVisibility,
            backButtonFunction = appBarBackButtonFunction
        )
        backPressedFunction = appBarBackButtonFunction
    }
}
