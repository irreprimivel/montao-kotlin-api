package org.unstoppable.montao.backend.channel.entity

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "channels")
data class Channel(
        val id: Long)
