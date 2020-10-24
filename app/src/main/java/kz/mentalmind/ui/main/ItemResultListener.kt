package kz.mentalmind.ui.main

import kz.mentalmind.data.CollectionResult

interface ItemResultListener {
    fun onItemClickedResult(collections: CollectionResult)
}