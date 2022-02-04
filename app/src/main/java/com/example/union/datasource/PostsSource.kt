package com.example.union.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.union.datasource.repository.MainRepository
import com.example.union.model.PostList
import javax.inject.Inject

class PostSource @Inject constructor(val mainRepository: MainRepository) :
    PagingSource<Int, PostList>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostList> {
        return try{
            val page = params.key ?: 1
            val postResponse = mainRepository.getPostData(page)
            LoadResult.Page(
                data = postResponse.PostList,
                prevKey = if (page == 1) null else -1,
                nextKey = page.plus(1)
            )
        }catch (e :Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PostList>): Int? {
        return state.anchorPosition
    }

}