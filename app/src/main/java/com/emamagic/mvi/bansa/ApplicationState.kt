package com.emamagic.mvi.bansa

data class ApplicationState(val isRefreshing: Boolean = true,
                            val isFetching: Boolean = false,
                            val gifs: List<Gif> = arrayListOf(),
                            val pagination: NextPage = NextPage(),
                            val orientation: Int = Configuration.ORIENTATION_UNDEFINED)