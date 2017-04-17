package org.irreprimivel.montao.api.message.entity

import com.fasterxml.jackson.annotation.JsonFormat
import org.irreprimivel.montao.api.channel.entity.Channel
import org.irreprimivel.montao.api.user.User
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "messages")
data class Message(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,

        @NotNull
        @Column(name = "uuid", nullable = false, unique = true)
        val uuid: String? = null,

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        val user: User? = null,

        @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
        @Column(name = "received_time", nullable = false)
        val receivedTime: LocalDateTime? = null,

        @Column(name = "text", nullable = false)
        val text: String? = null,

        @ManyToOne
        @JoinColumn(name = "channel_id")
        val channel: Channel? = null)