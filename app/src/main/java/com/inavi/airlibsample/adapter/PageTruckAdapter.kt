package com.inavi.airlibsample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.inavi.airlibsample.R
import com.inaviair.sdk.*
import java.util.*

enum class TruckFuncType(val value: Int) {
    NONE(0),
    HEIGHT(1),
    WEIGHT(2),
    UTURN(3),
    NARROW(4),
    RESTRICTION(5),
    GETTRUCKINFO(6),
    SETTRUCK(7),
    WARNING_HEIGHT(8),
    TRUCK_TYPE(9),
    SHOW_EXTEND_VIEW(10),
    CHANGE_TURN_VIEW_BG(11),
    CHANGE_NEXT_TURN_VIEW_BG(12),
    CHANGE_ARRIVAL_VIEW_BG(13),
    RESET_TURN_VIEW_BG(14),
    EVASION_LIMIT(15)
}

abstract class TruckListItemHolder(view: View): RecyclerView.ViewHolder(view)
class TruckListItemHolderHeader(view: LinearLayout): TruckListItemHolder(view) {
    var tvTitle: TextView = view.findViewById(R.id.tvRowTitle)
}
class TruckListItemHolderItem(view: ConstraintLayout): TruckListItemHolder(view) {
    var clRowMain: ConstraintLayout = view.findViewById(R.id.clRowMain)
    var tvContents: TextView = view.findViewById(R.id.tvContents)
}
class TruckListItemHolderQuery(view: ConstraintLayout): TruckListItemHolder(view) {
    var tvTitle: TextView = view.findViewById(R.id.tv_title)
    var etInput: EditText = view.findViewById(R.id.etInput)
    var btnSubmit: Button = view.findViewById(R.id.btnSubmit)
}

class PageTruckAdapter(private var list: List<TruckListItem>) : RecyclerView.Adapter<TruckListItemHolder>() {

