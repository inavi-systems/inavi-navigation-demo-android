package com.inavi.airlibsample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.inavi.airlibsample.R
import com.inaviair.sdk.*

enum class FuncMap(val value: Int) {
    NONE(0),
    CURRENTON(1),
    VIEWMODE(2),
    FONTSIZE(3),
    DAYNIGHT(4),
    ZOOMIN(5),
    ZOOMOUT(6),
    ADDMAPICON(7),
    REMOVEMAPICON(8),
    TRAFFICON(9),
    ENABLEROTATE(10),
    ROUTELINEPOINTS(11),
    APTCOLLISIONIGNORE(12),
    MAX(13)

}

abstract class MapListItemHolder(view: View): RecyclerView.ViewHolder(view)
class MapListItemHolderHeader(view: LinearLayout): MapListItemHolder(view) {
    val tvTitle: TextView by lazy{
        view.findViewById(R.id.tvRowTitle)
    }
}

class MapListItemHolderItem(view: ConstraintLayout): MapListItemHolder(view) {
    val clRowMain: ConstraintLayout by lazy{
        view.findViewById(R.id.clRowMain)
    }
    val tvContents: TextView by lazy{
        view.findViewById(R.id.tvContents)
    }
}

class PageMapAdapter(private var list: List<BasicListItem>) : RecyclerView.Adapter<MapListItemHolder>() {

    private var mTrafficOn = false
    private var mEnableRotate = true

