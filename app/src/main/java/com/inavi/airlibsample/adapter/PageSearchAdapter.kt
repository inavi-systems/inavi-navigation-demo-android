package com.inavi.airlibsample.adapter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.inavi.airlibsample.R
import com.inaviair.sdk.*
import kotlinx.android.synthetic.main.row_contents_basic.view.clRowMain
import kotlinx.android.synthetic.main.row_contents_basic.view.tvContents
import kotlinx.android.synthetic.main.row_contents_search.view.*
import kotlinx.android.synthetic.main.row_header.view.*
import kotlinx.android.synthetic.main.row_query_input.view.*

/**
 * Created by J.W. Park on 2019-10-16
 */

enum class SearchCode(val value: Int) {
    SUCCESS(0),
    FAIL(1),
    RECOMMEND(2),
    RECOMMENDFAIL(3),
    RECOMMENDSELECT(4)
}

abstract class SearchListItemHolder(view: View): RecyclerView.ViewHolder(view)
class SearchListItemHolderHeader(view: LinearLayout): SearchListItemHolder(view) {
    var tvTitle: TextView = view.tvRowTitle
}
class SearchListItemHolderQuery(view: ConstraintLayout): SearchListItemHolder(view) {
    var etKeyword: EditText = view.etKeyword
    var btnSearch: Button = view.btnSearch
}
class SearchListItemHolderItem(view: ConstraintLayout): SearchListItemHolder(view) {
    var clRowMain: ConstraintLayout = view.clRowMain
    var tvContents: TextView = view.tvContents
    var tvSubContents: TextView = view.tvSubContents
    var tvSubSubContents: TextView = view.tvSubSubContents
    var btnGoal: Button = view.btnGoal
}
class SearchListItemHolderRecommend(view: ConstraintLayout): SearchListItemHolder(view) {
    var clRowMain: ConstraintLayout = view.clRowMain
    var tvContents: TextView = view.tvContents
}

class PageSearchAdapter(private var list: List<SearchListItem>, private var context: Context, private var handler: Handler) : RecyclerView.Adapter<SearchListItemHolder>() {


    //private var mMapOverlay: MapOverlay? = null
    //private var mMapIcon: MapIcon? = null

