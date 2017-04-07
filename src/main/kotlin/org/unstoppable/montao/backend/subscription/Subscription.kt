package org.unstoppable.montao.backend.subscription

import org.unstoppable.montao.backend.community.entity.Community
import org.unstoppable.montao.backend.user.User
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "subscriptions")
data class Subscription(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long,

        @NotNull
        @ManyToOne
        @JoinColumn(name = "community_id")
        val community: Community,

        @NotNull
        @ManyToOne
        @JoinColumn(name = "user_id")
        val user: User
)