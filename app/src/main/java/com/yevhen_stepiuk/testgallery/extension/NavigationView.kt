package com.yevhen_stepiuk.testgallery.extension

import android.support.design.widget.NavigationView
import android.view.View


val NavigationView.headerView: View get() = getHeaderView(0)