    override fun getItemCount(): Int {
        return list.size
    }
    override fun getItemViewType(position: Int): Int {
        val item = list.getOrNull(position)?: return TruckListType.NONE.value
        return item.listType.value
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TruckListItemHolder {

        return when(viewType){
            TruckListType.HEADER.value -> {
                val cell = LayoutInflater.from(parent.context).inflate(R.layout.row_header, parent, false) as LinearLayout
                TruckListItemHolderHeader(cell)
            }
            TruckListType.ITEM.value -> {
                val cell = LayoutInflater.from(parent.context).inflate(R.layout.row_contents_basic, parent, false) as ConstraintLayout
                TruckListItemHolderItem(cell)
            }
            TruckListType.EDITTEXT.value -> {
                val cell = LayoutInflater.from(parent.context).inflate(R.layout.row_contents_edittext, parent, false) as ConstraintLayout
                TruckListItemHolderQuery(cell)
            }
            else -> {
                val cell = LayoutInflater.from(parent.context).inflate(R.layout.row_header, parent, false) as LinearLayout
                TruckListItemHolderHeader(cell)
            }
        }
    }
    override fun onBindViewHolder(holder: TruckListItemHolder, position: Int) {
        when (getItemViewType(position)) {
            TruckListType.HEADER.value -> {
                if (holder !is TruckListItemHolderHeader) return

                val item = list.getOrNull(position) ?: return
                holder.tvTitle.text = item.mainText
            }

            TruckListType.ITEM.value -> {
                if (holder !is TruckListItemHolderItem) return

                val item = list.getOrNull(position) ?: return

                when(item.funcType){
                    TruckFuncType.EVASION_LIMIT->{
                        holder.tvContents.text = item.mainText.plus(" ${INaviController.getAvoidEvasion()}")

                        holder.clRowMain.setOnClickListener {
                            val bIsAvoid  = !INaviController.getAvoidEvasion()
                            processFunc(item.funcType, bIsAvoid.toString())
                            holder.tvContents.text = "위수지역 회피 : $bIsAvoid"
                        }
                    }
                    TruckFuncType.RESTRICTION ->{
                        holder.tvContents.text = item.mainText.plus(" ${INaviController.getAvoidRestriction()}")

                        holder.clRowMain.setOnClickListener {
                            val bIsAvoid  = !INaviController.getAvoidRestriction()
                            processFunc(item.funcType, bIsAvoid.toString())
                            holder.tvContents.text = "통행제한구간 회피 : $bIsAvoid"
                        }
                    }
                    TruckFuncType.GETTRUCKINFO ->{

                        holder.tvContents.text = item.mainText.plus(" ${INaviController.getTruckInfos(PageDataStore.routeResult?.get(0) ?: "").size}")

                        holder.clRowMain.setOnClickListener {
                            holder.tvContents.text = item.mainText.plus(" ${INaviController.getTruckInfos(PageDataStore.routeResult?.get(0) ?: "").size}")
                        }
                    }

                    TruckFuncType.SETTRUCK ->{
                        holder.tvContents.text = item.mainText.plus(" ${INaviController.getTruck()}")

                        holder.clRowMain.setOnClickListener {
                            val bIsTruck  = !INaviController.getTruck()
                            processFunc(item.funcType, bIsTruck.toString())
                            holder.tvContents.text = "트럭 여부 : $bIsTruck"
                        }
                    }
                    TruckFuncType.WARNING_HEIGHT->{

                        var settings = INaviController.getGuidanceSettings().toMutableList()

                        holder.tvContents.text = item.mainText.plus(" ${settings.contains(GUIDANCESETTING.HEIGHT_LIMIT)}")

                        holder.clRowMain.setOnClickListener {

                            processFunc(item.funcType,"")

                            var bIsWarnHeight = INaviController.getGuidanceSettings().toMutableList().contains(GUIDANCESETTING.HEIGHT_LIMIT)

                            holder.tvContents.text = "높이제한 표출 여부 : $bIsWarnHeight"
                        }
                    }
                    TruckFuncType.SHOW_EXTEND_VIEW->{

                        var bIsShowExtendView = INaviController.getVisibleExtendView()

                        holder.tvContents.text = item.mainText.plus(" : $bIsShowExtendView")

                        holder.clRowMain.setOnClickListener {

                            processFunc(item.funcType,"")

                            bIsShowExtendView = INaviController.getVisibleExtendView()

                            holder.tvContents.text = "확대도 표출 여부 : $bIsShowExtendView"
                        }
                    }
                    TruckFuncType.CHANGE_TURN_VIEW_BG ->{
                        var currentColor = INaviController.getTurnViewBackgroundColor()

                        holder.tvContents.text = item.mainText.plus(" : $currentColor")

                        holder.clRowMain.setOnClickListener {
                            val obj = Random()
                            val nRandom: Int = obj.nextInt(0xffffff + 1)
                            val colorCode = String.format("#%06x", nRandom)
                            INaviController.resetTurnViewsBackgroundColor()
                            INaviController.setTurnViewBackgroundColor(colorCode)

                            holder.tvContents.text = "차기 BG : $colorCode"
                        }
                    }
                    TruckFuncType.CHANGE_NEXT_TURN_VIEW_BG ->{
                        var currentColor = INaviController.getNextTurnViewBackgroundColor()

                        holder.tvContents.text = item.mainText.plus(" : $currentColor")

                        holder.clRowMain.setOnClickListener {
                            val obj = Random()
                            val nRandom: Int = obj.nextInt(0xffffff + 1)
                            val colorCode = String.format("#%06x", nRandom)
                            INaviController.setNextTurnViewBackgroundColor(colorCode)

                            holder.tvContents.text = "차차기 BG : $colorCode"
                        }
                    }
                    TruckFuncType.CHANGE_ARRIVAL_VIEW_BG ->{
                        var currentColor = INaviController.getArrivalViewBackgroundColor()

                        holder.tvContents.text = item.mainText.plus(" : $currentColor")

                        holder.clRowMain.setOnClickListener {
                            val obj = Random()
                            val nRandom: Int = obj.nextInt(0xffffff + 1)
                            val colorCode = String.format("#%06x", nRandom)
                            INaviController.setArrivalViewBackgroundColor(colorCode)

                            holder.tvContents.text = "목적지 View BG : $colorCode"
                        }
                    }

                    TruckFuncType.RESET_TURN_VIEW_BG -> {
                        holder.tvContents.text = item.mainText
                        holder.clRowMain.setOnClickListener {
                            INaviController.resetTurnViewsBackgroundColor()
                            notifyDataSetChanged()
                        }
                    }
                    else ->{}
                }
            }

            TruckListType.EDITTEXT.value -> {
                if (holder !is TruckListItemHolderQuery) return

                val item = list.getOrNull(position) ?: return
                var strTxt = item.mainText

                when(item.funcType){
                    TruckFuncType.HEIGHT -> {
                        strTxt = strTxt.plus(" : ${INaviController.getCarHeight()}")
                    }
                    TruckFuncType.WEIGHT ->{
                        strTxt = strTxt.plus(" : ${INaviController.getCarWeight()}")
                    }
                    TruckFuncType.UTURN ->{
                        strTxt = strTxt.plus(" :  ${INaviController.getAvoidUturn()}")
                    }
                    TruckFuncType.NARROW ->{
                        strTxt = strTxt.plus(" :  ${INaviController.getAvoidNarrowRoad()}")
                    }
                    TruckFuncType.TRUCK_TYPE ->{
                        val curType = INaviController.getCarType()
                        strTxt = strTxt.plus(" : ${CARTYPE.getTitle(curType)}(${CARTYPE.getDescription(curType)})")
                    }
                    else->{}
                }

                holder.etInput.setOnEditorActionListener { _, actionId, _ ->
                    if(actionId == EditorInfo.IME_ACTION_DONE){
                        holder.btnSubmit.performClick()
                    }
                    true
                }

                holder.tvTitle.text = strTxt
                holder.btnSubmit.setOnClickListener {
                    processFunc(item.funcType, holder.etInput.text.toString())
                    holder.etInput.clearFocus()
                    holder.etInput.text.clear()
                    notifyDataSetChanged()
                }
            }
            else -> {
                if (holder !is TruckListItemHolderHeader) return

                holder.tvTitle.visibility = View.GONE
            }
        }
    }

    private fun processFunc(funcType: TruckFuncType, value: String) {
        when(funcType) {

            TruckFuncType.HEIGHT -> {
                val nValue = value.toIntOrNull()
                if(nValue != null){
                    INaviController.setCarHeight(nValue)
                }
            }

            TruckFuncType.WEIGHT -> {
                val nValue = value.toIntOrNull()
                if(nValue != null){
                    INaviController.setCarWeight(nValue)
                }
            }

            TruckFuncType.UTURN -> {
                val nValue = value.toIntOrNull()
                if(nValue != null){
                    INaviController.setAvoidUturn(nValue)
                }
            }

            TruckFuncType.NARROW -> {
                val nValue = value.toIntOrNull()
                if(nValue != null){
                    INaviController.setAvoidNarrowRoad(nValue)
                }
            }

            TruckFuncType.RESTRICTION -> {
                val bVal = value.toBoolean()
                INaviController.setAvoidRestriction(bVal)
            }

            TruckFuncType.EVASION_LIMIT -> {
                val bVal = value.toBoolean()
                INaviController.setAvoidEvasion(bVal)
            }

            TruckFuncType.GETTRUCKINFO -> {

            }

            TruckFuncType.SETTRUCK -> {
                val bVal = value.toBoolean()
                INaviController.setTruck(bVal)
            }

            TruckFuncType.WARNING_HEIGHT -> {

                var settings = INaviController.getGuidanceSettings().toMutableList()

                if(settings.contains(GUIDANCESETTING.HEIGHT_LIMIT)){
                    settings.remove(GUIDANCESETTING.HEIGHT_LIMIT)
                }else{
                    settings.add(GUIDANCESETTING.HEIGHT_LIMIT)
                }

                INaviController.setGuidanceSettings(settings)
            }

            TruckFuncType.TRUCK_TYPE -> {
                INaviController.setCarType(CARTYPE.TYPE_1)
            }

            TruckFuncType.SHOW_EXTEND_VIEW ->{
                INaviController.setVisibleExtendView(!INaviController.getVisibleExtendView())
            }
            else->{}
        }
    }
}
