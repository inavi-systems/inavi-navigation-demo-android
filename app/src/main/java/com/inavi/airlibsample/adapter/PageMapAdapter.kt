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
import kotlinx.android.synthetic.main.row_contents_basic.view.*
import kotlinx.android.synthetic.main.row_header.view.*

/**
 * Created by J.W. Park on 2019-10-14
 */


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
    MAX(11)

}

abstract class MapListItemHolder(view: View): RecyclerView.ViewHolder(view)
class MapListItemHolderHeader(view: LinearLayout): MapListItemHolder(view) {
    var tvTitle: TextView = view.tvRowTitle
}
class MapListItemHolderItem(view: ConstraintLayout): MapListItemHolder(view) {
    var clRowMain: ConstraintLayout = view.clRowMain
    var tvContents: TextView = view.tvContents
}

class PageMapAdapter(private var list: List<BasicListItem>) : RecyclerView.Adapter<MapListItemHolder>() {

    private var mTrafficOn = false
    private var mEnableRotate = true
    //private var mMapOverlay: MapOverlay? = null
    //private var mMapIcon: MapIcon? = null

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

                //val item = list.getOrNull(position) ?: return
                holder.tvTitle.visibility = View.GONE
            }
        }
    }

    private fun processFunc(funcType: FuncMap, tvContents: TextView) {
        when(funcType) {
            FuncMap.CURRENTON -> {
                INaviController.setCarCurrentPosition()

                //var curPos = INaviController.getCurrentPos()
                //android.util.Log.e("curPos", " lat : " + curPos.lat + ", lon : " + curPos.lon +", a : " + curPos.angle)
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


                /** ICONGRAVITY */

                // CENTER : 이미지의 정중앙이 map icon 좌표 위치
                // CENTER_TOP : 이미지의 정중앙 하단이 map icon 좌표 위치
                // LEFT_TOP : 이미지의 우측하단 좌표가 map icon 좌표 위치 (출력은 이미지가 좌표 기준 좌상단에 출력)
                // RIGHT_TOP : 이미지의 좌측하단 좌표가 map icon 좌표 위치 (출력은 이미지가 좌표 기준 우상단에 출력)
                // LEFT_BOTTOM : 이미지의 우측상단 좌표가 map icon 좌표 위치 (출력은 이미지가 좌표 기준 좌하단에 출력)
                // RIGHT_BOTTOM : 이미지의 좌측상단 좌표가 map icon 좌표 위치 (출력은 이미지가 좌표 기준 우하단에 출력)


                var markerLat = 37.402333
                var markerLon = 127.110589

                val mapIcon = INaviController.createMapIcon(
                    markerLat, //위도(lat)
                    markerLon, //경도(lon)
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

                PageDataStore.overlayMap = null
                PageDataStore.iconEx = null

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
        }
    }
}