    private var mLastQurety = ""
    private var mSearchIng = false
    override fun getItemCount(): Int {
        return list.size
    }
    override fun getItemViewType(position: Int): Int {
        val item = list.getOrNull(position)?: return SearchListType.NONE.value
        return item.listType.value
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListItemHolder {
        return when(viewType){
            SearchListType.HEADER.value -> {
                val cell = LayoutInflater.from(parent.context).inflate(R.layout.row_header, parent, false) as LinearLayout
                SearchListItemHolderHeader(cell)
            }
            SearchListType.QUERY.value -> {
                val cell = LayoutInflater.from(parent.context).inflate(R.layout.row_query_input, parent, false) as ConstraintLayout
                SearchListItemHolderQuery(cell)
            }
            SearchListType.RESULT.value -> {
                val cell = LayoutInflater.from(parent.context).inflate(R.layout.row_contents_search, parent, false) as ConstraintLayout
                SearchListItemHolderItem(cell)
            }
            SearchListType.RECOMMEND.value -> {
                val cell = LayoutInflater.from(parent.context).inflate(R.layout.row_contents_basic, parent, false) as ConstraintLayout
                SearchListItemHolderRecommend(cell)
            }
            else -> {
                val cell = LayoutInflater.from(parent.context).inflate(R.layout.row_header, parent, false) as LinearLayout
                SearchListItemHolderHeader(cell)
            }
        }
    }
    override fun onBindViewHolder(holder: SearchListItemHolder, position: Int) {
        when (getItemViewType(position)) {
            SearchListType.HEADER.value -> {
                if (holder !is SearchListItemHolderHeader) return

                val item = list.getOrNull(position) ?: return
                holder.tvTitle.text = item.mainText
            }
            SearchListType.QUERY.value -> {
                if (holder !is SearchListItemHolderQuery) return

                val item = list.getOrNull(position) ?: return
                if( !item.mainText.isNullOrEmpty() ) {
                    holder.etKeyword.setText(item.mainText)
                    holder.etKeyword.selectAll()
                }

                holder.etKeyword.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }

                    override fun afterTextChanged(s: Editable?) {

                        var query = s?.toString()?: return

                        if( mLastQurety.equals(query, true)) return
                        if( query.equals("검색중입니다.", true)) return

                        INaviController.runRecommendWord(query, object: OnRecommendWordListener {
                            override fun onSuccess(result: ArrayList<RecommendWord>) {

                                var msg = Message.obtain()
                                msg.what = SearchCode.RECOMMEND.value
                                msg.obj = result
                                mHandler.sendMessage(msg)
                            }
                            override fun onFail(errCode: Int, errMsg: String) {
                                var msg = Message.obtain()
                                msg.what = SearchCode.RECOMMENDFAIL.value
                                msg.arg1 = errCode
                                msg.obj = errMsg
                                mHandler.sendMessage(msg)
                            }
                        })
                    }

                })



                holder.btnSearch.setOnClickListener {
                    if( mSearchIng ) return@setOnClickListener

                    mSearchIng = true

                    var query = holder.etKeyword.text.toString()
                    holder.etKeyword.setText("검색중입니다.")

                    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    if( imm.isAcceptingText) {
                        imm.hideSoftInputFromWindow(holder.etKeyword.windowToken, 0)
                    }

                    mLastQurety = query
                    var curLoc = INaviController.getCurrentPos()
                    INaviController.runSearch(query, curLoc.lat, curLoc.lon, object : OnSearchListener{
                        override fun onSuccess(result: SearchResult) {
                            var msg = Message.obtain()
                            msg.what = SearchCode.SUCCESS.value
                            msg.obj = result
                            mHandler.sendMessage(msg)
                            mSearchIng = false
                        }
                        override fun onFail(errCode: Int, errMsg: String) {
                            var msg = Message.obtain()
                            msg.what = SearchCode.FAIL.value
                            msg.arg1 = errCode
                            msg.obj = errMsg
                            mHandler.sendMessage(msg)
                            mSearchIng = false
                        }
                    })
                }
            }
            SearchListType.RESULT.value -> {
                if (holder !is SearchListItemHolderItem) return

                val item = list.getOrNull(position) ?: return
                holder.tvContents.text = item.mainText
                holder.tvSubContents.text = item.subText
                holder.tvSubSubContents.text = item.subSubText

                holder.clRowMain.setOnClickListener {
                    if( item.dpLat <=0 || item.dpLon <=0)
                        return@setOnClickListener
                    INaviController.setMapPostion(item.dpLat, item.dpLon, 0.0)

                    if( PageDataStore.overlayRoute == null )
                        PageDataStore.overlayRoute = INaviController.createMapOverlay()

                    PageDataStore.overlayRoute?.let { overlay ->
                        PageDataStore.mapIconPin?.let { prevIcon ->
                            INaviController.removeMapIcon(overlay, prevIcon)
                        }
                    }
                    var markerIcon = INaviController.createMapIcon(item.dpLat, item.dpLon, R.drawable.icon_sample_normal, ICONGRAVITY.CENTER_TOP)?: return@setOnClickListener

                    PageDataStore.overlayRoute?.let { overlay ->
                        PageDataStore.mapIconPin = INaviController.addMapIcon(overlay, markerIcon)
                    }

                }
                holder.btnGoal.setOnClickListener {

                    if( item.rpLat <=0 || item.rpLon <=0)
                        return@setOnClickListener
                    INaviController.setMapPostion(item.rpLat, item.rpLon, 0.0)

                    if( PageDataStore.overlayRoute == null )
                        PageDataStore.overlayRoute = INaviController.createMapOverlay()

                    PageDataStore.overlayRoute?.let { overlay ->
                        PageDataStore.mapIconGoal?.let { prevGoalIcon ->
                            INaviController.removeMapIcon(overlay, prevGoalIcon)
                        }
                        PageDataStore.mapIconPin?.let { prevPinIcon ->
                            INaviController.removeMapIcon(overlay, prevPinIcon)
                        }

                        //INaviController.removeMapIconALL(overlay)
                    }


                    var goalIcon = INaviController.createMapIcon(item.rpLat, item.rpLon, R.drawable.icon_sample_goal, ICONGRAVITY.CENTER_TOP)?: return@setOnClickListener

                    PageDataStore.overlayRoute?.let { overlay ->
                        PageDataStore.mapIconGoal = INaviController.addMapIcon(overlay, goalIcon)
                    }

                    PageDataStore.goalPoint = RoutePtItem(item.mainText, item.rpLat, item.rpLon, item.dpLat, item.dpLon)

                    handler.sendEmptyMessage(2)//탐색 페이지로 이동
                }
            }
            SearchListType.RECOMMEND.value -> {
                if (holder !is SearchListItemHolderRecommend) return

                val item = list.getOrNull(position) ?: return
                holder.tvContents.text = item.mainText

                holder.clRowMain.setOnClickListener {
                    var msg = Message.obtain()
                    msg.what = SearchCode.RECOMMENDSELECT.value
                    msg.obj = item.mainText
                    mHandler.sendMessage(msg)
                }
            }

