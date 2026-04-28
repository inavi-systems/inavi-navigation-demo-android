package com.inavi.airlibsample.adapter

import com.inaviair.sdk.RoutePtItem
import com.thinkware.inaviair.map.mapoverlay.MapIcon
import com.thinkware.inaviair.map.mapoverlay.MapOverlay

object PageDataStore {

    @JvmStatic
    var overlayMap: MapOverlay? = null

    @JvmStatic
    var iconEx: MapIcon? = null

    @JvmStatic
    var overlayRoute: MapOverlay? = null

    @JvmStatic
    var mapIconPin: MapIcon? = null

    @JvmStatic
    var mapIconGoal: MapIcon? = null

    @JvmStatic
    var startPoint = RoutePtItem()

    @JvmStatic
    var goalPoint = RoutePtItem()

    @JvmStatic
    var viaPoints = mutableListOf<RoutePtItem>()

    @JvmStatic
    var routeResult: ArrayList<String>? = null

    @JvmStatic
    var selectedRCIndex = 0


    @JvmStatic
    fun getSelectedRID(): String? {
        routeResult?.let {
            return@getSelectedRID it[selectedRCIndex]
        }

        return null
    }

    @JvmStatic
    fun destroy() {
        overlayMap = null
        iconEx = null
        overlayRoute = null
        mapIconPin = null
        mapIconGoal = null
        routeResult = null
        viaPoints.clear()
    }
}
