package com.lihao.springredditclone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Subreddit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Community Name required")
    private String communityName;

    @NotBlank(message = "Descriptions required")
    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts;

    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser appUser;
}
