package com.ahoy.data

import FindContriesOfAContinentQuery
import com.apollographql.apollo.api.Response
import io.reactivex.rxjava3.core.Observable

interface ApolloClient {

    fun getContinents(): Observable<Response<GetContinentsQuery.Data>>

    fun getCountriesOfCotinent(code: String): Observable<Response<FindContriesOfAContinentQuery.Data>>
}