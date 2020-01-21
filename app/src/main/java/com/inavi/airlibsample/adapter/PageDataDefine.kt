package com.inavi.airlibsample.adapter

/**
 * Created by J.W. Park on 2019-10-14
 */


enum class PageTitle(val value: String) {
    MAP("지도 컨트롤"),
    SEARCH("통합 검색"),
    ROUTE("경로 탐색")
}

enum class BasicListType(val value: Int) {
    HEADER(0),
    ITEM(1),
    NONE(2)
}

data class BasicListItem (
    var listType: BasicListType = BasicListType.NONE,
    var funcType: FuncMap = FuncMap.NONE,
    var mainText: String = ""
)

enum class SearchListType(val value: Int) {
    HEADER(0),
    QUERY(1),
    RESULT(2),
    RECOMMEND(3),
    NONE(4)
}
data class SearchListItem (
    var listType: SearchListType = SearchListType.NONE,
    var mainText: String = "",
    var subText: String = "",
    var subSubText: String = "",
    var dpLat: Double = 0.0,
    var dpLon: Double = 0.0,
    var rpLat: Double = 0.0,
    var rpLon: Double = 0.0
)

data class RouteItem (
    var name: String = "",
    var rpLat: Double = 0.0,
    var rpLon: Double = 0.0
)

enum class RouteListType(val value: Int) {
    HEADER(0),
    ROUTE(1),
    RESULT(2),
    GUIDANCE(3),
    ROUTECANCEL(4),
    NONE(5)
}

data class RouteListItem (
    var listType: RouteListType = RouteListType.NONE,
    var mainText: String = "",
    var optName1: String = "",
    var dist1: String = "",
    var time1: String = "",
    var fee1: String = "",

    var optName2: String = "",
    var dist2: String = "",
    var time2: String = "",
    var fee2: String = "",

    var optName3: String = "",
    var dist3: String = "",
    var time3: String = "",
    var fee3: String = ""
)

