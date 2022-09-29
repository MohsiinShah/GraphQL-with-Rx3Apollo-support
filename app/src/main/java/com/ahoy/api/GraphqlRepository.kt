package com.ahoy.api

import FindContriesOfAContinentQuery
import GetContinentsQuery
import com.apollographql.apollo.api.Response
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GraphqlRepository @Inject constructor(val client: com.ahoy.data.ApolloClient) {

    fun getContinents(): Observable<Response<GetContinentsQuery.Data>> {
        return client.getContinents()
    }

    fun getCountriesOfAContinent(code: String): Observable<Response<FindContriesOfAContinentQuery.Data>> {
        return client.getCountriesOfCotinent(code)
    }
}