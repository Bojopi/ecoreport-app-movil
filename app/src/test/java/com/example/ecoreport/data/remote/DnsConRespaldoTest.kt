package com.example.ecoreport.data.remote

import okhttp3.Dns
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import java.net.InetAddress
import java.net.UnknownHostException

class DnsConRespaldoTest {
    private val dominio = "example.mockapi.io"
    private val direccion = listOf(InetAddress.getByAddress(byteArrayOf(127, 0, 0, 1)))

    @Test fun usaSistemaCuandoFunciona() {
        var usoRespaldo = false
        val dns = DnsConRespaldo(dominio, resolver { usoRespaldo = true; direccion }, resolver { direccion })
        assertEquals(direccion, dns.lookup(dominio))
        assertFalse(usoRespaldo)
    }

    @Test fun usaRespaldoCuandoFallaElDominioApi() {
        val dns = DnsConRespaldo(dominio, resolver { direccion }, resolver { throw UnknownHostException() })
        assertEquals(direccion, dns.lookup(dominio))
    }

    @Test(expected = UnknownHostException::class)
    fun noConsultaOtrosDominiosEnElRespaldo() {
        val dns = DnsConRespaldo(dominio, resolver { error("No debe consultar el respaldo") }, resolver { throw UnknownHostException() })
        dns.lookup("otro.example")
    }

    @Test(expected = UnknownHostException::class)
    fun propagaFalloSiElRespaldoTampocoFunciona() {
        val dns = DnsConRespaldo(dominio, resolver { throw UnknownHostException() }, resolver { throw UnknownHostException() })
        dns.lookup(dominio)
    }

    private fun resolver(lookup: () -> List<InetAddress>) = object : Dns {
        override fun lookup(hostname: String) = lookup()
    }
}
