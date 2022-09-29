package com.ahoy.ui

import FindContriesOfAContinentQuery
import GetContinentsQuery
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahoy.api.GraphqlRepository
import com.apollographql.apollo.api.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.observers.DisposableObserver
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(val repository: GraphqlRepository) : ViewModel() {

    val continents = MutableLiveData<Response<GetContinentsQuery.Data>>()
    val countries = MutableLiveData<Response<FindContriesOfAContinentQuery.Data>>()

    fun getContinents() {

        repository.getContinents()
            .subscribeWith(object : DisposableObserver<Response<GetContinentsQuery.Data>>() {
                override fun onNext(t: Response<GetContinentsQuery.Data>) {
                    continents.postValue(t)
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }


            })

    }

    fun getCountriesOfContinent(code: String) {

        repository.getCountriesOfAContinent(code)
            .subscribeWith(object : DisposableObserver<Response<FindContriesOfAContinentQuery.Data>>() {
                override fun onNext(t: Response<FindContriesOfAContinentQuery.Data>) {
                    countries.postValue(t)
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }


            })

    }

}