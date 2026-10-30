package com.example.ecoreport.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.dnsoverhttps.DnsOverHttps
import java.net.InetAddress
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL =
        "https://6ae4fb0228393034b08e10be.mockapi.io/"

    val api:
            ReporteApiService by lazy {

        val clienteDns = OkHttpClient.Builder()
            .callTimeout(8, TimeUnit.SECONDS)
            .build()
        val dnsHttps = DnsOverHttps.Builder()
            .client(clienteDns)
            .url("https://dns.google/dns-query".toHttpUrl())
            // Direcciones del resolvedor; las IP de MockAPI se consultan dinamicamente.
            .bootstrapDnsHosts(
                InetAddress.getByAddress(byteArrayOf(8, 8, 8, 8)),
                InetAddress.getByAddress(byteArrayOf(8, 8, 4, 4))
            )
            .includeIPv6(false)
            .build()
        val clienteApi = OkHttpClient.Builder()
            .dns(DnsConRespaldo(BASE_URL.toHttpUrl().host, dnsHttps))
            .build()

        Retrofit.Builder()
            .client(clienteApi)
            .baseUrl(
                BASE_URL
            )
            .addConverterFactory(
                GsonConverterFactory
                    .create()
            )
            .build()
            .create(
                ReporteApiService::class.java
            )
    }
}
