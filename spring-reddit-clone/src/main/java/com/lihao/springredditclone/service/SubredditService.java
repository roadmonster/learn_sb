package com.lihao.springredditclone.service;

import com.lihao.springredditclone.Repository.SubredditRepository;
import com.lihao.springredditclone.dto.SubredditDto;
import com.lihao.springredditclone.exception.SpringRedditException;
import com.lihao.springredditclone.mapper.SubredditMapper;
import com.lihao.springredditclone.model.Subreddit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto){
        Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll(){
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mypSubredditToDto)
                .collect(Collectors.toList());
    }

    public SubredditDto getSubreddit(Long id){
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException(
                        "No subreddit found with ID: " + id));
        return subredditMapper.mypSubredditToDto(subreddit);
    }
}
