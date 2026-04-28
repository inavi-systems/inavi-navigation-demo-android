package com.inavi.airlibsample

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.inavi.airlibsample.adapter.*

class NaviViewPagerAdapter : PagerAdapter {


    private val mPageCount = 4
    private var mContext: Context
    private var mHandler: Handler


    private lateinit var mPageRouteAdapter: PageRouteAdapter

    constructor(ctxt: Context, handler: Handler) {
        mContext = ctxt
        mHandler = handler
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        var view = when(position) {
            0 -> initMapPage()
            1 -> initSearchPage()
            2 -> initRoutePage()
            3 -> initTruckPage()
            else -> initTestPage()
        }?: return Any()

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }


    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == (obj as View)
    }

    override fun getCount(): Int {
        return mPageCount
    }


    private fun initMapPage(): View? {
        var view = LayoutInflater.from(mContext).inflate(R.layout.page_basic, null)?: return null

        var listItems = mutableListOf<BasicListItem>()
        listItems.add(BasicListItem(BasicListType.HEADER, FuncMap.NONE, PageTitle.MAP.value))
        listItems.add(BasicListItem(BasicListType.ITEM, FuncMap.CURRENTON, "지도 현위치 이동"))
        listItems.add(BasicListItem(BasicListType.ITEM, FuncMap.VIEWMODE, "지도 모드 변경"))

        listItems.add(BasicListItem(BasicListType.ITEM, FuncMap.FONTSIZE, "글자크기"))
        listItems.add(BasicListItem(BasicListType.ITEM, FuncMap.DAYNIGHT, "주/야간설정"))

        listItems.add(BasicListItem(BasicListType.ITEM, FuncMap.ZOOMIN,"지도 확대"))
        listItems.add(BasicListItem(BasicListType.ITEM, FuncMap.ZOOMOUT,"지도 축소"))
        listItems.add(BasicListItem(BasicListType.ITEM, FuncMap.ADDMAPICON,"지도 아이콘 추가"))
        listItems.add(BasicListItem(BasicListType.ITEM, FuncMap.REMOVEMAPICON,"지도 아이콘 제거"))
        listItems.add(BasicListItem(BasicListType.ITEM, FuncMap.TRAFFICON,"교통정보 라인 표출"))
        listItems.add(BasicListItem(BasicListType.ITEM, FuncMap.ENABLEROTATE,"지도 회전 세팅"))
        listItems.add(BasicListItem(BasicListType.ITEM, FuncMap.ROUTELINEPOINTS,"남은 좌표"))
        listItems.add(BasicListItem(BasicListType.ITEM, FuncMap.APTCOLLISIONIGNORE,"POI 충돌 허용"))

        val recyclerView: RecyclerView = view.findViewById(R.id.rvBoard)
        recyclerView.run {
            addItemDecoration(DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL))
            adapter = PageMapAdapter(listItems)
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        }

        return view
    }
    private fun initSearchPage(): View? {
        var view = LayoutInflater.from(mContext).inflate(R.layout.page_basic, null)?: return null

        var listItems = mutableListOf<SearchListItem>()
        listItems.add(SearchListItem(SearchListType.HEADER, PageTitle.SEARCH.value))
        listItems.add(SearchListItem(SearchListType.QUERY, ""))

        val recyclerView: RecyclerView = view.findViewById(R.id.rvBoard)
        recyclerView.run {
            addItemDecoration(DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL))
            adapter = PageSearchAdapter(listItems, mContext, mHandler)
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        }

        return view
    }

    private fun initRoutePage(): View? {
        var view = LayoutInflater.from(mContext).inflate(R.layout.page_basic, null)?: return null


        var listItems = mutableListOf<RouteListItem>()
        listItems.add(RouteListItem(RouteListType.HEADER, PageTitle.ROUTE.value))
        listItems.add(RouteListItem(RouteListType.ROUTE, ""))

        mPageRouteAdapter = PageRouteAdapter(listItems, mContext)
        val recyclerView: RecyclerView = view.findViewById(R.id.rvBoard)
        recyclerView.run {
            addItemDecoration(DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL))
            adapter = mPageRouteAdapter
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        }

        return view
    }

    private fun initTruckPage(): View? {
        var view = LayoutInflater.from(mContext).inflate(R.layout.page_basic, null)?: return null

        var listItems = mutableListOf<TruckListItem>()
        listItems.add(TruckListItem(TruckListType.HEADER, TruckFuncType.NONE, PageTitle.TRUCK.value))
        listItems.add(TruckListItem(TruckListType.EDITTEXT, TruckFuncType.HEIGHT, "높이 (cm)"))
        listItems.add(TruckListItem(TruckListType.EDITTEXT, TruckFuncType.WEIGHT, "중량 (kg)"))
        listItems.add(TruckListItem(TruckListType.EDITTEXT, TruckFuncType.UTURN, "유턴 회피 (차선)"))
        listItems.add(TruckListItem(TruckListType.EDITTEXT, TruckFuncType.NARROW, "좁은길 회피 (차선)"))
        listItems.add(TruckListItem(TruckListType.ITEM, TruckFuncType.RESTRICTION, "통행제한 구간 회피 여부"))
        listItems.add(TruckListItem(TruckListType.ITEM, TruckFuncType.GETTRUCKINFO, "유턴/좁은길 정보"))
        listItems.add(TruckListItem(TruckListType.ITEM, TruckFuncType.SETTRUCK, "트럭 설정 여부"))
        listItems.add(TruckListItem(TruckListType.ITEM, TruckFuncType.WARNING_HEIGHT, "높이제한 안내 표출 여부"))
        listItems.add(TruckListItem(TruckListType.EDITTEXT, TruckFuncType.TRUCK_TYPE, "자동차 타입"))
        listItems.add(TruckListItem(TruckListType.ITEM, TruckFuncType.SHOW_EXTEND_VIEW, "확대도 표출 여부"))
        listItems.add(TruckListItem(TruckListType.ITEM, TruckFuncType.CHANGE_TURN_VIEW_BG, "차기 BG : "))
        listItems.add(TruckListItem(TruckListType.ITEM, TruckFuncType.CHANGE_NEXT_TURN_VIEW_BG, "차차기 BG : "))
        listItems.add(TruckListItem(TruckListType.ITEM, TruckFuncType.CHANGE_ARRIVAL_VIEW_BG, "목적지 View BG : "))
        listItems.add(TruckListItem(TruckListType.ITEM, TruckFuncType.RESET_TURN_VIEW_BG, "TurnView BG 초기화"))
        listItems.add(TruckListItem(TruckListType.ITEM, TruckFuncType.EVASION_LIMIT, "위수 지역 회피 여부"))

        val recyclerView: RecyclerView = view.findViewById(R.id.rvBoard)
        recyclerView.run {
            addItemDecoration(DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL))
            adapter = PageTruckAdapter(listItems)
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        }

        return view
    }

    private fun initTestPage(): View? {
        var view = LayoutInflater.from(mContext).inflate(R.layout.page_basic, null)?: return null

        var listItems = mutableListOf<BasicListItem>()
        listItems.add(BasicListItem(BasicListType.HEADER, FuncMap.NONE, "Test..."))

        val recyclerView: RecyclerView = view.findViewById(R.id.rvBoard)
        recyclerView.run {
            addItemDecoration(DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL))
            adapter = PageMapAdapter(listItems)
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        }

        return view
    }


    fun refreshRoutePage() {
        mPageRouteAdapter.notifyDataSetChanged()
    }
}