            else -> {
                if (holder !is SearchListItemHolderHeader) return
                holder.tvTitle.visibility = View.GONE
            }
        }
    }

    fun updateList(temp: List<SearchListItem>){
        list = temp
        notifyDataSetChanged()
    }


    private val mHandler = object: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            //super.handleMessage(msg)
            msg?: return

            when(msg.what) {
                SearchCode.SUCCESS.value -> {
                    var listItems = mutableListOf<SearchListItem>()
                    listItems.add(SearchListItem(SearchListType.HEADER, PageTitle.SEARCH.value))
                    msg.obj?.let {
                        var result = msg.obj as SearchResult
                        listItems.add(SearchListItem(SearchListType.QUERY, result.query))
                        result.items?.forEach {
                            listItems.add(SearchListItem(SearchListType.RESULT, it.mainTitle, it.addrRoad, it.addrJibun, it.dpLat, it.dpLon, it.rpLat, it.rpLon))
                        }
                    }
                    updateList(listItems)
                }
                SearchCode.FAIL.value -> {
                    var listItems = mutableListOf<SearchListItem>()
                    listItems.add(SearchListItem(SearchListType.HEADER, PageTitle.SEARCH.value))
                    listItems.add(SearchListItem(SearchListType.QUERY, "검색 실패"))
                    listItems.add(SearchListItem(SearchListType.RESULT, msg.obj as String, msg.arg1.toString()))
                    updateList(listItems)
                }

                SearchCode.RECOMMEND.value -> {

                    var listItems = mutableListOf<SearchListItem>()
                    listItems.add(SearchListItem(SearchListType.HEADER, PageTitle.SEARCH.value))
                    listItems.add(SearchListItem(SearchListType.QUERY, ""))
                    var recList = msg.obj as ArrayList<RecommendWord>
                    recList.forEach {
                        listItems.add(SearchListItem(SearchListType.RECOMMEND, it.recommendWord))
                    }
                    updateList(listItems)
                }

                SearchCode.RECOMMENDFAIL.value -> {
                    var listItems = mutableListOf<SearchListItem>()
                    listItems.add(SearchListItem(SearchListType.HEADER, PageTitle.SEARCH.value))
                    listItems.add(SearchListItem(SearchListType.QUERY, ""))
                    updateList(listItems)
                }
                SearchCode.RECOMMENDSELECT.value -> {
                    var listItems = mutableListOf<SearchListItem>()
                    listItems.add(SearchListItem(SearchListType.HEADER, PageTitle.SEARCH.value))
                    listItems.add(SearchListItem(SearchListType.QUERY, msg.obj as String))
                    updateList(listItems)
                }


            }
        }
    }
}