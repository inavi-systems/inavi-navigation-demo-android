package com.inavi.airlibsample

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Typeface
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.viewpager.widget.ViewPager
import com.inavi.airlibsample.adapter.PageTitle
import com.inavi.airlibsample.adapter.PageDataStore
import com.inavi.airlibsample.databinding.ActivityMainBinding
import com.inaviair.sdk.APPSTATUS
import com.inaviair.sdk.DRIVINGSTATUS
import com.inaviair.sdk.ICONGRAVITY
import com.inaviair.sdk.INaviController
import com.inaviair.sdk.MAPROUTECOLOR
import com.inaviair.sdk.MAPVIEWMODE
import com.inaviair.sdk.NAVIEVENTTYPE
import com.inaviair.sdk.NaviEvent
import com.inaviair.sdk.OnBringToFrontListener
import com.inaviair.sdk.OnCarSpeedListener
import com.inaviair.sdk.OnDrivingStatusListener
import com.inaviair.sdk.OnMapClickListener
import com.inaviair.sdk.OnMapMoveChangedListener
import com.inaviair.sdk.OnNaviEventListener
import com.inaviair.sdk.OnNaviInitListener
import com.inaviair.sdk.OnRouteChangedListener
import com.inaviair.sdk.RoutePtItem


class MainActivity : AppCompatActivity() {

    companion object {
        init {
            INaviController.loadNaviLibrary()
        }
    }

    private val REQUEST_CODE_TBT_PERMISSION = 100

    private val requestCodePermission = 100
    private var overlayPermissionRequested = false
    private val mIntroEx: ConstraintLayout by lazy { findViewById(R.id.clIntroEx) }
    private val mControlView: ConstraintLayout by lazy { findViewById(R.id.clControlView) }
    private val mVpPager: ViewPager by lazy { findViewById(R.id.vpPager) }
    private val clMainLayout: ConstraintLayout by lazy { findViewById(R.id.clMainLayout) }
    private val clNaviArea: ConstraintLayout by lazy { findViewById(R.id.clNaviArea) }
    private val clControlView: ConstraintLayout by lazy { findViewById(R.id.clControlView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout(binding)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermisson()
        } else {
            initNaviSDK()
        }

        initBackgroundTBT()
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyApp()
        INaviController.destroyNavi()
    }

    override fun onBackPressed() {

        toggleControlView()

    }

