package com.ilsangtech.ilsang.core.data.coupon

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ilsangtech.ilsang.core.network.api.CouponApiService
import com.ilsangtech.ilsang.core.network.model.coupon.CouponDetailNetworkModel

class CouponPagingSource(
    private val couponApiService: CouponApiService
) : PagingSource<Int, CouponDetailNetworkModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CouponDetailNetworkModel> {
        return try {
            val pageNumber = params.key ?: 0
            val response = couponApiService.getCouponList(
                page = pageNumber,
                size = params.loadSize
            )

            val prevKey = if (pageNumber == 0) null else pageNumber - 1
            val nextKey = if (response.isLast) null else pageNumber + 1

            LoadResult.Page(
                data = response.content,
                prevKey = prevKey,
                nextKey = nextKey
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CouponDetailNetworkModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}