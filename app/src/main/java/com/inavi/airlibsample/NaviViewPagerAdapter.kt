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

/**
 * Created by J.W. Park on 2019-10-11
 */
class NaviViewPagerAdapter : PagerAdapter {


    private val mPageCount = 3
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
            else -> initTestPage()
        }?: return Any()

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        //super.destroyItem(container, position, obj)
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
        mPageRouteAdapter?.notifyDataSetChanged()
    }


//    private val mVpHandler = object: Handler(Looper.getMainLooper()) {
//        override fun handleMessage(msg: Message?) {
//            //super.handleMessage(msg)
//            msg ?: return
//            when(msg.what) {
//                0 -> {
//                    mPageRouteAdapter.notifyDataSetChanged()
//                }
//            }
//
//
//        }
//    }

}