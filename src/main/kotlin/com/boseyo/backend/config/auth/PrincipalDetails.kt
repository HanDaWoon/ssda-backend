package com.boseyo.backend.config.auth

import com.boseyo.backend.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class PrincipalDetails(user: UserEntity?) : UserDetails {
    private val user: UserEntity?

    init {
        this.user = user!!
    }

    fun setPasswd(passwd: String) {
        user!!.password = passwd
    }

    fun setRole(role: String) {
        user!!.roles = role
    }

    fun getUser(): UserEntity {
        return user!!
    }

    override fun getPassword(): String {
        return user!!.password
    }

    override fun getUsername(): String {
        return user!!.username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        val authorities: MutableCollection<GrantedAuthority> = ArrayList()
        user!!.getRoleList().forEach { authorities.add(GrantedAuthority { it[0] }) }
        return authorities
    }
}