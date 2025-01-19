package com.bps.fiatscape.common.base

import androidx.fragment.app.Fragment
import com.bps.fiatscape.common.navigation.Navigator
import javax.inject.Inject

class BaseFragment: Fragment() {

    @Inject lateinit var navi: Navigator

}
