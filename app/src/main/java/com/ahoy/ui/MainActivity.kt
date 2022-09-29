package com.ahoy.ui

import FindContriesOfAContinentQuery
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahoy.apollographql.R
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.logging.Logger


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var activity: MainActivity
    private val BASE_URL = "https://countries.trevorblades.com"
    private lateinit var client: ApolloClient

    lateinit var countriesRV: RecyclerView
    lateinit var continentSP: Spinner

    companion object {
        val LOG: Logger = Logger.getLogger(MainActivity::class.java.name)
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        setContentView(R.layout.activity_main)
        countriesRV = findViewById(R.id.countries_view)
        continentSP = findViewById(R.id.continent_spinner)
        countriesRV.layoutManager = LinearLayoutManager(activity)
        client = setUpApolloClient()

        viewModel.getContinents()

        viewModel.continents.observe(this, Observer {
           val continents = it.data!!.continents()

            continentSP.adapter = ContinentAdapter(
                this@MainActivity,
                R.layout.continent_row, continents
            )

            continentSP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    viewModel.getCountriesOfContinent(continents[p2].code())
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        })

        viewModel.countries.observe(this) {
            countriesRV.adapter = CountriesAdapter(it.data)

        }

    }

    private fun callForCountriesList(code: String) {
        client.query(
            FindContriesOfAContinentQuery
                .builder()
                .code(code?.let { code }.toString())
                .build()
        )
            .enqueue(object : ApolloCall.Callback<FindContriesOfAContinentQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    LOG.info(e.message.toString())
                }

                override fun onResponse(response: Response<FindContriesOfAContinentQuery.Data>) {
                    runOnUiThread {
                        countriesRV.adapter = CountriesAdapter(response.data())
                    }
                }

            })
    }

    /**
     * Basic set up for graphql API, OkHttp is used for graphql with apollo client
     */
    private fun setUpApolloClient(): ApolloClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttp = OkHttpClient
            .Builder()
            .addInterceptor(logging)
        return ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttp.build())
            .build()
    }

}