package com.example.ecoreport.data.remote

import okhttp3.Dns
import java.net.InetAddress
import java.net.UnknownHostException

/** Usa el respaldo solo para el dominio publico de nuestra API. */
internal class DnsConRespaldo(
    private val dominioApi: String,
    private val respaldo: Dns,
    private val sistema: Dns = Dns.SYSTEM
) : Dns {
    override fun lookup(hostname: String): List<InetAddress> {
        return try {
            sistema.lookup(hostname)
        } catch (error: UnknownHostException) {
            if (!hostname.equals(dominioApi, ignoreCase = true)) throw error
            respaldo.lookup(hostname)
        }
    }
}