    override fun getItemCount(): Int {
        return list.size
    }
    override fun getItemViewType(position: Int): Int {
        val item = list.getOrNull(position)?: return BasicListType.NONE.value
        return item.listType.value
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapListItemHolder {

        return when(viewType){
            BasicListType.HEADER.value -> {
                val cell = LayoutInflater.from(parent.context).inflate(R.layout.row_header, parent, false) as LinearLayout
                MapListItemHolderHeader(cell)
            }
            BasicListType.ITEM.value -> {
                val cell = LayoutInflater.from(parent.context).inflate(R.layout.row_contents_basic, parent, false) as ConstraintLayout
                MapListItemHolderItem(cell)
            }
            else -> {
                val cell = LayoutInflater.from(parent.context).inflate(R.layout.row_header, parent, false) as LinearLayout
                MapListItemHolderHeader(cell)
            }
        }
    }
    override fun onBindViewHolder(holder: MapListItemHolder, position: Int) {
        when (getItemViewType(position)) {
            BasicListType.HEADER.value -> {
                if (holder !is MapListItemHolderHeader) return

                val item = list.getOrNull(position) ?: return
                holder.tvTitle.text = item.mainText
            }
            BasicListType.ITEM.value -> {
                if (holder !is MapListItemHolderItem) return

                val item = list.getOrNull(position) ?: return
                holder.tvContents.text = item.mainText

                holder.clRowMain.setOnClickListener {
                    processFunc(item.funcType, holder.tvContents)
                }
            }
            else -> {
                if (holder !is MapListItemHolderHeader) return

                holder.tvTitle.visibility = View.GONE
            }
        }
    }


    private fun addAllRoutePointMarker(){
        PageDataStore.getSelectedRID()?.let {
            var routeLinePoints = INaviController.getRouteLinePoints(it)
            for (i in 0 until routeLinePoints!!.length()) {
                val item = routeLinePoints.getJSONObject(i)

                val nextPointLonX = item.getDouble("lon")
                val nextPointLatY = item.getDouble("lat")

                val mapIcon = INaviController.createMapIcon(
                    nextPointLatY,
                    nextPointLonX,
                    R.drawable.icon_sample_normal,
                    R.drawable.icon_sample_normal,
                    ICONGRAVITY.CENTER,
                    0,
                    14,
                    true
                )?: return

                PageDataStore.iconEx?.let { mIcon ->
                    if(mIcon.compare(mapIcon))
                        return
                }

                PageDataStore.overlayRoute?.let { overlay ->
                    PageDataStore.iconEx = INaviController.addMapIcon(overlay, mapIcon)
                }
            }

        }
    }

    private fun processFunc(funcType: FuncMap, tvContents: TextView) {
        when(funcType) {
            FuncMap.CURRENTON -> {
                INaviController.setCarCurrentPosition()
            }
            FuncMap.VIEWMODE -> {

                var curViewMode = INaviController.getMapViewMode()
                curViewMode = curViewMode.next()
                INaviController.setMapViewMode(curViewMode)

                when(curViewMode) {
                    MAPVIEWMODE.VIEWMODE_3D -> tvContents.text = "지도 모드 : 3D뷰"
                    MAPVIEWMODE.VIEWMODE_2D -> tvContents.text = "지도 모드 : 2D 회전뷰"
                    MAPVIEWMODE.VIEWMODE_2D_FIX -> tvContents.text = "지도 모드 : 2D 고정뷰"
                    else -> tvContents.text = "지도 모드"
                }
            }
            FuncMap.FONTSIZE -> {
                var curFontType = INaviController.getMapFontSizeType()
                curFontType = curFontType.next()

                INaviController.setMapFontSizeType(curFontType)

                when(curFontType) {
                    MAPFONTSIZETYPE.NORMAL -> tvContents.text = "글자크기 : 기본"
                    MAPFONTSIZETYPE.LARGE -> tvContents.text = "글자크기 : 크게"
                    else -> tvContents.text = "글자크기 : 보통"
                }

            }
            FuncMap.DAYNIGHT -> {
                var curDayNight = INaviController.getMapDayNightMode()
                curDayNight = curDayNight.next()
                INaviController.setMapDayNigthMode(curDayNight)

                when(curDayNight) {
                    MAPDAYNIGHTMODE.AUTO -> tvContents.text = "주/야간설정 : 시간대별 자동"
                    MAPDAYNIGHTMODE.ALWAYSDAY -> tvContents.text = "주/야간설정 : 항상 주간"
                    MAPDAYNIGHTMODE.ALWAYSNIGHT -> tvContents.text = "주/야간설정 : 항상 야간"
                    else -> tvContents.text = "주/야간설정 : 시간대별 자동"
                }

            }
            FuncMap.ZOOMIN -> {
                INaviController.zoomIn()
            }
            FuncMap.ZOOMOUT -> {
                INaviController.zoomOut()
            }
            FuncMap.ADDMAPICON -> {
                if( PageDataStore.overlayMap == null )
                    PageDataStore.overlayMap = INaviController.createMapOverlay()


                PageDataStore.overlayMap?.setMapIconListener {
                    PageDataStore.overlayMap?.setSelect(it, !it.isSelected, false, true)
                }

                val markerLat = 37.402333
                val markerLon = 127.110589

                val mapIcon = INaviController.createMapIcon(
                    markerLat,
                    markerLon,
                    R.drawable.icon_sample_oil_sample,
                    R.drawable.icon_sample_oil_sample_s,
                    ICONGRAVITY.RIGHT_TOP,
                    0,
                    14,
                    true
                )?: return

                PageDataStore.iconEx?.let {
                    if(it.compare(mapIcon))
                        return
                }

                PageDataStore.overlayMap?.let {
                    PageDataStore.iconEx = INaviController.addMapIcon(it, mapIcon)

                    INaviController.setMapPosition(markerLat, markerLon, 0.0)
                }
            }
            FuncMap.REMOVEMAPICON -> {
                PageDataStore.overlayMap?.let { overlay ->
                    PageDataStore.iconEx?.let { mapIcon ->
                        INaviController.removeMapIcon(overlay, mapIcon)
                    }
                    INaviController.removeMapOverlay(overlay)
                }
                PageDataStore.overlayRoute?.let {
                    INaviController.removeMapIconALL(it)
                }
                PageDataStore.overlayMap = null
                PageDataStore.overlayRoute = null
                PageDataStore.iconEx = null
                PageDataStore.mapIconGoal = null
                PageDataStore.mapIconPin = null
                PageDataStore.startPoint = RoutePtItem()
                PageDataStore.goalPoint = RoutePtItem()
                PageDataStore.viaPoints = mutableListOf<RoutePtItem>()

            }
            FuncMap.TRAFFICON -> {
                mTrafficOn = !mTrafficOn
                INaviController.visibleTrafficLine(mTrafficOn)
            }
            FuncMap.ENABLEROTATE -> {
                mEnableRotate = !mEnableRotate
                INaviController.setMapEnableRotate(mEnableRotate)

                if( mEnableRotate )
                    tvContents.text = "지도 회전 세팅 : 회전 가능"
                else
                    tvContents.text = "지도 회전 세팅 : 회전 불가"
            }
            FuncMap.ROUTELINEPOINTS -> {
                addAllRoutePointMarker()
            }

            FuncMap.APTCOLLISIONIGNORE -> {
                INaviController.setAptPoiCollisionIgnored(true)
            }

            else -> {}
        }
    }
}
