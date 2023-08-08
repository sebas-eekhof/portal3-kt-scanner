package com.jsmecommerce.portal3scanner.stores

import org.reduxkotlin.Reducer
import org.reduxkotlin.StoreSubscriber
import org.reduxkotlin.StoreSubscription
import org.reduxkotlin.createThreadSafeStore

val DEFAULT_TAB = NavigationStoreState.TabType.SETTINGS

class NavigationStoreState(val tab: TabType, val historyMem: HashMap<TabType, ArrayList<String>>) {
    val history: ArrayList<String> = if (this.historyMem.containsKey(tab)) this.historyMem[tab]!! else ArrayList()
    val currentPage: String? = history.lastOrNull()

    data class SET_TAB(val tab: TabType)
    data class NAVIGATE(val page: String)
    class BACK

    enum class TabType {
        DASHBOARD,
        ORDERS,
        SETTINGS
    }
}

val reducer: Reducer<NavigationStoreState> = { state, action ->
    when(action) {
        is NavigationStoreState.SET_TAB -> NavigationStoreState(action.tab, state.historyMem)
        is NavigationStoreState.NAVIGATE -> {
            val navlist = state.history
            navlist.add(action.page)
            val navmap = state.historyMem
            navmap[state.tab] = navlist
            NavigationStoreState(state.tab, navmap)
        }
        is NavigationStoreState.BACK -> {
            val navlist = state.history
            if(navlist.isEmpty()) state else {
                navlist.removeAt(navlist.lastIndex)
                val navmap = state.historyMem
                navmap[state.tab] = navlist
                NavigationStoreState(state.tab, navmap)
            }
        }
        else -> state
    }
}

val store = createThreadSafeStore(
    reducer = reducer,
    preloadedState = NavigationStoreState(DEFAULT_TAB, HashMap())
)

class NavigationStore {
    fun getState() = store.getState()
    fun subscribe(subscriber: StoreSubscriber): StoreSubscription = store.subscribe(subscriber)
    fun setTab(tab: NavigationStoreState.TabType) = store.dispatch(NavigationStoreState.SET_TAB(tab))
    fun navigate(page: String) = store.dispatch(NavigationStoreState.NAVIGATE(page))
    fun back() = store.dispatch(NavigationStoreState.BACK())
}