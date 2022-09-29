package com.ahoy.data

import FindContriesOfAContinentQuery
import GetContinentsQuery
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.Rx3Apollo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ApolloClientImpl @Inject constructor(val apolloClient: com.apollographql.apollo.ApolloClient):
    ApolloClient {
    override fun getContinents(): Observable<Response<GetContinentsQuery.Data>> {

        val call = apolloClient.query(GetContinentsQuery())

        return Rx3Apollo.from(call)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter { it -> it.data != null }
    }

    override fun getCountriesOfCotinent(code: String): Observable<Response<FindContriesOfAContinentQuery.Data>> {
        val call = apolloClient.query(FindContriesOfAContinentQuery(code))

        return Rx3Apollo.from(call)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter { it -> it.data != null }
    }
}