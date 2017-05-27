package org.irreprimivel.montao.api.subscription.entity

import com.fasterxml.jackson.annotation.JsonFilter
import org.irreprimivel.montao.api.community.entity.Community
import org.irreprimivel.montao.api.user.User
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "subscriptions")
@JsonFilter("subscription")
data class Subscription(@Id
                        @GeneratedValue(strategy = GenerationType.AUTO)
                        val id: Long?,

                        @NotNull
                        @ManyToOne
                        @JoinColumn(name = "community_id", nullable = false)
                        val community: Community,

                        @NotNull
                        @ManyToOne
                        @JoinColumn(name = "user_id", nullable = false)
                        val user: User
)