    private fun runOnMainThread(action: () -> Unit) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            action()
        } else {
            runOnUiThread {
                action()
            }
        }
    }

    private fun showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
        runOnMainThread {
            Toast.makeText(this, message, duration).show()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            ConstraintSet().let {
                it.clone(clMainLayout)

                it.connect(
                    clNaviArea.id,
                    ConstraintSet.RIGHT,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.RIGHT
                )
                it.connect(clNaviArea.id, ConstraintSet.BOTTOM, clControlView.id, ConstraintSet.TOP)
                it.connect(
                    clControlView.id,
                    ConstraintSet.LEFT,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.LEFT
                )
                it.connect(clControlView.id, ConstraintSet.TOP, clNaviArea.id, ConstraintSet.BOTTOM)

                it.applyTo(clMainLayout)
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ConstraintSet().let {
                it.clone(clMainLayout)

                it.connect(clNaviArea.id, ConstraintSet.RIGHT, clControlView.id, ConstraintSet.LEFT)
                it.connect(
                    clNaviArea.id,
                    ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM
                )
                it.connect(clControlView.id, ConstraintSet.LEFT, clNaviArea.id, ConstraintSet.RIGHT)
                it.connect(
                    clControlView.id,
                    ConstraintSet.TOP,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.TOP
                )

                it.applyTo(clMainLayout)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodePermission) {
            var denyCnt = 0
            for (i in permissions.indices) {
                val grantResult = grantResults[i]
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    denyCnt++
                }
            }

            if (denyCnt == 0) {
                initNaviSDK()
            } else {
                showToast("권한이 필요합니다.")
            }

        }
    }

    override fun onResume() {
        super.onResume()
        INaviController.setApplicatonStatus(APPSTATUS.FOREGROUND)
        INaviController.setDrawBackgroundTBT(Settings.canDrawOverlays(applicationContext))
    }

    override fun onStop() {
        super.onStop()
        INaviController.setApplicatonStatus(APPSTATUS.BACKGROUND)
    }

    private fun initBackgroundTBT(){
        INaviController.setDrawBackgroundTBT(Settings.canDrawOverlays(applicationContext))

        INaviController.setBringToFrontListener(object : OnBringToFrontListener {
            override fun getBringToFrontPendingIntent(context: Context?): PendingIntent? {
                val intent = Intent(context, MainActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)

                var flags = PendingIntent.FLAG_UPDATE_CURRENT
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    flags = flags or PendingIntent.FLAG_IMMUTABLE
                }

                return PendingIntent.getActivity(context, 0, intent, flags)
            }
        })

        requestOverlayPermissionIfNeeded()
    }

    private fun requestOverlayPermissionIfNeeded() {
        if (Settings.canDrawOverlays(applicationContext) || overlayPermissionRequested) {
            return
        }

        overlayPermissionRequested = true
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            "package:$packageName".toUri()
        )
        startActivityForResult(intent, REQUEST_CODE_TBT_PERMISSION)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermisson() {

        val permissionsList: ArrayList<String> = arrayListOf()

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }

        if (permissionsList.size > 0) {
            requestPermissions(permissionsList.toTypedArray(), requestCodePermission)
        } else {
            initNaviSDK()
        }
    }

    private fun initNaviSDK() {

        INaviController.setEmulatorMode(true)
        INaviController.setTurnViewTaxiIcon(false)

        val streamType = AudioManager.STREAM_MUSIC
        volumeControlStream = streamType
        INaviController.setAudioStreamType(streamType)

        val uniqueID = "UniqueID"
        INaviController.initalizeNavi(
            this,
            getRootPath(),
            uniqueID,
            object : OnNaviInitListener {
                override fun onSuccess() {
                    mVpPager.visibility = View.VISIBLE
                    mIntroEx.visibility = View.GONE
                    INaviController.setMapRouteColor(MAPROUTECOLOR.GREEN)

                    INaviController.setMapClickListener(mMapClickListener)
                    INaviController.setNaviEventListener(mNaviEventListener)
                    INaviController.setRouteChangedListener(mRouteChangedListener)
                    INaviController.setDrivingStatusListener(mDrivingStatusListener, 200)
                    INaviController.setMapMoveChangedListener(mMapMoveChangedListener)
                    INaviController.setCarSpeedListener(mCarSpeedListener)

                    INaviController.setNaviVolume(1.0f)
                    INaviController.setMapViewMode(MAPVIEWMODE.VIEWMODE_2D)
                }

                override fun onFail(errCode: Int, errMsg: String) {
                    showToast("SDK init failed: $errCode")
                    destroyApp()
                    INaviController.destroyNavi()
                    finish()
                }
            })
    }

    private fun initLayout(binding: ActivityMainBinding) {
        mIntroEx.setOnClickListener {}

        INaviController.initLayout(
            this,
            binding.root,
            binding.mapAdapter.id,
            binding.mapLayer.id
        )

        mVpPager.adapter = NaviViewPagerAdapter(this, mHandler)
        initPageTitleBar(binding)
        INaviController.setApplicatonStatus(APPSTATUS.FOREGROUND)
    }

    private fun initPageTitleBar(binding: ActivityMainBinding) {
        val titleViews = listOf(
            binding.tvMapPageTitle,
            binding.tvSearchPageTitle,
            binding.tvRoutePageTitle,
            binding.tvTruckPageTitle
        )
        val titles = listOf(
            PageTitle.MAP.value,
            PageTitle.SEARCH.value,
            PageTitle.ROUTE.value,
            PageTitle.TRUCK.value
        )

        titleViews.forEachIndexed { index, textView ->
            textView.text = titles[index]
            textView.setOnClickListener {
                mVpPager.setCurrentItem(index, true)
            }
        }

        mVpPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                updatePageTitleBar(titleViews, position)
            }
        })

        updatePageTitleBar(titleViews, mVpPager.currentItem)
    }

    private fun updatePageTitleBar(titleViews: List<TextView>, selectedPosition: Int) {
        titleViews.forEachIndexed { index, textView ->
            val selected = index == selectedPosition
            textView.setTypeface(null, if (selected) Typeface.BOLD else Typeface.NORMAL)
            textView.setTextColor(
                ContextCompat.getColor(this, if (selected) R.color.black01 else R.color.gray03)
            )
            textView.setBackgroundColor(
                ContextCompat.getColor(this, if (selected) R.color.gray01 else R.color.white01)
            )
        }
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


    private fun movePage(pageIdx: Int) {
        mVpPager.setCurrentItem(pageIdx, true)
        (mVpPager.adapter as NaviViewPagerAdapter).refreshRoutePage()
    }

    private fun toggleControlView() {
        mControlView.visibility = when (mControlView.visibility) {
            View.VISIBLE -> View.GONE
            else -> View.VISIBLE
        }
    }

    private val mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val pageIdx = msg.what
            if (pageIdx in 0..3)
                movePage(msg.what)
        }
    }

    private fun addViaPoint(lat: Double, lon: Double) {
        var markerIcon = INaviController.createMapIcon(
            lat,
            lon,
            R.drawable.icon_sample_normal,
            ICONGRAVITY.CENTER_TOP
        ) ?: return

        PageDataStore.overlayRoute?.let { overlay ->
            INaviController.addMapIcon(overlay, markerIcon)
        }

        var jibunAddr = INaviController.getJibunAddr(lat, lon) + " " + PageDataStore.viaPoints.size

        val viaPoint = RoutePtItem(jibunAddr, lat, lon, lat, lon, 0)
        PageDataStore.viaPoints.add(viaPoint)
    }

    private fun addStartPoint(lat: Double, lon: Double) {
        var markerIcon = INaviController.createMapIcon(
            lat,
            lon,
            R.drawable.icon_sample_start,
            ICONGRAVITY.CENTER_TOP
        ) ?: return

        PageDataStore.overlayRoute?.let { overlay ->
            INaviController.addMapIcon(overlay, markerIcon)
        }

        var jibunAddr = INaviController.getJibunAddr(lat, lon)

        val startPoint = RoutePtItem(jibunAddr, lat, lon, lat, lon, 0)
        PageDataStore.startPoint = startPoint
    }

    private val mMapClickListener = object : OnMapClickListener {
        override fun onMapLongClick(lat: Double, lon: Double) {

            if (PageDataStore.overlayRoute == null)
                PageDataStore.overlayRoute = INaviController.createMapOverlay()

            PageDataStore.overlayRoute?.let {
                if (PageDataStore.startPoint.name.isEmpty()) {
                    addStartPoint(lat, lon)
                    return
                }
                PageDataStore.mapIconGoal?.let {
                    addViaPoint(lat, lon)
                    return
                }
            }
            var markerIcon = INaviController.createMapIcon(
                lat,
                lon,
                R.drawable.icon_sample_goal,
                ICONGRAVITY.CENTER_TOP
            ) ?: return

            PageDataStore.overlayRoute?.let { overlay ->
                PageDataStore.mapIconGoal = INaviController.addMapIcon(overlay, markerIcon)
            }

            var jibunAddr = INaviController.getJibunAddr(lat, lon)

            PageDataStore.goalPoint = RoutePtItem(jibunAddr, lat, lon, lat, lon, 0)

            var fullAddr = INaviController.getFullAddr(lat, lon, true)

            showToast("fullAddr : $fullAddr")
        }
    }

    private val mNaviEventListener = object : OnNaviEventListener {
        override fun onEvent(e: NaviEvent) {
            when (e.eventType) {
                NAVIEVENTTYPE.NONE -> {
                }

                NAVIEVENTTYPE.MAINMENU -> {
                    showToast("main menu event")
                }

                NAVIEVENTTYPE.MULTIMENU -> {
                    runOnMainThread {
                        showToast("multi menu event")
                        INaviController.drivingRouteZoomMap()
                    }
                }

                NAVIEVENTTYPE.EXITAPPLICATION -> {
                    showToast("exit application event")
                }

                NAVIEVENTTYPE.VOLUMEMAPBUTTON -> {
                    showToast("volume map button event")
                }

                NAVIEVENTTYPE.CURRRENTPOSITION -> {
                    showToast("current position event")
                }

                NAVIEVENTTYPE.ALLROUTEVIEW -> {
                    showToast("all route view event")
                }
            }
        }

    }

    private val mRouteChangedListener = object : OnRouteChangedListener {
        override fun onChanged(routeID: String) {
            PageDataStore.routeResult = arrayListOf(routeID)
            PageDataStore.selectedRCIndex = 0
        }
    }

    private val mDrivingStatusListener = object : OnDrivingStatusListener {
        override fun onStatus(status: DRIVINGSTATUS) {
        }

        override fun onArrivalFee(fee: Int) {
        }

        override fun onArrivalVia(nIndex: Int) {
        }
    }

    private val mMapMoveChangedListener = object : OnMapMoveChangedListener {
        override fun onStart() {
        }

        override fun onStop() {
        }

    }


    private val mCarSpeedListener = object : OnCarSpeedListener {
        override fun onSpeed(speed: Int) {
        }

    }
}
