package com.inavi.airlibsample.fragment

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.inavi.airlibsample.R
import com.inaviair.sdk.*

/**
 * Created by J.W. Park on 2/4/21
 */
class MapFragment(private val acty: Activity) : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)


        INaviController.initLayout(acty, rootView, R.id.mapAdapter, R.id.mapLayer)

        initNaviSDK()
        return rootView
    }

    private fun initNaviSDK() {


        //INaviController.forceHideMapComponent()
        //INaviController.forceHideMapComponent(MAPCOMPONENT.CUR_ON_BTN, true)
        INaviController.forceHideMapComponent(MAPCOMPONENT.ROUTE_TRAFFIC_STATUS_BAR, false)
        /*
        INaviController.forceHideMapComponent(MAPCOMPONENT.ANGLE_BTN, false)
        INaviController.forceHideMapComponent(MAPCOMPONENT.ZOOM_IN_OUT_BTN, true)
        INaviController.forceHideMapComponent(MAPCOMPONENT.CUR_ON_BTN, false)
        INaviController.forceHideMapComponent(MAPCOMPONENT.BOTTOM_NORMAL_VIEW, true)
        INaviController.forceHideMapComponent(MAPCOMPONENT.BOTTOM_DRIVING_VIEW, true)
        INaviController.forceHideMapComponent(MAPCOMPONENT.TOP_ARRIVAL_VIEW, true)
        INaviController.forceHideMapComponent(MAPCOMPONENT.BOTTOM_ARRIVAL_VIEW, true)
        */

        //INaviController.forceHideMapComponent(MAPCOMPONENT.BOTTOM_ARRIVAL_VIEW, false)
        //INaviController.forceHideMapComponent(MAPCOMPONENT.ALL_COMPONENT, true)
        //INaviController.forceHideMapComponent(MAPCOMPONENT.BOTTOM_NORMAL_VIEW, false)


        //INaviController.setMapStyle(MAPSTYLE.MAPBOX)
        INaviController.setTurnViewTaxiIcon(true)

        val streamType = AudioManager.STREAM_NOTIFICATION
        /** 하드웨어키에 대한 볼륨조정시 미디어볼륨이 되도록 설정*/
        INaviController.setAudioStreamType(streamType)


        /**
         * Navi SDK 초기화
         * @param context Application 시작 Context
         * @param rootPath Application 시작 Path
         * @param uniqueID 유일 식별자 ( 휴대폰 번호 )
         * @param listener 결과 리턴 Callback
         */
        val phoneNumber = "UniqueID"//"01012345678"
        INaviController.initalizeNavi(acty, getRootPath(), phoneNumber, object :
            OnNaviInitListener {
            override fun onSuccess() {

                /**
                 * 단말기 미디어 볼륨과 별개로 내비에서만 사용하는 음성 조절
                 * @param vol 0.0 ~ 1.0
                 */
                INaviController.setNaviVolume(1.0f)

                INaviController.setMapViewMode(MAPVIEWMODE.VIEWMODE_2D)

                INaviController.setCarSpeedListener(mCarSpeedListener)


            }
            override fun onFail(errCode: Int, errMsg: String) {

                INaviController.destroyNavi()

            }
        })
    }

    private fun getRootPath(): String {
        var strStorageDir: String = ""
        val fRootPath = ContextCompat.getExternalFilesDirs(context!!, null)
        if (fRootPath.isNotEmpty() && fRootPath[0] != null)
            strStorageDir = fRootPath[0].absolutePath

        return strStorageDir
    }

    private val mCarSpeedListener = object : OnCarSpeedListener {
        override fun onSpeed(speed: Int) {
        }

    }
}