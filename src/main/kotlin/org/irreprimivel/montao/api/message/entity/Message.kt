package org.irreprimivel.montao.api.message.entity

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonFormat
import org.irreprimivel.montao.api.channel.entity.Channel
import org.irreprimivel.montao.api.user.User
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "messages")
@JsonFilter("message")
data class Message(@Id
                   @GeneratedValue(strategy = GenerationType.AUTO)
                   val id: Long?,

                   @NotNull
                   @Column(name = "uuid", nullable = false, unique = true)
                   val uuid: String,

                   @ManyToOne
                   @JoinColumn(name = "user_id", nullable = false)
                   val user: User,

                   @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
                   @Column(name = "received_time", nullable = false)
                   val receivedTime: LocalDateTime,

                   @Column(name = "text", nullable = false)
                   val text: String,

                   @ManyToOne
                   @JoinColumn(name = "channel_id")
                   val channel: Channel
)