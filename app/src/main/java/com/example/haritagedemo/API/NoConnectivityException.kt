package com.example.haritagedemo.API

import java.io.IOException

class NoConnectivityException:IOException() {

    override fun getLocalizedMessage(): String? {
        return "No Connectivity Exception"
    }
}