package org.irreprimivel.montao.api.channel.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import org.hibernate.validator.constraints.NotEmpty
import org.irreprimivel.montao.api.community.entity.Community
import org.irreprimivel.montao.api.message.entity.Message
import javax.persistence.*

@Entity
@Table(name = "channels")
data class Channel(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,

        @Column(name = "title", nullable = false)
        @NotEmpty
        val title: String,

        @Column(name = "description")
        val description: String,

        @JsonBackReference
        @ManyToOne
        @JoinColumn(name = "community_id")
        val community: Community,

        @JsonManagedReference
        @OneToMany(mappedBy = "channel", fetch = FetchType.EAGER)
        val messages: List<Message>)
