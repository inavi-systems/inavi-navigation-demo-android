package com.inavi.airlibsample

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.media.AudioManager
import android.os.*
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.inavi.airlibsample.adapter.PageDataStore
import com.inavi.airlibsample.fragment.MapFragment
import com.inaviair.sdk.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        init {
            //초기 필수 ( application 초기화 시에 호출 )
            INaviController.loadNaviLibrary()
        }
    }


    private val isFragment = false

    private val requestCodePermission = 100

    private val mIntroEx: ConstraintLayout by lazy { clIntroEx }
    private val mControlView: ConstraintLayout by lazy { clControlView }
    private val mVpPager: ViewPager by lazy { vpPager }


    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if( isFragment ) {
            //fragment로 실행시 "activity_main.xml"의 MapLayer, MapAdapter 는 주석 처리해야함
            initLayoutFragment()
        }
        else {
            initLayout()
            checkPermisson()
        }
        

    }

    override fun onDestroy() {
        super.onDestroy()
        destroyApp()
        INaviController.destroyNavi()
    }

    override fun onBackPressed() {

        toggleControlView()
        /*
        val dlgBuilder = AlertDialog.Builder(this)
        dlgBuilder.setTitle(getString(R.string.app_name)).setMessage("앱을 종료 하시겠습니까?")

        dlgBuilder.setPositiveButton("확인") { _, _ ->
            super.onBackPressed()
            destroyApp()
            INaviController.destroyNavi()
            finish()
        }
        dlgBuilder.setNegativeButton("취소") { _, _ ->
            //nothing
        }

        dlgBuilder.create()?.show()

         */
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if( newConfig?.orientation == Configuration.ORIENTATION_PORTRAIT ) {
            ConstraintSet().let {
                it.clone(clMainLayout)

                it.connect(clNaviMap.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)
                it.connect(clNaviMap.id, ConstraintSet.BOTTOM, clControlView.id, ConstraintSet.TOP)
                it.connect(clControlView.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT)
                it.connect(clControlView.id, ConstraintSet.TOP, clNaviMap.id, ConstraintSet.BOTTOM)

                it.applyTo(clMainLayout)
            }


            INaviController.setOrientation(Configuration.ORIENTATION_PORTRAIT)
        }
        else if( newConfig?.orientation == Configuration.ORIENTATION_LANDSCAPE ) {
            ConstraintSet().let {
                it.clone(clMainLayout)

                it.connect(clNaviMap.id, ConstraintSet.RIGHT, clControlView.id, ConstraintSet.LEFT)
                it.connect(clNaviMap.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
                it.connect(clControlView.id, ConstraintSet.LEFT, clNaviMap.id, ConstraintSet.RIGHT)
                it.connect(clControlView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)

                it.applyTo(clMainLayout)
            }

            INaviController.setOrientation(Configuration.ORIENTATION_LANDSCAPE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if( requestCode == requestCodePermission ) {
            var denyCnt = 0
            for (i in permissions.indices) {
                val grantResult = grantResults[i]
                //val permission = permissions[i]

                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    denyCnt++
                }
            }

            if( denyCnt == 0 ) {
                initNaviSDK()
            }
            else {
                Toast.makeText(this, "권한이 필요합니다.", Toast.LENGTH_LONG).show()
            }

        }
    }

    //private fun s
    private fun checkPermisson() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var permissionsList: ArrayList<String> = arrayListOf()

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }

            if(permissionsList.size > 0 ) {
                requestPermissions(permissionsList.toTypedArray(), requestCodePermission)
            }
            else {
                initNaviSDK()
            }
        }
        else {
            initNaviSDK()
        }

    }
    private fun initNaviSDK() {

        //INaviController.forceHideMapComponent()


        INaviController.setEmulatorMode(true)
        //INaviController.setMapStyle(MAPSTYLE.MAPBOX)
        val streamType = AudioManager.STREAM_NOTIFICATION
        /** 하드웨어키에 대한 볼륨조정시 미디어볼륨이 되도록 설정*/
        volumeControlStream = streamType
        INaviController.setAudioStreamType(streamType)
        /**
         * Navi SDK 초기화
         * @param context Application 시작 Context
         * @param rootPath Application 시작 Path
         * @param uniqueID 유일 식별자 ( 휴대폰 번호 )
         * @param listener 결과 리턴 Callback
         */
        val phoneNumber = "UniqueID"
        INaviController.initalizeNavi(this, getRootPath(), phoneNumber, object : OnNaviInitListener {
            override fun onSuccess() {
                mVpPager.visibility = View.VISIBLE
                mIntroEx.visibility = View.GONE

                INaviController.setMapClickListener(mMapClickListener)
                INaviController.setNaviEventListener(mNaviEventListener)
                INaviController.setRouteChangedListener(mRouteChangedListener)
                INaviController.setDrivingStatusListener(mDrivingStatusListener, 300)
                INaviController.setMapMoveChangedListener(mMapMoveChangedListener)

                /**
                 * 단말기 미디어 볼륨과 별개로 내비에서만 사용하는 음성 조절
                 * @param vol 0.0 ~ 1.0
                 */
                INaviController.setNaviVolume(1.0f)
            }
            override fun onFail(errCode: Int, errMsg: String) {
                destroyApp()
                INaviController.destroyNavi()
                finish()
            }
        })
    }

    private var fragmentManager: FragmentManager? = null
    private var mapFragment: MapFragment? = null
    private var fragTransaction: FragmentTransaction?= null

    private fun initLayoutFragment() {
        mIntroEx.setOnClickListener { /* nothing */ }
        mVpPager.adapter = NaviViewPagerAdapter(this, mHandler)

        mapFragment = MapFragment(this)

        fragmentManager = supportFragmentManager
        fragTransaction = fragmentManager?.beginTransaction()
        fragTransaction?.replace(R.id.clNaviMap, mapFragment!!)?.commitAllowingStateLoss();

        //ex
        INaviController.setApplicatonStatus(APPSTATUS.FOREGROUND)


        mVpPager.visibility = View.VISIBLE
        mIntroEx.visibility = View.GONE
    }

    private fun initLayout() {

        mIntroEx.setOnClickListener { /* nothing */ }

        //OnCreate 에서 호출되어야 함
        INaviController.initLayout(this, R.id.mapAdapter, R.id.mapLayer)
        mVpPager.adapter = NaviViewPagerAdapter(this, mHandler)

        //ex
        INaviController.setApplicatonStatus(APPSTATUS.FOREGROUND)
    }

    private fun getRootPath(): String {
        var strStorageDir: String = ""
        val fRootPath = ContextCompat.getExternalFilesDirs(this, null)
        if (fRootPath.isNotEmpty() && fRootPath[0] != null)
            strStorageDir = fRootPath[0].absolutePath

        return strStorageDir
    }


    private fun destroyApp() {
        PageDataStore.overlayMap?.let {
            INaviController.removeMapIconALL(it)
            INaviController.removeMapOverlay(it)
        }

        PageDataStore.overlayRoute?.let {
            INaviController.removeMapIconALL(it)
            INaviController.removeMapOverlay(it)
        }
        PageDataStore.destroy()

    }

    /*
    private fun restartApp() {

        val dlgBuilder = AlertDialog.Builder(this)
        dlgBuilder.setTitle(getString(R.string.app_name)).setMessage("인증이 만료되어 재시작이 필요합니다.")

        dlgBuilder.setPositiveButton("자동 재시작") { _, _ ->
            val restartApp = Intent(this@MainActivity, MainActivity::class.java)
            val restartAppId = 1234567
            val restartPedingIntent = PendingIntent.getActivity(
                this@MainActivity,
                restartAppId,
                restartApp,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
            val mgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            mgr.set(
                AlarmManager.RTC,
                System.currentTimeMillis() + 2000,
                restartPedingIntent
            )

            destroyApp()
            INaviController.destroyNavi()

            killProcess(myPid())
        }

        dlgBuilder.create()?.show()

    }
    */

    private fun movePage(pageIdx: Int) {
        mVpPager.setCurrentItem(pageIdx, true)
        (mVpPager.adapter as NaviViewPagerAdapter).refreshRoutePage()
    }

    private fun toggleControlView() {

        mControlView.visibility = when(mControlView.visibility) {
            View.VISIBLE -> View.GONE
            else -> View.VISIBLE
        }
    }


    private val mHandler = object: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            //super.handleMessage(msg)
            msg ?: return
            val pageIdx = msg.what
            if(pageIdx in 0..3)
                movePage(msg.what)
        }
    }

    private val mMapClickListener = object : OnMapClickListener {
        override fun onMapLongClick(lat: Double, lon: Double) {


            if( PageDataStore.overlayRoute == null )
                PageDataStore.overlayRoute = INaviController.createMapOverlay()

            PageDataStore.overlayRoute?.let { overlay ->
                PageDataStore.mapIconGoal?.let { prevIcon ->
                    INaviController.removeMapIcon(overlay, prevIcon)
                }
            }
            var markerIcon = INaviController.createMapIcon(lat, lon, R.drawable.icon_sample_goal, ICONGRAVITY.CENTER_TOP)?: return

            PageDataStore.overlayRoute?.let { overlay ->
                PageDataStore.mapIconGoal = INaviController.addMapIcon(overlay, markerIcon)
            }

            var jibunAddr = INaviController.getJibunAddr(lat, lon)
            PageDataStore.goalPoint = RoutePtItem(jibunAddr, lat, lon, lat, lon )
            //PageDataStore.startPoint = RoutePtItem(jibunAddr, lat, lon, lat, lon )
        }
    }

    private val mNaviEventListener = object : OnNaviEventListener {
        override fun onEvent(e: NaviEvent) {
            when(e.eventType) {
                NAVIEVENTTYPE.NONE -> {
                    //nothing
                }
                NAVIEVENTTYPE.MAINMENU -> {
                    //하단 좌측 버튼 이벤트
                    Toast.makeText(this@MainActivity, "main menu event", Toast.LENGTH_LONG).show()
                }
                NAVIEVENTTYPE.MULTIMENU -> {
                    //하단 우측 버튼 이벤트
                    toggleControlView()
                    Toast.makeText(this@MainActivity, "multi menu event", Toast.LENGTH_LONG).show()
                }
                NAVIEVENTTYPE.EXITAPPLICATION -> {
                    //목적지 도착 하단바 "앱 종료" 이벤트
                    Toast.makeText(this@MainActivity, "exit application event", Toast.LENGTH_LONG).show()
                }
                NAVIEVENTTYPE.VOLUMEMAPBUTTON -> {
                    //지도 이동 "볼륨 버튼" 이벤트 
                    Toast.makeText(this@MainActivity, "volume map button event", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private val mRouteChangedListener = object : OnRouteChangedListener {
        override fun onChanged(routeID: String) {
        }
    }
    private val mDrivingStatusListener = object : OnDrivingStatusListener {
        override fun onStatus(status: DRIVINGSTATUS) {
        }
    }
    private val mMapMoveChangedListener = object : OnMapMoveChangedListener {
        override fun onStart() {
        }

        override fun onStop() {
        }

    }
}
