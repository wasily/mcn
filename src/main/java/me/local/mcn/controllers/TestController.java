package me.local.mcn.controllers;

import lombok.RequiredArgsConstructor;
import me.local.mcn.domain.Movie;
import me.local.mcn.repositories.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final MovieRepository movieRepository;

    @GetMapping(path = "/")
    public String test() {
        return "test";
    }

    @GetMapping(path = "/movie/{id}")
    public ResponseEntity<Movie> findMovieById(@PathVariable(name = "id") String id) {
        return new ResponseEntity<>(movieRepository.findByImdbId(id), HttpStatus.OK);
    }
}
