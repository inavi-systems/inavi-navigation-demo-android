package com.inavi.airlibsample.adapter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.inavi.airlibsample.R
import com.inaviair.sdk.*

enum class RouteCode(val value: Int) {
    INIT(0),
    ROUTESUM(1),
    GUIDE(2)
}

abstract class RouteListItemHolder(view: View): RecyclerView.ViewHolder(view)
class RouteListItemHolderHeader(view: LinearLayout): RouteListItemHolder(view) {
    val tvTitle: TextView by lazy{
        view.findViewById(R.id.tvRowTitle)
    }
}
class RouteListItemHolderRoute(view: ConstraintLayout): RouteListItemHolder(view) {
    val tvStartName: TextView by lazy{
        view.findViewById(R.id.tvStartName)
    }
    val tvGoalName: TextView by lazy{
        view.findViewById(R.id.tvGoalName)
    }
    val btnRoute: Button by lazy{
        view.findViewById(R.id.btnRoute)
    }
}

class RouteListItemHolderItem(view: ConstraintLayout): RouteListItemHolder(view) {
    val clRouteInfo1: ConstraintLayout by lazy{
        view.findViewById(R.id.clRouteInfo1)
    }
    val tvOptionName1: TextView by lazy{
        view.findViewById(R.id.tvOptionName1)
    }
    val tvDist1: TextView by lazy{
        view.findViewById(R.id.tvDist1)
    }
    val tvTime1: TextView by lazy{
        view.findViewById(R.id.tvTime1)
    }
    val tvFee1: TextView by lazy{
        view.findViewById(R.id.tvFee1)
    }

    val clRouteInfo2: ConstraintLayout by lazy{
        view.findViewById(R.id.clRouteInfo2)
    }
    val tvOptionName2: TextView by lazy{
        view.findViewById(R.id.tvOptionName2)
    }
    val tvDist2: TextView by lazy{
        view.findViewById(R.id.tvDist2)
    }
    val tvTime2: TextView by lazy{
        view.findViewById(R.id.tvTime2)
    }
    val tvFee2: TextView by lazy{
        view.findViewById(R.id.tvFee2)
    }

    val clRouteInfo3: ConstraintLayout by lazy{
        view.findViewById(R.id.clRouteInfo3)
    }
    val tvOptionName3: TextView by lazy{
        view.findViewById(R.id.tvOptionName3)
    }
    val tvDist3: TextView by lazy{
        view.findViewById(R.id.tvDist3)
    }
    val tvTime3: TextView by lazy{
        view.findViewById(R.id.tvTime3)
    }
    val tvFee3: TextView by lazy{
        view.findViewById(R.id.tvFee3)
    }
}
class RouteListItemHolderGuide(view: ConstraintLayout): RouteListItemHolder(view) {
    val btnCancel: Button by lazy{
        view.findViewById(R.id.btnLeft)
    }
    val btnSimul: Button by lazy{
        view.findViewById(R.id.btnCenter)
    }
    val btnGuide: Button by lazy{
        view.findViewById(R.id.btnRight)
    }

}
class RouteListItemHolderCancel(view: ConstraintLayout): RouteListItemHolder(view) {
    val btnCancel: Button by lazy{
        view.findViewById(R.id.btnLeft)
    }
    val btnCenter: Button by lazy{
        view.findViewById(R.id.btnCenter)
    }
    val btnRight: Button by lazy{
        view.findViewById(R.id.btnRight)
    }
}


class PageRouteAdapter(private var list: List<RouteListItem>, private var context: Context) : RecyclerView.Adapter<RouteListItemHolder>() {


