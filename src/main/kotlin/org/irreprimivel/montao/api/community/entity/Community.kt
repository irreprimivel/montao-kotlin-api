package org.irreprimivel.montao.api.community.entity

import com.fasterxml.jackson.annotation.JsonFilter
import org.hibernate.validator.constraints.NotEmpty
import org.irreprimivel.montao.api.user.User
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "communities")
@JsonFilter("community")
data class Community(@Id
                     @GeneratedValue(strategy = GenerationType.AUTO)
                     val id: Long?,

                     @Column(name = "title", nullable = false, unique = true)
                     @NotEmpty
                     @Size(min = 4)
                     val title: String,

                     @Column(name = "description")
                     val description: String?,

                     @Column(name = "creation_date", nullable = false)
                     val creationDate: LocalDate,

                     @ManyToOne
                     @JoinColumn(name = "founder_id")
                     val founder: User,

                     @Column(name = "is_visible", nullable = false)
                     @NotNull
                     val isVisible: Boolean

        /* @JsonManagedReference
         @OneToMany(mappedBy = "community", fetch = FetchType.EAGER)
         val channels: List<Channel>? = null*/
)