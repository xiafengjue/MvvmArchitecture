package com.sora.mvvmarchitecture

import com.sora.mvvmarchitecture.account.IAccount

class NetConfig {
    companion object {
        private var iAccount: IAccount? = null
            get() = field

        fun initAccount(account: IAccount) {
            this.iAccount = account
        }
    }
}