    private var mRouteIng = false
    override fun getItemCount(): Int {
        return list.size
    }
    override fun getItemViewType(position: Int): Int {
        val item = list.getOrNull(position)?: return RouteListType.NONE.value
        return item.listType.value
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteListItemHolder {
        return when(viewType){
            RouteListType.HEADER.value -> {
                val cell = LayoutInflater.from(parent.context).inflate(R.layout.row_header, parent, false) as LinearLayout
                RouteListItemHolderHeader(cell)
            }
            RouteListType.ROUTE.value -> {
                val cell = LayoutInflater.from(parent.context).inflate(R.layout.row_contents_route, parent, false) as ConstraintLayout
                RouteListItemHolderRoute(cell)
            }
            RouteListType.RESULT.value -> {
                val cell = LayoutInflater.from(parent.context).inflate(R.layout.row_contents_routeinfo, parent, false) as ConstraintLayout
                RouteListItemHolderItem(cell)
            }
            RouteListType.GUIDANCE.value -> {
                val cell = LayoutInflater.from(parent.context).inflate(R.layout.row_contents_multi_button, parent, false) as ConstraintLayout
                RouteListItemHolderGuide(cell)
            }
            RouteListType.ROUTECANCEL.value -> {
                val cell = LayoutInflater.from(parent.context).inflate(R.layout.row_contents_multi_button, parent, false) as ConstraintLayout
                RouteListItemHolderCancel(cell)
            }

            else -> {
                val cell = LayoutInflater.from(parent.context).inflate(R.layout.row_header, parent, false) as LinearLayout
                RouteListItemHolderHeader(cell)
            }
        }
    }
    override fun onBindViewHolder(holder: RouteListItemHolder, position: Int) {
        when (getItemViewType(position)) {
            RouteListType.HEADER.value -> {
                if (holder !is RouteListItemHolderHeader) return

                val item = list.getOrNull(position) ?: return
                holder.tvTitle.text = item.mainText
            }

            RouteListType.ROUTE.value -> {

                if (holder !is RouteListItemHolderRoute) return

                holder.tvStartName.text = "출발지 : ${PageDataStore.startPoint.name}"
                holder.tvGoalName.text = "목적지 : ${PageDataStore.goalPoint.name}"


                holder.btnRoute.setOnClickListener {
                    if( mRouteIng ) return@setOnClickListener

                    mRouteIng = true
                    holder.tvStartName.text = "경로 탐색 진행 중입니다."
                    holder.tvGoalName.text = "경로 탐색 진행 중입니다."


                    PageDataStore.startPoint.angle = 0

                    var viaList = PageDataStore.viaPoints
                    val optionType = mutableListOf<ROUTEOPTIONTYPE>()
                    optionType.add(ROUTEOPTIONTYPE.TRUCK)
                    optionType.add(ROUTEOPTIONTYPE.TRUCK2)
                    INaviController.runRoute(PageDataStore.startPoint, PageDataStore.goalPoint, viaList, optionType,null, object : OnRouteMultiListener {
                        override fun onSuccess(result: ArrayList<String>, same: Boolean) {
                            result.let {
                                INaviController.routeZoomMap(it, it[0])

                                PageDataStore.startPoint.name = INaviController.getRoutePtInfo(it[0], ROUTEPTTYPE.START)?.name?: ""
                                PageDataStore.startPoint.dpLon = INaviController.getRoutePtInfo(it[0], ROUTEPTTYPE.START)?.rpLon?: 0.0
                                PageDataStore.startPoint.dpLat = INaviController.getRoutePtInfo(it[0], ROUTEPTTYPE.START)?.rpLat?: 0.0
                                PageDataStore.goalPoint.name = INaviController.getRoutePtInfo(it[0], ROUTEPTTYPE.GOAL)?.name?: ""
                            }

                            PageDataStore.routeResult = result
                            mHandler.sendEmptyMessage(RouteCode.ROUTESUM.value)
                            mRouteIng = false

                            if( PageDataStore.overlayRoute == null )
                                PageDataStore.overlayRoute = INaviController.createMapOverlay()

                            PageDataStore.overlayRoute?.let { overlay ->
                                INaviController.removeMapIconALL(overlay)

                                var markerGoalIcon = INaviController.createMapIcon(
                                    PageDataStore.startPoint.dpLat,
                                    PageDataStore.startPoint.dpLon,
                                    R.drawable.icon_sample_start,
                                    ICONGRAVITY.CENTER_TOP
                                )?: return

                                INaviController.addMapIcon(overlay, markerGoalIcon)

                            }
                        }
                        override fun onFail(errCode: Int, msg: String) {
                            holder.tvStartName.text = "탐색 실패 (code : $errCode)"
                            holder.tvGoalName.text = msg

                            PageDataStore.routeResult = null
                            mRouteIng = false
                        }
                    })
                }
            }
            RouteListType.RESULT.value -> {
                if (holder !is RouteListItemHolderItem) return

                val item = list.getOrNull(position) ?: return

                if( item.optName1.isNullOrEmpty() )
                {
                    holder.clRouteInfo1.visibility = View.INVISIBLE
                }
                else {
                    holder.clRouteInfo1.visibility = View.VISIBLE
                    holder.clRouteInfo1.isSelected = true
                    PageDataStore.selectedRCIndex = 0

                    holder.tvOptionName1.text = item.optName1
                    holder.tvDist1.text = item.dist1
                    holder.tvTime1.text = item.time1
                    holder.tvFee1.text = item.fee1

                    holder.clRouteInfo1.setOnClickListener {
                        holder.clRouteInfo1.isSelected = true
                        holder.clRouteInfo2.isSelected = false
                        holder.clRouteInfo3.isSelected = false
                        PageDataStore.selectedRCIndex = 0
                        INaviController.routeZoomMap(PageDataStore.routeResult, PageDataStore.getSelectedRID()?:"")
                    }
                }

                if( item.optName2.isNullOrEmpty() )
                {
                    holder.clRouteInfo2.visibility = View.INVISIBLE
                }
                else {
                    holder.clRouteInfo2.visibility = View.VISIBLE
                    holder.clRouteInfo2.isSelected = false
                    PageDataStore.selectedRCIndex = 0

                    holder.tvOptionName2.text = item.optName2
                    holder.tvDist2.text = item.dist2
                    holder.tvTime2.text = item.time2
                    holder.tvFee2.text = item.fee2

                    holder.clRouteInfo2.setOnClickListener {
                        holder.clRouteInfo1.isSelected = false
                        holder.clRouteInfo2.isSelected = true
                        holder.clRouteInfo3.isSelected = false
                        PageDataStore.selectedRCIndex = 1
                        INaviController.routeZoomMapWithPadding(PageDataStore.routeResult, PageDataStore.getSelectedRID()?:"",
                            android.graphics.RectF(0f,0f,0f,60f))
                    }
                }

                if( item.optName3.isNullOrEmpty() )
                {
                    holder.clRouteInfo3.visibility = View.INVISIBLE
                }
                else {
                    holder.clRouteInfo3.visibility = View.VISIBLE
                    holder.clRouteInfo3.isSelected = false
                    PageDataStore.selectedRCIndex = 0

                    holder.tvOptionName3.text = item.optName3
                    holder.tvDist3.text = item.dist3
                    holder.tvTime3.text = item.time3
                    holder.tvFee3.text = item.fee3

                    holder.clRouteInfo3.setOnClickListener {
                        holder.clRouteInfo1.isSelected = false
                        holder.clRouteInfo2.isSelected = false
                        holder.clRouteInfo3.isSelected = true
                        PageDataStore.selectedRCIndex = 2
                        INaviController.routeZoomMap(PageDataStore.routeResult, PageDataStore.getSelectedRID()?:"")
                    }
                }
            }

            RouteListType.GUIDANCE.value -> {
                if (holder !is RouteListItemHolderGuide) return

                holder.btnCancel.text = "경로취소"
                holder.btnCancel.setOnClickListener {
                    INaviController.cancelRoute()
                    mHandler.sendEmptyMessage(RouteCode.INIT.value)
                }

                holder.btnSimul.text = "모의주행"
                holder.btnSimul.setOnClickListener {
                    PageDataStore.getSelectedRID()?.let {
                        INaviController.startSimulation(it)
                    }
                    mHandler.sendEmptyMessage(RouteCode.GUIDE.value)
                }


                holder.btnGuide.text = "안내시작"
                holder.btnGuide.setOnClickListener {

                    PageDataStore.getSelectedRID()?.let {
                        INaviController.runGuidance(it)
                    }
                    mHandler.sendEmptyMessage(RouteCode.GUIDE.value)
                }
            }

            RouteListType.ROUTECANCEL.value -> {
                if (holder !is RouteListItemHolderCancel) return

                var isSimul = INaviController.isSimulation()

                holder.btnCancel.text = if(isSimul) "모의주행종료" else "경로취소"
                holder.btnCancel.setOnClickListener {
                    if(isSimul)
                        INaviController.finishSimulation()

                    INaviController.cancelRoute()

                    mHandler.sendEmptyMessage(RouteCode.INIT.value)

                }

                holder.btnCenter.visibility = View.GONE
                holder.btnRight.visibility = View.GONE

            }
            else -> {
                if (holder !is RouteListItemHolderHeader) return
                holder.tvTitle.visibility = View.GONE
            }
        }
    }

    fun updateList(temp: List<RouteListItem>){
        list = temp
        notifyDataSetChanged()
    }

    private val mHandler = object: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                RouteCode.INIT.value -> {
                    var listItems = mutableListOf<RouteListItem>()
                    listItems.add(RouteListItem(RouteListType.HEADER, PageTitle.ROUTE.value))
                    listItems.add(RouteListItem(RouteListType.ROUTE, ""))
                    updateList(listItems)
                    PageDataStore.overlayRoute?.let { overlay ->
                        INaviController.removeMapIconALL(overlay)
                    }
                }

                RouteCode.ROUTESUM.value -> {

                    var ridList = PageDataStore.routeResult ?: return
                    var rCoreInfo = ridList.map {
                        INaviController.makeRouteSumInfo(it)
                    }.toTypedArray()

                    var listItems = mutableListOf<RouteListItem>()
                    listItems.add(RouteListItem(RouteListType.HEADER, PageTitle.ROUTE.value))
                    listItems.add(RouteListItem(RouteListType.ROUTE, ""))

                    var routeCount = rCoreInfo.size
                    var addItem = when(routeCount) {
                        1 -> {
                            RouteListItem(RouteListType.RESULT, "",
                                rCoreInfo[0]?.optionName?:"", rCoreInfo[0]?.dist?:"", rCoreInfo[0]?.time?:"", rCoreInfo[0]?.fee?:""
                            )
                        }
                        2 -> {
                            RouteListItem(RouteListType.RESULT, "",
                                rCoreInfo[0]?.optionName?:"", rCoreInfo[0]?.dist?:"", rCoreInfo[0]?.time?:"", rCoreInfo[0]?.fee?:"",
                                rCoreInfo[1]?.optionName?:"", rCoreInfo[1]?.dist?:"", rCoreInfo[1]?.time?:"", rCoreInfo[1]?.fee?:""
                            )
                        }
                        3 -> {
                            RouteListItem(RouteListType.RESULT, "",
                                rCoreInfo[0]?.optionName?:"", rCoreInfo[0]?.dist?:"", rCoreInfo[0]?.time?:"", rCoreInfo[0]?.fee?:"",
                                rCoreInfo[1]?.optionName?:"", rCoreInfo[1]?.dist?:"", rCoreInfo[1]?.time?:"", rCoreInfo[1]?.fee?:"",
                                rCoreInfo[2]?.optionName?:"", rCoreInfo[2]?.dist?:"", rCoreInfo[2]?.time?:"", rCoreInfo[2]?.fee?:""
                            )
                        }
                        else -> {
                            RouteListItem(RouteListType.RESULT)
                        }
                    }

                    listItems.add(addItem)

                    listItems.add(RouteListItem(RouteListType.GUIDANCE))

                    updateList(listItems)

                }

                RouteCode.GUIDE.value -> {
                    var listItems = mutableListOf<RouteListItem>()
                    listItems.add(RouteListItem(RouteListType.HEADER, PageTitle.ROUTE.value))
                    listItems.add(RouteListItem(RouteListType.ROUTECANCEL, ""))
                    updateList(listItems)
                }
            }
        }
    }
}
