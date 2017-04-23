package org.irreprimivel.montao.api.community.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import org.hibernate.validator.constraints.NotEmpty
import org.irreprimivel.montao.api.channel.entity.Channel
import org.irreprimivel.montao.api.user.User
import java.math.BigInteger
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "communities")
data class Community(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,

        @Column(name = "title", nullable = false, unique = true)
        @NotEmpty
        @Size(min = 4)
        val title: String? = null,

        @Column(name = "description")
        val description: String? = null,

        @Column(name = "creation_date", nullable = false)
        val creationDate: LocalDate? = null,

        @ManyToOne
        @JoinColumn(name = "founder_id")
        val founder: User? = null,

        @Column(name = "is_visible", nullable = false)
        @NotNull
        val isVisible: Boolean? = null,

        @JsonManagedReference
        @OneToMany(mappedBy = "community", fetch = FetchType.EAGER)
        val channels: List<Channel>? = null)